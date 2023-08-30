var test = localStorage.loggedin;
if (test != "true") {
    window.location.replace("login.php");
}
if (localStorage.locked == "true") {
    window.location.replace("lock_screen.php");
}
var config = {
    apiKey: "AIzaSyD0CsnZgDBGSFBVrtZH0HRBbVQWk__txe0",
    authDomain: "ezbio-98be1.firebaseapp.com",
    databaseURL: "https://ezbio-98be1.firebaseio.com",
    storageBucket: "ezbio-98be1.appspot.com",
    messagingSenderId: "595946963410"
};
firebase.initializeApp(config);
var student_keys = new Array();
var columns = new Array();
var quizzes = new Array();
$(document).ready(function() {
    $("#teachername").html(localStorage.teacher_name);
    $('#tbl_assess').dataTable();
    firebase.database().ref('classes').orderByKey().once('value').then(function(snapshot) {
        snapshot.forEach(function(childSnapshot) {
            var section = childSnapshot.val().grp_name;
            var year = childSnapshot.val().grp_year;
            var gteacher = childSnapshot.val().grp_teacher;
            var gteacherclass = childSnapshot.val().grp_subject;
            if (gteacher == localStorage.teacher) {
                $('#cmb_class').append("<option value = '" + section + "' data-year = '" + year + "' data-code = '" + childSnapshot.key + "'> " + section + "(" + year + ")" + "</option>")
            }
        });
    }).then(function() {
        if (typeof GetParameterValues("sec") !== 'undefined') { //WORKS LIKE THE ISSET FUNCTION IN PHP
            var decrypted = CryptoJS.AES.decrypt(GetParameterValues("sec"), "Secret Passphrase").toString(CryptoJS.enc.Utf8);
            $("#cmb_class option[value=\"" + decrypted + "\"]").attr('selected', 'selected');
        }
    }).then(function() {
        canc();
    });
    $(this).on("change", "#cmb_class", function() {
        canc();
    });
    if (typeof GetParameterValues("approved") !== 'undefined') {
        if (GetParameterValues("approved") == "true") {
            var dt = new Date();
            var added = dt.toUTCString();
            var oldRef = firebase.database().ref("classes/" + GetParameterValues("group") + "/pending_members/" + GetParameterValues("key"));
            var newRef = firebase.database().ref("classes/" + GetParameterValues("group") + "/member/" + GetParameterValues("key"));
            newRef.set({
                date_joined: added
            }).then(function() {
                oldRef.remove();
                alert("Approved");
                window.location.replace("assessment.php?sec=" + CryptoJS.AES.encrypt($("#cmb_class").val(), "Secret Passphrase"));
            });
        } else {
            alert("Rejected");
        }
    }
});
function canc() {
    $('#tbl_assess').DataTable().clear().draw();
    var cansy = $("#cmb_class option:selected").data('code');
    firebase.database().ref('classes/'+cansy+"/member").orderByKey().once('value').then(function(snapshot) {
        snapshot.forEach(function(childSnapshot) {
            firebase.database().ref("members/" + childSnapshot.key).orderByKey().once('value').then(function(snapshot) {
                var email = snapshot.val().email;
                var firstname = snapshot.val().firstname;
                var lastname = snapshot.val().lastname;
                $('#tbl_assess').DataTable().row.add(["<a href = 'studassessment.php?key=" + childSnapshot.key + "&sectionkey=" + cansy + "'>View Assessment</a>", firstname, lastname, email]).draw();
            });
        });
    }).then(function(){
        pendingmembers();
    });
};

function pendingmembers() {
    $('#tbl_pending').DataTable().clear().draw();
    /*firebase.database().ref('classes').orderByKey().once('value').then(function(snapshot) {
        snapshot.forEach(function(childSnapshot) {
            var sectionkey = childSnapshot.key;
            var section = childSnapshot.val().grp_name;
            var year = childSnapshot.val().grp_year;
            if ($('#cmb_class').val() == section && $('option').data("year") == year) {
                firebase.database().ref("classes/" + sectionkey + "/pending_members/").orderByKey().once('value').then(function(snapshot) {
                    snapshot.forEach(function(childSnapshot) {
                        firebase.database().ref("members/" + childSnapshot.key).orderByKey().once('value').then(function(snapshot) {
                            var email = snapshot.val().email;
                            var firstname = snapshot.val().firstname;
                            var lastname = snapshot.val().lastname;
                            var approved_code = "<a href = assessment.php?approved=true&key=" + childSnapshot.key + "&group=" + sectionkey + ">Approve</a>";
                            var rejected_code = "<a href = assessment.php?approved=false&key=" + childSnapshot.key + "&group=" + sectionkey + ">Reject</a>";
                            alert(snapshot.key);
                            $('#tbl_pending').DataTable().row.add([firstname, lastname, email, approved_code, rejected_code]).draw();
                        });
                    });
                });
            }
        });
    });*/
    var cansy = $("#cmb_class option:selected").data('code');
    firebase.database().ref("classes/"+cansy).child("pending_members").once('value').then(function(snapshot){
        snapshot.forEach(function(childSnapshot){
            firebase.database().ref("members/" + childSnapshot.key).orderByKey().once('value').then(function(snapshot) {
                var email = snapshot.val().email;
                var firstname = snapshot.val().firstname;
                var lastname = snapshot.val().lastname;
                var approved_code = "<a class = 'btn btn-info' href = assessment.php?approved=true&key=" + childSnapshot.key + "&group=" + cansy + ">Approve</a>";
                var rejected_code = "<a class = 'btn btn-danger' href = assessment.php?approved=false&key=" + childSnapshot.key + "&group=" + cansy + ">Reject</a>";
                var check_approve = "<input type = 'checkbox' name = 'chk_values[]' value = '"+childSnapshot.key+"'>";
                $('#tbl_pending').DataTable().row.add([check_approve, firstname, lastname, email, approved_code, rejected_code]).draw();
            });
        });
    }).then(function(){
        loadQuizzes();
    });
}
$("#btn_batchApprove").on("click", function(){
    var checked = new Array();
    $("input[name='chk_values[]']:checked").each(function ()
    {
        checked.push($(this).val());
    });
    checked.forEach(function(vals){
        var dt = new Date();
        var added = dt.toUTCString();
        var cansy = $("#cmb_class option:selected").data('code');
        var oldRef = firebase.database().ref("classes/" + cansy + "/pending_members/" + vals);
        var newRef = firebase.database().ref("classes/" + cansy + "/member/" + vals);
        newRef.set({
            date_joined: added
        });
    });
    checked.forEach(function(valy){
        var dt = new Date();
        var added = dt.toUTCString();
        var cansy = $("#cmb_class option:selected").data('code');
        var oldRef = firebase.database().ref("classes/" + cansy + "/pending_members/" + valy);
        oldRef.remove();
    });
    alert("Approved");
    window.location.replace("assessment.php?sec=" + CryptoJS.AES.encrypt($("#cmb_class").val(), "Secret Passphrase"));
});

function GetParameterValues(param) {
    var url = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for (var i = 0; i < url.length; i++) {
        var urlparam = url[i].split('=');
        if (urlparam[0] == param) {
            return urlparam[1];
        }
    }
}
function loadQuizzes(){
    $("#cmb_assessmentfor").empty();
    $("#cmb_assessmentfor").append("<option>None</option>");
    quizzes = [];
    var cansy = $("#cmb_class option:selected").data('code');
    firebase.database().ref("assessment/"+localStorage.memberkey+"/"+cansy).once('value').then(function(snapshot){
        snapshot.forEach(function(childSnapshot){
            firebase.database().ref("assessment/"+localStorage.memberkey+"/"+cansy+"/"+childSnapshot.key).once('value').then(function(snappy){
                snappy.forEach(function(childSnappy){
                    if(quizzes.indexOf(childSnappy.key) == -1){
                        quizzes.push(childSnappy.key);
                    }
                });
            });
        });
    });
    quizzes.forEach(function(x){
        $("#cmb_assessmentfor").append("<option>"+x+"</option>");
    });
}
function replaceAll(str, find, replace) {
    return str.replace(new RegExp(find, 'g'), replace);
}

function Get(param) {
    return replaceAll(GetParameterValues(param), /%20/g, ' ');
}
firebase.database().ref("classes").on('child_changed', function(){
  window.location.href = "assessment.php";
});
firebase.database().ref("assessment").on('child_changed', function(){
  window.location.href = "assessment.php";
});
