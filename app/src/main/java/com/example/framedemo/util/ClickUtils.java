package com.example.framedemo.util;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

/**
 *  自定义长按时间工具类
 */
public class ClickUtils {

    public static void setClick(final Handler handler, final View clickView, final View.OnClickListener clickListener,
                                final OnLongClickListener longClickListener, final long delayMillis) {
        final boolean[] isLongClick = new boolean[1];
        clickView.setOnTouchListener(new OnTouchListener() {
            private int TOUCH_MAX = 50;
            private int mLastMotionX;
            private int mLastMotionY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        handler.removeCallbacks(r);
                        if (!isLongClick[0] && clickListener != null) {
                            clickListener.onClick(v);
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (Math.abs(mLastMotionX - x) > TOUCH_MAX
                                || Math.abs(mLastMotionY - y) > TOUCH_MAX) {
                            handler.removeCallbacks(r);
                        }
                        break;
                    case MotionEvent.ACTION_DOWN:
                        isLongClick[0] = false;
                        handler.removeCallbacks(r);
                        mLastMotionX = x;
                        mLastMotionY = y;
                        handler.postDelayed(r, delayMillis);
                        break;
                }
                return true;
            }

            private Runnable r = new Runnable() {
                @Override
                public void run() {
                    if (longClickListener != null) {
                        isLongClick[0] = true;
                        longClickListener.onLongClick(clickView);
                    }
                }
            };
        });
    }
}
