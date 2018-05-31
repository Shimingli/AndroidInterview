package com.android.interview.layout_performance_comparison;

import android.animation.LayoutTransition;
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

/*
1.RelativeLayout会对子View进行两次measure，LinearLayout只对子View进行一次measure，而在设置了weight时，也会对weight进行两次measure，通常情况下，LinearLayout的性能要优于RelativeLayout。
2.在View的层级扁平，没有过多的嵌套的情况下，用LinearLayout效率更高，并且要尽量减少使用weight属性。
3.如果View的层级嵌套过多，则需要使用RelativeLayout来降低层级，因为Android是递归生成View的，过多的层级嵌套会严重影响View的绘制效率。
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

//        LayoutTransition layoutTransition = getLayoutTransition();
//        layoutTransition.isChangingLayout()
    }

    // TODO: 2018/5/31  onMeasure 也会走两次  为什么
    /**
     * <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent" android:layout_height="match_parent" android:fitsSystemWindows="true" android:orientation="vertical">
     * <ViewStub android:id="@+id/action_mode_bar_stub" android:inflatedId="@+id/action_mode_bar" android:layout="@layout/action_mode_bar" android:layout_width="match_parent" android:layout_height="wrap_content" android:theme="?attr/actionBarTheme"/>
     * <FrameLayout android:id="@android:id/content" android:layout_width="match_parent" android:layout_height="match_parent" android:foregroundInsidePadding="false" android:foregroundGravity="fill_horizontal|top" android:foreground="?android:attr/windowContentOverlay"/>
     * </LinearLayout>
     */
    // TODO: 2018/5/31 可以看到 FrameLayout 是所有上Activity 填充的父布局
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        long l1 = SystemClock.currentThreadTimeMillis();
        //long l11 = System.currentTimeMillis();
        //System.nanoTime()
        long l11 = System.nanoTime();
        System.out.println("shiming MyLinearLayout l1===="+l11);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        long l2 = SystemClock.currentThreadTimeMillis();
        long l22 = System.nanoTime();
        System.out.println("shiming MyLinearLayout l2===="+l22);
        long newtime = l22 - l11;
        // TODO: 2018/5/31
        /**
         onMeasure time=====246875  1000000   大概第一次绘制的的时间是1.120834 ms
         布局设置了weight 属性   孩子测量了两次 大概时间 1.120834 ms

         取消孩子的weight 属性  绘制的时间 onMeasure time=====1056250   大概是1.05ms
         onMeasure time=====111459
         onMeasure time=====251042
         */
        System.out.println("shiming MyLinearLayout  onMeasure time====="+newtime);
        System.out.println("shiming MyLinearLayout onMeasure");
        // TODO: 2018/5/31 需要对 FrameLayout 进行 onMeasure 代码分析
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        long l1 = SystemClock.currentThreadTimeMillis();
        System.out.println("shiming MyLinearLayout onLayout");
        super.onLayout(changed, l, t, r, b);
        long l2 = SystemClock.currentThreadTimeMillis();
        long l11 = l2 - mL;
        System.out.println("shiming MyLinearLayout  onLayout time====="+l11);

    }

    /**
     *onDraw 就画了一个Dividers 孩子和孩子之间的线  更不不去绘制孩子
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("shiming MyLinearLayout onDraw");
    }
    /*
    其实就是 View中的draw的方法 ，注意调用的时机 ，底层是通过
    ViewRootImpl.performDraw() 调用的draw
    执行绘制
     */
    @Override
    public void draw(Canvas canvas) {
        System.out.println("shiming MyLinearLayout draw");
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
      //  System.out.println("shiming MyLinearLayout 这个方法会走一次么 measureChildWithMargins");
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

    @Override
    public int getChildCount() {
        return super.getChildCount();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        System.out.println("shiming MyLinearLayout onFinishInflate");
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        System.out.println("shiming MyLinearLayout onAttachedToWindow");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        System.out.println("shiming MyLinearLayout onSizeChanged");
    }
}
