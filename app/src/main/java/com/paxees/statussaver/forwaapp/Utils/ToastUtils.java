package com.paxees.statussaver.forwaapp.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.paxees.statussaver.forwaapp.R;


public class ToastUtils {
    public static Boolean IS_ENABLED = true;

    @SuppressLint("WrongConstant")
    public static void showToastWith(Context context, String message) {
//        if (IS_ENABLED)
//        Toast.makeText(context,message,1000).show();
        showToast(context, message);
    }


    @SuppressLint("WrongConstant")
    public static void showToast(Context context, String message) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) ((Activity) context).findViewById(R.id.custom_toast_layout));
        TextView tv = (TextView) layout.findViewById(R.id.txtvw);
        tv.setText(message);
        Toast toast = new Toast(((Activity) context).getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(1500);
        toast.setView(layout);
        toast.show();
    }
}
