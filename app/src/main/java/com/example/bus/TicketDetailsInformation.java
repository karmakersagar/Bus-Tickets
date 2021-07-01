package com.example.bus;

public class TicketDetailsInformation {
    String busName, from, to, journeyDate, busCondition, seats, time, TotalCost, ticketID;

    public TicketDetailsInformation() {
    }

    public TicketDetailsInformation( String busName, String from, String to, String journeyDate, String busCondition, String seat, String time, String cost, String ticketID) {

        this.busName = busName;
        this.from = from;
        this.to = to;
        this.journeyDate = journeyDate;
        this.busCondition = busCondition;
        this.seats = seats;
        this.time = time;
        this.TotalCost = TotalCost;
        this.ticketID = ticketID;
    }

    public String getTotalCost() {
        return TotalCost;
    }

    public void setCost(String cost) {
        this.TotalCost = TotalCost;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
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

    public String getBusCondition() {
        return busCondition;
    }

    public void setBusCondition(String busCondition) {
        this.busCondition = busCondition;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeat(String seat) {
        this.seats = seats;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }
}
