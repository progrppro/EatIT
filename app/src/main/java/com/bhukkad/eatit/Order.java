package com.bhukkad.eatit;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable{
    public List<String> dishes_name;
    public List<String> dishes_price;
    public List<Integer> dishes_count;
    public List<String> dishes_image;

    public Order(){
        dishes_count = new ArrayList<>();
        dishes_name = new ArrayList<>();
        dishes_price = new ArrayList<>();
        dishes_image = new ArrayList<>();
    }

    public List<String> getDishes_name() {
        return dishes_name;
    }

    public void setDishes_name(List<String> dishes_name) {
        this.dishes_name = dishes_name;
    }

    public List<String> getDishes_price() {
        return dishes_price;
    }

    public void setDishes_price(List<String> dishes_price) {
        this.dishes_price = dishes_price;
    }

    public List<Integer> getDishes_count() {
        return dishes_count;
    }

    public void setDishes_count(List<Integer> dishes_count) {
        this.dishes_count = dishes_count;
    }
    public int getCount(){
//        Log.e("dishes", "getCount: " + dishes_name);
        return dishes_name.size();
    }
    public void addDish(String dish, String price,String image){
        if(dishes_name.contains(dish)){
            int x = dishes_name.indexOf(dish);
            int cnt = dishes_count.get(x);
            dishes_count.set(x,cnt+1);
        } else {
            dishes_name.add(dish);
            dishes_price.add(price);
            dishes_count.add(1);
            dishes_image.add(image);
        }
    }
}
