package com.android.interview.merge_and_viewstub_demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        // TODO: 2018/5/22 如果在include的标签下
        View titleView = findViewById(R.id.my_title_parent_id) ;
        /*
        include时设置了该布局的id为my_title_ly，而my_title_layout.xml中的根视图的id为my_title_parent_id
        。此时如果通过findViewById来找my_title_parent_id这个控件，然后再查找my_title_parent_id下的子控件则会抛出空指针
         */
        // TODO: 2018/5/22 shiming titleTextViewnull
        // TODO: 2018/5/22 himing titleTextViewandroid.widget.RelativeLayout{5117337 V.E...... ......I. 0,0-0,0 #7f0b0078 app:id/my_title_parent_id} 
        System.out.println("shiming titleTextView" + titleView);
        if (titleView!=null) {
            // 此时 titleView 为空，找不到。此时空指针
            TextView titleTextView = (TextView) titleView.findViewById(R.id.title_tv);
            titleTextView.setText("new Title titleTextView");
        }

        // 使用include时设置的id,即R.id.my_title_ly
        // TODO: 2018/5/22      include标签的设置的id===  android:id="@+id/my_title_ly"
       //View includeLayout = findViewById(R.id.my_title_ly) ;
       View includeLayout = null ;
         // 通过titleView找子控件
        // TODO: 2018/5/22 shiming includeLayoutandroid.widget.RelativeLayout{78e0cf8 V.E...... ......I. 0,0-0,0 #7f0b0077 app:id/my_title_ly}
        // todo shiming includeLayoutnull 
         System.out.println("shiming includeLayout" + includeLayout);
        if (includeLayout!=null) {
            TextView titleTextView1 = (TextView) includeLayout.findViewById(R.id.title_tv);
            if (titleTextView1 != null)
                titleTextView1.setText("new Title--titleTextView1");
        }
        /*
       在ViewStub中， 如果android:layout="@layout/view_stub_imageview"，比如view_stub_imageview根布局
       说是个ImagView，那么找出来的id为null，得必须注意这一点，详情请看，安卓性能优化的文章 https://www.jianshu.com/p/82b76e0cb41e
         */
         /*
          merge标签可以自动消除当一个布局插入到另一个布局时产生的多余的View Group，
          也可用于替换FrameLayout。用法就是直接使用merge标签标签作为复用布局的根节
           */
        LayoutInflater.from(this).inflate(R.layout.activity_merge_principle,null);

        //blink单词也是这个意思.闪烁频率500毫秒
    }
    // TODO: 2018/5/22 三种setContentView的源码解析  AppComatDelegateImplV9
//    @Override
//    public void setContentView(View v) {
//        ensureSubDecor();
//        ViewGroup contentParent = (ViewGroup) mSubDecor.findViewById(android.R.id.content);
//        contentParent.removeAllViews();
//        contentParent.addView(v);
//        mOriginalWindowCallback.onContentChanged();
//    }
//
//    @Override
//    public void setContentView(int resId) {
//        ensureSubDecor();
//        ViewGroup contentParent = (ViewGroup) mSubDecor.findViewById(android.R.id.content);
//        contentParent.removeAllViews();
//        LayoutInflater.from(mContext).inflate(resId, contentParent);
//        mOriginalWindowCallback.onContentChanged();
//    }
//
//    @Override
//    public void setContentView(View v, ViewGroup.LayoutParams lp) {
//        ensureSubDecor();
//        ViewGroup contentParent = (ViewGroup) mSubDecor.findViewById(android.R.id.content);
//        contentParent.removeAllViews();
//        contentParent.addView(v, lp);
//        mOriginalWindowCallback.onContentChanged();
//    }


}
