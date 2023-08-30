var test = localStorage.loggedin;
if (test != "true") {
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
    getStudentName();
    setTimeout(function() { getGrades(); }, 2000);
    setTimeout(function() { performStatistics(); }, 3000);
});
function getStudentName(){
    var key = GetParameterValues("key");
    firebase.database().ref("members/"+key).orderByKey().once('value').then(function(snapshot) {
      $("#studentName").html(snapshot.val().firstname + " " + snapshot.val().lastname);
    });
}
var timesToRead = new Array();
var timeOnQuiz = new Array();
var scores = new Array();
function getGrades(){
    var key = GetParameterValues("key");
    var sectionkey = GetParameterValues("sectionkey");
    var teacherkey = localStorage.memberkey;
    firebase.database().ref("assessment/"+teacherkey+"/"+sectionkey+"/"+key).orderByKey().once('value').then(function(snapshot) {
      snapshot.forEach(function(childSnapshot){
        $("#rows").append("<tr><td>"+childSnapshot.key+"</td><td>"+parseFloat(childSnapshot.val().timeonRead/60).toFixed(2)+"</td><td>"+parseFloat(childSnapshot.val().timeonQuiz/60).toFixed(2)+"</td><td>"+childSnapshot.val().score+"</td><td>"+childSnapshot.val().date_quiztaken+"</td></tr>");
        timesToRead.push(parseFloat(childSnapshot.val().timeonRead/60).toFixed(2));
        timeOnQuiz.push(parseFloat(childSnapshot.val().timeonQuiz/60).toFixed(2));
        scores.push(parseFloat(childSnapshot.val().score));
      });
    });
}
function performStatistics(){
    $("#mintimetoread").html(ss.min(timesToRead));
    $("#maxtimetoread").html(ss.max(timesToRead));
    $("#avgtimetoread").html(ss.mean(timesToRead));
    $("#mintimetoquiz").html(ss.min(timeOnQuiz));
    $("#maxtimetoquiz").html(ss.max(timeOnQuiz));
    $("#avgtimetoquiz").html(ss.mean(timeOnQuiz));
    $("#minperf").html(ss.min(scores));
    $("#maxperf").html(ss.max(scores));
    $("#avgperf").html(ss.mean(scores));

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
