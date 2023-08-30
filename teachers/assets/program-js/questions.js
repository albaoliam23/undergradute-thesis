/*------------------------------------------------------
    Author : www.webthemez.com
    License: Commons Attribution 3.0
    http://creativecommons.org/licenses/by/3.0/
---------------------------------------------------------  */
var test = sessionStorage.loggedin;
    if(test != "true"){
        window.location.replace("login.php");
}
var config = {
  apiKey: "AIzaSyD0CsnZgDBGSFBVrtZH0HRBbVQWk__txe0",
  authDomain: "ezbio-98be1.firebaseapp.com",
  databaseURL: "https://ezbio-98be1.firebaseio.com",
  storageBucket: "ezbio-98be1.appspot.com",
  messagingSenderId: "595946963410"
};
firebase.initializeApp(config);
(function ($) {
    "use strict";
    var chartData;
    var mainApp = {

        initFunction: function () {
            /*MENU 
            ------------------------------------*/
            $('#main-menu').metisMenu();
            
            $(window).bind("load resize", function () {
                if ($(this).width() < 768) {
                    $('div.sidebar-collapse').addClass('collapse')
                } else {
                    $('div.sidebar-collapse').removeClass('collapse')
                }
            }); 
       
     
        },

        initialization: function () {
            mainApp.initFunction();

        }


    }
    // Initializing ///

    $(document).ready(function () {
        mainApp.initFunction(); 
        $("#sideNav").click(function(){
            if($(this).hasClass('closed')){
                $('.navbar-side').animate({left: '0px'});
                $(this).removeClass('closed');
                $('#page-wrapper').animate({'margin-left' : '260px'});
                
            }
            else{
                $(this).addClass('closed');
                $('.navbar-side').animate({left: '-260px'});
                $('#page-wrapper').animate({'margin-left' : '0px'}); 
            }
        });
        var key = GetParameterValues("key");
        var cansa = replaceAll(key, /%20/g , ' ')
        var can = sessionStorage.subject;
        var v = can + "_" + cansa;
        firebase.database().ref("quizzes/" + v + "/questions").orderByKey().once('value').then(function(snapshot) {
          snapshot.forEach(function(childSnapshot) {
            var a = childSnapshot.val().q_a;
            var b = childSnapshot.val().q_b;
            var c = childSnapshot.val().q_c;
            var d = childSnapshot.val().q_d;
            var question = childSnapshot.val().q_question;
            var lesson = childSnapshot.val().q_lesson;
            var rightans = childSnapshot.val().q_rightans;
            var difficulty = (childSnapshot.val().q_mult)*100;
            $("#tbl_questions_body").append("<tr><td> " + question + "</td><td> " + a + "</td><td> " + b + "</td><td> " + c + "</td><td> " + d + "</td><td> " + rightans + "</td><td><div class='progress'><div class='progress-bar progress-bar-success' role='progressbar' aria-valuenow=" + difficulty + " aria-valuemin='0' aria-valuemax='100' style='width: " + difficulty + "%'><span class='sr-only'>" + difficulty + "%</span></div></div></td></tr>"); 
          });
        });
    });
}(jQuery));
function logout(){
    firebase.auth().signOut().then(function() {
      sessionStorage.clear();
      window.location.replace("login.php");
    }, function(error) {
      alert('Sign Out Error', error);
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
function replaceAll(str, find, replace) {
  return str.replace(new RegExp(find, 'g'), replace);
}
