package com.example.bus;

public class TicketActivityItem {
    String ticketID, busName, fromLocation, toLocation, issueTime, issueDate;

    public TicketActivityItem() {
    }

    public TicketActivityItem(String ticketID, String busName, String fromLocation, String toLocation, String issueTime, String issueDate) {
        this.ticketID = ticketID;
        this.busName = busName;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.issueTime = issueTime;
        this.issueDate = issueDate;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }
}

