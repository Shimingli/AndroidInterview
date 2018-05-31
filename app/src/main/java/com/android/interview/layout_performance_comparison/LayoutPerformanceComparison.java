package com.android.interview.layout_performance_comparison;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.interview.R;

/**
 * 我是为了测试布局性能的Activity
 */
public class LayoutPerformanceComparison extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //MyLinearLayout
        //setContentView(R.layout.activity_layout_performance_comparison_linear);
        /*
shiming MyLinearLayout  onMeasure time=====26
 shiming MyRelativeLayout  onMeasure time=====13
 shiming MyLinearLayout  onMeasure time=====28
 shiming MyRelativeLayout  onMeasure time=====14
shiming MyLinearLayout  onLayout time=====29
 shiming MyRelativeLayout  onLayout time=====15
shiming MyLinearLayout  draw time=====32
shiming MyRelativeLayout  draw time=====19
         */
        //实现同样的布局的效果，不管是LinearLayout或者是RelativeLayout,都会onMeasure 两次
        //当实现的布局比较简单的时候，一个ViewGroup，里面加上一个View，个人感觉在性能上差不多
        //当布局比较复杂的时候，通过测量。布局和绘制的时间来说，RelativeLayout更加占优？？
        setContentView(R.layout.activity_layout_performance_comparison_relative);


    }
}
