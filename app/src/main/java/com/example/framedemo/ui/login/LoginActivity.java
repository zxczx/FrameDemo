package com.example.framedemo.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.example.framedemo.databinding.ActivityLoginBinding;
import com.example.framedemo.ui.base.BaseActivity;
import com.example.framedemo.ui.my.roomDemo.RoomDemoActivity;

public class LoginActivity extends BaseActivity {


    public ActivityLoginBinding mActivityLoginBinding;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

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
