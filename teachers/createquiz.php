<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="" name="description">
    <meta content="Dashboard" name="author">
    <meta content="Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina" name="keyword">
    <title>CREATE QUIZ | EDUTOO | INSTRUCTOR MODULE</title>

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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/qtip2/2.0.0/basic/jquery.qtip.min.css" />

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
            </div>
            <!--logo start-->
            <a class="logo" href="index.php"><img src="assets/img/logagotoo2.png" width="30">&nbsp;<b>EDUTOO</b>
            </a>
            <!--logo end-->
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
            <section class="wrapper site-min-height">
                <div class="row">
                    <div class="nomargin">
                        <img src="assets/img/quizmanagementgif.gif" width="100%">
                    </div>
                    <div class="col-lg-12 main-content">
                        <div class="row mt">
                            <div class="form-panel">
                                <div class="form-group">
                                    <h3><i class="fa fa-pencil-square-o" aria-hidden="true"></i> Create Quiz</h3>

                                    <form class="form-horizontal" id = "cancy">
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <h4>Quiz Properties</h4>
                                                <div class="col-sm-3">
                                                  Quiz Title
                                                    <input type="text" class="form-control" placeholder="Quiz Title" id="txt_quizTitle" readonly required>
                                                </div>
                                                <div class="col-sm-3">
                                                  Quiz Type
                                                    <select class="form-control" id="cmb_quizType" readonly required>
                                                        <option value='ct'>Chapter Test</option>
                                                        <option value='aq'>After-Lesson Quiz</option>
                                                    </select>
                                                </div>

                                                <div class="col-sm-1">
                                                    Time Limit(Seconds)
                                                    <input type="number" class="form-control" placeholder="Time Limit(Seconds)" id="num_quizTimeLimit" required min="1" value = "1">
                                                </div>

                                                <div class="col-sm-1">
                                                    Number of Items
                                                    <input type="number" class="form-control" placeholder="Number of Items" id="num_quizItems" title = "Changing the number of items resets the questions in the quiz." required min="3" value="3">
                                                </div>
                                                <div class="col-sm-2">
                                                    Expiration Date
                                                    <input type="datetime-local" class="form-control" id="dt_expiration" required>
                                                </div>
                                                <div class="col-sm-1">
                                                    <label>
                                                        <input type="checkbox" id="chk_randomize"> Randomize
                                                    </label>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <div class="col-sm-3">
                                                </div>
                                                <div class="col-sm-3">
                                                  Default Question Type
                                                    <select class="form-control" id="cmb_defaultType">
                                                        <option>Multiple Choice</option>
                                                        <option>True/False</option>
                                                    </select>
                                                </div>

                                                <div class="col-sm-1">
                            
                                                </div>

                                                <div class="col-sm-1">
                                                    
                                                </div>
                                                <div class="col-sm-2">
                                                   
                                                </div>
                                                <div class="col-sm-1">
                                                    
                                                </div>
                                            </div>
                                        </div>
                                        <div id = "questions_container">

                                        </div>
                                        <div class="form-group">
                                            <div class="col-md-2">
                                                <button class="btn btn-info" id="btn_addquestion" type = "button"><i class="fa fa-plus"></i>&nbsp; Add Question </button>
                                            </div>
                                            <div class="col-md-2">
                                                <button class="btn btn-info" id="btn_addquestionfile" type = "button"><i class="fa fa-plus"></i>&nbsp; Add Questions from File </button>
                                            </div>

                                            <div class="col-md-3 pull-right">
                                                <button type="submit" class="btn btn-info" id="btn_add" onclick = "return confirm('Create new quiz?');"><i class="fa fa-floppy-o" aria-hidden="true"></i>&nbsp; Done</button>
                                                <a href = 'index.php' class="btn btn-danger" onclick = "return confirm('Cancel quiz creation and go back to homepage?');"><i class="fa fa-ban"></i>&nbsp; Cancel</a>
                                            </div>
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
        <footer class="site-footer">
            <div class="text-center">
                All Rights Reserved. &copy; 2017 EDUTOO Developers. Template by <a href="http://alvarez.is/"> Alvarez.is </a> <a class="go-top" href="createquiz.php#"><i class="fa fa-angle-up"></i></a>
            </div>
        </footer>
        <!--footer end-->
    </section>
    <!--MODAL START-->
    <!-- Modal -->
    <div class="modal fade" id="uploadmodal" role="dialog">
      <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
          <div class="modal-header">
            <button class="close" data-dismiss="modal" type="button">&times;</button>
            <h4 class="modal-title"><i aria-hidden="true" class="fa fa-id-card"></i> Upload Questions File (.txt)</h4>
          </div>
          <div class="modal-body">
            <div class = form-horizontal>
                <h4>Copy paste Format</h4>
                <pre id = "show_format">
                      "questions" : {
                        {
                          "q_answer" : "True",
                          "q_question" : "a",
                          "q_type" : "True/False"
                        },
                        {
                          "q_answer" : "False",
                          "q_question" : "ab",
                          "q_type" : "True/False"
                        },
                        {
                          "q_answer" : "False",
                          "q_question" : "abc",
                          "q_type" : "True/False"
                        },
                        {
                          "q_answer" : "True",
                          "q_question" : "abcd",
                          "q_type" : "True/False"
                        }
                      }
                </pre>
            </div>
            <div class="form-horizontal style-form">
              <input type = "file" class="form-control" id = "file_questions">
            </div>
          </div>
          <div class="modal-footer">
            <button class="btn btn-default" data-dismiss="modal" type="button" id = "btn_addqfile">Add</button>
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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/aes.js"></script>
    <script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
    <script src="https://www.gstatic.com/firebasejs/3.6.2/firebase.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/qtip2/2.0.0/basic/jquery.qtip.min.js"></script>
    <script src="assets/program-js/createquiz.js"></script>
</body>

</html>