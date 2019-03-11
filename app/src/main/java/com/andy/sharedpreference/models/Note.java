package com.andy.sharedpreference.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {
    private String title;
    private Date date;
    private String content;

    public Note(String title, Date date, String content) {
        this.title = title;
        this.date = date;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public String getFormattedDate(){
        DateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        return formatter.format(date);

    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
