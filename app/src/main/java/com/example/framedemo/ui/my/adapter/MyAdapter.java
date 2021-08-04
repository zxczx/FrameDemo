package com.example.framedemo.ui.my.adapter;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framedemo.R;


public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private final int[] mTitles = {R.string.my_room,R.string.my_rxjava,R.string.my_check_version,R.string.my_about,R.string.my_eggshell};
    private final int[] mIconId = {R.mipmap.ic_2,R.mipmap.ic_3,R.mipmap.ic_4,R.mipmap.ic_5,R.mipmap.ic_6};
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int ids, View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return MyViewHolder.create(parent,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(mTitles[position],mIconId[position]);
    }

    @Override
    public int getItemCount() {
        return mTitles.length;
    }
}
