package com.android.interview.layout_performance_comparison;

import android.content.Context;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * author： Created by shiming on 2018/5/30 11:42
 * mailbox：lamshiming@sina.com
 * des:怎么得出来的这个绘制的时间很长，搞不懂
 */

class MyRelativeLayout extends RelativeLayout {
    private long mL;
    public MyRelativeLayout(Context context) {
        this(context,null);
    }
    /*
05-30 11:45:38.836 2934-2934/com.android.interview I/System.out: shiming MyRelativeLayout
05-30 11:45:38.845 2934-2934/com.android.interview I/System.out: shiming MyRelativeLayout  onMeasure time=====7
05-30 11:45:38.853 2934-2934/com.android.interview I/System.out: shiming MyRelativeLayout  onMeasure time=====8
05-30 11:45:38.854 2934-2934/com.android.interview I/System.out: shiming MyRelativeLayout  onLayout time=====9
05-30 11:45:41.335 2934-2934/com.android.interview I/System.out: shiming MyRelativeLayout
05-30 11:45:41.343 2934-2934/com.android.interview I/System.out: shiming MyRelativeLayout  onMeasure time=====7
05-30 11:45:41.359 2934-2934/com.android.interview I/System.out: shiming MyRelativeLayout  onMeasure time=====8
05-30 11:45:41.360 2934-2934/com.android.interview I/System.out: shiming MyRelativeLayout  onLayout time=====8
05-30 11:45:43.593 2934-2934/com.android.interview I/System.out: shiming MyRelativeLayout
05-30 11:45:43.601 2934-2934/com.android.interview I/System.out: shiming MyRelativeLayout  onMeasure time=====6
05-30 11:45:43.617 2934-2934/com.android.interview I/System.out: shiming MyRelativeLayout  onMeasure time=====7
05-30 11:45:43.617 2934-2934/com.android.interview I/System.out: shiming MyRelativeLayout  onLayout time=====8
     */
    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mL = SystemClock.currentThreadTimeMillis();
        System.out.println("shiming MyRelativeLayout");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        long l2 = SystemClock.currentThreadTimeMillis();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        long l1 = SystemClock.currentThreadTimeMillis();
        long l11 = l1 - mL;
        System.out.println("shiming MyRelativeLayout  onMeasure time====="+l11);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        long ll = SystemClock.currentThreadTimeMillis();
        super.onLayout(changed, l, t, r, b);
        long l2 = SystemClock.currentThreadTimeMillis();
        long l1l = l2 - mL;
        System.out.println("shiming MyRelativeLayout  onLayout time====="+l1l);
    }

    @Override
    public void draw(Canvas canvas) {
        long ll = SystemClock.currentThreadTimeMillis();
        super.draw(canvas);
        long l2 = SystemClock.currentThreadTimeMillis();
        long l1l = l2 - mL;
        System.out.println("shiming MyRelativeLayout  draw time====="+l1l);
    }
}
