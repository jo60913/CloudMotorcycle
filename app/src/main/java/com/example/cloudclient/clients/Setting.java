package com.example.cloudclient.clients;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Setting extends AppCompatActivity {

    private List<SettingItem>SList = new ArrayList<>();
    private RecyclerView SettingRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        SList.add(new SettingItem("更改密碼","請點及以密碼"));
        SettingRV = (RecyclerView)findViewById(R.id.setting_RecyclerView);
        SettingRV.setLayoutManager(new LinearLayoutManager(this));
        SettingRV.setAdapter(new SettingAdapter(SList));

    }
}
