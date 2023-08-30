package com.example.pikmi85.thesisfinal;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by pikmi85 on 1/20/2017.
 */
@IgnoreExtraProperties
public class User {
    public String firstname, lastname, type, email, gender;


    public User() {

    }
    public User(String fname, String lname, String utype, String email1, String gender){
        this.firstname = fname;
        this.lastname = lname;
        this.type = utype;
        this.email = email1;
        this.gender = gender;
    }
}
