package com.example.cloudclient.clients;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.Snackbar;
import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.R.attr.name;
import static com.example.cloudclient.clients.R.id.clientplate;
import static com.example.cloudclient.clients.R.id.menuGrid;
import static com.example.cloudclient.clients.R.id.milage;
import static com.example.cloudclient.clients.R.id.search_button;
import static com.example.cloudclient.clients.R.id.search_plate;
import static com.example.cloudclient.clients.R.id.search_totalmoney;

public class Transaction extends AppCompatActivity {
    private int userNum;
    private RelativeLayout RL;
    private ClientDB clientDB;
    private TransactionDB TDB;
    private SQLiteDatabase db;
    private SQLiteDatabase Tdb;
    private EditText clplateET;
    private String clientPlate;
    private TextView Search_client;
    private TextView Search_phone;
    private EditText Search_milage;
    private EditText Search_totalmoney;
    private Button Search_btn;
    private EditText[] Search_Item_array = new EditText[5];
    private EditText[] Search_cash_array = new EditText[5];
    private boolean menu_Open = false;
    private boolean isTransacting = false;
    private int client_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        setTitle("維修中");
        userNum = (getIntent().getIntExtra("usernum",-1))+1;
        clplateET = (EditText)findViewById(R.id.search_plate);
        clplateET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(clplateET.getText().toString().length() == 3){
                    clplateET.setText(clplateET.getText().toString()+"-");
                    clplateET.setSelection(clplateET.getText().length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        RL = (RelativeLayout)findViewById(R.id.trans_ReLayout);
        RL.setVisibility(View.INVISIBLE);
        clientDB = ClientDB.getInstance(this);
        db = clientDB.getWritableDatabase();
        TDB = TransactionDB.getInstance(this);
        Tdb = TDB.getWritableDatabase();
        Search_client = (TextView)findViewById(R.id.search_client);
        Search_phone = (TextView)findViewById(R.id.search_phone);
        Search_milage = (EditText) findViewById(R.id.search_milage);
        Search_btn = (Button)findViewById(R.id.search_client_btn);
        Search_totalmoney = (EditText)findViewById(R.id.search_totalmoney);

        Search_Item_array[0] = (EditText) findViewById(R.id.Item1_item);
        Search_Item_array[1] = (EditText) findViewById(R.id.Item2_item);
        Search_Item_array[2] = (EditText) findViewById(R.id.Item3_item);
        Search_Item_array[3] = (EditText) findViewById(R.id.Item4_item);
        Search_Item_array[4] = (EditText) findViewById(R.id.Item5_item);

        Search_cash_array[0] = (EditText)findViewById(R.id.Item1_cash);
        Search_cash_array[1] = (EditText)findViewById(R.id.Item2_cash);
        Search_cash_array[2] = (EditText)findViewById(R.id.Item3_cash);
        Search_cash_array[3] = (EditText)findViewById(R.id.Item4_cash);
        Search_cash_array[4] = (EditText)findViewById(R.id.Item5_cash);


        String getTransPlate = getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transPlate"+userNum,"");
        if("".equals(getTransPlate.trim())){
            isTransacting = false;
        }else{
            isTransacting = true;
            Search_Item_array[0].setText(getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transItem1"+userNum,"Error"));
            Search_Item_array[1].setText(getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transItem2"+userNum,"Error"));
            Search_Item_array[2].setText(getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transItem3"+userNum,"Error"));
            Search_Item_array[3].setText(getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transItem4"+userNum,"Error"));
            Search_Item_array[4].setText(getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transItem5"+userNum,"Error"));

            Search_cash_array[0].setText(getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transCash1"+userNum,"Error"));
            Search_cash_array[1].setText(getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transCash2"+userNum,"Error"));
            Search_cash_array[2].setText(getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transCash3"+userNum,"Error"));
            Search_cash_array[3].setText(getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transCash4"+userNum,"Error"));
            Search_cash_array[4].setText(getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transCash5"+userNum,"Error"));

            Search_totalmoney.setText(getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transTotalMoney"+userNum,"Error"));
        }

        if(isTransacting == true){
            clientPlate = clplateET.getText().toString();
            Cursor c = db.query("client", null, "plate=?", new String[]{clientPlate}, null, null, null);
            int row_num = c.getCount();
            if (row_num != 0) {
                RL.setVisibility(View.VISIBLE);
                c.moveToFirst();
                client_id = c.getInt(0);
                String name = c.getString(1);
                String phone = c.getString(2);
                String plate = c.getString(5);
                String milage = c.getString(6);

                Search_client.setText(name);
                Search_phone.setText(phone);
                clplateET.setText(plate);
                Search_milage.setHint(milage);
                findViewById(R.id.search_client_btn).setEnabled(false);
                menu_Open = true;
            }
        }else {
            Search_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("".equals(clplateET.getText().toString().trim())) {
                        new AlertDialog.Builder(v.getContext()).setTitle("錯誤").setMessage("請輸入車牌").setPositiveButton("OK", null).show();
                    } else {
                        clientPlate = clplateET.getText().toString();
                        Cursor c = db.query("client", null, "plate=?", new String[]{clientPlate}, null, null, null);
                        int row_num = c.getCount();
                        if (row_num != 0) {
                            RL.setVisibility(View.VISIBLE);
                            c.moveToFirst();
                            client_id = c.getInt(0);
                            String name = c.getString(1);
                            String phone = c.getString(2);
                            String milage = c.getString(6);

                            Search_client.setText(name);
                            Search_phone.setText(phone);
                            Search_milage.setHint(milage);
                            v.findViewById(R.id.search_client_btn).setEnabled(false);
                            menu_Open = true;
                            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(clplateET.getWindowToken(),0);
                        } else {
                            new AlertDialog.Builder(v.getContext()).setTitle("錯誤").setMessage("找不到車主").setPositiveButton("OK", null).show();
                        }
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.transaction_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(menu_Open == true){
            int id = item.getItemId();
            switch (id){
                case R.id.search_save:
                    SharedPreferences saveTrans = getSharedPreferences("saveTrans",MODE_PRIVATE);
                    saveTrans.edit().putString("transMilage"+userNum,Search_milage.getText().toString()).putString("transName"+userNum, Search_client.getText().toString())
                            .putBoolean("transing"+userNum,true).putString("transPlate"+userNum,clplateET.getText().toString())
                            .putString("transTotalMoney"+userNum,Search_totalmoney.getText().toString())
                            .putString("transItem1"+userNum,Search_Item_array[0].getText().toString())
                            .putString("transItem2"+userNum,Search_Item_array[1].getText().toString())
                            .putString("transItem3"+userNum,Search_Item_array[2].getText().toString())
                            .putString("transItem4"+userNum,Search_Item_array[3].getText().toString())
                            .putString("transItem5"+userNum,Search_Item_array[4].getText().toString())
                            .putString("transCash1"+userNum,Search_cash_array[0].getText().toString())
                            .putString("transCash2"+userNum,Search_cash_array[1].getText().toString())
                            .putString("transCash3"+userNum,Search_cash_array[2].getText().toString())
                            .putString("transCash4"+userNum,Search_cash_array[3].getText().toString())
                            .putString("transCash5"+userNum,Search_cash_array[4].getText().toString()).commit();
                    Snackbar.make(findViewById(R.id.scrollView),"保存完成", Snackbar.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    break;
                case R.id.search_check:
                    new AlertDialog.Builder(this).setTitle("完成交易").setMessage("確認交易").setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = new Date(System.currentTimeMillis());
                            String nowTime = dateformat.format(date);
                            Toast.makeText(Transaction.this, "notTime : "+nowTime, Toast.LENGTH_SHORT).show();
                            ContentValues cv = new ContentValues();
                            cv.put("clientid",String.valueOf(client_id));
                            cv.put("totalmoney",Search_totalmoney.getText().toString());
                            cv.put("date",nowTime);
                            cv.put("milage",Search_milage.getText().toString());
                            StringBuilder SB = new StringBuilder();
                            for(int i = 0 ;i<=4 ;i++){          //用迴圈跑過每個陣列
                                if(Search_Item_array[i].getText().toString().equals("")){

                                }else{
                                    SB.append(Search_Item_array[i].getText().toString()).append("(").append(Search_cash_array[i].getText().toString()).append(")").append("\n");
                                }
                            }
                            cv.put("item",SB.toString());
                            long id = Tdb.insert("TClient",null,cv);
                            Log.d("ADD",clientPlate+" : "+client_id);
                            Tdb.close();

                            cleanShared();
                            setResult(RESULT_OK);
                            finish();
                        }
                    }).setNegativeButton("取消",null).show();

                    break;
                case R.id.search_clean:
                    cleanShared();
                    Snackbar.make(findViewById(R.id.scrollView),"取消交易",Snackbar.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                    break;
                default:
                    break;
            }
        }else{
            new AlertDialog.Builder(this).setTitle("請輸入").setMessage("請先輸入車主車牌").setPositiveButton("OK",null).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void cleanShared(){
        SharedPreferences cleanTrans = getSharedPreferences("saveTrans",MODE_PRIVATE);
        cleanTrans.edit().remove("transMilage"+userNum)
                .remove("transName"+userNum).remove("transing"+userNum).remove("transPlate"+userNum)
                .remove("transTotalMoney"+userNum).remove("transItem1"+userNum).remove("transItem2"+userNum)
                .remove("transItem3"+userNum).remove("transItem4"+userNum).remove("transItem5"+userNum)
                .remove("transCash1"+userNum).remove("transCash2"+userNum).remove("transCash3"+userNum)
                .remove("transCash4"+userNum).remove("transCash5"+userNum).commit();
    }
}
