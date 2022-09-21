package com.android.interview.invalidate_demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * author： Created by shiming on 2018/8/6 16:13
 * mailbox：lamshiming@sina.com
 */

@SuppressLint("AppCompatCustomView")
class InvalidateTextView extends TextView {
    public InvalidateTextView(Context context) {
        this(context,null);
    }

    public InvalidateTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
     //measure 方法 被final修饰 不可以重写
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        System.out.println(this.getClass().getSimpleName()+"------onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        System.out.println(this.getClass().getSimpleName()+"-------layout");
        super.layout(l, t, r, b);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        System.out.println(this.getClass().getSimpleName()+"--------onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void draw(Canvas canvas) {
        System.out.println(this.getClass().getSimpleName()+"----------draw");
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        System.out.println(this.getClass().getSimpleName()+"------------onDraw");
        super.onDraw(canvas);
    }
}
