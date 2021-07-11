package com.example.framedemo.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import com.example.framedemo.R;
import com.example.framedemo.model.LoginModel;
import com.example.framedemo.mvp.login.LoginContract;
import com.example.framedemo.mvp.login.LoginPresenter;
import com.example.framedemo.ui.base.BaseActivity;
import com.example.framedemo.ui.base.LceNormalActivity;

import javax.inject.Inject;


public class LoginActivity extends BaseActivity {



    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getSupportFragmentManager();
        LoginFragment loginFragment = LoginFragment.newInstance();
        fm.beginTransaction()
                .add(R.id.container, loginFragment)
                .commit();
        loginFragment.onDestroy();
        loginFragment.fragmentLoginBinding.btnLogin.setText("-------");
    }
}
