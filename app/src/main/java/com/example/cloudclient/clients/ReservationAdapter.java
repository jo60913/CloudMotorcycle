package com.example.cloudclient.clients;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jo on 2017/4/16.
 */

public class ReservationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private Context mContext;
    private List<Reservation_Item> Item = new ArrayList<>();
    public ReservationAdapter(List<Reservation_Item> _Item){
        this.Item = _Item;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater ItemLayout = LayoutInflater.from(parent.getContext());
        final ViewHolder VH = new ViewHolder(ItemLayout.inflate(R.layout.reservation_item,parent,false));

        mContext = parent.getContext();
        VH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(VH.itemView.getContext(),Reservation_record.class);
                VH.itemView.setTag(VH.NameTV.getText().toString()+VH.InfoTV.getText().toString());
                String tag = String.valueOf(VH.itemView.getTag());
                Bundle bundle = new Bundle();
                bundle.putInt("AdapterPostition",VH.getAdapterPosition());
                bundle.putString("Tag",tag);
                //it.putExtra("ReservationPosition",tag);
                it.putExtras(bundle);
                ((Activity)mContext).startActivityForResult(it,140);    //跳入紀錄的Activity record
            }
        });
        return VH;
    }
    public void addItem(String _name,String _info,String _date){
        Reservation_Item RI = new Reservation_Item(_name,_date,_info);
        Item.add(RI);
        notifyDataSetChanged();
    }

    public void delItem(int num){
        Item.remove(num);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String name = Item.get(position).getName();
        String date = Item.get(position).getDate();
        String info = Item.get(position).getInfo();

        ViewHolder VH = (ViewHolder)holder;
        //VH.itemView.setTag(name+info);
        VH.NameTV.setText(name);
        VH.DateTV.setText(date);
        VH.InfoTV.setText(info);
    }

    @Override
    public int getItemCount() {
        return Item.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView NameTV;
        public TextView DateTV;
        public TextView InfoTV;
        public ViewHolder(final View itemView) {
            super(itemView);
            NameTV = (TextView)itemView.findViewById(R.id.reservation_item_name);
            DateTV = (TextView)itemView.findViewById(R.id.reservation_item_date);
            InfoTV = (TextView)itemView.findViewById(R.id.reservation_item_info);
        }
    }
}
