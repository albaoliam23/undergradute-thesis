var test = localStorage.loggedin;
if (test != "true") {
    window.location.replace("welcomepage.php");
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
function logout() {
    firebase.auth().signOut().then(function() {
        localStorage.clear();
        window.location.replace("welcomepage.php");
    }, function(error) {
        alert('Sign Out Error', error);
    });
}