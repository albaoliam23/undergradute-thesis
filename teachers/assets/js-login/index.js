var test = localStorage.loggedin;
    if(test == "true"){
        window.location.replace("index.php");
}
var config = {
  apiKey: "AIzaSyD0CsnZgDBGSFBVrtZH0HRBbVQWk__txe0",
  authDomain: "ezbio-98be1.firebaseapp.com",
  databaseURL: "https://ezbio-98be1.firebaseio.com",
  storageBucket: "ezbio-98be1.appspot.com",
  messagingSenderId: "595946963410"
};
firebase.initializeApp(config);
$('.form').find('input, textarea').on('keyup blur focus', function (e) {
  
  var $this = $(this),
      label = $this.prev('label');

	  if (e.type === 'keyup') {
			if ($this.val() === '') {
          label.removeClass('active highlight');
        } else {
          label.addClass('active highlight');
        }
    } else if (e.type === 'blur') {
    	if( $this.val() === '' ) {
    		label.removeClass('active highlight'); 
			} else {
		    label.removeClass('highlight');   
			}   
    } else if (e.type === 'focus') {
      
      if( $this.val() === '' ) {
    		label.removeClass('highlight'); 
			} 
      else if( $this.val() !== '' ) {
		    label.addClass('highlight');
			}
    }

});

$('.tab a').on('click', function (e) {
  
  e.preventDefault();
  
  $(this).parent().addClass('active');
  $(this).parent().siblings().removeClass('active');
  
  target = $(this).attr('href');

  $('.tab-content > div').not(target).hide();
  
  $(target).fadeIn(600);
  
});
jQuery.validator.setDefaults({
          debug: true,
          success: "valid"
        });
$("form").submit(function(e){
    e.preventDefault();
});
$("#frm_login").on("submit", function(){
  var xmlhttp = new XMLHttpRequest();
  var email = $("#txt_email").val();
  var password = $("#txt_pass").val();
  firebase.auth().signInWithEmailAndPassword(email, password).then(function(){
    var user = firebase.auth().currentUser;
    if(user.emailVerified){
        firebase.database().ref('members').orderByKey().once('value').then(function(snapshot) {
          snapshot.forEach(function(childSnapshot) {
              var search_type =  childSnapshot.val().type;
              var search_email = childSnapshot.val().email;
              if(search_email == email){
                  if(search_type == "Teacher"){
                    localStorage.loggedin = "true";
                    localStorage.teacher = email;
                    localStorage.memberkey = childSnapshot.key;
                    localStorage.teacher_name = childSnapshot.val().firstname + " " + childSnapshot.val().lastname;
                    window.location.replace("index.php");
                  }else{
                    alert("You are not a teacher!");
                    window.location.replace("login.php");
                  }
              }
          });
        });
    }else{
        alert("Email not yet confirmed!");   
    }
  }).catch(function(error) {
    var errorCode = error.code;
    var errorMessage = error.message;
    alert(errorCode + ": " + errorMessage);
    window.location.replace("login.php");
  });
});