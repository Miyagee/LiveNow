package com.now.live.livenow;

import java.util.Date;

public class Event {

    private String title;
    private String description;
    private String date;
    private String time;
    private String place;

    public Event(String title, String description, String date, String time, String place) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.date = date;
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
