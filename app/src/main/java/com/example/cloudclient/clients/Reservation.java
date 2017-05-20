package com.example.cloudclient.clients;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.PaintDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static android.R.attr.data;
import static android.media.CamcorderProfile.get;
import static android.media.CamcorderProfile.hasProfile;
import static java.lang.System.currentTimeMillis;

public class Reservation extends AppCompatActivity {

    private RecyclerView RV;
    private ReservationAdapter RA;
    private List<Reservation_Item> RI = new ArrayList<>();
    private ImageView NoItemShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        setTitle("預約紀錄");
        NoItemShow = (ImageView)findViewById(R.id.imageView);
        RV = (RecyclerView)findViewById(R.id.reservation_recyclerview);
        RV.setLayoutManager(new LinearLayoutManager(this));
        RA = new ReservationAdapter(RI);
        RV.setAdapter(RA);
        Listinit();
        RV.addItemDecoration(new RecyclerView.ItemDecoration() {
            private int space = 24;

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = space;
                outRect.left = 10;
                outRect.right = space;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        switch (item_id){
            case R.id.reservation_add:
                startActivityForResult(new Intent(this,Reservation_Add.class),130);     //改變int數字
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.reservation_setting,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void Listinit(){
        SharedPreferences sp = getSharedPreferences("reservation",MODE_PRIVATE);
        Map<String,?> preference = sp.getAll();
        List<?> List = new ArrayList<>(preference.values());
        for(int i = 0;i<List.size();i++){
            String ListItem = List.get(i).toString();
            String[] ItemAry = ListItem.split(",");
            RA.addItem(ItemAry[0],ItemAry[1],ItemAry[2]);
        }
        if(List.size()==0){
            noItem(true);
        }else{
            noItem(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 130){     //從新增的Activity返回
            if(resultCode == RESULT_OK){
                String name = data.getStringExtra("RservationName");
                String info = data.getStringExtra("ReservationInfo");
                String date = data.getStringExtra("ReservationDate");
                RA.addItem(name,info,date);
                if(RV.getVisibility() == View.INVISIBLE){
                    noItem(false);
                }
            }
        }
        if(requestCode == 140){     //從紀錄的Activity(record)返回
            if(resultCode == RESULT_CANCELED){
                int position = data.getIntExtra("ADAPTER_POSITION",0);
                RA.delItem(position);
                RA.notifyDataSetChanged();
                if(RA.getItemCount() == 0){
                    noItem(true);
                }
            }
        }
    }

    private void noItem (boolean control){
        if(control == true){
            NoItemShow.setVisibility(View.VISIBLE);
            RV.setVisibility(View.INVISIBLE);
        }else{
            NoItemShow.setVisibility(View.INVISIBLE);
            RV.setVisibility(View.VISIBLE);
        }
    }
}
