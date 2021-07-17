package com.example.framedemo.ui.base;



import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public abstract class BaseFragment extends Fragment {

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

    return getLayoutView(inflater,container);
  }

  protected abstract View getLayoutView(LayoutInflater inflater, ViewGroup container);
}
