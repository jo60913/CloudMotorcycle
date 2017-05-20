package com.example.cloudclient.clients;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cloudclient.clients.R;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;
import static com.example.cloudclient.clients.R.drawable.client;

/**
 * Created by jo on 2017/3/18.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context mContext;
    private List<TransactingClient> TClient = new ArrayList<>();
    public RecyclerAdapter(List<TransactingClient> _TClient){
        this.TClient = _TClient;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater ItemLayout = LayoutInflater.from(parent.getContext());
        final ViewHolder VH = new ViewHolder(ItemLayout.inflate(R.layout.client_trade,parent,false));
        mContext = parent.getContext();
        VH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(VH.itemView.getContext(),Transaction.class);
                int position = VH.getAdapterPosition();
                it.putExtra("usernum",position);
                ((Activity)mContext).startActivityForResult(it,120);
            }
        });
        return VH;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String ClientStr = TClient.get(position).getClientname();
        String ItemStr = TClient.get(position).getItem();
        String TotalMoney = TClient.get(position).getMoney();
        ViewHolder MVH = (ViewHolder)holder;
        MVH.ClientNameTV.setText(ClientStr);
        MVH.Item_showTV.setText(ItemStr);
        MVH.TotalMoneyTV.setText(TotalMoney);
    }

    @Override
    public int getItemCount() {

        return TClient.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView ClientNameTV;
        public TextView Item_showTV;
        public TextView TotalMoneyTV;
        public ViewHolder(final View itemView) {
            super(itemView);
            ClientNameTV = (TextView)itemView.findViewById(R.id.client_trade_client);
            Item_showTV = (TextView)itemView.findViewById(R.id.client_item_show);
            TotalMoneyTV = (TextView)itemView.findViewById(R.id.trade_totalmoney);
        }
    }
}
