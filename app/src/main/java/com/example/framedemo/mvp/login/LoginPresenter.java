package com.example.framedemo.mvp.login;


import com.example.framedemo.mvp.BasePresenter;
import com.example.framedemo.repository.LoginRepository;
import com.example.framedemo.rx.SchedulerHelper;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {


    private LoginRepository repository;

    public LoginPresenter(LoginRepository repository) {
        this.repository = repository;
    }

    @Override
    public void login(String userName, String password) {
        addDisposable(repository.login(userName, password)
                .compose(SchedulerHelper.ioMain())
                .subscribe(loginModel -> {
                    getView().login(loginModel);
                }, throwable -> getView().showError(throwable, false)));
    }


}
