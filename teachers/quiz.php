<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Dashboard">
    <meta name="keyword" content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">

    <title>QUIZZES | EDUTOO | INSTRUCTOR MODULE</title>

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
                <div class="fa fa-bars tooltips" data-placement="right" data-original-title="Toggle Navigation"></div>
            </div>
            <!--logo start-->
            <a href="index.php" class="logo"><img src="assets/img/logagotoo2.png" width="30">&nbsp;<b>EDUTOO</b>
            </a>
            <!--logo end-->
            <div class="nav notify-row" id="top_menu">
                
            </div>
            <div class="top-menu">
                <ul class="nav pull-right top-menu">
                    <li><a class="logout" href="logout.php">Logout</a>
                    </li>
                </ul>
            </div>
        </header>
        <!--header end-->

        <!-- **********************************************************************************************************************************************************
      MAIN SIDEBAR MENU
      *********************************************************************************************************************************************************** -->
        <!--sidebar start-->
        <aside>
            <div id="sidebar" class="nav-collapse ">
                <!-- sidebar menu start-->
                <ul class="sidebar-menu" id="nav-accordion">

                    <p class="centered">
                        <img src="assets/img/user_default.png" class="img-circle" width="60">
                    </p>
                    <h5 class="centered" id = "teachername"></h5>

                    <li class="mt">
                        <a href="index.php"><i class="fa fa-home"></i> <span>Home</span></a>
                    </li>
                    <li class="sub-menu">
                        <a href="assessment.php"><i class="fa fa-bar-chart"></i> <span>Records and Assessment</span></a>
                    </li>
                    <li class="sub-menu">
                        <a href="quiz.php" class="active"><i class="fa fa-pencil"></i> <span>Quiz Monitoring</span></a>
                    </li>
                    <li class="sub-menu">
                        <a href="javascript:;">
                            <i class="fa fa-list-alt"></i>
                            <span>Course Content</span>
                        </a>
                        <ul class="sub">
                            <li><a href="syllabus.php"><i aria-hidden="true" class="fa fa-list-alt"></i>Lessons</a>
                            </li>
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
                    <img src="assets/img/quizmanagementgif.gif" style="width: 100%;">

                    <div class="col-lg-12 main-content">

                        <div class="row mt">
                            <div class="form-panel">
                              <div class = "form-horizontal">
                                <h3><i aria-hidden="true" class="fa fa-pencil-square-o"></i> Quizzes</h3>
                                <div class="form-group">
                                    <div class="col-md-6">
                                      <label class="form-control-label">Select Class</label>
                                      <div class = "row">
                                        <div class = "col-md-6">
                                            <select class="form-control" id="cmb_subjects">

                                            </select> 
                                        </div>  
                                      </div>
                                    </div>
                                  </div>
                                </div>
                                <div class = "form-group" id = "container_subjects"></div>
                              </div>
                            </div>
                        </div>
                        <!-- /row mt -->

                    </div>
                    <!-- /col-lg-9 END SECTION MIDDLE -->


            </section>
        </section>

        <!--main content end-->
        <!--footer start-->
        <footer class="site-footer">
            <div class="text-center">
                All Rights Reserved. &copy; 2017 EDUTOO Developers. Template by <a href="http://alvarez.is/"> Alvarez.is </a>
                <a href="quiz.php#" class="go-top">
                    <i class="fa fa-angle-up"></i>
                </a>
            </div>
        </footer>
        <!--footer end-->
    </section>
    <!--MODAL START-->
    <!-- Modal -->
    <div class="modal fade" id="extendmodal" role="dialog">
      <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
          <div class="modal-header">
            <button class="close" data-dismiss="modal" type="button">&times;</button>
            <h4 class="modal-title"><i aria-hidden="true" class="fa fa-id-card"></i> Extend Quiz Expiration</h4>
          </div>
          <div class="modal-body">
            <div class="form-horizontal style-form">
              <input type = "datetime-local" class="form-control" id = "dt_extend">
            </div>
          </div>
          <div class="modal-footer">
            <button class="btn btn-default" data-dismiss="modal" type="button" id = "btn_extend">Extend</button>
          </div>
        </div>
      </div>
    </div><!--MODAL END-->

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
        $(document).ready(function() {
            $('#tbl_quizzes').DataTable();
        });
    </script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/aes.js"></script>
    <script src="https://www.gstatic.com/firebasejs/3.6.2/firebase.js"></script>
    <script src="assets/js/moment.js"></script>
    <script src="assets/program-js/quizmonitoring.js"></script>
</body>

</html>