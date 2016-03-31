package com.now.live.livenow;

import java.util.Date;

public class Event {

    private String title;
    private String description;
    private String place;
    private Date date;
    private String time;

    public Event(String title, String description, Date date, String time, String place) {
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

    public Date getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

}
