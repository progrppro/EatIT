package com.bhukkad.eatit;

class MenuUpload {

    private String name,imageuri;

    public MenuUpload(){

    }

    public MenuUpload(String name,String uri){
        if(name.trim().equals(""))
            name = "No Name";
        this.name = name ;
        imageuri = uri;
    }

    public String getImageuri() {
        return imageuri;
    }

    public String getName() {
        return name;
    }

}
