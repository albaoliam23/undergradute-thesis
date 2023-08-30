var config = {
  apiKey: "AIzaSyD0CsnZgDBGSFBVrtZH0HRBbVQWk__txe0",
  authDomain: "ezbio-98be1.firebaseapp.com",
  databaseURL: "https://ezbio-98be1.firebaseio.com",
  storageBucket: "ezbio-98be1.appspot.com",
  messagingSenderId: "595946963410"
};
firebase.initializeApp(config);
var test = localStorage.loggedin;
  if(test != "true"){
      window.location.replace("welcomepage.php");
}
if (localStorage.locked == "true") {
    window.location.replace("lock_screen.php");
}
var member_count = 0;
$(document).ready(function () {
  countMembers();
  itemAnalysis();
  validateCount();
});
function validateCount(){
  var loc = CryptoJS.AES.decrypt(GetParameterValues("subj"), "Secret Passphrase").toString(CryptoJS.enc.Utf8);
  var counted_questions;
  var numitems;
  setTimeout(function(){
    firebase.database().ref(loc+"/quiz/questions").once('value').then(function(snapshot){
      counted_questions = snapshot.numChildren();
    });
  }, 2000);
  setTimeout(function(){
    firebase.database().ref(loc+"/quiz").once('value').then(function(snapshot){
      numitems = snapshot.val().quiz_numItems;
      if(numitems != counted_questions){
        $("#warning").html("Warning: The number of questions in the quiz is not enough for the expected number of items.");
      }
    });
  }, 2000);
}
function countMembers(){
  var gkeys = new Array();
  var s = CryptoJS.AES.decrypt(GetParameterValues("s"), "Secret Passphrase").toString(CryptoJS.enc.Utf8)
  firebase.database().ref("classes").orderByKey().once('value').then(function(snapshot){
    snapshot.forEach(function(childSnapshot){
      var gteacherkey = childSnapshot.val().grp_teacher_key;
      var gsubject = childSnapshot.val().grp_subject;
      if(gteacherkey == localStorage.memberkey && gsubject == s){
        gkeys.push(childSnapshot.key);
      }
    });
  }).then(function(){
    gkeys.forEach(function(canc){
      firebase.database().ref("classes/"+ canc +"/").child("member").once('value').then(function(snapshot){
        member_count = member_count + parseInt(snapshot.numChildren());
      });
    });
  });
}
function itemAnalysis(){
  var questions_array = new Array();
  var difficulty = new Array();
  var analysis = new Array();
  var keys = new Array();
  var loc = CryptoJS.AES.decrypt(GetParameterValues("subj"), "Secret Passphrase").toString(CryptoJS.enc.Utf8);
  firebase.database().ref(loc + "/quiz/questions").orderByKey().once('value').then(function(snapshot) {
    snapshot.forEach(function(childSnapshot){
      if(childSnapshot.key != "quiz_numItems"){
        questions_array.push(childSnapshot.val().q_question);
        keys.push(childSnapshot.key);
      }
    });
  }).then(function(){
    firebase.database().ref(loc + "/quiz/item_analysis").orderByKey().once('value').then(function(snapshot) {
      snapshot.forEach(function(childSnapshot){
        childSnapshot.forEach(function(grandchildSnapshot){
          difficulty.push((grandchildSnapshot.numChildren() / member_count)*100);
        });
      });
    }).then(function(){
      for (var i = 0 ; i < questions_array.length ; ++i){
        parseFloat(difficulty[i]);
        analysis[questions_array[i]] = difficulty[i];
        var display;
        if(difficulty[i] >= 0 && difficulty[i] <= 33.32){
          display = "<td><font color = 'green'>"+difficulty[i]+"%</font></td>";
        }else if(difficulty[i] >= 33.33 && difficulty[i] <= 66.65){
          display = "<td><font color = 'orange'>"+difficulty[i]+"%</font></td>";
        }else if(difficulty[i] >= 66.66 && difficulty[i] <= 100.00){
          display = "<td><font color = 'red'>"+difficulty[i]+"%</font></td>";
        }else{
          display = "<td>Percentage of difficulty cannot be assessed.</td>";
        }
        $("#questions_container").append("<tr><td>" + questions_array[i] + "</td>"+display+"</tr>");
      }
    }).then(function(){
      $("#exp_num").append(member_count);
      $("#tbl_analysis").DataTable();
    });
  });
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