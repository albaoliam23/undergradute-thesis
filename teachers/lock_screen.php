<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Dashboard">
    <meta name="keyword" content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">

    <title>LOCKSCREEN | EDUTOO | INSTRUCTOR MODULE</title>

    <!-- Bootstrap core CSS -->
    <link href="assets/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
        
    <!-- Custom styles for this template -->
    <link href="assets/css/style.css" rel="stylesheet">
    <link href="assets/css/style-responsive.css" rel="stylesheet">
    <link href="assets/img/logagotoo2.png" rel="shortcut icon">
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body onload="getTime()">

      <!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->

	  	<div class="container">
	  		<div id="showtime"></div>
	  			<div class="col-lg-4 col-lg-offset-4">
	  				<div class="lock-screen">
		  				<h2><a data-toggle="modal" href="#myModal" class="lock">
                        <div class="icon-unlock"><i class="fa fa-lock"></i></div>
                        <div class="icon-lock"><i class="fa fa-unlock"></i></div>
                        </a></h2>
		  				<p style="color: #FFFFFF;">UNLOCK</p>
		  				
				          <!-- Modal -->
				          <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade">
				              <div class="modal-dialog">
				                  <div class="modal-content">
				                      <div class="modal-header">
				                          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				                          <h4 class="modal-title">Welcome Back!</h4>
				                      </div>
				                      <div class="modal-body">
				                          <p class="centered"><img class="img-circle" width="80" src="assets/img/user_default.png"></p>
                                          <strong><p class="centered" id = "teachername"></p></strong>
				                          <input type="password" name="password" placeholder="Password" autocomplete="off" class="form-control placeholder-no-fix">
				
				                      </div>
				                      <div class="modal-footer centered">
				                          <button data-dismiss="modal" class="btn btn-theme04" type="button">Cancel</button>
				                          <button class="btn btn-theme03" type="button" id = "btn_login">Login</button>
				                      </div>
				                  </div>
				              </div>
				          </div>
				          <!-- modal -->
		  				
		  				
	  				</div>
	  			</div><!-- /col-lg-4 -->
	  	
	  	</div><!-- /container -->

    <!-- js placed at the end of the document so the pages load faster -->
    <script src="assets/js/jquery.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>

    <!--BACKSTRETCH-->
    <!-- You can use an image of whatever size. This script will stretch to fit in any screen size.-->
    <script type="text/javascript" src="assets/js/jquery.backstretch.min.js"></script>
    <script src="https://www.gstatic.com/firebasejs/3.6.2/firebase.js"></script>
    <script>
        $.backstretch("assets/img/DSC04871.jpg", {speed: 500});
    </script>

    <script>
        function getTime()
        {
            var today=new Date();
            var h=today.getHours();
            var m=today.getMinutes();
            var s=today.getSeconds();
            // add a zero in front of numbers<10
            m=checkTime(m);
            s=checkTime(s);
            document.getElementById('showtime').innerHTML=h+":"+m+":"+s;
            t=setTimeout(function(){getTime()},500);
        }

        function checkTime(i)
        {
            if (i<10)
            {
                i="0" + i;
            }
            return i;
        }
    </script>
    <script>
        var config = {
            apiKey: "AIzaSyD0CsnZgDBGSFBVrtZH0HRBbVQWk__txe0",
            authDomain: "ezbio-98be1.firebaseapp.com",
            databaseURL: "https://ezbio-98be1.firebaseio.com",
            storageBucket: "ezbio-98be1.appspot.com",
            messagingSenderId: "595946963410"
        };
        firebase.initializeApp(config);
        $(document).ready(function(){
            localStorage.locked = true;
            $("#teachername").html(localStorage.teacher_name);
        });
        $("#btn_login").on('click', function(){
            var user = firebase.auth().currentUser;
            alert(user.title);
        });
    </script>

  </body>
</html>
