/*------------------------------------------------------
    Author : www.webthemez.com
    License: Commons Attribution 3.0
    http://creativecommons.org/licenses/by/3.0/
---------------------------------------------------------  */
var test = localStorage.loggedin;
    if(test != "true"){
        window.location.replace("welcomepage.php");
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
$(document).ready(function(){
  $("#teachername").html(localStorage.teacher_name);
});
var dbRef = firebase.database().ref();
if (typeof GetParameterValues("action") !== 'undefined') {
    if(Get("action") == 1){
    var subj_decrypted = CryptoJS.AES.decrypt(Get("title"), "Secret Passphrase").toString(CryptoJS.enc.Utf8);
    $("#btn_add").html("<i aria-hidden='true' class='fa fa-plus'></i>&nbsp; Edit Subject</button>");
    $("#win_title").html("<i aria-hidden='true' class='fa fa-plus'></i>&nbsp;Edit Subject");
    setTimeout(function() { 
        firebase.database().ref("subjects/"+localStorage.memberkey+"/"+subj_decrypted).once('value').then(function(snapshot){
            $("#txt_name").val(snapshot.key);
            $("#txt_desc").val(snapshot.val().subj_description);
            $("#txt_name").attr('readonly', 'true');
            $("#subj_title").html('You cannot change the title anymore. If you want to change, deletion is advised');
        });
     }, 2000);
    }
}
$("#btn_add").on('click', function(){
    if(GetParameterValues("action") == 1){
      var subj_decrypted = CryptoJS.AES.decrypt(GetParameterValues("title"), "Secret Passphrase").toString(CryptoJS.enc.Utf8);
      var conf = confirm('Are you sure to edit the selected subject? The action is irreversible');
      if(conf == true){
        var gteacher = localStorage.memberkey;
        var desc = $("#txt_desc").val();
        firebase.database().ref("subjects/"+gteacher+"/"+subj_decrypted).update({
          subj_description: desc
        }).then(function(){
          alert("Subject successfully edited!");
          setTimeout(function() {window.location.href = "syllabus.php";}, 1000);
        }); 
      }
    }else{
      var name = $("#txt_name").val();
      var dt = new Date();
      var gadded = dt.toUTCString();
      var gteacher = localStorage.memberkey;
      var desc = $("#txt_desc").val();
      dbRef.child("subjects/" + gteacher + "/" + name).set({
        subj_date_added: gadded,
        subj_description: desc
      }).then(function(){
        alert("Subject successfully added!");
        window.location.replace("syllabus.php");
      });
    }
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

function replaceAll(str, find, replace) {
    return str.replace(new RegExp(find, 'g'), replace);
}

function Get(param) {
    return replaceAll(GetParameterValues(param), /%20/g, ' ');
}