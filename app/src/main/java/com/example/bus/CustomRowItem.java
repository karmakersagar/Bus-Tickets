package com.example.bus;

public class CustomRowItem {

    String busName, startingPoint, endingPoint, time, fare, type;

    public CustomRowItem() {
    }

    public CustomRowItem(String busName, String startingPoint, String endingPoint, String time, String fare, String type) {
        this.busName = busName;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.time = time;
        this.fare = fare;
        this.type = type;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(String endingPoint) {
        this.endingPoint = endingPoint;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
