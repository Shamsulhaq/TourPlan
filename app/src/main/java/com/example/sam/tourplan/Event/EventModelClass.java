package com.example.sam.tourplan.Event;

/**
 * Created by ASUS on 4/30/2017.
 */

public class EventModelClass {


    private int eventId;
    private int userId;
    private String destination;
    private String startDate;
    private String endDate;
    private int budget;

    public EventModelClass(int userId, String destination, String startDate, String endDate, int budget) {
        this.userId = userId;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
    }

    public EventModelClass(int eventId, int userId, String destination, String startDate, String endDate, int budget) {
        this.eventId = eventId;
        this.userId = userId;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
    }

    public EventModelClass() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}
