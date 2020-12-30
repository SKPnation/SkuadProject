package com.example.skuadproject.Models;

public class Result {

    private String icon, name;
    //private OpeningHours openingHours;

    public Result() {
    }

    public Result(String icon, String name) {
        this.icon = icon;
        this.name = name;
        //this.openingHours = openingHours;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public OpeningHours getOpeningHours() {
//        return openingHours;
//    }
//
//    public void setOpeningHours(OpeningHours openingHours) {
//        this.openingHours = openingHours;
//    }
}
