package com.example.framedemo.ui.my.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framedemo.R;
import com.example.framedemo.databinding.ItemMyBinding;
import com.example.framedemo.ui.my.roomDemo.RoomDemoActivity;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final MyAdapter.OnItemClickListener mOnItemClickListener;
    public ItemMyBinding mItemMyBinding;
    public int title;

    public void bind(int title, int iconId) {
        this.title = title;
        mItemMyBinding.name.setText(title);
        mItemMyBinding.iv.setImageDrawable(itemView.getContext().getResources().getDrawable(iconId));
    }

    public static MyViewHolder create(ViewGroup parent, MyAdapter.OnItemClickListener onItemClickListener) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my, parent, false);
        return new MyViewHolder(view,onItemClickListener);
    }

    public MyViewHolder(@NonNull View itemView, MyAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        mItemMyBinding = ItemMyBinding.bind(itemView);
        itemView.setOnClickListener(this::onClick);
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        mOnItemClickListener.onItemClick(title,v);

    }
}
