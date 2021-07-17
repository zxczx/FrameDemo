package com.example.framedemo.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framedemo.R;


public class LoadingDialog {
    static Dialog loadingDialog;
    private static Context context1;

    public static void showLoadingDialog(Context context, String msg) {
        context1 = context;
        LayoutInflater inflater = null;
        try {
            inflater = LayoutInflater.from(context1);
            View v = inflater.inflate(R.layout.dialog_loading, null);
            LinearLayout layout = (LinearLayout) v
                    .findViewById(R.id.dialog_loading_view);
            TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);
            tipTextView.setText(msg);
            loadingDialog = new Dialog(context1, R.style.MyDialogStyle);
            loadingDialog.setCancelable(true);
            loadingDialog.setCanceledOnTouchOutside(false);
            loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            /**
             *将显示Dialog的方法封装在这里面
             */
            Window window = loadingDialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setGravity(Gravity.CENTER);
            window.setAttributes(lp);
            loadingDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showLoadingDialog(Context context) {
        context1 = context;
        LayoutInflater inflater = null;
        try {
            inflater = LayoutInflater.from(context1);

        View v = inflater.inflate(R.layout.dialog_loading, null);
        LinearLayout layout = (LinearLayout) v
                .findViewById(R.id.dialog_loading_view);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);
        tipTextView.setText("Loading...");
        loadingDialog = new Dialog(context1, R.style.MyDialogStyle);
        loadingDialog.setCancelable(true);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        /**
         *将显示Dialog的方法封装在这里面
         */
        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        loadingDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 关闭dialog
     *
     */
    public static void closeDialog() {

        try {
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
            context1=null;
            loadingDialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
