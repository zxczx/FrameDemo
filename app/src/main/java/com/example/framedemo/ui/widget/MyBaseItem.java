package com.example.framedemo.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class MyBaseItem extends LinearLayout {
    public MyBaseItem(Context context) {
        super(context);
    }

    public MyBaseItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBaseItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
