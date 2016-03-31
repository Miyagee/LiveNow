package com.now.live.livenow;

import java.util.Date;

public class Event {

    private String title;
    private String description;
    private String place;
    private String date;
    private String time;

    public Event(String title, String description, String date, String time, String place) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
        this.place = place;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

}
