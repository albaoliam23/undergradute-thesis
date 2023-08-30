var test = localStorage.loggedin;
    if(test != "true"){
        window.location.replace("welcomepage.php");
}
if (localStorage.locked == "true") {
    window.location.replace("lock_screen.php");
}
var config = {
  apiKey: "AIzaSyD0CsnZgDBGSFBVrtZH0HRBbVQWk__txe0",
  authDomain: "ezbio-98be1.firebaseapp.com",
  databaseURL: "https://ezbio-98be1.firebaseio.com",
  storageBucket: "ezbio-98be1.appspot.com",
  messagingSenderId: "595946963410"
};
firebase.initializeApp(config);
var populationdata = [];
(function ($) {
    "use strict";
    var mainApp = {

        initFunction: function () {
            /*MENU 
            ------------------------------------*/
            $('#main-menu').metisMenu();
			
            $(window).bind("load resize", function () {
                if ($(this).width() < 768) {
                    $('div.sidebar-collapse').addClass('collapse')
                } else {
                    $('div.sidebar-collapse').removeClass('collapse')
                }
            });

            /* MORRIS BAR CHART
			-----------------------------------------*/
            Morris.Bar({
                element: 'student-population',
                data: populationdata,
                xkey: 'y',
                ykeys: ['a', 'b'],
                labels: ['Male', 'Female'],
				 barColors: [
                    '#6ac100','#fc4646',
                    '#A8E9DC' 
                  ],
                hideHover: 'auto',
                resize: true
            });
            Morris.Bar({
                element: 'overall-class-performance-bar',
                data: [{
                    y: 'STEM-12-1',
                    a: 1,
                    b: 0
                }, {
                    y: 'STEM-12-2',
                    a: 1,
                    b: 1
                }],
                xkey: 'y',
                ykeys: ['a', 'b'],
                labels: ['Male', 'Female'],
                 barColors: [
                    '#6ac100','#fc4646',
                    '#A8E9DC' 
                  ],
                hideHover: 'auto',
                resize: true
            });
	 
            $('.bar-chart').cssCharts({type:"bar"});
            
            $('.pie-thychart').cssCharts({type:"pie"});
       
	 
        },

        initialization: function () {
            mainApp.initFunction();

        }

    }
    // Initializing ///
    $(document).ready(function () {
		$("#sideNav").click(function(){
			if($(this).hasClass('closed')){
				$('.navbar-side').animate({left: '0px'});
				$(this).removeClass('closed');
				$('#page-wrapper').animate({'margin-left' : '260px'});
				
			}
			else{
			    $(this).addClass('closed');
				$('.navbar-side').animate({left: '-260px'});
				$('#page-wrapper').animate({'margin-left' : '0px'}); 
			}
		});
        firebase.database().ref("classes").orderByKey().once('value').then(function(snapshot) {
            snapshot.forEach(function(childSnapshot) {
                var section;
                if(childSnapshot.val().grp_teacher == sessionStorage.teacher){
                    section = childSnapshot.val().grp_name;
                    firebase.database().ref("classes/"+childSnapshot.key+"/member").orderByKey().once('value').then(function(snapshot) {
                        var num_male = 0, num_female = 0;
                        snapshot.forEach(function(childSnapshot) {
                            if(childSnapshot.val().gender == "Male"){
                                num_male++;
                            }else{
                                num_female++;
                            }
                        });
                        populationdata.push({y: section, a: parseInt(num_male), b: parseInt(num_female)}); 
                    });
                }
            });
        });
    });
}(jQuery));