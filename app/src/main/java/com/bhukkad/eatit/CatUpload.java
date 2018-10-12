package com.bhukkad.eatit;

import android.net.Uri;

public class CatUpload {

    private String dish,price,imageuri,description;

    public CatUpload(){

    }

    public CatUpload(String dish,String price,String imageuri,String description){
        if(dish.trim().equals(""))
            dish = "No Name";
        if(price.trim().equals(""))
            price = "0";
        if(description.trim().equals(""))
            description = "";
        this.dish = dish ;
        this.imageuri = imageuri;
        this.description = description;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public String getImageuri() {
        return imageuri;
    }

    public String getPrice() {
        return price;
    }

    public String getDish() {
        return dish;
    }


}
