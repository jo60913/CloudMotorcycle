package com.example.cloudclient.clients;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ClientUpdate extends AppCompatActivity {
    private long ClientID;
    private ClientDB clientDB;
    private SQLiteDatabase SD;
    private EditText clientname;
    private EditText clientvehcile;
    private EditText clienttel;
    private EditText clientplate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_update);
        ClientID = getIntent().getExtras().getLong("CLIENTID",0);
        clientname = (EditText)findViewById(R.id.nameUpdateET);
        clienttel = (EditText)findViewById(R.id.telUpdateEt);
        clientplate = (EditText)findViewById(R.id.PlateUpdate);
        clientvehcile = (EditText)findViewById(R.id.VehiclesUpdate);
        clientDB = ClientDB.getInstance(this);
        SD = clientDB.getReadableDatabase();
        Cursor c = SD.query("client",null,"_id=?",new String[]{String.valueOf(ClientID)},null,null,null);
        int row_num = c.getCount();
        if(row_num != 0){
            c.moveToFirst();
            String name = c.getString(1);
            String tel = c.getString(2);
            String vehicle = c.getString(3);
            String plate = c.getString(5);

            clientname.setText(name);
            clienttel.setText(tel);
            clientplate.setText(plate);
            clientvehcile.setText(vehicle);
        }
    }
    public void UpdateOK(View v){
        ContentValues cv = new ContentValues();
        cv.put("name",clientname.getText().toString());
        cv.put("phone",clienttel.getText().toString());
        cv.put("vehicle",clientvehcile.getText().toString());
        cv.put("plate",clientplate.getText().toString());

        SD.update("client",cv,"_id= "+String.valueOf(ClientID),null);
        setResult(RESULT_OK,null);
        finish();

    }

    public void UpdateCancel(View v){
        finish();
    }
}
