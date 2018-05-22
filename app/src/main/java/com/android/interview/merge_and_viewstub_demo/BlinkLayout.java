package com.android.interview.merge_and_viewstub_demo;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * author： Created by shiming on 2018/5/22 20:15
 * mailbox：lamshiming@sina.com
 */

public   class BlinkLayout extends FrameLayout {
    private static final int MESSAGE_BLINK = 0x42;
    private static final int BLINK_DELAY = 500;
    private boolean mBlink;
    private boolean mBlinkState;
    private final Handler mHandler;

    public BlinkLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO: 2018/5/22 对handler  源码比较熟悉的话，这里就是另外的一种使用的方法 
        mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == MESSAGE_BLINK) {
                    if (mBlink) {
                        mBlinkState = !mBlinkState;
                        makeBlink();
                    }
                    invalidate();
                    return true;
                }
                return false;
            }
        });
    }

    private void makeBlink() {
        Message message = mHandler.obtainMessage(MESSAGE_BLINK);
        mHandler.sendMessageDelayed(message, BLINK_DELAY);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mBlink = true;
        mBlinkState = true;

        makeBlink();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mBlink = false;
        mBlinkState = true;

        mHandler.removeMessages(MESSAGE_BLINK);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mBlinkState) {
            super.dispatchDraw(canvas);
        }
    }
}