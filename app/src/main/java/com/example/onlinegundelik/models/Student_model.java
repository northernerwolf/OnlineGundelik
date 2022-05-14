package com.example.onlinegundelik.models;

import java.io.Serializable;

public class Student_model implements Serializable {

    private String name;
    private String studentid;
    private String classid;
    private String documentCl;

    public Student_model() {
    }

    public Student_model(String name, String studentid, String classid) {
        this.name = name;
        this.studentid = studentid;
        this.classid = classid;
    }

    public String getDocumentCl() {
        return documentCl;
    }

    public void setDocumentCl(String documentCl) {
        this.documentCl = documentCl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }
}
