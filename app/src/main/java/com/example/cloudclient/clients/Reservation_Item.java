package com.example.cloudclient.clients;

/**
 * Created by jo on 2017/4/16.
 */

public class Reservation_Item {
    private String name;
    private String date;
    private String info;

    public Reservation_Item(String _name,String _date,String _info){
        this.name = _name;
        this.date = _date;
        this.info = _info;
    }

    public String getName(){
        return this.name;
    }
    public String getDate(){
        return this.date;
    }
    public String getInfo(){
        return this.info;
    }
}
