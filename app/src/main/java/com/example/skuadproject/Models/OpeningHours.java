package com.example.skuadproject.Models;

public class OpeningHours {
    private Boolean open_now;

    public OpeningHours() {
    }

    public OpeningHours(Boolean open_now) {
        this.open_now = open_now;
    }

    public Boolean getOpen_now() {
        return open_now;
    }

    public void setOpen_now(Boolean open_now) {
        this.open_now = open_now;
    }
}
