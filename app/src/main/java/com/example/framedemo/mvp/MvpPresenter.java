package com.example.framedemo.mvp;

public interface MvpPresenter<V extends MvpView> {
    void attachView(V mvpView);
    void detachView();
}
