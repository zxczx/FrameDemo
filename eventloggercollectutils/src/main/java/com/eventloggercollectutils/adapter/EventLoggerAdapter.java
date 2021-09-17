package com.eventloggercollectutils.adapter;


import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eventloggercollectutils.db.EventLoggerData;
import java.util.ArrayList;
import java.util.List;


public class EventLoggerAdapter extends RecyclerView.Adapter<EventLoggerViewHolder> {

    private List<EventLoggerData> mEventLoggerData = new ArrayList<>();

    public void addSingleModels(List<EventLoggerData> users) {
        for (int i = 0; i < users.size(); i++) {
            mEventLoggerData.add(users.get(i));
        }
        notifyDataSetChanged();
    }

    public void clear() {
        mEventLoggerData.clear();
    }


    @NonNull
    @Override
    public EventLoggerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return EventLoggerViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull EventLoggerViewHolder holder, int position) {
        holder.bind(mEventLoggerData.get(position));
    }

    @Override
    public int getItemCount() {
        return mEventLoggerData.size();
    }
}
