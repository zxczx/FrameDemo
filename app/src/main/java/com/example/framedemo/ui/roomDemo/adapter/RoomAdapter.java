package com.example.framedemo.ui.roomDemo.adapter;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framedemo.db.user.User;

import java.util.ArrayList;
import java.util.List;


public class RoomAdapter extends RecyclerView.Adapter<RoomViewHolder> {

    private List<User> mUsers = new ArrayList<>();

    public void addSingleModels(List<User> users) {
        for (int i = 0; i < users.size(); i++) {
            mUsers.add(users.get(i));
        }
        notifyDataSetChanged();
    }

    public void clear() {
        mUsers.clear();
    }


    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return RoomViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        holder.bind(mUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}
