package com.example.cloudclient.clients;

import android.app.DatePickerDialog;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import static android.Manifest.permission_group.CALENDAR;
import static android.R.attr.name;

public class Reservation_Add extends AppCompatActivity {
    private Button reservation_OK;
    private Button reservation_Cancel;
    private String tag;
    private DatePicker DP;
    private TextView nameTV;
    private TextView infoTV;

    private String mYear;
    private String mMonth;
    private String mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation__add);
        setTitle("新增預約");
        reservation_OK = (Button)findViewById(R.id.reservation_OK);
        reservation_Cancel = (Button)findViewById(R.id.reservation_Cancel);
        nameTV = (TextView)findViewById(R.id.reservation_add_name);
        infoTV = (TextView)findViewById(R.id.reservation_add_info);
        DP = (DatePicker) findViewById(R.id.reservation_add_datePicker);
        Calendar c = Calendar.getInstance();        //取得現在時間
        int cYear = c.get(Calendar.YEAR);
        int cMonth = c.get(Calendar.MONTH);
        int cDay = c.get(Calendar.DAY_OF_MONTH);

        mYear = StringDay(cYear);
        mMonth = StringDay(cMonth+1);
        mDay = StringDay(cDay);
        //初始化日期 設定為現在時間
        DP.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mYear = StringDay(year);
                mMonth = StringDay((monthOfYear+1));
                mDay = StringDay(dayOfMonth);
            }
        });
        reservation_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tag = nameTV.getText().toString()+infoTV.getText().toString();
                String name = nameTV.getText().toString();
                String info = infoTV.getText().toString();
                String date = mYear+"/"+mMonth+"/"+mDay;
                if("".equals(name.trim())){
                    new AlertDialog.Builder(v.getContext()).setTitle("錯誤").setMessage("請輸入姓名").show();
                }else{
                    SharedPreferences SP = getSharedPreferences("reservation",MODE_PRIVATE);
                    String item = name+","+info+","+date;
                    SP.edit().putString("ReservationItem"+tag,item).commit();
                    getIntent().putExtra("RservationName",name);
                    getIntent().putExtra("ReservationInfo",info);
                    getIntent().putExtra("ReservationDate",date);
                    setResult(RESULT_OK,getIntent());
                    finish();
                }
            }
        });
        reservation_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String StringDay(int num){
        if(num >=10){
            return String.valueOf(num);
        }else{
            return "0"+String.valueOf(num);
        }
    }
}
