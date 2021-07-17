package com.example.framedemo.common.google.billing;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;


import com.android.billingclient.api.BillingClient;
import com.example.framedemo.FrameApplication;
import com.example.framedemo.R;
import com.example.framedemo.common.firebase.FrameAnalyticsEvents;
import com.example.framedemo.common.firebase.FrameEventLogger;

import org.greenrobot.eventbus.EventBus;


public class BillingActivity extends AppCompatActivity {

    private BillingViewModel mBillingViewModel;
    private BillingClientLifecycle mBillingClientLifecycle;
    private ConstraintLayout mYearlySubView;
    private View mFlashView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         FrameEventLogger.logEvent(FrameAnalyticsEvents.SHOW_SUB_PAGE);
        setContentView(R.layout.activity_billing);
        monitorBillingEvents();
        initView();
    }

    private void initView() {
        findViewById(R.id.iv_close).setOnClickListener(v -> finish());
        findViewById(R.id.tv_free).setOnClickListener(v -> finish());
        findViewById(R.id.cl_monthly_sub).setOnClickListener(v -> {
            mBillingViewModel.buyMonthly();
            FrameEventLogger.logEvent(FrameAnalyticsEvents.CLICK_SUB_MONTHLY);
        });
        mFlashView = findViewById(R.id.iv_flash);
        mYearlySubView = findViewById(R.id.cl_yearly_sub);
        mYearlySubView.setOnClickListener(v -> {
            mBillingViewModel.buyYearly();
          FrameEventLogger.logEvent(FrameAnalyticsEvents.CLICK_SUB_YEARLY);
        });
        mYearlySubView.post(this::initAnimations);
    }

    /**
     * 监听Billing相关事件。
     */
    private void monitorBillingEvents() {
        mBillingViewModel = new ViewModelProvider(this).get(BillingViewModel.class);
        mBillingClientLifecycle = ((FrameApplication) getApplication()).getBillingClientLifecycle();
        // Launch billing flow when user clicks button to buy something.
        mBillingViewModel.buyEvent.observe(this, billingFlowParams -> {
            if (billingFlowParams != null) {
                int responseCode = mBillingClientLifecycle
                        .launchBillingFlow(BillingActivity.this, billingFlowParams);
                if (responseCode != BillingClient.BillingResponseCode.OK) {
                    errorToast();
                }
            } else {
                errorToast();
            }
        });
        mBillingClientLifecycle.subscribed.observe(this, isSubscribed -> {
            EventBus.getDefault().post(new SubscribeEvent(isSubscribed));
            if (isSubscribed) {
                Toast.makeText(BillingActivity.this, R.string.subscribe_completed, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        mBillingClientLifecycle.skuDetailsErrorResponseCode.observe(this, responseCode -> errorToast(), false);
    }

    private void errorToast() {
        Toast.makeText(this, getString(R.string.billing_error_toast), Toast.LENGTH_SHORT).show();
    }

    /**
     * 创建订阅按钮相关属性动画。
     */
    private void initAnimations() {
        ObjectAnimator shakeAnimator = ObjectAnimator.ofFloat(mYearlySubView, "translationX", 0, 25, -25, 15, -15, 6, -6, 0);
        shakeAnimator.setDuration(1000);
        shakeAnimator.setStartDelay(200);
        ObjectAnimator flashAnimator = ObjectAnimator.ofFloat(mFlashView, "translationX", 0, mYearlySubView.getWidth() + mFlashView.getWidth());
        flashAnimator.setDuration(800);

        flashAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mFlashView != null) {
                    mFlashView.setVisibility(View.GONE);
                    shakeAnimator.start();
                }
            }
        });
        shakeAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (mFlashView != null) {
                    mFlashView.setVisibility(View.VISIBLE);
                    flashAnimator.start();
                }
            }
        });
        shakeAnimator.start();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.stay, R.anim.slide_down);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFlashView = null;
    }
}