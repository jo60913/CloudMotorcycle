package com.example.cloudclient.clients;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by jo on 2017/4/10.
 */

public class SettingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SettingItem> SettingList = new ArrayList<SettingItem>();
    private Context mContext;
    public SettingAdapter(List SettingList){
        this.SettingList = SettingList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater Iteminflater = LayoutInflater.from(parent.getContext());
        final ViewHolder VH = new ViewHolder(Iteminflater.inflate(R.layout.setting_inflater,parent,false));
        mContext = parent.getContext();
        VH.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = VH.getAdapterPosition();
                switch (position){
                    case 0:
                        Intent it = new Intent(VH.itemView.getContext(),Password_Change.class);
                        mContext.startActivity(it);
                        break;
                    default:
                        break;
                }
            }
        });
        return VH;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String title = SettingList.get(position).getTitle();
        String content = SettingList.get(position).getContent();
        ViewHolder VH = (ViewHolder)holder;
        VH.title.setText(title);
        VH.content.setText(content);
    }

    @Override
    public int getItemCount() {
        return SettingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView content;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.setting_Title);
            content = (TextView)itemView.findViewById(R.id.setting_Content);
        }
    }
}
