package com.example.yunxuan.menjin.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by daiMaGe on 2019/2/20.
 */

public class ToastUtil {

    public static void showShort(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public  static  void showCustomShort(String message,Context context){
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public  static  void showCustomLong(String message,Context context){
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
