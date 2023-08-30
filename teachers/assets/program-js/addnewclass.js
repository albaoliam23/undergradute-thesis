var test = localStorage.loggedin;
    if(test != "true"){
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
$(document).ready(function () {
    $("#teachername").html(localStorage.teacher_name);
    var dbRef = firebase.database().ref();
    loadSubjects();
    for(var i = 2000 ; i < 2050 ; ++i){
        $("#cmb_fromyear").append("<option>" + i + "</option>");
        $("#cmb_toyear").append("<option>" + i + "</option>");
    }
    
    if (typeof GetParameterValues("action") !== 'undefined') {
        if(GetParameterValues("action") == 1){
        setTimeout(function() { 
            firebase.database().ref("classes/"+Get("code")).once('value').then(function(snapshot){
                $("#txt_name").val(snapshot.val().grp_name);
                var dateseparated = (snapshot.val().grp_year).split('-');
                $("#cmb_fromyear").val(dateseparated[0]); 
                $("#cmb_toyear").val(dateseparated[1]);
                $("#cmb_curriculum option[value='"+snapshot.val().grp_subject+"']").attr('selected', 'selected');
                $("#num_capacity").val(snapshot.val().grp_capacity);
                $("#txt_grpcode").val(snapshot.key);
                $("#btn_gen_grpcode").prop('disabled', 'true');
            });
         }, 3000);
        }
    }
    $("#frm_newclass").on("submit", function(){
        if(GetParameterValues("action") == 1){
            var gname = $("#txt_name").val();
            var gsubject = $("#cmb_curriculum").val();
            var gyear =  $("#cmb_fromyear").val() + "-" + $("#cmb_toyear").val();
            var gcap = $("#num_capacity").val();
            var gcode = $("#txt_grpcode").val();
            var gteacher = localStorage.teacher;
            var gteacherkey = localStorage.memberkey;
            var dt = new Date();
            var added = dt.toUTCString();
            dbRef.child("classes/" + gcode).update({
              grp_name : gname,
              grp_subject : gsubject,
              grp_year : gyear,
              grp_capacity : gcap,
              grp_teacher: gteacher,
              grp_teacher_key: gteacherkey,
              grp_status: "Active",
              grp_date_created: added
            }).then(function(){
              alert("Class Edited");
              window.location.replace("classes.php");
            });
        }else{
            var gname = $("#txt_name").val();
            var gsubject = $("#cmb_curriculum").val();
            var gyear =  $("#cmb_fromyear").val() + "-" + $("#cmb_toyear").val();
            var gcap = $("#num_capacity").val();
            var gcode = $("#txt_grpcode").val();
            var gteacher = localStorage.teacher;
            var gteacherkey = localStorage.memberkey;
            var dt = new Date();
            var added = dt.toUTCString();
            dbRef.child("classes/" + gcode).set({
              grp_name : gname,
              grp_subject : gsubject,
              grp_year : gyear,
              grp_capacity : gcap,
              grp_teacher: gteacher,
              grp_teacher_key: gteacherkey,
              grp_status: "Active",
              grp_date_created: added
            }).then(function(){
              alert("Class Added");
              window.location.replace("classes.php");
            });
        }
    });
    $("#btn_gen_grpcode").click(function(){
        var text = "";
        var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for( var i=0; i < 6; i++ ){
            text += possible.charAt(Math.floor(Math.random() * possible.length));
        }
        var validate = false;
        firebase.database().ref("classes").once('value', function(snapshot) {
            if (snapshot.hasChild(text)) {
                validate =  true;
            }
            else{
                validate =  false;
            }
        }).then(function(){
            if(validate == false)
                $("#txt_grpcode").val(text);
        });
    });
});	
firebase.database().ref("classes").on('child_changed', function(){
  window.location.href = "addnewclass.php";
});
function loadSubjects(){
    firebase.database().ref("subjects/" + localStorage.memberkey).orderByKey().once('value').then(function(snapshot) {
        snapshot.forEach(function(childSnapshot) {
            $("#cmb_curriculum").append("<option value = '"+childSnapshot.key+"'> " + childSnapshot.key + "</option>")
            
        });
    })
}
function GetParameterValues(param) {
    var url = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for (var i = 0; i < url.length; i++) {
        var urlparam = url[i].split('=');
        if (urlparam[0] == param) {
            return urlparam[1];
        }
    }
}

function replaceAll(str, find, replace) {
    return str.replace(new RegExp(find, 'g'), replace);
}

function Get(param) {
    return replaceAll(GetParameterValues(param), /%20/g, ' ');
}