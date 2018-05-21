package com.android.interview.merge_and_viewstub_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.android.interview.R;
/*
Principle 原理 merge 标签 and StubView
布局相关的<merge>、<viewstub>控件作用及实现原理
 */
public class MergePrincipleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge_principle);

         /*
          merge标签可以自动消除当一个布局插入到另一个布局时产生的多余的View Group，
          也可用于替换FrameLayout。用法就是直接使用merge标签标签作为复用布局的根节
           */
        LayoutInflater.from(this).inflate(R.layout.activity_merge_principle,null);
    }
}
