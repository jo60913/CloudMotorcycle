package com.example.cloudclient.clients;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Password_Change extends AppCompatActivity {

    private EditText OldPs;
    private EditText NewPs;
    private EditText NewPsConfirm;
    private Button PsOKBtn;
    private Button PsCancelBtn;
    private String userPs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password__change);
        setTitle("密碼更改");
        OldPs = (EditText)findViewById(R.id.password_change_oldps);
        NewPs = (EditText)findViewById(R.id.password_change_newps);
        NewPsConfirm = (EditText)findViewById(R.id.password_change_newps_confirm);

        PsOKBtn = (Button)findViewById(R.id.password_change_OK);
        PsCancelBtn = (Button)findViewById(R.id.password_change_Cancel);
        userPs = getSharedPreferences("Member",MODE_PRIVATE).getString("Password","1234");


        PsOKBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userPs.equals(OldPs.getText().toString())){
                    if(NewPs.getText().toString().equals(NewPsConfirm.getText().toString())){
                        new AlertDialog.Builder(v.getContext()).setTitle("確認").setMessage("確定更改密碼").setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences ps_change = getSharedPreferences("Member",MODE_PRIVATE);
                                ps_change.edit().putString("Password",NewPsConfirm.getText().toString()).commit();
                                finish();
                            }
                        }).setNegativeButton("取消",null).show();
                    }else{
                        new AlertDialog.Builder(v.getContext()).setTitle("錯誤").setMessage("密碼錯誤").setPositiveButton("確定",null).show();
                    }
                }else{
                    new AlertDialog.Builder(v.getContext()).setTitle("錯誤").setMessage("密碼錯誤").setPositiveButton("確定",null).show();
                }
            }
        });
    }
}
