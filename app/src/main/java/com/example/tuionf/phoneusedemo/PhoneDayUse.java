package com.example.tuionf.phoneusedemo;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.ArrayList;

/**
 * Created by tuion on 2017/9/19.
 */

public class PhoneDayUse {

    @Id
    private Long date; //20170920
    @Property(nameInDb = "dayUseTime")
    private long dayUseTime;    //使用总时长
    @Property(nameInDb = "dayUseRate")
    private int dayUseRate;     //使用频次
    @Property(nameInDb = "dayUseAppCount")
    private int dayUseAppCount;  //使用个数
    @Property(nameInDb = "dayUseUnlockRate")
    private int dayUseUnlockRate; //解锁频次

    private ArrayList<AppInfo> appInfoList;

    public long getDayUseTime() {
        return dayUseTime;
    }

    public void setDayUseTime(long dayUseTime) {
        this.dayUseTime = dayUseTime;
    }

    public int getDayUseRate() {
        return dayUseRate;
    }

    public void setDayUseRate(int dayUseRate) {
        this.dayUseRate = dayUseRate;
    }

    public int getDayUseAppCount() {
        return dayUseAppCount;
    }

    public void setDayUseAppCount(int dayUseAppCount) {
        this.dayUseAppCount = dayUseAppCount;
    }

    public int getDayUseUnlockRate() {
        return dayUseUnlockRate;
    }

    public void setDayUseUnlockRate(int dayUseUnlockRate) {
        this.dayUseUnlockRate = dayUseUnlockRate;
    }

    public ArrayList<AppInfo> getAppInfoList() {
        return appInfoList;
    }

    public void setAppInfoList(ArrayList<AppInfo> appInfoList) {
        this.appInfoList = appInfoList;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
