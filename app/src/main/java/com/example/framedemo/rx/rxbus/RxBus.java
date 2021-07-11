package com.example.framedemo.rx.rxbus;

import java.util.HashMap;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import timber.log.Timber;

;

public class RxBus {

    private HashMap<String, CompositeDisposable> subscriptionMap;
    private static volatile RxBus rxBus;
    private final Subject<Object> subject;

    public static RxBus getInstance(){
        if (rxBus == null){
            synchronized (RxBus.class){
                if(rxBus==null){
                    rxBus = new RxBus();
                }
            }
        }
        return rxBus;
    }
    public RxBus(){
        subject = PublishSubject.create().toSerialized();
    }

    public void post(Object o){
        subject.onNext(o);
    }

    public <T>Flowable<T> getObservable(Class<T> type){
        return subject.toFlowable(BackpressureStrategy.BUFFER)
                .ofType(type);
    }

    public <T> Disposable doSubscribe(Class<T> type, Consumer<T> next, Consumer<Throwable> error){
        return getObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next,error);
    }

    public <T> void registerRxBus(Object object, Class<T> eventType, Consumer<T> action) {
        Disposable disposable = doSubscribe(eventType, action, Timber::e);
        addSubscription(object, disposable);
    }

    public void addSubscription(Object o, Disposable disposable) {
        if (subscriptionMap == null) {
            subscriptionMap = new HashMap<>();
        }
        String key = o.getClass().getName();
        if (subscriptionMap.get(key) != null) {
            subscriptionMap.get(key).add(disposable);
        } else {
            CompositeDisposable disposables = new CompositeDisposable();
            disposables.add(disposable);
            subscriptionMap.put(key, disposables);
        }
    }

    public void unSubscribe(Object o) {
        if (subscriptionMap == null) {
            return;
        }

        String key = o.getClass().getName();
        if (!subscriptionMap.containsKey(key)){
            return;
        }
        if (subscriptionMap.get(key) != null) {
            subscriptionMap.get(key).dispose();
        }

        subscriptionMap.remove(key);
    }

    public void unregisterRxBus(Object o) {
        unSubscribe(o);
    }

}
