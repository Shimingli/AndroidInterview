package com.android.interview.layout_performance_comparison;

import android.content.Context;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * author： Created by shiming on 2018/5/30 11:32
 * mailbox：lamshiming@sina.com
 */
public class MyLinearLayout extends LinearLayout {

    private long mL;

    public MyLinearLayout(Context context) {
        this(context,null);
    }
   /*
05-30 11:40:57.556 2124-2124/com.android.interview I/System.out: shiming MyLinearLayout  onMeasure time=====7
05-30 11:40:57.562 2124-2124/com.android.interview I/System.out: shiming MyLinearLayout  onMeasure time=====8
05-30 11:40:57.563 2124-2124/com.android.interview I/System.out: shiming MyLinearLayout  onLayout time=====8
    */
    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mL = SystemClock.currentThreadTimeMillis();
        System.out.println("shiming MyLinearLayout");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        long l1 = SystemClock.currentThreadTimeMillis();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        long l2 = SystemClock.currentThreadTimeMillis();
        long newtime = l2 - mL;
        System.out.println("shiming MyLinearLayout  onMeasure time====="+newtime);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        long l1 = SystemClock.currentThreadTimeMillis();
        super.onLayout(changed, l, t, r, b);
        long l2 = SystemClock.currentThreadTimeMillis();
        long l11 = l2 - mL;
        System.out.println("shiming MyLinearLayout  onLayout time====="+l11);
    }

    @Override
    public void draw(Canvas canvas) {
        long l1 = SystemClock.currentThreadTimeMillis();
        super.draw(canvas);
        long l2 = SystemClock.currentThreadTimeMillis();
        long l11 = l2 - mL;
        System.out.println("shiming MyLinearLayout  draw time====="+l11);
    }

    /**
     * 有多少孩子的话，就测量多少次 ，如果有4个孩子的话，就测量4次
     * @param child
     * @param parentWidthMeasureSpec
     * @param widthUsed
     * @param parentHeightMeasureSpec
     * @param heightUsed
     */
    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
        //    //重新调用了一次，child.measure方法
        //        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        System.out.println("shiming MyLinearLayout 这个方法会走一次么 measureChildWithMargins");
    }

    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
        System.out.println("shiming MyLinearLayout 这个方法会走一次么 measureChild");
    }

    @Override
    protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
        super.measureChildren(widthMeasureSpec, heightMeasureSpec);
        System.out.println("shiming MyLinearLayout 这个方法会走一次么 measureChildren");
//        measureChildBeforeLayout
    }


}
