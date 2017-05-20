package com.example.cloudclient.clients;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.data;
import static com.example.cloudclient.clients.R.id.milage;


public class Client extends AppCompatActivity implements Transaction_List.OnFragmentInteractionListener {
    private long ClientID;
    private TextView clientName;
    private TextView clientTel;
    private TextView clientVehcile;
    private TextView clientPlate;
    private TextView clientInfo;
    private EditText clientInfoET;
    private TextView clientmailge;

    private ClientDB clientDB;
    private TransactionDB TDB;
    private FragmentManager FM;
    private Transaction_List TList;
    SQLiteDatabase db;
    SQLiteDatabase Tdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        ClientID = getIntent().getExtras().getLong("ClientID",0);
        clientName = (TextView) findViewById(R.id.clientNameTex);
        clientTel = (TextView) findViewById(R.id.clientTelTex);
        clientVehcile = (TextView) findViewById(R.id.clientVehicleTex);
        clientPlate = (TextView) findViewById(R.id.clientPlateTex);
        clientInfo = (TextView) findViewById(R.id.clientinfoTex);
        clientInfoET = (EditText)findViewById(R.id.clientInfoEdit);
        clientmailge = (TextView)findViewById(R.id.clientmilage);
        clientDB = ClientDB.getInstance(this);
        TDB = TransactionDB.getInstance(this);
        Tdb = TDB.getReadableDatabase();
        db = clientDB.getReadableDatabase();
        Cursor c = db.query("client",null,"_id=?",new String[]{String.valueOf(ClientID)},null,null,null);
        int row_num = c.getCount();
        if(row_num != 0){
            c.moveToFirst();
            String name = c.getString(1);
            String phone = c.getString(2);
            String vehicle = c.getString(3);
            String info = c.getString(4);
            String plate = c.getString(5);
            String milage = c.getString(6);
            clientName.setText(name);
            clientTel.setText(phone);
            clientVehcile.setText(vehicle);
            clientPlate.setText(plate);
            clientInfo.setText(info);
            clientmailge.setText(milage);
        }
        Cursor Tc = Tdb.query("TClient",null,"clientid=?",new String[]{String.valueOf(ClientID)},null,null,null);
        int Trow_num = Tc.getCount();
        if(Trow_num !=0){
            Tc.moveToFirst();
            StringBuilder item = new StringBuilder();
            do{
                item.append(Tc.getString(4)).append("\n").append(Tc.getString(3)).append(Tc.getString(5)+"公里").append("\n").append(Tc.getString(2)).append("\n");
            }while(Tc.moveToNext());

            TList = Transaction_List.newInstance(item.toString());
        }else{
            TList = Transaction_List.newInstance("無過往交易紀錄");
        }
        FM = getSupportFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.add(R.id.fragment4,TList);
        FT.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 20){
            if(resultCode == RESULT_OK){
                //Snackbar 顯示修改完成

                db = clientDB.getReadableDatabase();
                Cursor c = db.query("client",null,"_id=?",new String[]{String.valueOf(ClientID)},null,null,null);
                int row_num = c.getCount();
                if(row_num != 0){
                    c.moveToFirst();
                    String name = c.getString(1);
                    String phone = c.getString(2);
                    String vehicle = c.getString(3);
                    String info = c.getString(4);
                    String plate = c.getString(5);

                    clientName.setText(name);
                    clientTel.setText(phone);
                    clientVehcile.setText(vehicle);
                    clientPlate.setText(plate);
                    clientInfo.setText(info);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.updatemenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID) {
            case R.id.updateEdit:
                Intent it = new Intent(this,ClientUpdate.class);
                Bundle bag = new Bundle();
                bag.putLong("CLIENTID",ClientID);
                it.putExtras(bag);
                startActivityForResult(it,20);
                break;
            case R.id.updateDel: //記得填寫按下去的功能
                new AlertDialog.Builder(this).setTitle("刪除").setMessage("確定是否刪除").setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.delete("client","_id="+String.valueOf(ClientID),null);
                        finish();
                    }
                }).setNegativeButton("取消",null).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveInfo(View v){
        ContentValues cv = new ContentValues();
        cv.put("info",clientInfoET.getText().toString());

        db = clientDB.getWritableDatabase();
        db.update("client",cv,"_id = "+String.valueOf(ClientID),null);
        clientInfoET.setText("");
        new AlertDialog.Builder(this).setTitle("更新").setMessage("備註更新完成").setPositiveButton("OK",null).show();
        db = clientDB.getReadableDatabase();
        Cursor c = db.query("client",null,"_id=?",new String[]{String.valueOf(ClientID)},null,null,null);
        int row_num = c.getCount();
        if(row_num != 0){
            c.moveToFirst();
            String info = c.getString(4);
            clientInfo.setText(info);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
