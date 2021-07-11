package com.example.framedemo.ui.base;



import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

//  private Unbinder unbinder;
//
//
//
//  @Nullable
//  @Override
//  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//
//    View view = inflater.inflate(getLayoutResId(), container, false);
//    unbinder = ButterKnife.bind(this, view);
//
//    return view;
//  }
//
//  @Override
//  public void onDestroyView() {
//    super.onDestroyView();
//    if (unbinder != null) {
//      unbinder.unbind();
//    }
//  }

//  protected abstract int getLayoutResId();
}
