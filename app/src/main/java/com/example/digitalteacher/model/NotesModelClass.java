package com.example.digitalteacher.model;

public class NotesModelClass {
    public String noteHanding;
    public String note;
    public String date;
    public String userEmail;


    public NotesModelClass(){
    }

    public String getNoteHanding() {
        return noteHanding;
    }

    public void setNoteHanding(String noteHanding) {
        this.noteHanding = noteHanding;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
