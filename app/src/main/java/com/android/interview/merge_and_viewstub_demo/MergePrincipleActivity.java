package com.android.interview.merge_and_viewstub_demo;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.interview.R;


/*
Principle 原理 merge 标签 and StubView
布局相关的<merge>、<viewstub>控件作用及实现原理
 */
public class MergePrincipleActivity extends AppCompatActivity {
    private ViewStub mViewStub;
    private Button mBtnViewStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merge_principle);
        // TODO: 2018/5/22 如果在include的标签下
        View titleView = findViewById(R.id.my_title_parent_id);
        //View titleView = findViewById(R.id.my_title_ly) ;
        //LayoutInflater.from(mContext).inflate(resId, contentParent);

//        LayoutInflater inflater1 = getLayoutInflater();//调用Activity的getLayoutInflater()
//        LayoutInflater inflater2 = LayoutInflater.from(this);
//        LayoutInflater inflater3 = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        /*
        include时设置了该布局的id为my_title_ly，而my_title_layout.xml中的根视图的id为my_title_parent_id
        。此时如果通过findViewById来找my_title_parent_id这个控件，然后再查找my_title_parent_id下的子控件则会抛出空指针
         */
        // TODO: 2018/5/22 shiming titleTextViewnull
        // TODO: 2018/5/22 himing titleTextViewandroid.widget.RelativeLayout{5117337 V.E...... ......I. 0,0-0,0 #7f0b0078 app:id/my_title_parent_id} 
        System.out.println("shiming titleTextView" + titleView);
        if (titleView != null) {
            // 此时 titleView 为空，找不到。此时空指针
            TextView titleTextView = (TextView) titleView.findViewById(R.id.title_tv);
            titleTextView.setText("new Title titleTextView");
        }

        // 使用include时设置的id,即R.id.my_title_ly
        // TODO: 2018/5/22      include标签的设置的id===  android:id="@+id/my_title_ly"
        //View includeLayout = findViewById(R.id.my_title_ly) ;
        View includeLayout = null;
        // 通过titleView找子控件
        // TODO: 2018/5/22 shiming includeLayoutandroid.widget.RelativeLayout{78e0cf8 V.E...... ......I. 0,0-0,0 #7f0b0077 app:id/my_title_ly}
        // todo shiming includeLayoutnull 
        System.out.println("shiming includeLayout" + includeLayout);
        if (includeLayout != null) {
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
        // TODO: 2018/5/23    LayoutInflater.from(this).inflate(R.layout.activity_merge_principle,null);

        //blink单词也是这个意思.闪烁频率500毫秒

        /**ViewStub 是一种不可见的并且大小为0的试图，它可以延迟到运行时才填充inflate 布局资源，
         * 当Viewstub设为可见或者是inflate的时候，就会填充布局资源，这个布局和普通的试图就基本上没有任何区别，
         * 比如说，加载网络失败，或者是一个比较消耗性能的功能，需要用户去点击才可以加载，参考我的开源的项目WritingPen
         * 还有这种情况：ViewStub来惰性加载一个消息流的评论列表，因为一个帖子可能并没有评论，
         * 此时我可以不加载这个评论的ListView，只有当有评论时我才把它加载出来
         */
        //why  tell me why
        mBtnViewStub = findViewById(R.id.btn_view_stub);
        mViewStub = findViewById(R.id.view_stub);
        mBtnViewStub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mViewStub.getParent()) {
                    /*
                    android:inflatedId 的值是Java代码中调用ViewStub的 inflate()或者是serVisibility方法返回的Id，这个id就是被填充的View的Id
                     */
                    /**
                     * ViewStub.inflate() 的方法和 setVisibility 方法是差不多，因为 setVisibility方法会（看源码）走这个inflate的方法
                     */
//                    View inflate = mViewStub.inflate();
                    mViewStub.setVisibility(View.VISIBLE);
                    //inflate--->android.support.v7.widget.AppCompatImageView{de7e3a2 V.ED..... ......I. 0,0-0,0 #7f07003e app:id/find_view_stub}
//                    System.out.println("shiming inflate--->"+inflate);
                    final View find_view_stub = findViewById(R.id.find_view_stub);
                    System.out.println("shiming ----" + find_view_stub);
                    // TODO: 2018/5/23  根据输出的结果 为null  只要ViewStub 设置了    android:inflatedId="@+id/find_view_stub"  找出来就是null
                    View viewById = findViewById(R.id.view_stub_inner_frame_layout);
                    System.out.println("shiming ViewStub 里面的根布局的id 为===" + viewById);
                    View iamgeivew11 = find_view_stub.findViewById(R.id.imageview);
                    //himing ---- iamgeivew11null
                    // TODO: 2018/5/4 為啥為null  原因是布局文件中根布局只有View，没有ViewGroup
                    System.out.println("shiming ---- iamgeivew11" + iamgeivew11);

                } else {
                    Toast.makeText(MergePrincipleActivity.this, "已经inflate了", Toast.LENGTH_LONG).show();
                    final View viewById = findViewById(R.id.find_view_stub);
                    View iamgeivew = findViewById(R.id.imageview);
                    //已经inflate了android.support.v7.widget.AppCompatImageView{4637833 V.ED..... ........ 348,294-732,678 #7f07003e app:id/find_view_stub}
                    System.out.println("shiming l----已经inflate了" + viewById);//
                    System.out.println("shiming l----已经inflate了iamgeivew" + iamgeivew);//已经inflate了iamgeivew==null
                    View iamgeivew11 = viewById.findViewById(R.id.imageview);
                    //已经inflate了 iamgeivew11null
                    System.out.println("shiming l----已经inflate了 iamgeivew11" + iamgeivew11);

                    // TODO: 2018/5/23  根据输出的结果 为null  只要ViewStub 设置了    android:inflatedId="@+id/find_view_stub"  找出来就是null
                    View viewById1 = findViewById(R.id.view_stub_inner_frame_layout);
                    System.out.println("shiming ViewStub 已经inflate了 ===里面的根布局的id 为===" + viewById1);
                }
            }
        });
        // TODO: 2018/5/23  ViewStub 中的第二种的加载的方式 
//        //commLv2 ViewStub 中的第二种的加载的方式
//        mViewStub = findViewById(R.id.view_stub);
//        // 成员变量commLv2为空则代表未加载 commLv2 的id为ViewStub中的根布局的id
//        View commLv2=findViewById(R.id.my_title_parent_id);
//        if ( commLv2 == null ) {
//            // 加载评论列表布局, 并且获取评论ListView,inflate函数直接返回ListView对象
//            commLv2 = (View)mViewStub.inflate();
//        } else {
//            // ViewStub已经加载
//        }

    }


}
