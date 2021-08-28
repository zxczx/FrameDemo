package com.amessage.eventloggercollectutils;

import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//切换线程
public class SchedulerHelper {
    public static <T> SingleTransformer<T, T> ioMain() {
        return upstream -> upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> SingleTransformer<T, T> io() {
        return upstream -> upstream
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io());
    }
}
