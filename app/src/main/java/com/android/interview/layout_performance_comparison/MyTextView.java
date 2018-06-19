package com.android.interview.layout_performance_comparison;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * author： Created by shiming on 2018/6/4 16:28
 * mailbox：lamshiming@sina.com
 * des:测试绘制的是否需要两次
 */

@SuppressLint("AppCompatCustomView")
class MyTextView extends TextView {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        System.out.println("shiming MyTextView ");
    }

    /**
     * 也会onMeasure 两次 比较可以滴
     *
     * 打印方法呢，需要注意，在哪里打印，当设置了weight的属性的时候
     * 下面的TextView会走47次
     *
     * 如果不设置 weight的属性的话 只会走两次
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        System.out.println("shiming MyTextView  onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        System.out.println("shiming MyTextView  onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("shiming MyTextView  onDraw");
    }

    /**
     * 是在View中的一个空实现，是在onMeasure之前调用，先填充完成后，再去测回，onLayout  onDraw
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        System.out.println("shiming MyTextView onFinishInflate" );
    }
}
