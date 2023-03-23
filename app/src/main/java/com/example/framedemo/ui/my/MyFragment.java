package com.example.framedemo.ui.my;

import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.framedemo.R;
import com.example.framedemo.common.firebase.FrameEventLogger;
import com.example.framedemo.databinding.FragmentMyBinding;
import com.example.framedemo.mvp.room.RoomContract;
import com.example.framedemo.ui.base.LceNormalFragment;
import com.example.framedemo.ui.login.LoginActivity;
import com.example.framedemo.ui.my.about.AboutActivity;
import com.example.framedemo.ui.my.adapter.MyAdapter;
import com.example.framedemo.ui.my.roomDemo.RoomDemoActivity;
import com.example.framedemo.ui.my.rxjavademo.RxJavaDemoActivity;
import com.example.framedemo.util.ClickUtils;
import com.example.framedemo.util.VersionChecker;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class MyFragment extends LceNormalFragment implements View.OnClickListener, MyAdapter.OnItemClickListener {

    public FragmentMyBinding mFragmentMyBinding;

    @Inject
    MyAdapter mAdapter;

    public static MyFragment newInstance() {
        return new MyFragment();
    }


    @Override
    protected View getLayoutView(LayoutInflater inflater, ViewGroup container) {
        mFragmentMyBinding = FragmentMyBinding.inflate(inflater, container, false);
        return mFragmentMyBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AndroidSupportInjection.inject(this);
        mFragmentMyBinding.accountIv.setOnClickListener(this);
        mFragmentMyBinding.accountNameTv.setOnClickListener(this);
        mFragmentMyBinding.toolbar.title.setText(R.string.my);
        mFragmentMyBinding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFragmentMyBinding.rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this::onItemClick);
        mAdapter.notifyDataSetChanged();
        eggshell();
    }

    private void eggshell() {
        ClickUtils.setClick(new Handler(), mFragmentMyBinding.accountIv, v -> {
            LoginActivity.start(getActivity());
        }, v -> {
            if (getContext() != null) {
                String str2 = new String(Base64.decode(getContext().getResources().getString(R.string.package_temp).getBytes(), Base64.DEFAULT));
                Toast.makeText(getContext(), str2, Toast.LENGTH_LONG).show();
            }
            return true;
        }, 20000);
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_name_tv:
                LoginActivity.start(getActivity());
                break;

        }

    }

    @Override
    public void onItemClick(int ids, View view) {
        switch (ids) {
            case R.string.my_room:
                FrameEventLogger.logEvent("click_a");
                RoomDemoActivity.start(getActivity());
                break;
            case R.string.my_rxjava:
                RxJavaDemoActivity.start(getActivity());
                FrameEventLogger.logEvent("click_b");
                break;
            case R.string.my_check_version:
                FrameEventLogger.logEvent("click_c");
                checkVersion();
                break;
            case R.string.my_about:
                FrameEventLogger.logEvent("click_d");
                AboutActivity.start(getActivity());
                break;
            case R.string.my_eggshell:
                FrameEventLogger.logEvent("click_e");
                Toast.makeText(getContext(), "长按头像试试", Toast.LENGTH_LONG).show();
                break;

        }
    }

    private void checkVersion() {
        if (VersionChecker.hasNewVersionInStore(getActivity())) {
            VersionChecker.showUpdatedVersionDialog(getActivity());
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.this_is_the_latest_version), Toast.LENGTH_SHORT).show();
        }
    }
}
