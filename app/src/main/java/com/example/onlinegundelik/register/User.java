package com.example.onlinegundelik.register;

import java.io.StringReader;

public class User {
    public String email, phonenumber,username,password,location,lessons,school;

    public User() {
    }


    public User(String email, String username, String password, String lessons, String school, String location) {
        this.email = email;
        this.phonenumber = phonenumber;
        this.username = username;
        this.password = password;
        this.location= location;
        this.lessons = lessons;
        this.school = school;
    }
}
