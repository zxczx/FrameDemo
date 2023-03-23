package com.eventloggercollectutils.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eventloggercollectutils.R;
import com.eventloggercollectutils.databinding.ItemOrderBinding;
import com.eventloggercollectutils.db.OrderDate;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderViewHolder extends RecyclerView.ViewHolder {

    public ItemOrderBinding mItemOrderBinding;

    public void bind(OrderDate orderDate) {

        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
        String t = format.format(orderDate.getTime());
        if (orderDate.getTime() == 0) {
            t = "";
        }
        mItemOrderBinding.key.setText(orderDate.getNum() + " : " + orderDate.getKey());
        mItemOrderBinding.type.setText("Type :" + orderDate.getType() + "       " + "Time :" + t);

        if (orderDate.getCheckNum() == 0) {
            mItemOrderBinding.action.setText("异常信息：未打点");
            mItemOrderBinding.action.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_red_dark));
        } else if (orderDate.getCheckNum() > 1) {
            mItemOrderBinding.action.setText("异常信息：" + orderDate.getMsg());
            mItemOrderBinding.action.setTextColor(itemView.getContext().getResources().getColor(android.R.color.holo_red_dark));
        }

    }

    public static OrderViewHolder create(ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemOrderBinding = ItemOrderBinding.bind(itemView);
    }

}

