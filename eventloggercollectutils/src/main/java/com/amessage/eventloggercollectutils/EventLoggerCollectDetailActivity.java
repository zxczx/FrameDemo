package com.amessage.eventloggercollectutils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.amessage.eventloggercollectutils.adapter.EventLoggerAdapter;
import com.amessage.eventloggercollectutils.databinding.ActivityEventLoggerCollectDetailBinding;
import com.amessage.eventloggercollectutils.db.EventLoggerData;
import com.amessage.eventloggercollectutils.db.EventLoggerDatabase;

import java.util.List;


public class EventLoggerCollectDetailActivity extends AppCompatActivity {

    private ActivityEventLoggerCollectDetailBinding mActivityEventLoggerCollectDetailBinding;
    private int mFlag;
    private List<EventLoggerData> mEventLoggerData;


    //flag: 0 所有点
    public static void start(Context context,  int flag) {
        Intent intent = new Intent(context, EventLoggerCollectDetailActivity.class);
        intent.putExtra("flag", flag);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityEventLoggerCollectDetailBinding = ActivityEventLoggerCollectDetailBinding.inflate(getLayoutInflater());
        setContentView(mActivityEventLoggerCollectDetailBinding.getRoot());
        mFlag = getIntent().getIntExtra("flag", 0);
        initToolBar();
        initView();

    }

    private void initView() {
        EventLoggerAdapter adapter = new EventLoggerAdapter();
        mActivityEventLoggerCollectDetailBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        mActivityEventLoggerCollectDetailBinding.rv.setAdapter(adapter);
        adapter.addSingleModels(mEventLoggerData);
        if (mFlag == 0) {
            mActivityEventLoggerCollectDetailBinding.necessaryAnalytics.setText("所有点:");
//            mEventLoggerData =  EventLoggerDatabase.getInstance(this).getEventLoggerDataDao().getAllEventLoggerData();
        } else if (mFlag == 1) {
            mActivityEventLoggerCollectDetailBinding.necessaryAnalytics.setText("已测点:");
//            mEventLoggerData =  EventLoggerDatabase.getInstance(this).getEventLoggerDataDao().getAlreadyEventLoggerData();
        }
        mActivityEventLoggerCollectDetailBinding.necessaryAnalyticsTv.setText(String.valueOf(mEventLoggerData.size()));
    }

    private void initToolBar() {
        if (mFlag == 0) {
            mActivityEventLoggerCollectDetailBinding.titleLayout.title.setText("所有点");
        } else if (mFlag == 1) {
            mActivityEventLoggerCollectDetailBinding.titleLayout.title.setText("已测点");
        }
        mActivityEventLoggerCollectDetailBinding.titleLayout.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}