package com.example.framedemo.mvp;

public interface MvpView {
    void showLoading();
    void showEmptyView();
    void showError(Throwable throwable, boolean isShowErrorPage);
    void showContentView();
    void finishActivity();

}
