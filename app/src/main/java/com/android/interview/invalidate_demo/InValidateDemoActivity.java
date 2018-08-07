package com.android.interview.invalidate_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.interview.R;
/*
    invalidate、postInvalidate、requestLayout应用场景

    View确定自身不再适合当前区域，比如说它的LayoutParams发生了改变，需要父布局对其进行重新测量、布局、绘制这三个流程，往往使用requestLayout。而invalidate则是刷新当前View，使当前View进行重绘，不会进行测量、布局流程，因此如果View只需要重绘而不需要测量，布局的时候，使用invalidate方法往往比requestLayout方法更高效
 */
public class InValidateDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_validate_demo);
        final InvalidateTextView textView= findViewById(R.id.invalidate_text_view);
        findViewById(R.id.postInvalidate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //这个方法与invalidate方法的作用是一样的，都是使View树重绘，但两者的使用条件不同，postInvalidate是在非UI线程中调用，invalidate则是在UI线程中调用  postInvalidateDelayed() 可以设置延时的时间 才去绘制

                Toast.makeText(InValidateDemoActivity.this,"postInvalidate",Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        textView.postInvalidate();
                    }
                }).start();
                /*
 08-06 16:30:06.898 18819-18819/com.android.interview I/System.out: InvalidateTextView----------draw
08-06 16:30:06.898 18819-18819/com.android.interview I/System.out: InvalidateTextView------------onDraw
                 */
            }
        });
        findViewById(R.id.invalidate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                该方法的调用会引起View树的重绘，常用于内部调用(比如 setVisiblity())或者需要刷新界面的时候,需要在主线程(即UI线程)中调用该方法
                invalidate有多个重载方法，但最终都会调用invalidateInternal方法，在这个方法内部，进行了一系列的判断，判断View是否需要重绘，接着为该View设置标记位，然后把需要重绘的区域传递给父容器，即调用父容器的invalidateChild方法。
                 */
                // invalidate方法，当子View调用了invalidate方法后，会为该View添加一个标记位，同时不断向父容器请求刷新，父容器通过计算得出自身需要重绘的区域，直到传递到ViewRootImpl中，最终触发performTraversals方法，进行开始View树重绘流程(只绘制需要重绘的视图)
                textView.invalidate();
                Toast.makeText(InValidateDemoActivity.this,"invalidate",Toast.LENGTH_SHORT).show();
                /*
08-06 16:30:06.898 18819-18819/com.android.interview I/System.out: InvalidateTextView----------draw
08-06 16:30:06.898 18819-18819/com.android.interview I/System.out: InvalidateTextView------------onDraw
                 */
            }
        });
        findViewById(R.id.requestLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                “请求布局”，那就是说，如果调用了这个方法，那么对于一个子View来说，应该会重新进行布局流程。但是，真实情况略有不同，如果子View调用了这个方法，其实会从View树重新进行一次测量、布局、绘制这三个流程，最终就会显示子View的最终情况
                 */
                //虽然执行了两次这个方法，但是呢，其实只执行一次
                textView.requestLayout();
                textView.requestLayout();
                Toast.makeText(InValidateDemoActivity.this,"requestLayout",Toast.LENGTH_SHORT).show();
                /*
08-06 16:30:21.103 18819-18819/com.android.interview I/System.out: InvalidateTextView------onMeasure
08-06 16:30:21.103 18819-18819/com.android.interview I/System.out: InvalidateTextView------onMeasure
08-06 16:30:21.103 18819-18819/com.android.interview I/System.out: InvalidateTextView-------layout
08-06 16:30:21.103 18819-18819/com.android.interview I/System.out: InvalidateTextView--------onLayout
08-06 16:30:21.104 18819-18819/com.android.interview I/System.out: InvalidateTextView----------draw
08-06 16:30:21.104 18819-18819/com.android.interview I/System.out: InvalidateTextView------------onDraw

                 */
            }
        });
    }
}
