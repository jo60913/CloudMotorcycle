package com.example.cloudclient.clients;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Reservation_record extends AppCompatActivity {
    private String tag;
    private TextView DateTV;
    private TextView NameTV;
    private TextView InfoTV;
    private Button OKBtn;
    private Button CancelBtn;
    private int adapterPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_record);
        NameTV = (TextView)findViewById(R.id.reservation_record_name);
        InfoTV = (TextView)findViewById(R.id.reservation_record_info);
        Bundle bundle = getIntent().getExtras();
        tag = bundle.getString("Tag","0");
        adapterPosition = bundle.getInt("AdapterPostition",-1);
        SharedPreferences sp = getSharedPreferences("reservation",MODE_PRIVATE);
        String Item = sp.getString("ReservationItem"+tag,"Error");
        String[] ItemAry = Item.split(",");
        NameTV.setText(ItemAry[0]);
        InfoTV.setText(ItemAry[1]);
        String [] dates = ItemAry[2].split("/");
        int Intyear = Integer.valueOf(dates[0]);
        int Intmonth = Integer.valueOf(dates[1]);
        int Intday = Integer.valueOf(dates[2]);
        DateTV = (TextView)findViewById(R.id.reservation_record_date);
        DateTV.setText(Intyear+"/"+Intmonth+"/"+Intday);
        OKBtn = (Button)findViewById(R.id.reservation_record_OK);
        CancelBtn = (Button)findViewById(R.id.reservation_record_Cancel);
        OKBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });
        CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext()).setTitle("刪除").setMessage("是否確定刪除本預約").setPositiveButton("確定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tag = NameTV.getText().toString()+InfoTV.getText().toString();
                        SharedPreferences sp = getSharedPreferences("reservation",MODE_PRIVATE);
                        sp.edit().remove("ReservationItem"+tag).commit();
                        Toast.makeText(Reservation_record.this, "預約刪除", Toast.LENGTH_SHORT).show();
                        getIntent().putExtra("ADAPTER_POSITION",adapterPosition);
                        setResult(RESULT_CANCELED,getIntent());
                        finish();
                    }
                }).setNegativeButton("取消",null).show();
            }
        });
    }
}
