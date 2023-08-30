<!DOCTYPE html>

<html lang="en">
<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">
  <meta content="" name="description">
  <meta content="Dashboard" name="author">
  <meta content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina" name="keyword">

  <title>ADD NEW SUBJECT | EDUTOO | INSTRUCTOR MODULE</title>
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
  <!--END DATATABLE-->
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
        <div class="fa fa-bars tooltips" data-original-title="Toggle Navigation" data-placement="right">
        </div>
      </div>
      <!--logo start-->
      <a class="logo" href="index.php"><img src="assets/img/logagotoo2.png" width="30">&nbsp;<b>EDUTOO</b></a> <!--logo end-->


      <div class="nav notify-row" id="top_menu">
        
      </div>


      <div class="top-menu">
        <ul class="nav pull-right top-menu">
          <li>
            <a class="logout" href="logout.php">Logout</a>
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
                      <a href="javascript:;" -class="active">
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
      <section class="wrapper site-min-height">
        <div class="row">
          <img src="assets/img/addsubjectgif.gif" width="100%">

          <div class="col-lg-12 main-content">
            <div class="row mt">
              <div class="form-panel">
                <div class="form-group">
                  <h3 id = "win_title"><i aria-hidden="true" class="fa fa-plus"></i>&nbsp;Add New Subject</h3>
                  <div class = "form-horizontal">
                    <div class="form-group">
                      <label class="col-sm-2 control-label"><div class="center">Subject Title</div></label>
                      <div class="col-md-4">
                        <input class="form-control" placeholder="Enter Subject title of Choice" required="" type="text" id = "txt_name">
                      </div>
                    </div>
                    <div class = "form-group"><label class = "col-md-12"><div class = "center" id = "subj_title"></div></label></div>
                    <div class="form-group">
                      <label class="col-sm-2 control-label"><div class="center">Subject Description</div></label>
                      <div class="col-md-4">
                        <textarea class="form-control" required="" id = "txt_desc"></textarea>
                      </div>
                    </div>

                    <div class="form-group">
                      <div class="pull-right">
                        <div class="col-sm-6">
                          <button class="btn btn-info" id="btn_add"><i aria-hidden="true" class="fa fa-plus"></i>&nbsp; Add Subject</button>
                        </div>
                        <div class="col-sm-2">
                          <a class="btn btn-danger" id="btn_cancel" href = "syllabus.php"><i class="fa fa-ban"></i>&nbsp; Cancel</a>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </section>
    <!-- /MAIN CONTENT -->
    <!--main content end-->
    <!--footer start-->

    <!--MODAL START-->
    <!-- Modal -->
    <div class="modal fade" id="modal_success" role="dialog">
      <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
          <div class="modal-header">
            <button class="close" data-dismiss="modal" type="button">&times;</button>
            <h4 class="modal-title"><i class="fa fa-graduation-cap"></i>&nbsp;Edit : Education Background</h4>
          </div>
          <div class="modal-body">
            Subject successfully added!
          </div>
          <div class="modal-footer">
            <button class="btn btn-default" data-dismiss="modal" type="button">OK</button>
          </div>
        </div>
      </div>
    </div><!--MODAL END-->
    <footer class="site-footer">
      <div class="text-center">
         All Rights Reserved. &copy; 2017 EDUTOO Developers. Template by <a href="http://alvarez.is/"> Alvarez.is </a> <a class="go-top" href="addnewsubject.php#"><i class="fa fa-angle-up"></i></a>
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
    <script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/aes.js"></script>
    <script src="https://www.gstatic.com/firebasejs/3.6.2/firebase.js"></script>
    <script src="assets/program-js/addnewsubject.js"></script>
</body>
</html>