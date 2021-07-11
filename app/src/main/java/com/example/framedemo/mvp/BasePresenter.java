package com.example.framedemo.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<T extends MvpView> implements MvpPresenter<T>{

    private T view;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    public void attachView(T mvpView) {
        view = mvpView;
    }

    @Override
    public void detachView() {
        compositeDisposable.clear();
        view = null;
    }

    public T getView() {
        return view;
    }

    public void checkViewAttached(){
        if (!isViewAttached()) {
            throw new MvpViewNotAttachedException();
        }


    }
    private boolean isViewAttached() {
        return view != null;
    }

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }
    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" + " requesting name to the Presenter");
        }
    }
}