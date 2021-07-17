package com.example.framedemo.ui.login;


import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.framedemo.R;
import com.example.framedemo.api.Config;
import com.example.framedemo.databinding.FragmentLoginBinding;
import com.example.framedemo.model.LoginModel;
import com.example.framedemo.mvp.login.LoginContract;
import com.example.framedemo.mvp.login.LoginPresenter;
import com.example.framedemo.ui.base.LceNormalFragment;
import com.example.framedemo.ui.main.MainActivity;
import com.example.framedemo.ui.rxjavademo.RxJavaDemoActivity;
import com.example.framedemo.util.LoadingDialog;
import com.example.framedemo.util.PromotionManager;
import com.example.framedemo.util.Toaster;


import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;


public class LoginFragment extends LceNormalFragment implements LoginContract.View, View.OnClickListener {


    @Inject
    LoginPresenter presenter;

    public FragmentLoginBinding fragmentLoginBinding;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }


    @Override
    protected View getLayoutView(LayoutInflater inflater, ViewGroup container) {
        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false);
        return fragmentLoginBinding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AndroidSupportInjection.inject(this);
        presenter.attachView(this);
        fragmentLoginBinding.btnLogin.setOnClickListener(this::onClick);
        setUserPrivacy();

    }

    private void setUserPrivacy() {
        String string = getResources().getString(R.string.agree_privacy_policy_user_agreement);
        SpannableString spannableString = new SpannableString(string);
        int indexOf = string.indexOf(getResources().getString(R.string.privacy_policy));
        if (indexOf == -1) {
            indexOf = 0;
        }
        int length = getResources().getString(R.string.privacy_policy).length() + indexOf;
        spannableString.setSpan(new UnderlineSpan(), indexOf, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                PromotionManager.openUri(getContext(), Config.URL_PRIVACY_POLICY);
            }
        }, indexOf, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.privacy_policy_text_color)), indexOf, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        indexOf = string.indexOf(getResources().getString(R.string.user_agreement));
        if (indexOf == -1) {
            indexOf = 0;
        }
        int length2 = getResources().getString(R.string.user_agreement).length() + indexOf;
        spannableString.setSpan(new UnderlineSpan(), indexOf, length2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                PromotionManager.openUri(getContext(), Config.URL_USER_AGREEMENT);
            }
        }, indexOf, length2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.privacy_policy_text_color)), indexOf, length2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        fragmentLoginBinding.tvPrivacy.setText(spannableString);
        fragmentLoginBinding.tvPrivacy.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }

    }


    @Override
    public void login(LoginModel loginModel) {
        LoadingDialog.closeDialog();
        MainActivity.start(getActivity());
        getActivity().finish();
    }


    @Override
    public void showEmptyView() {

    }


    @Override
    public void onClick(View v) {
        if (fragmentLoginBinding.accountEt.getText().length() == 0) {
            Toaster.show(R.string.enter_account_tips);
            return;
        }

        if (fragmentLoginBinding.passwordEt.getText().length() == 0) {
            Toaster.show(R.string.login_hint_b);
            return;
        }

        if (!fragmentLoginBinding.cb.isChecked()) {
            Toaster.show(R.string.login_privacyPolicy_hint);
            return;
        }
        LoadingDialog.showLoadingDialog(getActivity());
        //登录走网络
        presenter.login(fragmentLoginBinding.accountEt.getText().toString(),
                fragmentLoginBinding.passwordEt.getText().toString());
    }
}
