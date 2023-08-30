<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>
      Sign-Up/Login Form | EDUTOO | INSTRUCTOR MODULE
    </title>
    <link href='https://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css" rel="stylesheet">
    <link href="assets/css-login/style.css" rel="stylesheet">
    <link href="assets/img/logagotoo2.png" rel="shortcut icon">
  </head>
  <body>
    <div class="form">
      <div class="logo">
        <center>
          <img alt="" src="assets/img/logagotoo2.png" width="200">
        </center>
      </div>
      <br>

      <ul class="tab-group">
        <li class="tab active">
          <a href="#login">Log In</a>
        </li>

        <li class="tab">
          <a href="#signup">Sign Up</a>
        </li>
      </ul>

      <div class="tab-content">
        <div id="login">
          <h1>
            Instructor Login
          </h1>

          <form id = "frm_login">
            <div class="field-wrap">
              <label>Email Address<span class="req">*</span></label> <input autocomplete="off" required="" type="email" id = "txt_email">
            </div>

            <div class="field-wrap">
              <label>Password<span class="req">*</span></label> <input autocomplete="off" required="" type="password" id = "txt_pass">
            </div>
            <br>

            <p class="forgot">
              <a href="forgot.php">Forgot Password?</a>
            </p>
            <button class="button button-block" type="submit" id = "loginteacher">Log In</button>
          </form>
          <br><div class="center">All Rights Reserved. &copy; EDUTOO Developers. Template by: <a href="https://codepen.io/ehermanson/">Eric</a></div>
        </div>

        <div id="signup">
          <h1>
            REGISTER HERE
          </h1>

          <form id = "frm_register">
            <div class="top-row">
              <div class="field-wrap">
                <label>First Name<span class="req">*</span></label> <input autocomplete="off" required="" type="text" id = "txt_reg_fname">
              </div>

              <div class="field-wrap">
                <label>Last Name<span class="req">*</span></label> <input autocomplete="off" required="" type="text" id = "txt_reg_lname">
              </div>
            </div>

            <div class="field-wrap">
              <label>Email Address<span class="req">*</span></label> <input autocomplete="off" required="" type="email" id = "txt_reg_email">
            </div>

            <div class="field-wrap">
              <label>Set A Password<span class="req">*</span></label> <input autocomplete="off" required="" type="password" id = "txt_reg_pass">
            </div><br>

            <button class="button button-block" type="submit">Get Started</button>
          </form>
          <br><div class="center">All Rights Reserved. &copy; EDUTOO Developers. Template by: <a href="https://codepen.io/ehermanson/">Eric</a></div>
        </div>
      </div>
      <!-- tab-content -->
    </div>
    <!-- /form -->
    <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'>
    </script> 
    <!-- FIREBASE -->
    <script src="https://www.gstatic.com/firebasejs/3.6.2/firebase.js"></script>
    <script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
    <script src="assets/js-login/index.js"></script>
    <script src="assets/program-js/register.js"></script>
  </body>
</html>