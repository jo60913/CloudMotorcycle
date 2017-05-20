package com.example.cloudclient.clients;

/**
 * Created by jo on 2017/4/4.
 */

public class TransactingClient {
    private String clientname;
    private String money;
    private String item;
    public TransactingClient(String _clientname,String _item,String _money){
        this.clientname = _clientname;
        this.money = _money;
        this.item = _item;
    }
    public String getMoney(){
        return this.money;
    }
    public String getItem(){
        return this.item;
    }
    public String getClientname(){
        return this.clientname;
    }
    public String setItem(String item){
        return item;
    }
    public String setMoney(String money){
        return money;
    }
}
