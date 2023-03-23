package com.eventloggercollectutils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.EventLoggerCollectApplication;
import com.eventloggercollectutils.adapter.EventLoggerAdapter;
import com.eventloggercollectutils.databinding.ActivityEventLoggerCollectBinding;
import com.eventloggercollectutils.db.EventLoggerData;
import com.eventloggercollectutils.db.EventLoggerDatabase;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class EventLoggerCollectActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityEventLoggerCollectBinding mActivityEventLoggerCollectBinding;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private EventLoggerAdapter mAdapter;
    private List<EventLoggerData> mAllData = new ArrayList<>();
    private List<EventLoggerData> mNecessaryData = new ArrayList<>();
    private List<EventLoggerData> mCheckData = new ArrayList<>();
    private List<EventLoggerData> mCheckNecessaryData = new ArrayList<>();

    public static void start(Context context) {
        Intent intent = new Intent(context, EventLoggerCollectActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityEventLoggerCollectBinding = ActivityEventLoggerCollectBinding.inflate(getLayoutInflater());
        setContentView(mActivityEventLoggerCollectBinding.getRoot());
        EventLoggerCollectApplication.isOpen = true;
        initToolBar();
        iniData();
        initView();
    }


    private void initView() {
        mAdapter = new EventLoggerAdapter();
        mActivityEventLoggerCollectBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        mActivityEventLoggerCollectBinding.rv.setAdapter(mAdapter);

        mActivityEventLoggerCollectBinding.allAnalytics.setOnClickListener(this::onClick);
        mActivityEventLoggerCollectBinding.alreadyAnalytics.setOnClickListener(this::onClick);
        mActivityEventLoggerCollectBinding.specialAnalytics.setOnClickListener(this::onClick);
        mActivityEventLoggerCollectBinding.copy.setOnClickListener(this::onClick);
        mActivityEventLoggerCollectBinding.allAnalyticsTv.setText(String.valueOf(mAllData.size()));
        mActivityEventLoggerCollectBinding.alreadyNecessaryAnalyticsTv.setText(String.valueOf(mCheckNecessaryData.size()));
        mActivityEventLoggerCollectBinding.necessaryAnalyticsTv.setText(String.valueOf(mNecessaryData.size()));
        mActivityEventLoggerCollectBinding.alreadyAnalyticsTv.setText(String.valueOf(mCheckData.size()));
        mAdapter.addSingleModels(mNecessaryData);

//        DecimalFormat df = new DecimalFormat("0.00");
//        String a = df.format(((float) mCheckData.size() * 100) / mAllData.size());
//        mActivityEventLoggerCollectBinding.fractionAnalyticsTv.setText(a + "%");
    }

    private void initToolBar() {
        mActivityEventLoggerCollectBinding.titleLayout.title.setText("打点");
        mActivityEventLoggerCollectBinding.titleLayout.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mActivityEventLoggerCollectBinding.titleLayout.reset.setVisibility(View.VISIBLE);
        mActivityEventLoggerCollectBinding.titleLayout.reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compositeDisposable.add(
                        Single.fromCallable(() -> EventLoggerDatabase.getInstance(EventLoggerCollectActivity.this).getEventLoggerDataDao().reset())
                                .compose(SchedulerHelper.ioMain())
                                .subscribe(integer -> {
                                    iniData();
                                })
                );
            }
        });
    }

    private void iniData() {
        compositeDisposable.add(EventLoggerDatabase.getInstance(this).getEventLoggerDataDao().getAllEventLoggerData()
                .flatMap(eventLoggerData -> {
                    mAllData.clear();
                    mAllData.addAll(eventLoggerData);
                    return EventLoggerDatabase.getInstance(this).getEventLoggerDataDao().getNecessaryEventLoggerData();
                }).flatMap(eventLoggerData -> {
                    mNecessaryData.clear();
                    mNecessaryData.addAll(eventLoggerData);
                    return EventLoggerDatabase.getInstance(this).getEventLoggerDataDao().getAlreadyEventLoggerData();
                }).flatMap(eventLoggerData -> {
                    mCheckData.clear();
                    mCheckData.addAll(eventLoggerData);
                    return EventLoggerDatabase.getInstance(this).getEventLoggerDataDao().getAlreadyNecessaryEventLoggerData();
                })
                .compose(SchedulerHelper.ioMain()).subscribe(eventLoggerData -> {
                    mCheckNecessaryData.clear();
                    mCheckNecessaryData.addAll(eventLoggerData);
                    initView();
                }));
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.all_analytics) {
            EventLoggerCollectDetailActivity.start(this, 0);
        } else if (id == R.id.already_analytics) {
            EventLoggerCollectDetailActivity.start(this, 1);
        } else if (id == R.id.copy) {
            compositeDisposable.add(EventLoggerDatabase.getInstance(this).getEventLoggerDataDao()
                    .getNecessaryEventLoggerData().compose(SchedulerHelper.ioMain()).subscribe(eventLoggerData -> {

                        if (eventLoggerData != null && eventLoggerData.size() > 0) {
                            String str = new Gson().toJson(eventLoggerData);
                            copyContentToClipboard(str, EventLoggerCollectActivity.this);
                        }

                    }));
        } else if (id == R.id.special_analytics) {
            EventLoggerOrderDateActivity.start(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    public void copyContentToClipboard(String content, Context context) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        Toast.makeText(this, "重要点信息已复制", Toast.LENGTH_SHORT).show();
    }
}