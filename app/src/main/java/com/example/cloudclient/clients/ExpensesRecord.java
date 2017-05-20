package com.example.cloudclient.clients;

import android.content.Intent;
import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.y;
import static com.example.cloudclient.clients.R.drawable.client;

public class ExpensesRecord extends AppCompatActivity {
    private RecyclerView RV;
    public List<TransactingClient> ClientsList = new ArrayList<TransactingClient>();
    private boolean[] clientTransating = new boolean[5];
    public RecyclerAdapter RA;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_record);
        setTitle("交易紀錄");
        ClientInit();
        RV = (RecyclerView)findViewById(R.id.recyclerView);
        RV.setLayoutManager(new LinearLayoutManager(this));
        RA = new RecyclerAdapter(ClientsList);
        RV.setAdapter(RA);
        clientTransating[0] = getSharedPreferences("saveTrans",MODE_PRIVATE).getBoolean("transing1",false);

        RV.addItemDecoration(new RecyclerView.ItemDecoration() {
            private int space = 12;

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = space;
                outRect.left = space;
                outRect.right = space;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 120){
            if(resultCode == RESULT_OK){
                ClientsList.clear();
                ClientInit();
                RV.setAdapter(new RecyclerAdapter(ClientsList));
                //RA.notifyItemChanged(0);
            }
        }
    }

    private void ClientInit(){
        String clientname1 = getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transName1","客戶一");
        String clientname2 = getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transName2","客戶二");
        String clientname3 = getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transName3","客戶三");
        String clientname4 = getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transName4","客戶四");
        String clientname5 = getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transName5","客戶五");

        String clientItem1 = getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transItem11","") + getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transItem12","");
        String clientItem2 = getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transItem21","") + getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transItem22","");
        String clientItem3 = getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transItem31","") + getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transItem32","");
        String clientItem4 = getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transItem41","") + getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transItem42","");
        String clientItem5 = getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transItem51","") + getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transItem52","");

        String clientmoney1 = getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transTotalMoney1","0");
        String clientmoney2 = getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transTotalMoney2","0");
        String clientmoney3 = getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transTotalMoney3","0");
        String clientmoney4 = getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transTotalMoney4","0");
        String clientmoney5 = getSharedPreferences("saveTrans",MODE_PRIVATE).getString("transTotalMoney5","0");

        TransactingClient T1 = new TransactingClient(clientname1,clientItem1,clientmoney1);
        ClientsList.add(T1);
        TransactingClient T2 = new TransactingClient(clientname2,clientItem2,clientmoney2);
        ClientsList.add(T2);
        TransactingClient T3 = new TransactingClient(clientname3,clientItem3,clientmoney3);
        ClientsList.add(T3);
        TransactingClient T4 = new TransactingClient(clientname4,clientItem4,clientmoney4);
        ClientsList.add(T4);
        TransactingClient T5 = new TransactingClient(clientname5,clientItem5,clientmoney5);
        ClientsList.add(T5);

    }
}
