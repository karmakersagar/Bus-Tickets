package com.example.bus;

public class passSearchData {
    private String toCity, fromCity, date;

    public passSearchData() {
    }

    public passSearchData(String toCity, String fromCity, String date) {
        this.toCity = toCity;
        this.fromCity = fromCity;
        this.date = date;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
