package com.bhukkad.eatit;

class Upload {

    private String name,imageuri ;

    public Upload(){

    }

    public Upload(String name,String uri){
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

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }

    public void setName(String name) {
        this.name = name;
    }
}
