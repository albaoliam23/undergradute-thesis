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
        firebase.database().ref('classes').orderByKey().once('value').then(function(snapshot) {
        snapshot.forEach(function(childSnapshot) {
            var code =  childSnapshot.key;
            var section = childSnapshot.val().grp_name;
            var year = childSnapshot.val().grp_year;
            var gteacher = childSnapshot.val().grp_teacher;
            var gsubject = childSnapshot.val().grp_subject;
            var gstatus = childSnapshot.val().grp_status;
            var gcap = childSnapshot.val().grp_capacity;
            var gcreated = new Date(childSnapshot.val().grp_date_created).toLocaleString();
            var hey = "<a class = 'btn btn-info' href = \"assessment.php?sec=" + CryptoJS.AES.encrypt(section, "Secret Passphrase") +"\">View Members</a>";
            var currcount = 0;
            firebase.database().ref("classes/"+code+"/member").once('value').then(function(snapshot){
              currcount = snapshot.numChildren();
            }).then(function(){
              var cap = currcount + "/" + gcap;
              if(gteacher == localStorage.teacher && gstatus == "Active"){
                 $('#tbl_class').DataTable().row.add( [code, section, gsubject, year, cap, gcreated, gstatus, hey, "<a  class = 'btn btn-warning' href = 'addnewclass.php?action=1&code="+code+"'>Edit</a> <a class = 'btn btn-danger' onclick = 'deactivate(\""+code+"\");'>Deactivate</a> <a class = 'btn btn-danger' onclick = 'deleteClass(\""+code+"\");'>Delete</a>"] ).draw();
              }else if(gteacher == localStorage.teacher && gstatus == "Inactive"){
                 $('#tbl_class').DataTable().row.add( [code, section, gsubject, year, cap, gcreated, gstatus, hey, "<a class = 'btn btn-warning href = 'addnewclass.php?action=1&code="+code+"'>Edit</a><a class = 'btn btn-info' onclick = 'activate(\""+code+"\");'>Activate</a> <a class = 'btn btn-danger' onclick = 'deleteClass(\""+code+"\");'>Delete</a>"] ).draw();
              }
            });
        });
    });
});
function deactivate(code){
  var teacherkey = localStorage.memberkey;
  firebase.database().ref("classes/"+code).update({grp_status: "Inactive"});
  window.location.href = "classes.php";
}
function activate(code){
  var teacherkey = localStorage.memberkey;
  firebase.database().ref("classes/"+code).update({grp_status: "Active"});
  window.location.href = "classes.php";
}
function deleteClass(code){
  var conf = confirm("Delete this class?");
  if (conf == true){
    firebase.database().ref("classes/"+code).remove();
    window.location.href = "classes.php";
  }
}