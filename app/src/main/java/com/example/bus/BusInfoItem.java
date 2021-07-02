package com.example.bus;

public class BusInfoItem {
    private String email, mobile;

    public BusInfoItem() {
    }

    public BusInfoItem(String email, String mobile) {
        this.email = email;
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
