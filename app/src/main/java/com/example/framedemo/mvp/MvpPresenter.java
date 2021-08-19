package com.example.framedemo.mvp;


public interface MvpPresenter<V extends MvpView> {
    //激活
    void attachView(V mvpView);
    //销毁
    void detachView();
}
