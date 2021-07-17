package com.example.framedemo.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.framedemo.FrameApplication;
import com.example.framedemo.R;
import com.example.framedemo.common.firebase.FrameAnalyticsEvents;
import com.example.framedemo.common.firebase.FrameEventLogger;
import com.example.framedemo.common.google.billing.BillingClientLifecycle;
import com.example.framedemo.common.google.billing.BillingUtilities;
import com.example.framedemo.databinding.ActivityMainBinding;
import com.example.framedemo.ui.base.BaseActivity;
import com.example.framedemo.ui.roomDemo.RoomDemoActivity;
import com.example.framedemo.ui.rxjavademo.RxJavaDemoActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    public ActivityMainBinding mActivityMainBinding;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBillingClientLifeCycle();
        mActivityMainBinding.room.setOnClickListener(this::onClick);
        mActivityMainBinding.rxjava.setOnClickListener(this::onClick);
    }

    @Override
    protected View getLayoutView() {
        mActivityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        return mActivityMainBinding.getRoot();
    }

    /**
     * 初始化BillingClientLifeCycle
     */
    private void initBillingClientLifeCycle() {
        BillingClientLifecycle billingClientLifecycle = FrameApplication.getApp().getBillingClientLifecycle();
        //将BillingClient的生命周期与MainActivity绑定。
        getLifecycle().addObserver(billingClientLifecycle);
        //监听对订阅进行确认（Acknowledge）后发出的事件。
        billingClientLifecycle.purchaseAcknowledgedEvent.observe(this, purchase -> {
            if (purchase == null) return;
            if (BillingUtilities.YEARLY_SKU.equals(purchase.getSku())) {
                FrameEventLogger.logEvent(FrameAnalyticsEvents.FIRST_PURCHASED_YEARLY_TRY);
            } else if (BillingUtilities.MONTHLY_SKU.equals(purchase.getSku())) {
                FrameEventLogger.logEvent(FrameAnalyticsEvents.FIRST_PURCHASED_MONTHLY_TRY);
            }
            FrameEventLogger.logEvent(FrameAnalyticsEvents.FIRST_PURCHASED_ALL_TRY);
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.room:
                RoomDemoActivity.start(this);
                break;
            case R.id.rxjava:
                RxJavaDemoActivity.start(this);
                break;
        }

    }
}