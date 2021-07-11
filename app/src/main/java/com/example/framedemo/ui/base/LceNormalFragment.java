package com.example.framedemo.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;

import com.example.framedemo.ErrorHandler;
import com.example.framedemo.mvp.MvpView;
import dagger.android.support.AndroidSupportInjection;


public abstract class LceNormalFragment extends BaseFragment implements MvpView {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void showLoading() {

    }


    @Override
    public void showContentView() {

    }

    @Override
    public void finshActivity() {

    }

    @Override
    public void showError(Throwable throwable, boolean showErrorPage) {
        ErrorHandler.handlerError(getActivity(), throwable);
    }
}
