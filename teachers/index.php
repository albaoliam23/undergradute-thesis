<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Dashboard">
    <meta name="keyword" content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">

    <title>DASHBOARD | EDUTOO | INSTRUCTOR MODULE</title>

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

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

  <section id="container" >
      <!-- **********************************************************************************************************************************************************
      TOP BAR CONTENT & NOTIFICATIONS
      *********************************************************************************************************************************************************** -->
      <!--header start-->
      <header class="header black-bg">
              <div class="sidebar-toggle-box">
                  <div class="fa fa-bars tooltips" data-placement="right" data-original-title="Toggle Navigation"></div>
              </div>
            <!--logo start-->
            <a href="index.php" class="logo"><img src="assets/img/logagotoo2.png" width="30">&nbsp;<b>EDUTOO</b></a>
            <!--logo end-->
            <div class="nav notify-row" id="top_menu"></div>
            <div class="top-menu">
              <ul class="nav pull-right top-menu">
                    <li><a class="logout" href = "logout.php">Logout</a></li>
              </ul>
            </div>
        </header>
      <!--header end-->
      
      <!-- **********************************************************************************************************************************************************
      MAIN SIDEBAR MENU
      *********************************************************************************************************************************************************** -->
      <!--sidebar start-->
      <aside>
          <div id="sidebar"  class="nav-collapse ">
              <!-- sidebar menu start-->
              <ul class="sidebar-menu" id="nav-accordion">
              
                  <p class="centered"><a href="profile.php"><img src="assets/img/user_default.png" class="img-circle" width="60"></a></p>
                  <h5 class="centered" id = "teachername"></h5>
                    
                  <li class="mt">
                      <a class="active" href="index.php"><i class="fa fa-home"></i> <span>Home</span></a>
                  </li>
                  <li class="sub-menu">
                    <a href="assessment.php"><i class="fa fa-bar-chart"></i> <span>Records and Assessment</span></a>
                  </li>
                  <li class="sub-menu">
                    <a href="quiz.php"><i class="fa fa-pencil"></i> <span>Quiz Monitoring</span></a>
                  </li>
                  <li class="sub-menu">
                      <a href="javascript:;" >
                          <i class="fa fa-list-alt"></i>
                          <span>Course Content</span>
                      </a>
                      <ul class="sub">
                          <li><a  href="syllabus.php"><i aria-hidden="true" class="fa fa-list-alt"></i>Lessons</a></li>
                      </ul>
                  </li>
                  <li class="sub-menu">
                    <a href="classes.php"><i class="fa fa-group"></i> <span>Classes</span></a>
                  </li>

              </ul>
              <!-- sidebar menu end-->
          </div>
      </aside>
      <!--sidebar end-->
      
      <!-- **********************************************************************************************************************************************************
      MAIN CONTENT
      *********************************************************************************************************************************************************** -->
      <!--main content start-->
      <section id="main-content">
          <section class="wrapper">

              <div class="row">
                <img src="assets/img/dashboardgif.gif" style="width: 100%;">
                  <div class="col-lg-12 main-chart">
                  
                    <div class="row mt">
                      <div class="col-lg-6">
                        <div class="form-panel">
                          <div class="form-group">
                            <h3>
                              <i aria-hidden="true" class="fa fa-percent"></i> Overall Class Performance
                            </h3>
                            <!--CUSTOM CHART START -->
                            <div class="custom-bar-chart">
                                <ul class="y-axis">
                                    <li><span>10.000</span></li>
                                    <li><span>8.000</span></li>
                                    <li><span>6.000</span></li>
                                    <li><span>4.000</span></li>
                                    <li><span>2.000</span></li>
                                    <li><span>0</span></li>
                                </ul>
                                <div class="bar">
                                    <div class="title">JAN</div>
                                    <div class="value tooltips" data-original-title="8.500" data-toggle="tooltip" data-placement="top">85%</div>
                                </div>
                                <div class="bar ">
                                    <div class="title">FEB</div>
                                    <div class="value tooltips" data-original-title="5.000" data-toggle="tooltip" data-placement="top">50%</div>
                                </div>
                                <div class="bar ">
                                    <div class="title">MAR</div>
                                    <div class="value tooltips" data-original-title="6.000" data-toggle="tooltip" data-placement="top">60%</div>
                                </div>
                                <div class="bar ">
                                    <div class="title">APR</div>
                                    <div class="value tooltips" data-original-title="4.500" data-toggle="tooltip" data-placement="top">45%</div>
                                </div>
                                <div class="bar">
                                    <div class="title">MAY</div>
                                    <div class="value tooltips" data-original-title="3.200" data-toggle="tooltip" data-placement="top">32%</div>
                                </div>
                                <div class="bar ">
                                    <div class="title">JUN</div>
                                    <div class="value tooltips" data-original-title="6.200" data-toggle="tooltip" data-placement="top">62%</div>
                                </div>
                                <div class="bar">
                                    <div class="title">JUL</div>
                                    <div class="value tooltips" data-original-title="7.500" data-toggle="tooltip" data-placement="top">75%</div>
                                </div>
                            </div>
                            <!--custom chart end-->
                          </div>
                        </div>
                      </div>
                      <div class="col-lg-6">
                        <div class="form-panel">
                          <div class="form-group">
                            <h3>
                              <i aria-hidden="true" class="fa fa-sort-amount-desc"></i> Top 10 Students
                            </h3><br>
                             <div class="col-md-12">
                              <div class="form-group">
                                <label class="form-control-label">Select Class</label> 
                                <select class="form-control" id="cmb_class">
                                  <option>STEM-12-1</option>
                                  <option>STEM-12-2</option>
                                  <option>STEM-12-3</option>
                                  <option>STEM-12-4</option>
                                  <option>STEM-12-5</option>
                                </select>
                              </div>
                            </div><br>
                            <table cellspacing="0" class="table table-striped table-bordered table-hover">
                              <thead>
                                <tr>
                                    <th>#</th>
                                    <th>First Name</th>
                                    <th>Last Name</th>
                                    <th>Username</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>1</td>
                                    <td>Mark</td>
                                    <td>Otto</td>
                                    <td>@mdo</td>
                                </tr>
                                <tr>
                                    <td>2</td>
                                    <td>Jacob</td>
                                    <td>Thornton</td>
                                    <td>@fat</td>
                                </tr>
                                <tr>
                                    <td>3</td>
                                    <td>Larry</td>
                                    <td>the Bird</td>
                                    <td>@twitter</td>
                                </tr>
                              </tbody>
                            </table>
                          </div>
                        </div>
                      </div>
                    </div>
                    <!--CUSTOM CHART START -->
                    <div class="row mt">
                      <div class="col-lg-6">
                        <div class="form-panel">
                          <div class="form-group">
                            <h3>
                              <i aria-hidden="true" class="fa fa-users"></i> Student Performance
                            </h3>
                            <br>
                          </div>
                        </div>
                      </div>
                    </div>
                    <!-- /row mt -->  
                        
                    </div><!-- /row -->
                    
                  </div><!-- /col-lg-9 END SECTION MIDDLE -->
                  
                  
          </section>
      </section>

      <!--main content end-->
      <!--footer start-->
      <footer class="site-footer">
          <div class="text-center">
              All Rights Reserved. &copy; 2017 EDUTOO Developers. Template by <a href="http://alvarez.is/"> Alvarez.is </a>
              <a href="index.php#" class="go-top">
                  <i class="fa fa-angle-up"></i>
              </a>
          </div>
      </footer>
      <!--footer end-->
  </section>

    <!-- js placed at the end of the document so the pages load faster -->
    
    <script src="assets/js/chart-master/Chart.js"></script>
    <script src="assets/js/jquery.js"></script>
    <script src="assets/js/jquery-1.8.3.min.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <script class="include" type="text/javascript" src="assets/js/jquery.dcjqaccordion.2.7.js"></script>
    <script src="assets/js/jquery.scrollTo.min.js"></script>
    <script src="assets/js/jquery.nicescroll.js" type="text/javascript"></script>
    <script src="assets/js/jquery.sparkline.js"></script>


    <!--common script for all pages-->
    <script src="assets/js/common-scripts.js"></script>
    
    <script type="text/javascript" src="assets/js/gritter/js/jquery.gritter.js"></script>
    <script type="text/javascript" src="assets/js/gritter-conf.js"></script>

    <!--script for this page-->
    <script src="assets/js/sparkline-chart.js"></script>    
    <script src="assets/js/zabuto_calendar.js"></script>  
    <!-- FIREBASE -->
     <script src="https://www.gstatic.com/firebasejs/3.6.2/firebase.js"></script>
  
    <script type="text/javascript">
          $(document).ready(function () {
                window.location.href = "assessment.php";
              var teacherkey = localStorage.memberkey;
              firebase.database().ref("members/"+teacherkey).orderByKey().once('value').then(function(snapshot) {
                localStorage.teacher_name = snapshot.val().firstname + " " + snapshot.val().lastname;
              }).then(function(){
                var unique_id = $.gritter.add({
                // (string | mandatory) the heading of the notification
                title: localStorage.teacher_name,
                // (string | mandatory) the text inside the notification
                text: 'Using EduToo, you can manage your classes like a pro!',
                // (string | optional) the image to display on the left
                image: 'assets/img/user_default.png',
                // (bool | optional) if you want it to fade out on its own or just sit there
                sticky: true,
                // (int | optional) the time you want it to be alive for before fading out
                time: '',
                // (string | optional) the class name you want to apply to that specific message
                class_name: 'my-sticky-class'
            });
                $("#teachername").html(localStorage.teacher_name);
          });
          return false;
          });
    </script>
    <script src = "assets/program-js/index.js"></script>
    
  </body>
</html>
