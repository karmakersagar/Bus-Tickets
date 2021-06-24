package com.example.bus;

public class CustomGrid {
    private int seatImage;
    private String seatName;

    public CustomGrid(int seatImage, String seatName) {
        this.seatImage = seatImage;
        this.seatName = seatName;
    }

    public int getSeatImage() {
        return seatImage;
    }

    public void setSeatImage(int seatImage) {
        this.seatImage = seatImage;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }
}
