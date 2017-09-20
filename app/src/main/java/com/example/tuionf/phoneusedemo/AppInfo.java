package com.example.tuionf.phoneusedemo;

import android.graphics.drawable.Drawable;

/**
 * Created by tuion on 2017/9/19.
 */

public class AppInfo {

    //包
    private String packagename = "";
    private int versionCode = 0;
    //名称
    private String appname = "";

    private String versionName = "";
    //图标
    private Drawable appicon = null;
    //使用频次
    private int useRate = 0;
    //使用时长
    private long useTime;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Drawable getAppicon() {
        return appicon;
    }

    public void setAppicon(Drawable appicon) {
        this.appicon = appicon;
    }

    public int getUseRate() {
        return useRate;
    }

    public void setUseRate(int useRate) {
        this.useRate = useRate;
    }

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }
}
