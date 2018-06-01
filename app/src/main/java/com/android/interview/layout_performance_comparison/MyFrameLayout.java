package com.android.interview.layout_performance_comparison;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * author： Created by shiming on 2018/5/31 18:16
 * mailbox：lamshiming@sina.com
 */

class MyFrameLayout extends FrameLayout {
    int i=1;
    public MyFrameLayout(Context context) {
        super(context);
    }

    public MyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

    }
    /*
    开头我发现是这里原因 DecorView.addView()是一个LinearLayout的里面有FrameLayout的布局的原因
    导致测量了两次
     */
    /*
api26：执行2次onMeasure、1次onLayout、1次onDraw
api25-24：执行2次onMeasure、2次onLayout、1次onDraw，
api23-21：执行3次onMeasure、2次onLayout、1次onDraw，
api19-16：执行2次onMeasure、2次onLayout、1次onDraw，
API等级24：Android 7.0 Nougat
API等级25：Android 7.1 Nougat
API等级26：Android 8.0 Oreo
API等级27：Android 8.1 Oreo
原因第一次performTranversals中只会执行measureHierarchy中的performMeasure，forceLayout标志位，离奇被置位true，导致无测量优化。
 
     */
    // TODO: 2018/5/31 https://www.jianshu.com/p/733c7e9fb284  这篇文章写得很好  真的很好
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        System.out.println("shiming MyFrameLayout childCount "+childCount);
        /**
         * 重写了这个方法，让它去测量孩子，然后在layout
         */
        // TODO: 2018/5/31  明天搞明白 我终于搞明白了，但是有个问题？知道为啥测量两次，第二次测量和第一次测量有什么作用，搞明白了，就可画图了，哈哈哈哈 
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        System.out.println("shiming MyFrameLayout onMeasure");
        System.out.println("shiming MyFrameLayout onMeasure 执行了几次"+i);
        i++;
    }
}
