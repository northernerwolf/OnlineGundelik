package com.example.onlinegundelik.register;

public class OUser {

    public String email, phonenumber,username,password,location,studentID,school;

    public OUser() {
    }

    public OUser(String email, String username, String password, String studentID, String school, String location) {
        this.email = email;
        this.phonenumber = phonenumber;
        this.username = username;
        this.password = password;
        this.location= location;
        this.studentID = studentID;
        this.school = school;

    }
}
