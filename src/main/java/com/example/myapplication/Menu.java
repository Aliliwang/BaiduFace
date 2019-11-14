package com.example.myapplication;

public class Menu {
    private String name;
    private int imageid;

    public Menu(String name,int imageid){
        this.name=name;
        this.imageid=imageid;
    }
    public String getName(){
        return name;
    }
    public int getImageid(){
        return imageid;
    }


}
