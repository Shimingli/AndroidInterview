package com.android.interview.layout_performance_comparison;

import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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


        /**
         * LinearLayout和RelativeLayout的性能差别主要体在onMeasure方法上，RelativeLayout始终要从竖直和水平两个方向对子View进行测量，而Linearlayout，当我们没有在子View中使用layout_weight属性时，LinearLayout只需对子View进行一次测量，反则需要对子View进行两次测量以确定最终大小，所以如果可以我们尽量少用layout_weight属性。在使用这两个布局之前，我们可以先进行衡量，如果需要实现的布局嵌套层次不深或者嵌套层次已经固定了，可以考虑用LinearLayout，相对的，如果某个布局嵌套层次很深，此时应该考虑使用RelativeLayout来减少嵌套层析从而优化布局的性能。
         */
        System.out.println("shiming  Activity  onCreate");
        setContentView(R.layout.activity_layout_performance_comparison_relative);
        // TODO: 2018/5/31  num =3;
        int num = 0x3;
        System.out.println("shiming 0x3" + num);

    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("shiming  Activity  onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("shiming  Activity  onResume");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        System.out.println("shiming  Activity  onPostResume");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        System.out.println("shiming  Activity  onWindowFocusChanged");
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        System.out.println("shiming  Activity  onContentChanged");
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        System.out.println("shiming  Activity  onPostCreate");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("shiming  Activity  onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("shiming  Activity  onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("shiming  Activity  onStop");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        System.out.println("shiming  Activity  onPostCreate");
    }
}
