package com.example.onlinegundelik.models;

public class MarkModel {

    private String mark;
    private String subject;
    private String markday;
    private String note;
    private String documentMark;

    public MarkModel() {
    }

    public String getDocumentMark() {
        return documentMark;
    }

    public void setDocumentMark(String documentMark) {
        this.documentMark = documentMark;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMarkday() {
        return markday;
    }

    public void setMarkday(String markday) {
        this.markday = markday;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
