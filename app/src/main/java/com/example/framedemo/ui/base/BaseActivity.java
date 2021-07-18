package com.example.framedemo.ui.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.framedemo.mvp.login.LoginContract;
import com.example.framedemo.util.ActivityUtil;


public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        if (getLayoutView() != null) {
            setContentView(getLayoutView());
        }
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected View getLayoutView() {
        return null;
    }

    public void initView(){}
}
