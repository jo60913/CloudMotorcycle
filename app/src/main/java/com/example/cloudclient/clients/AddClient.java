package com.example.cloudclient.clients;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddClient extends AppCompatActivity {
    private EditText name;
    private EditText tel;
    private EditText vehicles;
    private EditText plate;
    private EditText milage;
    ClientDB clientDB;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        name = (EditText)findViewById(R.id.nameET);
        tel = (EditText)findViewById(R.id.telEt);
        vehicles = (EditText)findViewById(R.id.Vehicles);
        plate = (EditText)findViewById(R.id.Plate);
        plate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(plate.getText().toString().length() == 3){
                    plate.setText(plate.getText().toString()+"-");
                    plate.setSelection(plate.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        milage = (EditText)findViewById(R.id.milage);
        clientDB = ClientDB.getInstance(this);
        db = clientDB.getWritableDatabase();
    }
    public void insert(View v){
        if("".equals(name.getText().toString().trim())) {
            new AlertDialog.Builder(this).setTitle("錯誤").setMessage("請完整填寫資料").setPositiveButton("OK",null).show();
        }else{              //一定要輸入名字才可以新增到資料庫
            ContentValues cv = new ContentValues();
            cv.put("name",name.getText().toString());
            cv.put("phone",tel.getText().toString());
            cv.put("vehicle",vehicles.getText().toString());
            cv.put("plate",plate.getText().toString());
            cv.put("milage",milage.getText().toString());
            long _id = db.insert("client",null,cv);
            Log.d("ADD",_id+"");
            clientDB.close();
            setResult(RESULT_OK, null);
            finish();
        }
    }
    public void cancel(View v){
        setResult(RESULT_CANCELED,null);
        finish();
    }
}
