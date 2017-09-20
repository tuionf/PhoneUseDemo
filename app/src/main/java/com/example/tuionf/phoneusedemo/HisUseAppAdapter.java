package com.example.tuionf.phoneusedemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tuion on 2017/9/19.
 */

public class HisUseAppAdapter extends RecyclerView.Adapter {

    private List<AppInfo> appInfoList = new ArrayList<>();
    private Context context;

    public HisUseAppAdapter(Context context,List<AppInfo> appInfoList) {
        this.context = context;
        this.appInfoList = appInfoList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.his_use_appinfo_layout,parent,false);
        return new HisUseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HisUseViewHolder hisUseViewHolder = (HisUseViewHolder) holder;
        AppInfo appInfo = appInfoList.get(position);
        hisUseViewHolder.app_icon.setBackground(appInfo.getAppicon());
        hisUseViewHolder.app_name.setText(appInfo.getAppname());
        long time = appInfo.getUseTime()/60/1000;
        String timeStr = "";
        if (time < 60) {
            timeStr = "约"+ time+"分钟";
        }else {
            timeStr = "约"+ (time - (time%60))/60 +"小时"+(time%60)+"分钟";
        }
        hisUseViewHolder.app_use_time.setText(timeStr);
    }

    @Override
    public int getItemCount() {
        return appInfoList.size();
    }

    class HisUseViewHolder extends RecyclerView.ViewHolder{

        TextView app_name,app_use_time;
        ImageView app_icon;
        public HisUseViewHolder(View itemView) {
            super(itemView);
            app_name = (TextView) itemView.findViewById(R.id.app_name);
            app_icon = (ImageView) itemView.findViewById(R.id.app_icon);
            app_use_time = (TextView) itemView.findViewById(R.id.app_use_time);
        }
    }
}
