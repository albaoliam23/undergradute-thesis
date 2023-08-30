package com.example.pikmi85.thesisfinal;

/**
 * Created by Albao on 2/24/2017.
 */

public class Assessment {
    public String score, timeonRead, timeonQuiz, date_quiztaken;

    public Assessment(){

    }
    public Assessment(String score, String timeonRead, String timeonQuiz, String date_quiztaken){
        this.score = score;
        this.timeonRead = timeonRead;
        this.timeonQuiz = timeonQuiz;
        this.date_quiztaken = date_quiztaken;
    }

}
