package com.example.mobileproject;

public class NoteClass {

    private String note, date, username;

    private static final NoteClass instance = new NoteClass();

    public static NoteClass getInstance(){
        return instance;

    }

    public NoteClass(){
        super();
    }

    public NoteClass(String note, String date) {
        this.note = note;
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
