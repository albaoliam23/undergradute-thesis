<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">
  <meta content="" name="description">
  <meta content="Dashboard" name="author">
  <meta content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina" name="keyword">
  <title>QUESTIONS | EDUTOO | INSTRUCTOR MODULE</title>

    <!-- Bootstrap core CSS -->
    <link href="assets/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="assets/css/zabuto_calendar.css">
    <link rel="stylesheet" type="text/css" href="assets/js/gritter/css/jquery.gritter.css" />
    <link rel="stylesheet" type="text/css" href="assets/lineicons/style.css">    

    <!-- Custom styles for this template -->
    <link href="assets/css/style.css" rel="stylesheet">
    <link href="assets/css/style-responsive.css" rel="stylesheet">
    <link href="assets/img/logagotoo2.png" rel="shortcut icon">

    <!-- TABLE STYLES-->
    <link href="assets/js/dataTables/dataTables.bootstrap.min.css" rel="stylesheet">
  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
  <section id="container">
    <!-- **********************************************************************************************************************************************************
      TOP BAR CONTENT & NOTIFICATIONS
      *********************************************************************************************************************************************************** -->
   <!--header start-->
    <header class="header black-bg">
      <div class="sidebar-toggle-box">
        <div class="fa fa-bars tooltips" data-original-title="Toggle Navigation" data-placement="right"></div>
      </div><!--logo start-->
      <a class="logo" href="index.php"><img src="assets/img/logagotoo2.png" width="30">&nbsp;<b>EDUTOO</b></a> <!--logo end-->
      <div class="nav notify-row" id="top_menu">
        <!--  notification start -->
        <ul class="nav top-menu">
          <!-- settings start -->
          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="index.php#"><i aria-hidden="true" class="fa fa-tasks"></i> <span class="badge bg-theme">4</span></a>
            <ul class="dropdown-menu extended tasks-bar">
              <li style="list-style: none; display: inline">
                <div class="notify-arrow notify-arrow-green"></div>
              </li>
              <li>
                <p class="green">You have 4 pending tasks</p>
              </li>
              <li>
                <a href="index.php#">
                <div class="task-info">
                  <div class="desc">
                    DashGum Admin Panel
                  </div>
                  <div class="percent">
                    40%
                  </div>
                </div>
                <div class="progress progress-striped">
                  <div aria-valuemax="100" aria-valuemin="0" aria-valuenow="40" class="progress-bar progress-bar-success" role="progressbar" style="width: 40%">
                    <span class="sr-only">40% Complete (success)</span>
                  </div>
                </div></a>
              </li>
              <li>
                <a href="index.php#">
                <div class="task-info">
                  <div class="desc">
                    Database Update
                  </div>
                  <div class="percent">
                    60%
                  </div>
                </div>
                <div class="progress progress-striped">
                  <div aria-valuemax="100" aria-valuemin="0" aria-valuenow="60" class="progress-bar progress-bar-warning" role="progressbar" style="width: 60%">
                    <span class="sr-only">60% Complete (warning)</span>
                  </div>
                </div></a>
              </li>
              <li>
                <a href="index.php#">
                <div class="task-info">
                  <div class="desc">
                    Product Development
                  </div>
                  <div class="percent">
                    80%
                  </div>
                </div>
                <div class="progress progress-striped">
                  <div aria-valuemax="100" aria-valuemin="0" aria-valuenow="80" class="progress-bar progress-bar-info" role="progressbar" style="width: 80%">
                    <span class="sr-only">80% Complete</span>
                  </div>
                </div></a>
              </li>
              <li>
                <a href="index.php#">
                <div class="task-info">
                  <div class="desc">
                    Payments Sent
                  </div>
                  <div class="percent">
                    70%
                  </div>
                </div>
                <div class="progress progress-striped">
                  <div aria-valuemax="100" aria-valuemin="0" aria-valuenow="70" class="progress-bar progress-bar-danger" role="progressbar" style="width: 70%">
                    <span class="sr-only">70% Complete (Important)</span>
                  </div>
                </div></a>
              </li>
              <li class="external">
                <a href="#">See All Tasks</a>
              </li>
            </ul>
          </li><!-- settings end -->
          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="index.php#"><i aria-hidden="true" class="fa fa-bell"></i><span class="badge bg-theme">4</span></a>
            <ul class="dropdown-menu extended tasks-bar">
              <li style="list-style: none; display: inline">
                <div class="notify-arrow notify-arrow-green"></div>
              </li>
              <li>
                <p class="green">Notification</p>
              </li>
              <li>
                <a href="index.php#">
                <div class="desc">
                  <i class="fa fa-comment fa-fw"></i> New Comment <span class="pull-right text-muted small">4 min</span>
                </div></a>
              </li>
              <li>
                <a href="index.php#">
                <div class="desc">
                  <i class="fa fa-twitter fa-fw"></i> 3 New Followers <span class="pull-right text-muted small">12 min</span>
                </div></a>
              </li>
              <li>
                <a href="index.php#">
                <div class="desc">
                  <i class="fa fa-envelope fa-fw"></i> Message Sent <span class="pull-right text-muted small">4 min</span>
                </div></a>
              </li>
              <li>
                <a href="index.php#">
                <div class="desc">
                  <i class="fa fa-tasks fa-fw"></i> New Task <span class="pull-right text-muted small">4 min</span>
                </div></a>
              </li>
              <li>
                <a href="index.php#">
                <div class="desc">
                  <i class="fa fa-upload fa-fw"></i> Server Rebooted <span class="pull-right text-muted small">4 min</span>
                </div></a>
              </li>
              <li class="external">
                <a href="#">See All Notifications</a>
              </li>
            </ul>
          </li><!-- inbox dropdown start-->
          <li class="dropdown" id="header_inbox_bar">
            <a class="dropdown-toggle" data-toggle="dropdown" href="index.php#"><i class="fa fa-envelope-o"></i> <span class="badge bg-theme">5</span></a>
            <ul class="dropdown-menu extended inbox">
              <li style="list-style: none; display: inline">
                <div class="notify-arrow notify-arrow-green"></div>
              </li>
              <li>
                <p class="green">You have 5 new messages</p>
              </li>
              <li>
                <a href="index.php#"><span class="photo"><img alt="avatar" src="assets/img/user_default.png"></span> <span class="subject"><span class="from">Zac Snider</span> <span class="time">Just now</span></span> <span class="message">Hi mate, how is everything?</span></a>
              </li>
              <li>
                <a href="index.php#"><span class="photo"><img alt="avatar" src="assets/img/user_default.png"></span> <span class="subject"><span class="from">Divya Manian</span> <span class="time">40 mins.</span></span> <span class="message">Hi, I need your help with this.</span></a>
              </li>
              <li>
                <a href="index.php#"><span class="photo"><img alt="avatar" src="assets/img/user_default.png"></span> <span class="subject"><span class="from">Dan Rogers</span> <span class="time">2 hrs.</span></span> <span class="message">Love your new Dashboard.</span></a>
              </li>
              <li>
                <a href="index.php#"><span class="photo"><img alt="avatar" src="assets/img/user_default.png"></span> <span class="subject"><span class="from">Dj Sherman</span> <span class="time">4 hrs.</span></span> <span class="message">Please, answer asap.</span></a>
              </li>
              <li>
                <a href="index.php#">See all messages</a>
              </li>
            </ul>
          </li><!-- inbox dropdown end -->
          <!-- lock start-->
          <li class="dropdown" id="header_inbox_bar">
            <a class="dropdown-toggle" href="lock_screen.php"><i class="fa fa-lock"></i></a>
          </li><!-- lock end -->
        </ul><!--  notification end -->
      </div>
      <div class="top-menu">
        <ul class="nav pull-right top-menu">
          <li>
            <a class="logout" href="logout.php">Logout</a>
          </li>
        </ul>
      </div>
    </header><!--header end-->
    <!-- **********************************************************************************************************************************************************
      MAIN SIDEBAR MENU
      *********************************************************************************************************************************************************** -->
    <!--sidebar start-->
    <aside>
      <div class="nav-collapse" id="sidebar">
        <!-- sidebar menu start-->
        <ul class="sidebar-menu" id="nav-accordion">
            <p class="centered"><a href="profile.php"><img class="img-circle" src="assets/img/user_default.png" width="60"></a></p>
            <h5 class="centered">Carl Williams</h5>
          <li class="mt">
            <a href="index.php"><i class="fa fa-home"></i> <span>Home</span></a>
          </li>
          <li class="sub-menu">
            <a href="assessment.php"><i class="fa fa-bar-chart"></i> <span>Records and Assessment</span></a>
          </li>
          <li class="sub-menu">
            <a class="active" href="quiz.php"><i class="fa fa-pencil"></i> <span>Quiz Monitoring</span></a>
          </li>
          

          <li class="sub-menu">
                      <a href="javascript:;" >
                          <i class="fa fa-list-alt" aria-hidden="true"></i>
                          <span>Course Content</span>
                      </a>
                      <ul class="sub">
                          <li><a  href="syllabus.php"><i class="fa fa-list-alt" aria-hidden="true"></i>Lessons</a></li>
                          
                      </ul>
                  </li>


          <li class="sub-menu">
            <a href="classes.php"><i class="fa fa-group"></i> <span>Classes</span></a>
          </li>
        </ul><!-- sidebar menu end-->
      </div>
    </aside><!--sidebar end-->
    <!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->
    <!--main content start-->
    <section id="main-content">
      <section class="wrapper site-min-height">
          <div class="row">
          <div class="nomargin">
          <img src="assets/img/quizmanagementgif.gif" width="100%">
          </div>
                    
          <div class="col-lg-12 main-content">
            <div class="row mt">
              <div class="form-panel">
                  <div class="form-group">
                    <h3><i class="fa fa-question-circle-o" aria-hidden="true"></i> Questions</h3>
                    <br>
                    <table cellspacing="0" class="table table-striped table-bordered table-hover" id="tbl_questions" width="100%">
                      <thead>
                        <tr>
                          <th>Questions</th>
                          <th>Choices</th>
                          <th>Right Answer</th>
                          <th>Difficulty</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <td> </td>
                          <td> </td>
                          <td></td>
                          <td></td>
                        </tr>
                        
                      </tbody>
                    </table>
                  </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </section><!-- /MAIN CONTENT -->
    <!--main content end-->
    <!--footer start-->
    <footer class="site-footer">
      <div class="text-center">
         All Rights Reserved. &copy; 2017 EDUTOO Developers. Template by <a href="http://alvarez.is/"> Alvarez.is </a> <a class="go-top" href="questions.php#"><i class="fa fa-angle-up"></i></a>
      </div>
    </footer><!--footer end-->
  </section>
  <!-- js placed at the end of the document so the pages load faster -->
    
    <!-- JS Scripts-->

    <!-- jQuery Js -->
    <script src="assets/js/jquery.js"></script>
    <script src="assets/js/jquery-1.8.3.min.js"></script>
    <script src="assets/js/jquery.scrollTo.min.js"></script>
    <script src="assets/js/jquery.nicescroll.js" type="text/javascript"></script>
    <script src="assets/js/jquery.sparkline.js"></script>
    <script class="include" type="text/javascript" src="assets/js/jquery.dcjqaccordion.2.7.js"></script>
    <script src="assets/js/dataTables/jquery.dataTables.min.js"></script>
    <script src="assets/js/dataTables/dataTables.bootstrap.min.js"></script>
    <!-- Bootstrap Js -->
    <script src="assets/js/bootstrap.min.js"></script>

    <script src="assets/js/chart-master/Chart.js"></script>
    
    <!--common script for all pages-->
    <script src="assets/js/common-scripts.js"></script>
    
    <script type="text/javascript" src="assets/js/gritter/js/jquery.gritter.js"></script>
    <script type="text/javascript" src="assets/js/gritter-conf.js"></script>

    <!--script for this page-->
    <script src="assets/js/sparkline-chart.js"></script>    
    <script src="assets/js/zabuto_calendar.js"></script>  

    <script type="text/javascript">
          $(document).ready(function(){
            $('#tbl_questions').DataTable();
        });
    </script>
</body>
</html>