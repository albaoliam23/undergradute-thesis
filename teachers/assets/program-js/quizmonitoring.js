var test = localStorage.loggedin;
if (test != "true") {
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
$(document).ready(function() {
    $("#teachername").html(localStorage.teacher_name);
    firebase.database().ref("subjects/" + localStorage.memberkey).orderByKey().once('value').then(function(snapshot) {
        snapshot.forEach(function(childSnapshot) {
            $('#cmb_subjects').append("<option value = '" + childSnapshot.key + "'> " + childSnapshot.key + "</option>")
        });
    }).then(function() {
        loadSubject();
    });
    $("#cmb_subjects").on('change', function(e) {
        e.preventDefault();
        $("#container_subjects").html('');
        $("#cmb_subjects").on('change', loadSubject());
    });
    loadCustomQuizzes();
});

function loadCustomQuizzes() {
    firebase.database().ref("classes").orderByKey().once('value').then(function(snapshot) {
        snapshot.forEach(function(childSnapshot) {
            if (childSnapshot.val().grp_teacher_key == localStorage.memberkey && childSnapshot.val().grp_status == "Active") {
                childSnapshot.forEach(function(apo) {
                    if (apo.key == "custom_quizzes") {
                        firebase.database().ref("classes/" + childSnapshot.key + "/custom_quizzes").orderByChild("quiz_date_added").once('value').then(function(snappy) {
                            snappy.forEach(function(childSnappy) {
                                var qtitle = childSnappy.val().quiz_title;
                                var qclass = childSnapshot.val().grp_name;
                                var qadded = new Date(childSnappy.val().quiz_date_added).toLocaleString();
                                $("#tbl_customquizzes").append("<tr><td>" + qtitle + "</td><td>Class</td><td>" + qclass + "</td><td>" + qadded + "</td></tr>");
                            });
                        });
                    }
                });
            }
        });
    });
    firebase.database().ref("subjects/" + localStorage.memberkey).orderByKey().once('value').then(function(snapshot) {
        snapshot.forEach(function(childSnapshot) {
            childSnapshot.forEach(function(apo) {
                if (apo.key == "custom_quizzes") {
                    firebase.database().ref("subjects/" + localStorage.memberkey + "/" + childSnapshot.key + "/custom_quizzes").orderByKey().once('value').then(function(snappy) {
                        snappy.forEach(function(childSnappy) {
                            var qtitle = childSnappy.val().quiz_title;
                            var qsubject = childSnapshot.key;
                            var qadded = new Date(childSnappy.val().quiz_date_added).toLocaleString();
                            $("#tbl_customquizzes").append("<tr><td>" + qtitle + "</td><td>Subject</td><td>" + qsubject + "</td><td>" + qadded + "</td></tr>");
                        });
                    });
                }
            });
        });
    });
}

function loadSubject() {
    var selected_subject = $('#cmb_subjects').val();
    var chap_num = 0;
    firebase.database().ref("subjects/" + localStorage.memberkey + "/" + selected_subject).orderByChild("chap_date_added").once('value').then(function(snapshot) {
        if (snapshot.numChildren() <= 2) //1 because of the subject added timestamp
            $("#container_subjects").append("<h1>No quizzes. Create your course content first.</h1><br>");
        snapshot.forEach(function(childSnapshot) {
            if (childSnapshot.key != "subj_date_added" && childSnapshot.key != "subj_description" && childSnapshot.key != "custom_quizzes") {
                chap_num++;
                var str = "subjects/" + localStorage.memberkey + "/" + selected_subject + "/" + childSnapshot.key;
                var subj = CryptoJS.AES.encrypt(str, "Secret Passphrase");
                var s = CryptoJS.AES.encrypt(selected_subject, "Secret Passphrase");
                var toggle = childSnapshot.key.replace(/\s+/g, ''); //used for names of div
                var panel_title = childSnapshot.key;
                var isExpired1 = false;
                var checkStarted1 = childSnapshot.child("quiz/quiz_started").val();
                var datenow1 = new Date().getTime();
                if (new Date(childSnapshot.child("quiz/quiz_expiration").val()).getTime() < datenow1) {
                  isExpired1 = true;
                }
                if (childSnapshot.child("quiz").exists()) {
                    var expirrr = new Date(childSnapshot.child("quiz/quiz_expiration").val()).toLocaleString();
                    if (isExpired1 == true) {
                        $("#container_subjects").append("<div class='panel-group'> <div class='panel panel-info'> <div class='panel-heading'> <h4 class='panel-title'> <a data-toggle='collapse' href='#" + toggle + "'>" + chap_num + ". " + childSnapshot.key + "</a> </h4> </div> <div id='" + toggle + "' class='panel-collapse collapse'><ul class='list-group' id = list_" + toggle + "> </ul> <ul class = 'list-group'><li class = 'list-group-item'><div class = 'row'><div class = 'col-sm-6'><p class = 'text-success'>Quiz Already Created for this chapter! Available until: " + expirrr + "</p></div><div class = 'col-sm-3'><a class = 'btn btn-primary' href = \"itemanalysis.php?subj=" + subj + "&s=" + s + "\">Item Analysis</a></div><div class = 'col-sm-3'><button class = 'btn btn-warning' onclick = 'extend(\"" + subj + "\");'>Quiz expired. Extend?</button>&nbsp;<a class = 'btn btn-danger' onclick = 'deleteQuiz(\"" + subj + "\");'>Delete quiz</a></div></div></li></ul> </div> </div> </div>");
                    } else {
                        if (checkStarted1 == "no") {
                            $("#container_subjects").append("<div class='panel-group'> <div class='panel panel-info'> <div class='panel-heading'> <h4 class='panel-title'> <a data-toggle='collapse' href='#" + toggle + "'>" + chap_num + ". " + childSnapshot.key + "</a> </h4> </div> <div id='" + toggle + "' class='panel-collapse collapse'><ul class='list-group' id = list_" + toggle + "> </ul> <ul class = 'list-group'><li class = 'list-group-item'><div class = 'row'><div class = 'col-sm-6'><p class = 'text-success'>Quiz Already Created for this chapter! Available until: " + expirrr + "</p></div><div class = 'col-sm-3'><a class = 'btn btn-primary' href = \"itemanalysis.php?subj=" + subj + "&s=" + s + "\">Item Analysis</a></div><div class = 'col-sm-3'><button class = 'btn btn-warning' onclick = 'startQuiz(\"" + subj + "\");'>Start Quiz</button>&nbsp;<a class = 'btn btn-danger' onclick = 'deleteQuiz(\"" + subj + "\");'>Delete quiz</a></div></div></li></ul> </div> </div> </div>");
                        } else {
                            $("#container_subjects").append("<div class='panel-group'> <div class='panel panel-info'> <div class='panel-heading'> <h4 class='panel-title'> <a data-toggle='collapse' href='#" + toggle + "'>" + chap_num + ". " + childSnapshot.key + "</a> </h4> </div> <div id='" + toggle + "' class='panel-collapse collapse'><ul class='list-group' id = list_" + toggle + "> </ul> <ul class = 'list-group'><li class = 'list-group-item'><div class = 'row'><div class = 'col-sm-6'><p class = 'text-success'>Quiz Already Created for this chapter! Available until: " + expirrr + "</p></div><div class = 'col-sm-3'><a class = 'btn btn-primary' href = \"itemanalysis.php?subj=" + subj + "&s=" + s + "\">Item Analysis</a></div><div class = 'col-sm-3'><button class = 'btn btn-warning' onclick = 'endQuiz(\"" + subj + "\");'>End Quiz</button>&nbsp;<a class = 'btn btn-danger' onclick = 'deleteQuiz(\"" + subj + "\");'>Delete quiz</a></div></div></li></ul> </div> </div> </div>");
                        }
                    }
                } else {
                    $("#container_subjects").append("<div class='panel-group'> <div class='panel panel-info'> <div class='panel-heading'> <h4 class='panel-title'> <a data-toggle='collapse' href='#" + toggle + "'>" + chap_num + ". " + childSnapshot.key + "</a> </h4> </div> <div id='" + toggle + "' class='panel-collapse collapse'> <ul class='list-group' id = list_" + toggle + "> </ul> <ul class = 'list-group'><li class = 'list-group-item'><a  class = 'btn btn-info' href = 'createquiz.php?title=" + CryptoJS.AES.encrypt(childSnapshot.key, "Secret Passphrase") + "&type=ct&subj=" + CryptoJS.AES.encrypt(selected_subject, "Secret Passphrase") + "'> + Create Quiz For This Chapter</a></li></ul> </div> </div> </div>");
                }
                firebase.database().ref("subjects/" + localStorage.memberkey + "/" + selected_subject + "/" + childSnapshot.key).orderByChild("lesson_date_added").once('value').then(function(snapshot) {
                    var lesson_num = 0;
                    snapshot.forEach(function(childSnapshot) {
                        if (childSnapshot.key != "chap_date_added" && childSnapshot.key != "quiz") {
                            lesson_num++;
                            var hey = CryptoJS.AES.encrypt(childSnapshot.key, "Secret Passphrase");
                            var str = "subjects/" + localStorage.memberkey + "/" + selected_subject + "/" + panel_title + "/" + childSnapshot.key;
                            var subj = CryptoJS.AES.encrypt(str, "Secret Passphrase");
                            var s = CryptoJS.AES.encrypt(selected_subject, "Secret Passphrase");
                            if (childSnapshot.child("quiz").exists()) {
                                var checkStarted = childSnapshot.child("quiz/quiz_started").val();
                                var isExpired = false;;
                                var datenow = new Date().getTime();
                                var exppp = new Date(childSnapshot.child("quiz/quiz_expiration").val()).getTime();
                                if (exppp < datenow) {
                                    isExpired = true;
                                }
                                if (isExpired == true) {
                                  $("#list_" + toggle).append("<li class='list-group-item'><div class = 'row'><div class = 'col-sm-3'>" + chap_num + "." + lesson_num + " " + childSnapshot.key + "</div><div class = 'col-sm-3'>Quiz Available until: " + new Date(childSnapshot.child("quiz/quiz_expiration").val()).toLocaleString() + "</div><div class = 'col-sm-3'><a class = 'btn btn-primary' href = \"itemanalysis.php?subj=" + subj + "&s=" + s + "\">Item Analysis</a></div><div class = 'col-sm-3'><button class = 'btn btn-warning' onclick = 'extend(\"" + subj + "\");'>Quiz expired. Extend?</button>&nbsp;<a class = 'btn btn-danger' onclick = 'deleteQuiz(\"" + subj + "\");'>Delete quiz</a></div></div></li>");
                                } else {
                                    if (checkStarted == "no") {
                                      $("#list_" + toggle).append("<li class='list-group-item'><div class = 'row'><div class = 'col-sm-3'>" + chap_num + "." + lesson_num + " " + childSnapshot.key + "</div><div class = 'col-sm-3'>Quiz Available until: " + new Date(childSnapshot.child("quiz/quiz_expiration").val()).toLocaleString() + "</div><div class = 'col-sm-3'><a class = 'btn btn-primary' href = \"itemanalysis.php?subj=" + subj + "&s=" + s + "\">Item Analysis</a></div><div class = 'col-sm-3'><button class = 'btn btn-success' onclick = 'startQuiz(\"" + subj + "\");'>Start Quiz</button>&nbsp;<a class = 'btn btn-danger' onclick = 'deleteQuiz(\"" + subj + "\");'>Delete quiz</a></div></div></li>");
                                    } else {
                                      $("#list_" + toggle).append("<li class='list-group-item'><div class = 'row'><div class = 'col-sm-3'>" + chap_num + "." + lesson_num + " " + childSnapshot.key + "</div><div class = 'col-sm-3'>Quiz Available until: " + new Date(childSnapshot.child("quiz/quiz_expiration").val()).toLocaleString() + "</div><div class = 'col-sm-3'><a class = 'btn btn-primary' href = \"itemanalysis.php?subj=" + subj + "&s=" + s + "\">Item Analysis</a></div><div class = 'col-sm-3'><button class = 'btn btn-warning' onclick = 'endQuiz(\"" + subj + "\");'>End Quiz</button>&nbsp;<a class = 'btn btn-danger' onclick = 'deleteQuiz(\"" + subj + "\");'>Delete quiz</a></div></div></li>");
                                    }
                                }
                            } else {
                                $("#list_" + toggle).append("<li class='list-group-item'>" + chap_num + "." + lesson_num + " " + childSnapshot.key + " <a  class = 'btn btn-info' href = 'createquiz.php?title=" + hey + "&type=aq&sj=" + CryptoJS.AES.encrypt(selected_subject, "Secret Passphrase") + "&chap=" + CryptoJS.AES.encrypt(panel_title, "Secret Passphrase") + "'> + Create Quiz For This Lesson</a></li>");
                            }
                        }
                    });
                });
            }
        });
    });
}

function deleteQuiz(loc) {
    var conf = confirm('Are you sure to delete the selected quiz? The action is irreversible');
    if (conf == true) {
        var location = CryptoJS.AES.decrypt(loc, "Secret Passphrase").toString(CryptoJS.enc.Utf8);
        firebase.database().ref(location + "/quiz").remove().then(function() {
            alert("Quiz successfully deleted!");
            setTimeout(function() {
                window.location.href = "quiz.php";
            }, 1000);
        });
    }
}

function startQuiz(loc) {
    var conf = confirm('Start the quiz?');
    if (conf == true) {
        var started = "yes";
        var location = CryptoJS.AES.decrypt(loc, "Secret Passphrase").toString(CryptoJS.enc.Utf8);
        firebase.database().ref(location + "/quiz").update({
            quiz_started: started
        }).then(function() {
            setTimeout(function() {
                window.location.href = "quiz.php";
            }, 1000);
        });
    }
}

function endQuiz(loc) {
    var conf = confirm('End the quiz?');
    if (conf == true) {
        var started = "no";
        var location = CryptoJS.AES.decrypt(loc, "Secret Passphrase").toString(CryptoJS.enc.Utf8);
        firebase.database().ref(location + "/quiz").update({
            quiz_started: started
        }).then(function() {
            setTimeout(function() {
                window.location.href = "quiz.php";
            }, 1000);
        });
    }
}

function extend(loc) {
    $("#extendmodal").modal('show');
    var datetoday = moment().format("YYYY-MM-DDTHH:mm");
    $("#dt_extend").attr("value", datetoday);
    $("#btn_extend").attr("onclick", "extendQuiz('" + loc + "')");
}

function extendQuiz(loc) {
    var started = "yes";
    var location = CryptoJS.AES.decrypt(loc, "Secret Passphrase").toString(CryptoJS.enc.Utf8);
    var exp = new Date($("#dt_extend").val()).toUTCString();
    firebase.database().ref(location + "/quiz").update({
        quiz_started: started,
        quiz_expiration: exp
    }).then(function() {
        alert("Quiz expiration extended!");
        setTimeout(function() {
            window.location.href = "quiz.php";
        }, 1000);
    });
}