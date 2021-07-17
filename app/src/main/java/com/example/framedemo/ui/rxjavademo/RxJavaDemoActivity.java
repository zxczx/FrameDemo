package com.example.framedemo.ui.rxjavademo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.framedemo.R;
import com.example.framedemo.databinding.ActivityRxJavaDemoBinding;
import com.example.framedemo.ui.base.BaseActivity;
import com.example.framedemo.ui.login.LoginActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class RxJavaDemoActivity extends BaseActivity {

    public ActivityRxJavaDemoBinding mActivityRxJavaDemoBinding;

    public static void start(Context context) {
        Intent intent = new Intent(context, RxJavaDemoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    protected View getLayoutView() {
        mActivityRxJavaDemoBinding = ActivityRxJavaDemoBinding.inflate(getLayoutInflater());
        return mActivityRxJavaDemoBinding.getRoot();
    }

    private void initData() {
        // RxJava的流式操作
        Observable.create(new ObservableOnSubscribe<Integer>() {
            // 1. 创建被观察者 & 生产事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            // 2. 通过通过订阅（subscribe）连接观察者和被观察者
            // 3. 创建观察者 & 定义响应事件的行为
            @Override
            public void onSubscribe(Disposable d) {
                Timber.i("开始采用subscribe连接");
            }
            // 默认最先调用复写的 onSubscribe（）

            @Override
            public void onNext(Integer value) {
                Timber.i("对Next事件" + value + "作出响应");
            }

            @Override
            public void onError(Throwable e) {
                Timber.i("对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Timber.i("对Complete事件作出响应");
            }

        });
    }
}
