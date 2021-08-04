package com.example.framedemo.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.framedemo.FrameApplication;
import com.example.framedemo.common.firebase.FrameAnalyticsEvents;
import com.example.framedemo.common.firebase.FrameEventLogger;
import com.example.framedemo.common.google.billing.BillingClientLifecycle;
import com.example.framedemo.common.google.billing.BillingUtilities;
import com.example.framedemo.databinding.ActivityMainBinding;
import com.example.framedemo.ui.base.BaseActivity;
import com.example.framedemo.ui.main.adapter.MainPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends BaseActivity {

    public ActivityMainBinding mActivityMainBinding;
    private MainPagerAdapter pagerAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBillingClientLifeCycle();
//        wrapBottomTab();
    }

    @Override
    public void initView() {
        super.initView();
        pagerAdapter = new MainPagerAdapter(this);
        mActivityMainBinding.viewPager.setAdapter(pagerAdapter);
        new TabLayoutMediator(mActivityMainBinding.indicatorTab, mActivityMainBinding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull @NotNull TabLayout.Tab tab, int position) {
                tab.setCustomView(MainPagerAdapter.getTabView(MainActivity.this, position));
            }
        }).attach();
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


//
//    @SuppressLint("NonConstantResourceId")
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.room:
//                RoomDemoActivity.start(this);
//                break;
//            case R.id.rxjava:
//                RxJavaDemoActivity.start(this);
//                break;
//            case R.id.check_version:
//                boolean hasNewVersionInStore = VersionChecker.hasNewVersionInStore(this);
//                if (hasNewVersionInStore) {
//                    VersionChecker.showUpdatedVersionDialog(this);
//                } else {
//                    Toast.makeText(this, getResources().getString(R.string.this_is_the_latest_version), Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.about:
//                AboutActivity.start(this);
//                break;
//        }
//
//    }
}