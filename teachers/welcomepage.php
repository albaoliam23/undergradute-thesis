<!DOCTYPE html>
<html class="no-js">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>WELCOME TO EDUTOO!</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="all,follow">
    <!-- Bootstrap and Font Awesome css-->
    <link rel="stylesheet" href="assets/css-w/font-awesome.css">
    <link rel="stylesheet" href="assets/css-w/bootstrap.min.css">
    <!-- Google fonts-->
    <link rel="stylesheet" type="text/css" href="assets/css-w/family=Waiting+for+the+Sunrise.css" />
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700">
    <link rel="stylesheet" type="text/css" href="assets/css-w/family=Schoolbell.css" />
    <!-- Theme stylesheet-->
    <link rel="stylesheet" href="assets/css-w/style.default.css" id="theme-stylesheet">
    <!-- Custom stylesheet - for your changes-->
    <link rel="stylesheet" href="assets/css-w/custom.css">
    <!--Animation-->
    <!-- Favicon-->
    <link rel="shortcut icon" href="assets/img/logagotoo2.png">
    <!-- Tweaks for older IEs--><!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->
    <style type="text/css">
      
    </style>
  </head>
  <body>
    <div style="background-image: url('assets/img/ueshsgif.gif')" class="main"> 
      <div class="overlay"></div>
      <div class="container">
        <div class="center"><label class="cursive" style="font-size: 90px; color: #FFFFFF; font-weight: bold;">WELCOME TO EDUTOO</label></div>
        <h2 class="sub">An <strong>Educational</strong> Tool made for <strong>you</strong> and your <strong>students</strong>. </h2>
        <div class="mailing-list">
          <form action="login.php">
          <button class="button" style="vertical-align:middle"><span class="sub">Proceed to login </span></button>
          </form>
        </div>
      </div>
      <div class="footer">
        <div class="container">
          <div class="row">
            <div class="col-md-6">
              <p>All Rights Reserved &copy;2017 EDUTOO</p>
            </div>
            <div class="col-md-6">
              <p class="credit">Template by <a href="https://www.bootstrapious.com">Bootstrapious</a><br />& <a href="https://remoteplease.com">RemotePlease</a></p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- JAVASCRIPT FILES -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="javascripts/vendor/jquery-1.11.0.min.js"><\/script>')</script>
    <script src="assets/js-w/bootstrap.min.js"></script>
    <script src="assets/js-w/jquery.cookie.js"></script>
    <script src="assets/js-w/front.js"></script>
    <!-- Google Analytics: change UA-XXXXX-X to be your site's ID.-->
    <script>
      (function(b,o,i,l,e,r){b.GoogleAnalyticsObject=l;b[l]||(b[l]=
      function(){(b[l].q=b[l].q||[]).push(arguments)});b[l].l=+new Date;
      e=o.createElement(i);r=o.getElementsByTagName(i)[0];
      e.src='//www.google-analytics.com/analytics.js';
      r.parentNode.insertBefore(e,r)}(window,document,'script','ga'));
      ga('create','UA-XXXXX-X');ga('send','pageview');
      var test = localStorage.loggedin;
      if(test == "true"){
          window.location.replace("index.php");
      }
    </script>
  </body>
</html>