package com.example.framedemo.ui.roomDemo.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framedemo.R;
import com.example.framedemo.databinding.ItemRoomBinding;
import com.example.framedemo.db.user.User;

public class RoomViewHolder extends RecyclerView.ViewHolder {

    public ItemRoomBinding mItemRoomBinding;

    public void bind(User user) {
        mItemRoomBinding.name.setText(user.nickName);
    }

    public static RoomViewHolder create(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    public RoomViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemRoomBinding = ItemRoomBinding.bind(itemView);
    }

}
