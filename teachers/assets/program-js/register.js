$(window, document, undefined).ready(function() {
  $("#frm_register").on("submit", function(e){
      e.preventDefault();
      var user_email = $("#txt_reg_email").val();
      var user_pass = $("#txt_reg_pass").val();
      var user_fname = $("#txt_reg_fname").val();
      var user_lname = $("#txt_reg_lname").val();
      var user_type = "Teacher";
      firebase.auth().createUserWithEmailAndPassword(user_email, user_pass).then(function(e){
        firebase.database().ref().child("members").push({
          email: user_email,
          firstname: user_fname,
          lastname : user_lname,
          type: user_type,
        }).then(function(){
            var user = firebase.auth().currentUser;
            user.sendEmailVerification().then(function() {
                alert("Check your email for confirmation. ");
                location.replace("login.php");
            }).catch(function(error) {
              alert("Error: " + error.message);
            });
        }).catch(function(error){
          var errorCode = error.code;
          var errorMessage = error.message;
          alert(errorCode + ": " + errorMessage);
        });
      }).catch(function(error) {
        // Handle Errors here.
        var errorCode = error.code;
        var errorMessage = error.message;
        alert(errorCode + ": " + errorMessage);
      });
  });

});
