package com.example.userSetting.util;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;


public class SysApplication extends Application {
    private List mList = new LinkedList();
    private static SysApplication instance;

    private SysApplication() {
    }

    public synchronized static SysApplication getInstance() {
        if (null == instance) {
            instance = new SysApplication();
        }
        return instance;
    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (int i = 0; i < mList.size(); i++) {
                Activity activity = (Activity) mList.get(i);
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}