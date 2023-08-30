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
$(document).ready(function() {
    $("#teachername").html(localStorage.teacher_name);
    var lesson_titles = new Array();
    firebase.database().ref("subjects/" + localStorage.memberkey).orderByKey().once('value').then(function(snapshot) {
        snapshot.forEach(function(childSnapshot) {
            $('#cmb_subjects').append("<option value = '" + childSnapshot.key + "'> " + childSnapshot.key + "</option>")
        });
    }).then(function() {
        loadSubject();
    });
    $(window).bind('beforeunload',function(){
        sessionStorage.removeItem('editongoing');
        sessionStorage.removeItem('oldKey');
    });
    $("#txt_sidenotes").summernote();
    $(document).on("change", "#cmb_subjects", function() {
        $("#container_subjects").html("");
        loadSubject();
    });
    $("#frm_addlesson").hide();
    $("#frm_addnewchapter").hide();
    $("#up_progress").hide();
    $("#form_addlesson").validate();
    $("#form_addchap").validate();
    jQuery.validator.setDefaults({
      debug: true,
      success: "valid"
    });
    $("form").submit(function(e){
        e.preventDefault();
    });
    $(document).on("click", "#btn_addchap", function() {
        if($("#form_addchap").valid()){
            var alreadyexists = false;
            var selected_subject = $('#cmb_subjects').val();
            var chaptitle = $("#txt_name").val();
            firebase.database().ref("subjects/" + localStorage.memberkey + "/" + selected_subject).once('value').then(function(snapshot){
                if(snapshot.child(chaptitle).exists()){
                    alreadyexists = true;
                }
            });
            setTimeout(function(){
                if(alreadyexists == true){
                    alert("Chapter already exists.");
                }else{
                    if(sessionStorage.editongoing == 1){
                        var conf = window.confirm('Edit this chapter? The action is irreversible.');
                        var cank = $("#cmb_subjects").val();
                        if(conf == true){
                            var newchap = $("#txt_name").val();
                            var oldRef = firebase.database().ref("subjects/"+localStorage.memberkey+"/"+cank+"/"+sessionStorage.oldKey);
                            var newRef= firebase.database().ref("subjects/"+localStorage.memberkey+"/"+cank+"/"+newchap);
                            moveChapterChildren(oldRef, newRef);
                        }
                    }else{
                        var dt = new Date();
                        var added = dt.toUTCString();
                        firebase.database().ref("subjects/" + localStorage.memberkey + "/" + selected_subject + "/" + chaptitle).set({
                            chap_date_added: added
                        }).then(function() {
                            window.alert("Chapter Added");
                            window.location.replace("syllabus.php");
                        });
                    }
                }
            }, 1000);
        }
    });
    $(document).on("click", "#btn_addlesson", function() {
        if($("#form_addlesson").valid()){
            var alreadyexists = false;
            var selected_subject = $('#cmb_subjects').val();
            var lessontitle = $("#txt_lessonName").val();
            var dt = new Date();
            var added = dt.toUTCString();
            var sidenotes = $("#txt_sidenotes").summernote('code');
            var expiration = new Date($("#dt_expiration").val()).toUTCString();
            //var hashed_filename = 
            //console.log(material_file);
            firebase.database().ref("subjects/" + localStorage.memberkey + "/" + selected_subject + "/" + selected_chapter).once('value').then(function(snapshot){
                if(snapshot.child(lessontitle).exists()){
                    alreadyexists = true;
                }
            });
            setTimeout(function(){
                if(alreadyexists == true){
                    alert("Cannot proceed. Lesson by that name already exists");
                }else{
                    if(sessionStorage.editongoing == 2){
                        var conf = window.confirm('Edit this lesson? The action is irreversible.');
                        var cank = $("#cmb_subjects").val();
                        if(conf == true){
                            var oldRef = firebase.database().ref("subjects/"+localStorage.memberkey+"/"+cank+"/"+sessionStorage.oldKeyForLessonChapter+"/"+sessionStorage.oldKeyForLesson);
                            oldRef.update({
                                lesson_sidenotes: sidenotes
                            }).then(function(){
                                var newRef= firebase.database().ref("subjects/"+localStorage.memberkey+"/"+cank+"/"+sessionStorage.oldKeyForLessonChapter+"/"+lessontitle);
                                moveLessonChildren(oldRef, newRef);
                            });
                        }
                    }else{
                        var material_file = $("#file_lessonMaterial").prop("files")[0];
                        var material = $("#file_lessonMaterial").val();
                        var material_filename = material.split('\\').pop();
                        var material_loc = "files/"+localStorage.memberkey+"/"+selected_subject+"/"+selected_chapter+"/"+material_filename;
                        firebase.database().ref("subjects/" + localStorage.memberkey + "/" + selected_subject + "/" + selected_chapter + "/" + lessontitle).set({
                        lesson_date_added: added,
                        lesson_material : material_loc,
                        lesson_sidenotes : sidenotes
                    }).then(function() {
                        var uploadFile = firebase.storage().ref().child(material_loc).put(material_file);
                        $("#up_progress").show();
                        uploadFile.on(firebase.storage.TaskEvent.STATE_CHANGED, // or 'state_changed'
                          function(snapshot) {
                            // Get task progress, including the number of bytes uploaded and the total number of bytes to be uploaded
                            var progress = Math.floor((snapshot.bytesTransferred / snapshot.totalBytes) * 100);
                            //console.log('Upload is ' + progress + '% done');
                            if(progress == 100){
                                $("#prog_upload").attr('aria-valuenow', progress).css('width', progress+"%");
                                $("#prog_upload").html(progress + "% uploading");
                                setTimeout(lessonbye(), 10000);
                            }else{
                                $("#prog_upload").attr('aria-valuenow', progress).css('width', progress+"%");
                                $("#prog_upload").html(progress + "% uploading");
                            }
                        });
                    });
                    }    
                }
            }, 2000);
        }
    });
});
$("#txt_lessonName").on("keypress", function(event){
    var regex = new RegExp("^[a-zA-Z0-9\\s]+$");
    var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
    if (!regex.test(key)) {
       event.preventDefault();
       return false;
    }
});
function loadSubject() {
    var selected_subject = $('#cmb_subjects').val();
    var chap_num = 0;
    firebase.database().ref("subjects/" + localStorage.memberkey + "/" + selected_subject).orderByChild("chap_date_added").once('value').then(function(snapshot) {
        if (snapshot.numChildren() <= 2) //1 because of the subject added timestamp
            $("#container_subjects").append("<h1>No chapters on this subject.</h1><br>");
        snapshot.forEach(function(childSnapshot) {
            if (childSnapshot.key != "subj_date_added" && childSnapshot.key != "subj_description" && childSnapshot.key != "custom_quizzes") {
                chap_num++;
                var toggle = childSnapshot.key.replace(/\s+/g, ''); //used for names of div
                var panel_title = childSnapshot.key;
                $("#container_subjects").append("<div class='panel-group'> <div class='panel panel-info'> <div class='panel-heading'> <h4 class='panel-title'> <a data-toggle='collapse' href='#" + toggle + "'>" + chap_num + ". " + childSnapshot.key + "</a> </h4> </div> <div id='" + toggle + "' class='panel-collapse collapse'> <ul class='list-group' id = list_" + toggle + "> </ul> <ul class = 'list-group'><li class = 'list-group-item'><a class = 'btn btn-info' onclick = 'addLesson(\"" + panel_title + "\")'> + Add Lesson </a></li><li class = 'list-group-item'><a class = 'btn btn-info' href = 'createquiz.php?title=" + CryptoJS.AES.encrypt(childSnapshot.key, "Secret Passphrase") +"&type=ct&subj=" + CryptoJS.AES.encrypt(selected_subject, "Secret Passphrase") + "'> + Create Quiz For This Chapter</a></li><li class = 'list-group-item'><a class = 'btn btn-warning' onclick = 'editChapter(\""+panel_title+"\");'>Edit Chapter</a></li><li class = 'list-group-item'> <a class = 'btn btn-danger' onclick = 'deleteChapter(\""+panel_title+"\");'>Delete Chapter</a></li></ul> </div> </div> </div>");
                firebase.database().ref("subjects/" + localStorage.memberkey + "/" + selected_subject + "/" + childSnapshot.key).orderByChild("lesson_date_added").once('value').then(function(snapshot) {
                    var lesson_num = 0;
                    snapshot.forEach(function(childSnapshot) {
                        if (childSnapshot.key != "chap_date_added" && childSnapshot.key != "quiz") {
                            lesson_num++;
                            var hey = CryptoJS.AES.encrypt(childSnapshot.key, "Secret Passphrase");
                            if(childSnapshot.child("quiz").exists()){
                                $("#list_" + toggle).append("<li class='list-group-item'><div class = 'row'><div class = 'col-sm-4'>" + chap_num + "." + lesson_num + " " + childSnapshot.key + "</div><div class = 'col-sm-4'><font color = 'green'>Quiz Already Created for this Lesson</font></div><div class = 'col-sm-4'><a class = 'btn btn-warning' onclick = 'editLesson(\""+panel_title+"\",\""+childSnapshot.key+"\");'>Edit Lesson</a> <a class = 'btn btn-danger' onclick = 'deleteLesson(\""+panel_title+"\",\""+childSnapshot.key+"\");'>Delete Lesson</a></div></div></li>");
                            }else{
                                $("#list_" + toggle).append("<li class='list-group-item'><div class = 'row'><div class = 'col-sm-4'>" + chap_num + "." + lesson_num + " " + childSnapshot.key + "</div><div class = 'col-sm-4'><a class = 'btn btn-info' href = 'createquiz.php?title=" + hey +"&type=aq&sj=" + CryptoJS.AES.encrypt(selected_subject, "Secret Passphrase") + "&chap=" + CryptoJS.AES.encrypt(panel_title, "Secret Passphrase") + "'> + Create Quiz For This Lesson</a></div><div class = 'col-sm-4'><a class = 'btn btn-warning' onclick = 'editLesson(\""+panel_title+"\",\""+childSnapshot.key+"\");'>Edit Lesson</a> <a class = 'btn btn-danger' onclick = 'deleteLesson(\""+panel_title+"\",\""+childSnapshot.key+"\");'>Delete Lesson</a></div></div></li>");
                            }
                        }
                    });
                });
            }
        });
    });
}
$("#btn_deletesubject").on('click', function(e){
    e.preventDefault();
    var conf = confirm('Are you sure to delete the selected subject? All chapters, lessons, and quizzes on the syllabus will be deleted. The action is irreversible');
    if(conf == true){
        var cank = $("#cmb_subjects").val();
        firebase.database().ref("subjects/"+localStorage.memberkey+"/"+cank).remove().then(function(){
            alert("Subject successfully deleted!");
            setTimeout(function() {window.location.href = "syllabus.php";}, 1000);
        }); 
    }
});
$("#btn_editsubject").on('click', function(e){
    e.preventDefault();
    var hey = CryptoJS.AES.encrypt($("#cmb_subjects").val(), "Secret Passphrase");
    window.location.replace("addnewsubject.php?action=1&title="+hey);
});
function deleteChapter(key){
    var conf = window.confirm('Delete this Chapter? The lessons and quizzes under the selected chapter will be deleted. The action is irreversible.');
    var cank = $("#cmb_subjects").val();
    if(conf == true){
        firebase.database().ref("subjects/"+localStorage.memberkey+"/"+cank+"/"+key).remove().then(function(){
            alert("Chapter successfully deleted!");
            setTimeout(function() {window.location.href = "syllabus.php";}, 1000);
        }); 
    }
}
function editChapter(key){
    sessionStorage.editongoing = 1;
    sessionStorage.oldKey = key;
    $("#frm_addnewchapter").show();
    $("#container_subjects").hide();
    $("#link_addchap").hide();
    $("#txt_name").val(key);   
}
function deleteLesson(chapter, lesson){
    var conf = window.confirm('Delete this Lesson? The quizzes under the selected lesson will be deleted. The action is irreversible.');
    var cank = $("#cmb_subjects").val();
    if(conf == true){
        firebase.database().ref("subjects/"+localStorage.memberkey+"/"+cank+"/"+chapter+"/"+lesson).remove().then(function(){
            alert("Lesson successfully deleted!");
            setTimeout(function() {window.location.href = "syllabus.php";}, 1000);
        }); 
    }
}
function editLesson(chapter,lesson){
    sessionStorage.editongoing = 2;
    sessionStorage.oldKeyForLessonChapter = chapter;
    sessionStorage.oldKeyForLesson = lesson;
    var cank = $("#cmb_subjects").val();
    $("#frm_addlesson").show();
    $("#chaptertitle").append(chapter);
    selected_chapter = chapter;
    $("#container_subjects").hide();
    $("#link_addchap").hide();
    firebase.database().ref("subjects/"+localStorage.memberkey+"/"+cank+"/"+chapter+"/"+lesson).once('value').then(function(snapshot){
        $("#txt_lessonName").val(snapshot.key);
        $("#txt_sidenotes").summernote('code', snapshot.val().lesson_sidenotes);
        /*$("#file_lessonMaterial").prop("files")[0];
        $("#file_lessonMaterial").val(snapshot.val().lesson_material);*/
    });
}
function moveChapterChildren(oldRef, newRef) {    
     oldRef.once('value', function(snap)  {
          newRef.set( snap.val(), function(error) {
               if( !error ) {  oldRef.remove(); alert("Chapter successfully edited!");
            setTimeout(function() {window.location.href = "syllabus.php";}, 1000);}
               else if( typeof(console) !== 'undefined' && console.error ) {  console.error(error); }
          });
     });
}
function moveLessonChildren(oldRef, newRef) {    
     oldRef.once('value', function(snap)  {
          newRef.set( snap.val(), function(error) {
               if( !error ) {  oldRef.remove(); alert("Lesson successfully edited!");
            setTimeout(function() {window.location.href = "syllabus.php";}, 1000);}
               else if( typeof(console) !== 'undefined' && console.error ) {  console.error(error); }
          });
     });
}
function addNewChapter() {
    $("#frm_addnewchapter").show();
    $("#container_subjects").hide();
    $("#link_addchap").hide();
}
function addLesson(c) {
    $("#frm_addlesson").show();
    $("#chaptertitle").append(c);
    selected_chapter = c;
    $("#container_subjects").hide();
    $("#link_addchap").hide();
}
function lessonbye(){
    window.alert("Lesson Added");
    window.location.replace("syllabus.php");
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