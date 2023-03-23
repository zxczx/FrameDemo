package com.eventloggercollectutils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.eventloggercollectutils.adapter.EventLoggerAdapter;
import com.eventloggercollectutils.adapter.OrderAdapter;
import com.eventloggercollectutils.databinding.ActivityEventLoggerCollectDetailBinding;
import com.eventloggercollectutils.databinding.ActivityEventLoggerOrderDateBinding;
import com.eventloggercollectutils.db.EventLoggerData;
import com.eventloggercollectutils.db.EventLoggerDatabase;
import com.eventloggercollectutils.db.OrderDate;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class EventLoggerOrderDateActivity extends AppCompatActivity {

    private ActivityEventLoggerOrderDateBinding mActivityEventLoggerOrderDateBinding;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<OrderDate> mOrderDate;

    public static void start(Context context) {
        Intent intent = new Intent(context, EventLoggerOrderDateActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityEventLoggerOrderDateBinding = ActivityEventLoggerOrderDateBinding.inflate(getLayoutInflater());
        setContentView(mActivityEventLoggerOrderDateBinding.getRoot());
        initToolBar();
        initData();
    }

    private void initToolBar() {
        mActivityEventLoggerOrderDateBinding.titleLayout.back.setOnClickListener(v -> finish());
        mActivityEventLoggerOrderDateBinding.titleLayout.reset.setVisibility(View.VISIBLE);
        mActivityEventLoggerOrderDateBinding.titleLayout.reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compositeDisposable.add(
                        Single.fromCallable(() -> EventLoggerDatabase.getInstance(EventLoggerOrderDateActivity.this).getOrderDateDao().reset())
                                .compose(SchedulerHelper.ioMain())
                                .subscribe(integer -> {
                                    initData();
                                })
                );
            }
        });
    }

    private void initData() {
        compositeDisposable.add(EventLoggerDatabase.getInstance(this).getOrderDateDao().getAllOrderDate()
                .compose(SchedulerHelper.ioMain()).subscribe(
                        orderDates -> {
                            mOrderDate = orderDates;
                            initView();
                        }
                ));

    }

    private void initView() {
        OrderAdapter adapter = new OrderAdapter();
        mActivityEventLoggerOrderDateBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        mActivityEventLoggerOrderDateBinding.rv.setAdapter(adapter);
        adapter.addSingleModels(mOrderDate);
    }


}