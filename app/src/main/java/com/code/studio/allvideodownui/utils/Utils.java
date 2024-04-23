package com.code.studio.allvideodownui.utils;

import android.content.Context;
import android.widget.Toast;

public class Utils {

    public static void toast(String msg, Context context){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
