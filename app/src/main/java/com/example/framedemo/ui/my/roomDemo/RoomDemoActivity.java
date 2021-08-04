package com.example.framedemo.ui.my.roomDemo;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.framedemo.databinding.ActivityRoomDemoBinding;
import com.example.framedemo.db.user.User;
import com.example.framedemo.mvp.room.RoomContract;
import com.example.framedemo.mvp.room.RoomPresenter;
import com.example.framedemo.ui.base.LceNormalActivity;
import com.example.framedemo.ui.my.roomDemo.adapter.RoomAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class RoomDemoActivity extends LceNormalActivity implements RoomContract.View, View.OnClickListener {

    public ActivityRoomDemoBinding mActivityRoomDemoBinding;
    @Inject
    RoomPresenter mRoomPresenter;
    @Inject
    RoomAdapter mRoomAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, RoomDemoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRoomPresenter.attachView(this);

        initData();
    }

    @Override
    public void initView() {
        super.initView();
        mActivityRoomDemoBinding.toolbar.title.setText("room");
        mActivityRoomDemoBinding.toolbar.back.setVisibility(View.VISIBLE);
        mActivityRoomDemoBinding.toolbar.back.setOnClickListener(this::onClick);
        mActivityRoomDemoBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        mActivityRoomDemoBinding.rv.setAdapter(mRoomAdapter);

    }

    private void initData() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i <20; i++) {
            User user = new User("xiaoniu "+i);
            user.setArea("中国");
            user.setAvatarUrl("");
            user.setDateCreated(new Date().toString());
            user.setNickName("小牛 "+i);
            user.setPhone("1111111"+i);
            users.add(user);
        }
        mRoomPresenter.updateUsers(users);
        mRoomPresenter.getUsers();
    }

    @Override
    protected View getLayoutView() {
        mActivityRoomDemoBinding = ActivityRoomDemoBinding.inflate(getLayoutInflater());
        return mActivityRoomDemoBinding.getRoot();
    }

    @Override
    public void usersData(List<User> user) {
        mRoomAdapter.addSingleModels(user);

    }

    @Override
    public void updateSuccess(Boolean is) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRoomPresenter.detachView();
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}