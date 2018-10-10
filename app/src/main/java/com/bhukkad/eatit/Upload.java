package com.bhukkad.eatit;

class Upload {

    private String name,imageuri, hotel_user ;

    public Upload(){

    }

    public Upload(String hotel_user, String name,String uri){
        if(name.trim().equals(""))
            name = "No Name";
        this.name = name ;
        imageuri = uri;
        this.hotel_user = hotel_user;
    }

    public String getImageuri() {
        return imageuri;
    }

    public String getName() {
        return name;
    }

    public String getHotel_user() {
        return hotel_user;
    }
}
