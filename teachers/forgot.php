<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>
      FORGOT PASSWORD | EDUTOO | INSTRUCTOR MODULE<
    </title>
    <link href='https://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css" rel="stylesheet">
    <link href="assets/css-login/style.css" rel="stylesheet">
  </head>
  <body>
    <div class="form">
      <div class="logo">
        <center>
          <img alt="" src="assets/img/logagotoo2.png" width="200">
        </center>
      </div>

      <div id="login">
        <h1>
          Forgot Password
        </h1>

          <div class="field-wrap">
            <label>Email Address<span class="req">*</span></label> <input autocomplete="off" required="" type="email" id = "txt_email">
          </div>
          <br>
          <p class="forgot1">
            <a href="index.php">Suddenly remembered? Login here</a>
          </p>
          <br>

          <p class="forgot">
            <a href="login.php">You don't have an account? Register here</a>
          </p>
          <button class="button button-block" id = "btn_reset" type = "submit">Send Reminder</button>
        <br><div class="center">All Rights Reserved. &copy; EDUTOO Developers. Template by: <a href="https://codepen.io/ehermanson/">Eric</a></div>
      </div>
    </div>
    <!-- tab-content -->
    <!-- /form -->
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'>
    </script> 
    <script src="https://www.gstatic.com/firebasejs/3.6.2/firebase.js"></script>
    <script src="assets/program-js/forgot.js"></script>

  </body>
</html>