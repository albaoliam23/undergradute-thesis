package com.example.pikmi85.thesisfinal;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Albao on 2/27/2017.
 */

public class globalVariables {
    private static int score=100, indexess;
    private static long timespentonRead, timespentonQuiz, memberscount;
    private static String studentkey,teacherkey;
    private static String grpCode, chapters, lessons, email, bullet, curriculum, bullcurs, fname, classess, onquiz;
    private static String curr_subject, curr_topic, curr_lesson;
    private static byte[] pdf;
    private static ArrayList<String> chapter = new ArrayList<String>();
    private static ArrayList<String> lesson = new ArrayList<String>();
    private static ArrayList<String> bullcur = new ArrayList<String>();
    private static ArrayList<String> classes = new ArrayList<String>();

    public static void setteacherkey(String newteacher_key) {
        teacherkey = newteacher_key;
    }
    public static void setpdf(byte[] newpdf) {
        pdf = newpdf;
    }
    public static byte[] getpdf() {
        return pdf;
    }
    public static String getteacherkey() {
        return teacherkey;
    }

    public static void setcurr_subject(String newcurr_subject) {
        curr_subject = newcurr_subject;
    }
    public static void setcurr_lesson(String newcurr_lesson) {
        curr_lesson = newcurr_lesson;
    }
    public static void setcurr_topic(String newcurr_topic) {
        curr_topic = newcurr_topic;
    }

    public static String getcurr_subject() {
        return curr_subject;
    }
    public static void setonquiz(String newonquiz) {
        onquiz = newonquiz;
    }
    public static String getonquiz() {
        return onquiz;
    }
    public static String getcurr_lesson() {
        return curr_lesson;
    }
    public static String getcurr_topic() {
        return curr_topic;
    }


    public static int getscore() {
        return score;
    }
    public static String getgrpCode() {
        return grpCode;
    }
    public static String getname() {
        return fname;
    }
    public static String getchapters(int idx) {
        return chapter.get(idx);
    }
    public static String getclasses(int idx) {
        return classes.get(idx);
    }
    public static int getclassessize() {
        return classes.size();
    }
    public static int getchapterscount() {
        return chapter.size();
    }
    public static String getlessons(int indx) {
        return lesson.get(indx);
    }
    public static int getlessonscount() {
        return lesson.size();
    }
    public static void setscore(int newscore) {
        score = newscore;
    }
    public static void setname(String newname) {
        fname = newname;
    }
    public static void setgrpCode(String newgrpCode) {
        grpCode = newgrpCode;
    }
    public static void setlessons(String newlessons) {
        lessons = newlessons;
        lesson.add(lessons);

    }
    public static void setchapters(String newchapters) {
        chapters = newchapters;
        chapter.add(chapters);

    }
    public static void setclasses(String newclasses) {
        classess = newclasses;
        classes.add(classess);

    }
    public static void clearClasses(){
        classes.clear();
    }

    public static void setEmail(String newEmail) {
        email = newEmail;
    }
    public static String getEmail() {
        return email;
    }
    public static int getbulcurcount() {
        return bullcur.size();
    }
    public static void setcurriculumBullet(String newCurr, String newBullet) {
        bullet = newBullet;
        curriculum = newCurr;
        bullcurs = curriculum + "_" + bullet;
        bullcur.add(bullcurs);
    }
    public static String getcurriculumBullet(int indexes) {
        return bullcur.get(indexes);
    }
    public static void setIndexess(int j) {
        indexess = j;
    }
    public static int getindexess() {
        return indexess;
    }
    public static void setTimespentonRead(long time1, String reset) {
        if(Objects.equals(reset, "yes")){
            timespentonRead = 0;
        }else {
            timespentonRead = timespentonRead + time1;
        }
    }
    public static long getTimespentonRead() {
        return timespentonRead;
    }
    public static void setcurrclassmemcnt(long newmemberscount) {
        memberscount = newmemberscount;

    }
    public static long getcurrclassmemcnt() {
        return memberscount;
    }
    public static void setTimespentonQuiz(long time2, String reset2) {
        if(Objects.equals(reset2, "yes")){
            timespentonQuiz = 0;
        }else {
            timespentonQuiz = timespentonQuiz + time2;
        }
    }
    public static long getTimespentonQuiz() {
        return timespentonQuiz;
    }
    public static void setstudentKey(String key) {
        studentkey = key;
    }
    public static String getstudentKey() {
        return studentkey;
    }
}
