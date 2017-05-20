package com.example.cloudclient.clients;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static java.lang.System.currentTimeMillis;


public class Menu extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private boolean login = true;
    String[] itemName = {"顧客紀錄","消費紀錄","預約","設定","離開"};
    int[] icons = {R.drawable.client,R.drawable.ic_add_shopping_cart_black_48dp,R.drawable.reservation,R.drawable.setting,R.drawable.quit};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("雲騰二輪交易紀錄");
        GridView grid = (GridView)findViewById(R.id.menuGrid);
        IconAdapter gAdapter = new IconAdapter();
        grid.setAdapter(gAdapter);
        grid.setOnItemClickListener(this);
        if(login == false){
            Intent it = new Intent(this,Login.class);
            startActivityForResult(it,1);
        }

        Calendar c = Calendar.getInstance();
        c.set(2017,Calendar.MAY,16,8,00);
        Intent i = new Intent();
        i.setClass(this,Alarm.class);
        PendingIntent pending = PendingIntent.getBroadcast(this, 0, i, 0);
        AlarmManager AM = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        AM.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pending);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Snackbar.make(findViewById(R.id.menuGrid),"登入成功",Snackbar.LENGTH_SHORT).show();
            }else{
                finish();
            }
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                startActivity(new Intent(this,ClientRecord.class));
                break;
            case 1:     //消費紀錄
                startActivity(new Intent(this,ExpensesRecord.class));
                break;
            case 2:
                startActivity(new Intent(this,Reservation.class));
                break;
            case 3:     //設定
                startActivity(new Intent(this,Setting.class));
                break;
            case 4:
                finish();
                break;
        }
    }

    class IconAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return itemName.length;
        }

        @Override
        public Object getItem(int position) {
            return itemName[position];
        }

        @Override
        public long getItemId(int position) {
            return icons[position];
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null){
                row = getLayoutInflater().inflate(R.layout.menu, null);
                ImageView image = (ImageView) row.findViewById(R.id.item_image);
                TextView text = (TextView) row.findViewById(R.id.item_text);
                image.setImageResource(icons[position]);
                text.setText(itemName[position]);
            }
            return row;
        }
    }
}
