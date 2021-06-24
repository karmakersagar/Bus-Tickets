package com.example.bus;

public class CustomRowItem {

    private int imageResource;
    private String busNumber,busName,busCondition,from,to,journeyDate;

    public CustomRowItem(int imageResource, String busNumber, String busName, String busCondition, String from, String to, String journeyDate) {
        this.imageResource = imageResource;
        this.busNumber = busNumber;
        this.busName = busName;
        this.busCondition = busCondition;
        this.from = from;
        this.to = to;
        this.journeyDate = journeyDate;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusCondition() {
        return busCondition;
    }

    public void setBusCondition(String busCondition) {
        this.busCondition = busCondition;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(String journeyDate) {
        this.journeyDate = journeyDate;
    }
}
