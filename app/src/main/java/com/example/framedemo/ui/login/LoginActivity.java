package com.example.framedemo.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.example.framedemo.R;
import com.example.framedemo.databinding.ActivityLoginBinding;
import com.example.framedemo.databinding.ActivityMainBinding;
import com.example.framedemo.model.LoginModel;
import com.example.framedemo.mvp.login.LoginContract;
import com.example.framedemo.mvp.login.LoginPresenter;
import com.example.framedemo.ui.base.BaseActivity;
import com.example.framedemo.ui.base.LceNormalActivity;

import javax.inject.Inject;


public class LoginActivity extends BaseActivity {


    public ActivityLoginBinding mActivityLoginBinding;

    @Override
    protected View getLayoutView() {
        mActivityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        return mActivityLoginBinding.getRoot();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(mActivityLoginBinding.container.getId(), LoginFragment.newInstance())
                .commit();
    }
}
