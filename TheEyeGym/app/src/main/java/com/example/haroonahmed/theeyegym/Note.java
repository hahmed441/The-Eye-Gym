package com.example.haroonahmed.theeyegym;

public class Note {
    private String date;
    private String description;

    public Note() {
        //empty constructor needed
    }

    public Note(String date, String description) {
        this.date = date;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}


