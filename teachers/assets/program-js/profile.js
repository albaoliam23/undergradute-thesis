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
$(document).ready(function () {
  $("#teachername").html(localStorage.teacher_name);
  loadProfile();
});
var rid = 0;
$("#btn_basic_info").on('click', function(){
  var title = $("#cmb_degree").val();
  var name = $("#txt_fname").val() + " " + $("#txt_mname").val() + " " + $("#txt_lname").val();
  $("#displayname").html(title + " " + name);
  var yrsteaching =  parseInt(new Date().getFullYear()) - parseInt($("#cmb_yearstarted").val());
  $("#yrsteaching").html(yrsteaching);
  $("#currsch").html($("#txt_work").val());
});
$("#btn_about").on('click', function(){
  $("#about").html($("#txt_about").val());
});
$("#btn_addeduc").on('click', function(){
  rid++;
  var deg = $("#cmb_educ_degree").val();
  var degtitle = $("#txt_degtitle").val();
  var school = $("#txt_school").val();
  var yeargrad = $("#cmb_yeargrad").val();
  $("#tbl_educ").append("<tr id = 'row_"+rid+"'><td>"+deg+"</td><td>"+degtitle+"</td><td>"+school+"</td><td>"+yeargrad+"</td><td><a onclick = \"deleteRow(\'row_"+rid+"\');\"><i class = 'fa fa-trash-o'></i></a></td></tr>");
});
function deleteRow(rowid){
  $("#"+rowid).remove();
}
$("#btn_save").on('click', function(){
  var conf = confirm('Save your profile?');
  if (conf == true) {
    var name = $("#txt_fname").val() + " " + $("#txt_mname").val() + " " + $("#txt_lname").val();
    var bday = $("#txt_bday_month").val() + " " + $("#txt_bday_date").val() + ", " + $("#txt_bday_year").val();
    var yearstart = $("#cmb_yearstarted").val();
    var currsch = $("#txt_work").val();
    var t_about = $("#txt_about").val();
    var titles = $("#cmb_degree").val();
    var firstname = $("#txt_fname").val();
    var midname = $("#txt_mname").val();
    var lastname = $("#txt_lname").val();
    var user = firebase.database().ref("members/"+localStorage.memberkey+"/profile")
    user.update({
      title: titles,
      fname: firstname,
      mname: midname,
      lname: lastname,
      birthday: bday,
      yearstarted: yearstart,
      current_school: currsch,
      about: t_about
    }).then(function() {
      window.location.replace("profile.php");
    }).catch(function(error) {
      alert(error.message);
    });      
  }
});
function loadProfile(){
  var user = firebase.database().ref("members/"+localStorage.memberkey+"/profile");
  user.once('value').then(function(snapshot){
    $("#displayname").html(snapshot.val().title + " " + snapshot.val().fname + " " + snapshot.val().mname + " " + snapshot.val().lname);
    $("#txt_lname").val(snapshot.val().lname);
    $("#txt_fname").val(snapshot.val().fname);
    $("#txt_mname").val(snapshot.val().mname);
    $("#cmb_degree option[value='"+snapshot.val().title+"']").attr('selected', 'selected');
    $("#txt_about").val(snapshot.val().about);
  });
}