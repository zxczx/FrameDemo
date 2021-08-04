package com.example.framedemo.ui.login;


import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.framedemo.R;
import com.example.framedemo.databinding.FragmentLoginBinding;
import com.example.framedemo.db.user.User;
import com.example.framedemo.mvp.login.LoginContract;
import com.example.framedemo.mvp.login.LoginPresenter;
import com.example.framedemo.ui.base.LceNormalFragment;
import com.example.framedemo.ui.main.MainActivity;
import com.example.framedemo.util.LoadingDialog;
import com.example.framedemo.util.Toaster;
import com.example.framedemo.util.UserPrivacyUtils;


import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class LoginFragment extends LceNormalFragment implements LoginContract.View, View.OnClickListener {

    @Inject
    LoginPresenter presenter;

    public FragmentLoginBinding mFragmentLoginBinding;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }


    @Override
    protected View getLayoutView(LayoutInflater inflater, ViewGroup container) {
        mFragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false);
        return mFragmentLoginBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AndroidSupportInjection.inject(this);
        presenter.attachView(this);
        mFragmentLoginBinding.btnLogin.setOnClickListener(this::onClick);
        setUserPrivacy();

    }

    private void setUserPrivacy() {
        mFragmentLoginBinding.tvPrivacy.setText(UserPrivacyUtils.setUserPrivacy(getContext()));
        mFragmentLoginBinding.tvPrivacy.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }

    }


    @Override
    public void login(User user) {
        LoadingDialog.closeDialog();
        MainActivity.start(getActivity());
        getActivity().finish();
    }


    @Override
    public void showEmptyView() {

    }


    @Override
    public void onClick(View v) {
        if (mFragmentLoginBinding.accountEt.getText().length() == 0) {
            Toaster.show(R.string.enter_account_tips);
            return;
        }

        if (mFragmentLoginBinding.passwordEt.getText().length() == 0) {
            Toaster.show(R.string.login_hint_b);
            return;
        }

        if (!mFragmentLoginBinding.cb.isChecked()) {
            Toaster.show(R.string.login_privacyPolicy_hint);
            return;
        }
        LoadingDialog.showLoadingDialog(getActivity());
        //登录走网络
        presenter.login(mFragmentLoginBinding.accountEt.getText().toString(),
                mFragmentLoginBinding.passwordEt.getText().toString());
    }
}
