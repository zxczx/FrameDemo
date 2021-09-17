package com.example.framedemo.mvp.login;


import com.example.framedemo.mvp.BasePresenter;
import com.example.framedemo.repository.LoginRepository;
import com.example.framedemo.rx.SchedulerHelper;

import java.util.Timer;
import java.util.TimerTask;

/**
 * LoginPresenter，访问LoginRepository中方法，以及返回数据给view
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {


    private LoginRepository repository;

    public LoginPresenter(LoginRepository repository) {
        this.repository = repository;
    }

    @Override
    public void login(String userName, String password) {
//        Timer timer = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                //TODO 模拟登录，假装花费了 2000 毫秒去提交用户信息。
//                getView().login(null);
//            }
//        };
//        timer.schedule(task, 2000);
        addDisposable(repository.login(userName, password)
                .compose(SchedulerHelper.ioMain())
                .subscribe(loginModel -> {
                    getView().login(loginModel);
                }, throwable -> getView().showError(throwable, false)));
    }


}
