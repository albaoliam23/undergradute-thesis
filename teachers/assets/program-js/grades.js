var test = sessionStorage.loggedin;
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
  firebase.database().ref('classes').orderByKey().once('value').then(function(snapshot) {
  snapshot.forEach(function(childSnapshot) {
      var section = childSnapshot.val().grp_name;
      var year = childSnapshot.val().grp_year;
      var gteacher = childSnapshot.val().grp_teacher;
      if(gteacher == sessionStorage.teacher){
         $('#cmb_class').append("<option value = '" + section + "' data-year = '" +  year +"' data-code = '" +  childSnapshot.key +"'> " + section + "(" + year + ")" + "</option>")
      }
    });
  }).then(function(){
    if(typeof GetParameterValues("sec") !== 'undefined'){ //WORKS LIKE THE ISSET FUNCTION IN PHP
      var decrypted = CryptoJS.AES.decrypt(GetParameterValues("sec"), "Secret Passphrase").toString(CryptoJS.enc.Utf8);
      $("#cmb_class option[value=\"" + decrypted + "\"]").attr('selected','selected');
    }
  }).then(function(){
    canc();
  });
  $(this).on("change", "#cmb_class", function(){canc();});
});
function canc(){
  $('#tbl_grades').empty();
  firebase.database().ref('classes').orderByKey().once('value').then(function(snapshot) {
  snapshot.forEach(function(childSnapshot) {
      var sectionkey = childSnapshot.key;
      var section = childSnapshot.val().grp_name;
      var year = childSnapshot.val().grp_year;
      if($('#cmb_class').val() == section && $('option').data("year") == year){
       firebase.database().ref("classes/"+sectionkey+"/member/").orderByKey().once('value').then(function(snapshot) {
        snapshot.forEach(function(childSnapshot) {
            firebase.database().ref("members/" + childSnapshot.key).orderByKey().once('value').then(function(snapshot) {
              //pending_keys.push(childSnapshot.key);
              //console.log(pending_keys);
              var email = snapshot.val().email;
              var firstname = snapshot.val().firstname;
              var lastname = snapshot.val().lastname;
              $('#tbl_grades').append("<tr><td>" + lastname.toUpperCase() + "," + firstname +"</td><td>" + "HOY" +"</td></tr>");
            });
          });
        });
      }
    });
  }).then(function(){
    var code = $("#cmb_class").find(':selected').data("code");
    firebase.database().ref("classes/" + code).child("grp_subject").orderByKey().once('value').then(function(snapshot) {
      var subj = snapshot.val();
      firebase.database().ref("subjects/" + sessionStorage.memberkey + "/" + subj).orderByKey().once('value').then(function(snapshot2) {
        snapshot2.forEach(function(childSnapshot) {
            childSnapshot.forEach(function(childSnapshot2) {
              childSnapshot2.forEach(function(childSnapshot3) {
                if(childSnapshot3.key == "quiz")
                  $('#col_grades').append("<th>Name</th><th>" + childSnapshot2.key +"</th>");
              });
            });
          });
      });
    });
  });
};
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
function Get(param){
  return replaceAll(GetParameterValues(param), /%20/g , ' ');
}