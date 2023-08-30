<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Dashboard">
    <meta name="keyword" content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">

    <title>CLASSES | EDUTOO | INSTRUCTOR MODULE</title>

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
            <div class="nav notify-row" id="top_menu">
                
            </div>
            <div class="top-menu">
              <ul class="nav pull-right top-menu">
                    <li><a class="logout" href="logout.php">Logout</a></li>
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
              
                  <p class="centered"><img src="assets/img/user_default.png" class="img-circle" width="60"></p>
                  <h5 class="centered" id = "teachername"></h5>
                    
                  <li class="mt">
                      <a href="index.php"><i class="fa fa-home"></i> <span>Home</span></a>
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
                          <li><a  href="syllabus.php" class="active"><i aria-hidden="true" class="fa fa-list-alt"></i>Lessons</a></li>
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
                <img src="assets/img/recordsandassessmentgif.gif" style="width: 100%;">
                  
                  <div class="col-lg-12 main-content">
                  
                    <div class="row mt">
                      <div class="form-panel">
                        <div class="form-group">
                           <h3><i aria-hidden="true" class="fa fa-users"></i> Your Classes</h3><br>
                            <form action="addnewclass.php">
                             <button class="btn btn-info" type="submit"><i class="fa fa-plus"></i>&nbsp; Add Class</button>
                            </form>
                            <br>
                           <table cellspacing="0" class="table table-striped table-bordered table-hover" id="tbl_class">
                            <thead>
                              <tr>
                                <th>Code</th>
                                <th>Section</th>
                                <th>Subject/Curriculum</th>
                                <th>Academic Year</th>
                                <th>No. of Students</th>
                                <th>Date Created</th>
                                <th>Status</th>
                                <th>Members</th>
                                <th>Actions</th>
                              </tr>
                            </thead>
                           </table>
                        </div>
                      </div>
                    </div><!-- /row mt -->
                    
                  </div><!-- /col-lg-9 END SECTION MIDDLE -->
                  
                  
          </section>
      </section>

      <!--main content end-->
      <!--footer start-->
      <footer class="site-footer">
          <div class="text-center">
               All Rights Reserved. &copy; 2017 EDUTOO Developers. Template by <a href="http://alvarez.is/"> Alvarez.is </a>
              <a href="classes.php#" class="go-top">
                  <i class="fa fa-angle-up"></i>
              </a>
          </div>
      </footer>
      <!--footer end-->
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
    <script src="https://www.gstatic.com/firebasejs/3.6.2/firebase.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/aes.js"></script>
    <script type="text/javascript">
          $(document).ready(function(){
            $('#tbl_classes').DataTable();
        });
    </script>
    <script src = "assets/program-js/classes.js"></script>
  </body>
</html>
