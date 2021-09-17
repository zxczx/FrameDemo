package com.example.framedemo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.framedemo.R;
import com.example.framedemo.common.google.GPlayManager;
import com.example.framedemo.databinding.FragmentHomeBinding;
import com.example.framedemo.databinding.FragmentLoginBinding;
import com.example.framedemo.ui.base.LceNormalFragment;

import org.jetbrains.annotations.NotNull;

public class HomeFragment extends LceNormalFragment implements View.OnClickListener {


    public FragmentHomeBinding mFragmentHomeBinding;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected View getLayoutView(LayoutInflater inflater, ViewGroup container) {
        mFragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return mFragmentHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentHomeBinding.toolbar.title.setText(R.string.home);
        mFragmentHomeBinding.iv.setOnClickListener(this);
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void onClick(View v) {
        GPlayManager.findAppFromGooglePlay(getActivity(),GPlayManager.getUrlWithUtmSrc(getActivity().getPackageName()));
    }
}
