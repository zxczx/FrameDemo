package com.example.framedemo.ui.my.about;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.view.View;

import com.EventLoggerCollectApplication;
import com.eventloggercollectutils.EventLoggerCollectActivity;
import com.eventloggercollectutils.EventLoggerCollectInitSuccess;
import com.example.framedemo.BuildConfig;
import com.example.framedemo.R;
import com.example.framedemo.common.firebase.FrameEventLogger;
import com.example.framedemo.databinding.ActivityAboutBinding;
import com.example.framedemo.ui.base.BaseActivity;
import com.example.framedemo.util.ClickUtils;
import com.example.framedemo.util.EventLoggerOpenOnClickUtil;
import com.example.framedemo.util.PromotionManager;
import com.example.framedemo.util.UserPrivacyUtils;

public class AboutActivity extends BaseActivity implements View.OnClickListener{

    private ActivityAboutBinding mActivityAboutBinding;

    public static void start(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected View getLayoutView() {
        mActivityAboutBinding = ActivityAboutBinding.inflate(getLayoutInflater());
        return mActivityAboutBinding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAboutBinding.checkNewVersion.setOnClickListener(this::onClick);
        mActivityAboutBinding.toolbar.title.setText("about");
        mActivityAboutBinding.toolbar.back.setVisibility(View.VISIBLE);
        mActivityAboutBinding.toolbar.back.setOnClickListener(this::onClick);
        setUserPrivacy();
        setVersion();
        FrameEventLogger.logEvent("sendpicture_im_click");
        showEventLoggerCollect();
    }

    private void setVersion() {
        String versionInfoStr = "V" + BuildConfig.VERSION_NAME + "-" + BuildConfig.BuildID;
        mActivityAboutBinding.tvVersion.setText(versionInfoStr);
    }

    private void setUserPrivacy() {
        mActivityAboutBinding.tvPrivacy.setText(UserPrivacyUtils.setUserPrivacy(this));
        mActivityAboutBinding.tvPrivacy.setMovementMethod(LinkMovementMethod.getInstance());
    }
    private void showEventLoggerCollect() {
        mActivityAboutBinding.tvAppName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                if (EventLoggerOpenOnClickUtil.isOpen()){

                    EventLoggerCollectApplication.getInstance().initialize(getApplicationContext(), new EventLoggerCollectInitSuccess() {
                        @Override
                        public void initFinishCallBack() {
                            EventLoggerCollectActivity.start(AboutActivity.this);
                        }
                    });

                }
//            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.check_new_version:
                PromotionManager.searchAppOnPlay(AboutActivity.this, getPackageName());
                break;
        }
    }
}