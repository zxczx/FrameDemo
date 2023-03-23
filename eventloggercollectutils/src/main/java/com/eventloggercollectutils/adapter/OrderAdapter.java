package com.eventloggercollectutils.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eventloggercollectutils.db.EventLoggerData;
import com.eventloggercollectutils.db.OrderDate;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    private List<OrderDate> mOrderDate = new ArrayList<>();

    public void addSingleModels(List<OrderDate> users) {
        for (int i = 0; i < users.size(); i++) {
            mOrderDate.add(users.get(i));
        }
        notifyDataSetChanged();
    }

    public void clear() {
        mOrderDate.clear();
    }


    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return OrderViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        holder.bind(mOrderDate.get(position));
    }

    @Override
    public int getItemCount() {
        return mOrderDate.size();
    }
}
