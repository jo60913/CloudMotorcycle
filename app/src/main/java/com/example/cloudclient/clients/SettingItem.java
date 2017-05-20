package com.example.cloudclient.clients;

/**
 * Created by jo on 2017/4/10.
 */

public class SettingItem {
    private String Title;
    private String Content;
    public SettingItem(String _Title){
        this.Title = _Title;
    }
    public SettingItem(String _Title,String _Content){
        this.Title = _Title;
        this.Content = _Content;
    }

    public String getTitle(){
        return this.Title;
    }
    public String getContent(){
        return this.Content;
    }
}
