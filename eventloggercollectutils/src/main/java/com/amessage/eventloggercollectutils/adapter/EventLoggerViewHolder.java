package com.amessage.eventloggercollectutils.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amessage.eventloggercollectutils.db.EventLoggerData;
import com.amessage.eventloggercollectutils.R;
import com.amessage.eventloggercollectutils.databinding.ItemEventLoggerBinding;


public class EventLoggerViewHolder extends RecyclerView.ViewHolder {

    public ItemEventLoggerBinding mItemEventLoggerBinding;

    public void bind(EventLoggerData eventLoggerData) {

        if (eventLoggerData.getIsCheck()==1) {
            mItemEventLoggerBinding.key.setTextColor(itemView.getContext().getResources().getColor(R.color.color_accent));
        } else {
            mItemEventLoggerBinding.key.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_red_dark));
        }

        mItemEventLoggerBinding.key.setText(eventLoggerData.getKey());
        mItemEventLoggerBinding.action.setText(eventLoggerData.getAction());

    }

    public static EventLoggerViewHolder create(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event_logger, parent, false);
        return new EventLoggerViewHolder(view);
    }

    public EventLoggerViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemEventLoggerBinding = ItemEventLoggerBinding.bind(itemView);
    }

}
