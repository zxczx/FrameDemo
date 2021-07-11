package com.example.framedemo.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.framedemo.ErrorHandler;
import com.example.framedemo.mvp.MvpView;

import dagger.android.AndroidInjection;

public class LceNormalActivity extends BaseActivity implements MvpView {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showError(Throwable throwable, boolean isShowErrorPage) {
        ErrorHandler.handlerError(this, throwable);
    }

    @Override
    public void showContentView() {

    }

    @Override
    public void finshActivity() {

    }


}
