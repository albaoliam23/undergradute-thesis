/*------------------------------------------------------
    Author : www.webthemez.com
    License: Commons Attribution 3.0
    http://creativecommons.org/licenses/by/3.0/
---------------------------------------------------------  */
var test = localStorage.loggedin;
if (test != "true") {
    window.location.replace("welcomepage.php");
}
if (localStorage.locked == "true") {
    window.location.replace("lock_screen.php");
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
var trigger_fileadd = false;
var texthere;
$(document).ready(function() {
    InitialLoad();
    $("#teachername").html(localStorage.teacher_name);
    var today = new Date().toISOString().split('.')[0]+"Z";
    $("#dt_expiration").attr('min', today);
    $(this).on("click", "#btn_addquestion", function(e){
        e.preventDefault();
        questionnum++;
        $("#questions_container").append("<div class = 'row' id = 'cont_"+questionnum+"'><div class = 'col-md-12'> <div class='panel-group'> <div class='panel panel-info'> <div class='panel-heading'> <h4 class='panel-title'> <a data-toggle='collapse' href='#q" + questionnum + "' id = 'titles_"+questionnum+"'>Question " + questionnum + "</a> </h4> </div> <div id='q" + questionnum + "' class='panel-collapse collapse'> <div class='panel-body'><form id = \"frm_q" + questionnum +"\" name = \"frm_q" + questionnum +"\" class = 'form-horizontal'><div class = 'form-group'><div class = 'col-md-3 pull-right'><button class = 'btn btn-danger' onclick = \"deleteQuestion(\'"+questionnum+"\');\"><i class='fa fa-ban'></i>&nbsp; Delete Question</button></div></div><div class = 'form-group'><div class = 'col-md-12'>Question<textarea class = 'form-control' id='q" + questionnum + "_question' name='q" + questionnum + "_question' style = 'resize:none;'></textarea></div></div><div class = 'form-group'><div class ='col-md-6'>Type <select class = 'form-control' id='q" + questionnum + "_type' name='q" + questionnum + "_type'><option>Multiple Choice</option><option>True/False</option></select></div></div><div id = 'canc_" + questionnum + "'></div></div></form></div> </div> </div> </div> </div></div>");
        question_forms.push("#frm_q" + questionnum);
        panel_titles.push("#titles_" + questionnum);
        $("body").append("<script>$(\"#q"+questionnum+"_type\").on('change', function(){ \n\t$(\"#canc_" + questionnum +"\").html(\"\");\n\tquestionType(\"frm_q" + questionnum +"\", $(\"#q"+questionnum+"_type\").val(), \"q" + questionnum +"\", \"canc_" + questionnum +"\"); \n});$('#q'+questionnum+'_type').val('"+$("#cmb_defaultType").val()+"');</script>");
        questionType("frm_q" + questionnum, $("#q"+questionnum+"_type").val(), "q" + questionnum, "canc_" + questionnum);
    });
    $(this).on("submit", "#cancy", function(e){
        e.preventDefault();
        var dt = new Date();
        var added = dt.toUTCString();
        var qtype = $("#cmb_quizType").val();
        var tlimit = $("#num_quizTimeLimit").val();
        var items = $("#num_quizItems").val();
        var rand = "no";
        var expiration = new Date($("#dt_expiration").val()).toUTCString();
        var started = "no";
        if($("#chk_randomize").is(':checked')){
            rand = "yes";
        }
        
        if(trigger_fileadd == true){
            var batchie = JSON.parse(texthere.replace(/\n/g, ""));
            if(batchie.length < items){
                alert('The number of question is less than the indicated number of items. The questions < number of items. Add more question/s.')
            }else if(batchie.length > items){
                alert('The number of question is greater than the indicated number of items. The questions > number of items. Remove question/s.')
            }else{
               if(qtype == "aq"){
                    var subj = CryptoJS.AES.decrypt(GetParameterValues("sj"), "Secret Passphrase").toString(CryptoJS.enc.Utf8);
                    var chap = CryptoJS.AES.decrypt(GetParameterValues("chap"), "Secret Passphrase").toString(CryptoJS.enc.Utf8);
                    var title = CryptoJS.AES.decrypt(GetParameterValues("title"), "Secret Passphrase").toString(CryptoJS.enc.Utf8);
                    firebase.database().ref("subjects/" + localStorage.memberkey + "/" + subj + "/" + chap + "/" + title + "/quiz/" ).set({
                        quiz_date_added: added,
                        quiz_type : qtype,
                        quiz_timelimit : tlimit,
                        quiz_numItems : items,
                        quiz_randomize: rand,
                        quiz_expiration: expiration,
                        quiz_started: started
                    }).then(function(){
                        batchie.forEach(function(x){
                            firebase.database().ref("subjects/" + localStorage.memberkey + "/" + subj + "/" + chap + "/" + title + "/quiz/questions/").push(
                                x
                            );
                        });
                    }).then(function(){
                        window.alert("Quiz successfully created");
                        window.location.replace("quiz.php");
                    });
                }else if(qtype == "ct"){
                    var subj = CryptoJS.AES.decrypt(GetParameterValues("subj"), "Secret Passphrase").toString(CryptoJS.enc.Utf8);
                    var title = CryptoJS.AES.decrypt(GetParameterValues("title"), "Secret Passphrase").toString(CryptoJS.enc.Utf8);
                    firebase.database().ref("subjects/" + localStorage.memberkey + "/" + subj + "/" + title + "/quiz/" ).set({
                        quiz_date_added: added,
                        quiz_type : qtype,
                        quiz_timelimit : tlimit,
                        quiz_randomize: rand,
                        quiz_numItems: items,
                        quiz_expiration: expiration,
                        quiz_started: started
                    }).then(function(){
                        batchie.forEach(function(x){
                            firebase.database().ref("subjects/" + localStorage.memberkey + "/" + subj + "/" + title + "/quiz/questions/").push(
                                x
                            );
                        });
                    }).then(function(){
                        window.alert("Chapter Test successfully created");
                        window.location.replace("quiz.php");
                    });
                } 
            } 
        }else{
            if(question_forms.length < items){
                alert('The number of question is less than the indicated number of items. The questions < number of items. Add more question/s.')
            }else if(question_forms.length > items){
                alert('The number of question is greater than the indicated number of items. The questions > number of items. Remove question/s.')
            }else{
               if(qtype == "aq"){
                    var subj = CryptoJS.AES.decrypt(GetParameterValues("sj"), "Secret Passphrase").toString(CryptoJS.enc.Utf8);
                    var chap = CryptoJS.AES.decrypt(GetParameterValues("chap"), "Secret Passphrase").toString(CryptoJS.enc.Utf8);
                    var title = CryptoJS.AES.decrypt(GetParameterValues("title"), "Secret Passphrase").toString(CryptoJS.enc.Utf8);
                    firebase.database().ref("subjects/" + localStorage.memberkey + "/" + subj + "/" + chap + "/" + title + "/quiz/" ).set({
                        quiz_date_added: added,
                        quiz_type : qtype,
                        quiz_timelimit : tlimit,
                        quiz_numItems : items,
                        quiz_randomize: rand,
                        quiz_expiration: expiration,
                        quiz_started: started
                    }).then(function(){
                        for (var i = 0 ; i < question_forms.length ; i++){
                            var hey = objectifyForm($(question_forms[i]).serializeArray());
                            firebase.database().ref("subjects/" + localStorage.memberkey + "/" + subj + "/" + chap + "/" + title + "/quiz/questions/").push(
                                hey
                            );
                        }
                    }).then(function(){
                        window.alert("Quiz successfully created");
                        window.location.replace("quiz.php");
                    });
                }else if(qtype == "ct"){
                    var subj = CryptoJS.AES.decrypt(GetParameterValues("subj"), "Secret Passphrase").toString(CryptoJS.enc.Utf8);
                    var title = CryptoJS.AES.decrypt(GetParameterValues("title"), "Secret Passphrase").toString(CryptoJS.enc.Utf8);
                    firebase.database().ref("subjects/" + localStorage.memberkey + "/" + subj + "/" + title + "/quiz/" ).set({
                        quiz_date_added: added,
                        quiz_type : qtype,
                        quiz_timelimit : tlimit,
                        quiz_randomize: rand,
                        quiz_numItems: items,
                        quiz_expiration: expiration,
                        quiz_started: started
                    }).then(function(){
                        for (var i = 0 ; i < question_forms.length ; i++){
                        var hey = objectifyForm($(question_forms[i]).serializeArray());
                        firebase.database().ref("subjects/" + localStorage.memberkey + "/" + subj + "/" + title + "/quiz/questions/").push(
                            hey
                        )};
                    }).then(function(){
                        window.alert("Chapter Test successfully created");
                        window.location.replace("quiz.php");
                    });
                } 
            } 
        } 
    });
    $("[type='number']").keypress(function (evt) {
        evt.preventDefault();
    });
    $( "#num_quizItems" ).trigger( "change" );
    $( "#num_quizItems[title]" ).qtip();
    $("#btn_addquestionfile").click(function(){
        question_forms = [];
        panel_titles = [];
        questionnum = 0;
        $("#questions_container").empty();
        $("#uploadmodal").modal('show');
    });
});
$("#num_quizItems").on("change", function(e){
    question_forms = [];
    panel_titles = [];
    questionnum = 0;
    $("#questions_container").empty();
    var numm = $("#num_quizItems").val();
    for(var i = 0 ; i < numm ; ++i){
        $( "#btn_addquestion" ).trigger( "click" );
    }

});
$("#file_questions").on('change', function(e){
    var file = $(this).prop('files')[0];
    var textType = /text.*/;

    if (file.type.match(textType)) {
        var reader = new FileReader();

        reader.onload = function(e) {
            $("#show_format").text(reader.result);
            texthere = reader.result;
        }
        reader.readAsText(file);    
    } else {
        $("#show_format").text("File not supported!");
    }
});
$("#btn_addqfile").on('click', function(e){
    $("#questions_container").empty();
    $("#questions_container").append("<pre>"+texthere+"</pre>");
    trigger_fileadd = true;
});
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
function InitialLoad(){
    var quizTitle_decrypted = CryptoJS.AES.decrypt(GetParameterValues("title"), "Secret Passphrase").toString(CryptoJS.enc.Utf8);
    $("#txt_quizTitle").val(quizTitle_decrypted);
    $("#cmb_quizType").val(GetParameterValues("type"));
}
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
        for(var k = 0 ; k < panel_titles.length ; k++){
            var cans = k+1;
            $(panel_titles[k]).html("Question " + cans);
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