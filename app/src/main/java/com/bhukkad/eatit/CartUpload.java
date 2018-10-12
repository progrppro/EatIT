package com.bhukkad.eatit;

import android.net.Uri;

public class CartUpload {

    private String dish,price,imageuri;
    private int count;

    public CartUpload(){

    }

    public CartUpload(String dish,String price,String imageuri,int count){
        if(dish.trim().equals(""))
            dish = "No Name";
        if(price.trim().equals(""))
            price = "0";
        this.dish = dish ;
        this.imageuri = imageuri;
        this.price = price;
        this.count = count;
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

    public int getCount(){return count;}

}
