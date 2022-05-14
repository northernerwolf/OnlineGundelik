package com.example.onlinegundelik.models;

import java.io.Serializable;

public class Classs implements Serializable {

    private String title;
    private String teacher;
    private String classid;
    private String documentid;

    public Classs() {
    }

    public Classs(String title, String teacher, String classid) {
        this.title = title;
        this.teacher = teacher;
        this.classid = classid;
    }

    public String getDocumentid() {
        return documentid;
    }

    public void setDocumentid(String documentid) {
        this.documentid = documentid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }
}
