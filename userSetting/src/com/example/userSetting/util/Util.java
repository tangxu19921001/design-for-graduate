package com.example.userSetting.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.Toast;

/**
 * create time 2015/1/17
 * Created by Administrator on 2015/1/17.
 */
public class Util {
    /**
     * @param id  the id of the String that you want to show
     * @param con the context show
     */
    public static void showToast(int id, Context con) {
        String msg = con.getResources().getString(id);
        Toast.makeText(con, msg, Toast.LENGTH_LONG).show();
    }

    public static int getWindowWidth(Context con) {
        DisplayMetrics dm = con.getResources().getDisplayMetrics();
        return dm.widthPixels;//宽度height = dm.heightPixels ;//高度
    }

    public static int getWindowHeight(Context con) {
        DisplayMetrics dm = con.getResources().getDisplayMetrics();
        return dm.heightPixels;//宽度height = dm.heightPixels ;//高度
    }
}
