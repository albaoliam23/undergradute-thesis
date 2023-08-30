/*------------------------------------------------------
    Author : www.webthemez.com
    License: Commons Attribution 3.0
    http://creativecommons.org/licenses/by/3.0/
---------------------------------------------------------  */
var test = localStorage.loggedin;
if (test != "true") {
    window.location.replace("welcomepage.php");
}
var selected_chapter = "";
var config = {
    apiKey: "AIzaSyD0CsnZgDBGSFBVrtZH0HRBbVQWk__txe0",
    authDomain: "ezbio-98be1.firebaseapp.com",
    databaseURL: "https://ezbio-98be1.firebaseio.com",
    storageBucket: "ezbio-98be1.appspot.com",
    messagingSenderId: "595946963410"
};
firebase.initializeApp(config);
var question_forms = new Array();
var panel_titles = new Array();
var questionnum = 0;
$(document).ready(function() {
    $("#teachername").html(localStorage.teacher_name);
    $(this).on("click", "#btn_addquestion", function(e){
        e.preventDefault();
        questionnum++;
        $("#questions_container").append("<div class = 'row' id = 'cont_"+questionnum+"'><div class = 'col-md-12'> <div class='panel-group'> <div class='panel panel-info'> <div class='panel-heading'> <h4 class='panel-title'> <a data-toggle='collapse' href='#q" + questionnum + "' id = 'titles_"+questionnum+"'>Question " + questionnum + "</a> </h4> </div> <div id='q" + questionnum + "' class='panel-collapse collapse'> <div class='panel-body'><form id = \"frm_q" + questionnum +"\" name = \"frm_q" + questionnum +"\" class = 'form-horizontal'><div class = 'form-group'><div class = 'col-md-3 pull-right'><button class = 'btn btn-danger' onclick = \"deleteQuestion(\'"+questionnum+"\');\"><i class='fa fa-ban'></i>&nbsp; Delete Question</button></div></div><div class = 'form-group'><div class = 'col-md-12'>Question<textarea class = 'form-control' id='q" + questionnum + "_question' name='q" + questionnum + "_question' style = 'resize:none;'></textarea></div></div><div class = 'form-group'><div class ='col-md-6'>Type <select class = 'form-control' id='q" + questionnum + "_type' name='q" + questionnum + "_type'><option>Multiple Choice</option><option>True/False</option><option>Fill in the Blanks</option><option>Matching Type</option></select></div></div><div id = 'canc_" + questionnum + "'></div></div></form></div> </div> </div> </div> </div></div>");
        question_forms.push("#frm_q" + questionnum);
        panel_titles.push("#titles_" + questionnum);
        $("body").append("<script>$(\"#q"+questionnum+"_type\").on('change', function(){ \n\t$(\"#canc_" + questionnum +"\").html(\"\");\n\tquestionType(\"frm_q" + questionnum +"\", $(\"#q"+questionnum+"_type\").val(), \"q" + questionnum +"\", \"canc_" + questionnum +"\"); \n});</script>");
        questionType("frm_q" + questionnum, $("#q"+questionnum+"_type").val(), "q" + questionnum, "canc_" + questionnum);
    });
    $(this).on("submit", "#cancy", function(e){
        e.preventDefault();
        var dt = new Date();
        var added = dt.toUTCString();
        var qtype = "ct";
        var tlimit = $("#num_quizTimeLimit").val();
        var items = $("#num_quizItems").val();
        var qname = $("#txt_quizTitle").val();
        var rand = "no";
        if($("#chk_randomize").is(':checked')){
            rand = "yes";
        }
        if(question_forms.length+1 < items){
            alert('The number of question is less than the indicated number of items. The questions <= number of items. Add more questions.')
        }else{
            var where = $("input[name='rdo_quizfor']:checked").val();
            var loc = $("#cmb_cs option:selected").data('key');
           if(where == "Class"){
                var push1 = firebase.database().ref("classes/"+loc+"/custom_quizzes").push({
                    quiz_date_added: added,
                    quiz_type : qtype,
                    quiz_timelimit : tlimit,
                    quiz_numItems : items,
                    quiz_randomize: rand,
                    quiz_title : qname
                });
                var push1key = push1.key;
                push1.then(function(){
                for (var i = 0 ; i < question_forms.length ; i++){
                var hey = objectifyForm($(question_forms[i]).serializeArray());
                    firebase.database().ref("classes/"+loc+"/custom_quizzes/"+push1key+"/questions").push(
                        hey
                    )};
                }).then(function(){
                    window.alert("Quiz successfully created");
                    window.location.replace("quiz.php");
                });
            }else if(where == "Subject"){
                var push2 = firebase.database().ref("subjects/"+localStorage.memberkey+"/"+loc+"/custom_quizzes").push({
                    quiz_date_added: added,
                    quiz_type : qtype,
                    quiz_timelimit : tlimit,
                    quiz_numItems : items,
                    quiz_randomize: rand,
                    quiz_title : qname
                });
                var push2key = push2.key;
                push2.then(function(){
                for (var i = 0 ; i < question_forms.length ; i++){
                var hey = objectifyForm($(question_forms[i]).serializeArray());
                    firebase.database().ref("subjects/"+localStorage.memberkey+"/"+loc+"/custom_quizzes/"+push2key+"/questions").push(
                        hey
                    )};
                }).then(function(){
                    window.alert("Quiz successfully created");
                    window.location.replace("quiz.php");
                });
            } 
        }
        
    });
});

function logout() {
    firebase.auth().signOut().then(function() {
        localStorage.clear();
        window.location.replace("logout.php");
    }, function(error) {
        alert('Sign Out Error', error);
    });
}
function GetParameterValues(param) {  
    var url = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');  
    for (var i = 0; i < url.length; i++) {  
        var urlparam = url[i].split('=');  
        if (urlparam[0] == param) {  
            return urlparam[1];  
        }  
    }  
}
var choiceCount = 0;
$("input[name='rdo_quizfor']").change(function() {
    $("#cmb_cs").empty();
    if(this.value == "Class"){
        firebase.database().ref("classes").orderByChild("grp_date_created").once('value').then(function(snapshot){
            snapshot.forEach(function(childSnapshot){
                if(childSnapshot.val().grp_teacher_key == localStorage.memberkey && childSnapshot.val().grp_status == "Active"){
                    $("#cmb_cs").append("<option value = '"+childSnapshot.val().grp_name+"' data-key = '"+childSnapshot.key+"'>"+childSnapshot.val().grp_name+"("+childSnapshot.val().grp_year+")</option>");
                }
            });
        });
    }else if(this.value == "Subject"){
        firebase.database().ref("subjects/"+localStorage.memberkey).orderByKey().once('value').then(function(snapshot){
            snapshot.forEach(function(childSnapshot){
                $("#cmb_cs").append("<option value = '"+childSnapshot.key+"' data-key = '"+childSnapshot.key+"'>"+childSnapshot.key+"</option>");
            });
        });
    }
});
function objectifyForm(formArray) {//serialize data function

  var returnArray = {};
  for (var i = 0; i < formArray.length; i++){
    if((formArray[i]['name']).match(/_type/))
        formArray[i]['name'] = "q_type";
    if((formArray[i]['name']).match(/_answer/))
        formArray[i]['name'] = "q_answer";
    if((formArray[i]['name']).match(/_question/))
        formArray[i]['name'] = "q_question";
    returnArray[formArray[i]['name']] = formArray[i]['value'];
  }
  return returnArray;
}
function questionType(formid, type, questionIndex, cancIndex){
    if(type == "Multiple Choice"){
        $("#"+cancIndex).append("<div class = 'form-group has-success'><div class = 'col-md-3'>Right Answer<input class = 'form-control' type = 'text' id = '" + questionIndex + "_answer' name = '" + questionIndex + "_answer' readonly></div></div><div class = 'form-group'><div class = 'col-md-3'><button type = 'button' class = 'btn btn-info' id = \""+questionIndex+"_addchoice\" onclick = \"addChoice(\'" + formid + "\',\'" + type + "\', \'" + questionIndex + "\', \'" + cancIndex + "\')\" required>+Add Choice</button></div></div></div>");
        addChoice(formid, type, questionIndex, cancIndex);
        addChoice(formid, type, questionIndex, cancIndex);
    }else if(type == "True/False"){
        $("#"+cancIndex).append("<div class = 'row'><div class = 'col-md-3 has-success'>Answer<select class = 'form-control' id = '" + questionIndex + "_answer' name = '" + questionIndex + "_answer'><option>True</option><option>False</option></select></div></div>");
    }
}
function addChoice(formid, type, questionIndex, cancIndex){
    choiceCount++;
    $("#"+ cancIndex).append("<div class = 'form-group' id = 'cont_" + questionIndex + "_" + choiceCount + "'><div class = 'col-md-6'><input class = 'form-control' type = 'text' id = '" + questionIndex + "_" + choiceCount + "' name = '" + questionIndex + "_" + choiceCount + "'></div><div class = 'col-md-3'><button type = 'button' id = 'right_" + questionIndex + "_" + choiceCount +"' class = 'btn btn-info' onclick = \"right_ans(\'"+ questionIndex + "\', \'" + choiceCount + "\');\">This is the right answer</button></div><div class = 'col-md-3'><button type = 'button' id = 'right_" + questionIndex + "_" + choiceCount +"' class = 'btn btn-danger' onclick = \"deleteChoice(\'"+ questionIndex + "\', \'" + choiceCount + "\');\">Delete this choice</button></div></div>");
}

function deleteQuestion(qNum){
    $("#cont_"+qNum).remove();
     question_forms.splice($.inArray("#frm_q" + qNum, question_forms),1);
     panel_titles.splice($.inArray("#titles_" + qNum, panel_titles),1);
     if (panel_titles.length == 0){
        questionnum = 0;
     }else{
        for(var i = 0 ; i < panel_titles.length ; i++){
            var cans = i+1;
            $(panel_titles[i]).html("Question " + cans);
        }
     }
}
function deleteChoice(qI,cc){
    $("#cont_"+qI+"_"+cc).remove();
}
function right_ans(qI, cc){
    var ans = $("#"+qI+"_"+cc).val();
    $("#"+qI+"_answer").val(ans);
}