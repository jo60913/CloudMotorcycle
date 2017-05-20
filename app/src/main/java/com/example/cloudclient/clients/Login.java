package com.example.cloudclient.clients;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.cloudclient.clients.R.id.account;

public class Login extends AppCompatActivity {
    EditText accountET;
    EditText passwordET;
    private String Account;
    private String Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accountET = (EditText)findViewById(account);
        passwordET = (EditText)findViewById(R.id.password);
        Account = getSharedPreferences("Member",MODE_PRIVATE).getString("Account","jack");
        Password = getSharedPreferences("Member",MODE_PRIVATE).getString("Password","1234");

    }
    public void Login(View v){
        String userAc = accountET.getText().toString();
        String userPW = passwordET.getText().toString();
        if(userAc.equals(Account) && userPW.equals(Password)){
            getIntent().putExtra("USERNAME",userAc);
            getIntent().putExtra("USERPASSWORD",userPW);
            setResult(RESULT_OK,getIntent());
            finish();
        }else{
            new AlertDialog.Builder(this).setTitle("錯誤").setMessage("帳號或密碼錯誤").setPositiveButton("確定",null).show();
        }
    }
    public void Quit(View v){
        finish();
    }
}
