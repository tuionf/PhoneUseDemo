package com.example.tuionf.phoneusedemo;

import org.litepal.LitePalApplication;

/**
 * Created by tuion on 2017/9/20.
 */

public class BaseApplication extends LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initDatabase();
    }

    private void initDatabase() {
        //创建数据库shop.db"
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shop.db", null);

    }
}
