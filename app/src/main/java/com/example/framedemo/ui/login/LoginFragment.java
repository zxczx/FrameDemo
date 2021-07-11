package com.example.framedemo.ui.login;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.framedemo.R;
import com.example.framedemo.databinding.ActivityMainBinding;
import com.example.framedemo.databinding.FragmentLoginBinding;
import com.example.framedemo.model.LoginModel;
import com.example.framedemo.mvp.login.LoginContract;
import com.example.framedemo.mvp.login.LoginPresenter;
import com.example.framedemo.ui.base.LceNormalFragment;
import com.example.framedemo.ui.rxjavademo.RxJavaDemoActivity;


import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class LoginFragment extends LceNormalFragment implements LoginContract.View ,View.OnClickListener{


    @Inject
    LoginPresenter presenter;

    public FragmentLoginBinding fragmentLoginBinding;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false);
        return fragmentLoginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AndroidSupportInjection.inject(this);
        presenter.attachView(this);
        presenter.login("","");
        fragmentLoginBinding.zxc.removeViewAt(0);
        fragmentLoginBinding.btnLogin.setOnClickListener(this::onClick);
        fragmentLoginBinding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (fragmentLoginBinding.btnLogin==null){

                }
                fragmentLoginBinding.btnLogin.setText("0000");
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.detachView();
        }

    }


    @Override
    public void login(LoginModel loginModel) {

    }



    @Override
    public void showEmptyView() {

    }


    @Override
    public void onClick(View v) {
        RxJavaDemoActivity.start(getContext());
    }
}
