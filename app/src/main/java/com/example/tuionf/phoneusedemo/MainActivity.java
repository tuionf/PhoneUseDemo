package com.example.tuionf.phoneusedemo;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView his_use_top5;
    private PhoneDayUse phoneDayUse = new PhoneDayUse();
    ArrayList<AppInfo> appList = new ArrayList<AppInfo>();
    ArrayList<AppInfo> appInstallList = new ArrayList<AppInfo>();
    //解锁频次
    private int dayUseUnlockRate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dayUseUnlockRate = 0;

        his_use_top5 = (RecyclerView) findViewById(R.id.his_use_top5);

        getInstallApp();


        IntentFilter intentFilter = new IntentFilter();
        //解锁屏幕广播
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        //亮屏广播
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);

        registerReceiver(new ScreenBroadcastReceiver(),intentFilter);

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });

        his_use_top5.setLayoutManager(new LinearLayoutManager(this));

    }

    private void getInstallApp() {
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packages.size();i++) {
            PackageInfo packageInfo = packages.get(i);
            AppInfo tmpInfo = new AppInfo();
            tmpInfo.setAppname(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString());
            tmpInfo.setPackagename(packageInfo.packageName);
            tmpInfo.setVersionName(packageInfo.versionName);
//            tmpInfo.setAppicon(packageInfo.);

            Log.e("hhp", "getInstallApp: "+ packageInfo.packageName+"---"+tmpInfo.getAppname());
//            tmpInfo.versionCode = packageInfo.versionCode;
            tmpInfo.setAppicon(packageInfo.applicationInfo.loadIcon(getPackageManager()));
            appInstallList.add(tmpInfo);
        }

        init();
    }

    private void init() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            try {
                startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));

                SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
                Calendar calendar = Calendar.getInstance();
                Long endTime = calendar.getTimeInMillis();
                calendar.add(Calendar.HOUR,-16);
                Long startTime = calendar.getTimeInMillis();

                Log.e("hhp", "init: 开启时间"+sdf.format(new Date(startTime))+"---结束时间---"+
                    sdf.format(new Date(endTime)));

                List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,startTime,endTime);

                Log.e("hhp", "init: "+usageStatsList.size());
                long allTime = 0;
                int allUseRate = 0;
                for (int i = 0; i < usageStatsList.size(); i++) {
                    AppInfo tempAppInfo = new AppInfo();

                    String packageName = usageStatsList.get(i).getPackageName();
                    long time = usageStatsList.get(i).getTotalTimeInForeground();
                    allTime += time;
                    Field field = usageStatsList.get(i).getClass().getDeclaredField("mLaunchCount");
                    int mLaunchCount= field.getInt(usageStatsList.get(i));
                    allUseRate += mLaunchCount;
                    if (time/60/1000 > 0 && mLaunchCount > 0){
                        tempAppInfo.setPackagename(packageName);
                        tempAppInfo.setUseTime(time);
                        tempAppInfo.setUseRate(mLaunchCount);

                        Log.e("hhp", "init: 包名"+packageName +"--运行时间-分钟"+time/60/1000+"---启动次数---"+mLaunchCount);

                        for (int j = 0; j < appInstallList.size(); j++) {
                            AppInfo appInfo = appInstallList.get(j);
                            if (packageName.equals(appInfo.getPackagename())){
                                tempAppInfo.setAppname(appInfo.getAppname());
                                tempAppInfo.setAppicon(appInfo.getAppicon());
                                appList.add(tempAppInfo);
//                                continue;
                            }


                        }
                    }
                }

                phoneDayUse.setDayUseTime(allTime);
                phoneDayUse.setDayUseRate(allUseRate);
                phoneDayUse.setAppInfoList(appList);
                Log.e("hhp", "init:总时间 "+ allTime/60/1000 );

                HisUseAppAdapter hisUseAppAdapter = new HisUseAppAdapter(this,phoneDayUse.getAppInfoList());
                his_use_top5.setAdapter(hisUseAppAdapter);

            }catch (Exception e){
                Toast.makeText(this, "无法开启，请手动开启", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    //TODO  hhp 应该在服务中接收，随后写
    private class ScreenBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String action = intent.getAction();
                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    Log.e("hhpp", "ScreenBroadcastReceiver --> ACTION_SCREEN_ON");

                } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                    Log.e("hhpp", "ScreenBroadcastReceiver --> ACTION_USER_PRESENT");
                    dayUseUnlockRate++;
                    phoneDayUse.setDayUseUnlockRate(dayUseUnlockRate);
                }
            }
        }
    }

}
