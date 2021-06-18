package com.kiloway.androidlibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kiloway.androidlibrary.R;


public class CustomToast {

    private Toast mToast = null;
    private static CustomToast mToastUtil = null;

    public CustomToast(){
    }

    public static CustomToast getInstance(){
        if(mToastUtil == null)
            mToastUtil = new CustomToast();
        return mToastUtil;
    }

    public void showToast(Context context,String text) {
        if(mToast == null) {
            mToast = makeText(context, text,2000);
        } else {
            ((TextView)mToast.getView().findViewById(R.id.tv_content)).setText(text);
        }
        mToast.show();
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    private Toast makeText(Context context, String msg,int duration){
        @SuppressLint("WrongConstant")
        View toastRoot = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.toast_layout, null);
        Toast toast=new Toast(context);
        toast.setView(toastRoot);
        TextView tv=(TextView)toastRoot.findViewById(R.id.tv_content);
        toast.setGravity(Gravity.CENTER, 0, 0);//设置toast显示的位置，这是居中
        tv.setText(msg);
        toast.setDuration(duration);
        return toast;
    }
}

