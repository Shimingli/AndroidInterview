package com.android.interview.view_source_code;

import android.content.Context;
import android.content.ContextWrapper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.interview.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewSourceCodeDemoActivity extends AppCompatActivity {

    private Button mBtnDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_source_code_demo);
        mBtnDemo = findViewById(R.id.btn_on_click_demo);
        Context viewSourceCodeDemoActivity = this;
        mBtnDemo.setOnClickListener(new DeclaredOnClickListener(viewSourceCodeDemoActivity, "demoOnclick"));
        String string = mBtnDemo.toString();
        //android.support.v7.widget.AppCompatButton{9d97ae6 VFED..C.. ......I. 0,0-0,0 #7f0b007e app:id/btn_on_click_demo}
        System.out.println("shiming " + string);

        /*View的绘制流程 shiming ，这个真的鸡巴的累，可千万不要只看别人的文档，基本上没戏，要自己看源代码才可以*/
        //1、ActivityThread 通过C++,启动，程序的入口函数

        //View
    }

    private void demoOnclick() {
        Toast.makeText(this, "我是源码级别实现的点击事件", Toast.LENGTH_SHORT).show();
    }

    private static class DeclaredOnClickListener implements View.OnClickListener {
        private final Context mHostView;
        private final String mMethodName;

        private Method mResolvedMethod;
        private Context mResolvedContext;

        public DeclaredOnClickListener(@NonNull Context context, @NonNull String methodName) {
            mHostView = context;
            mMethodName = methodName;
        }

        @Override
        public void onClick(@NonNull View v) {
            if (mResolvedMethod == null) {
                resolveMethod(mHostView);
            }

            try {
                mResolvedMethod.invoke(mResolvedContext, v);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(
                        "Could not execute non-public method for android:onClick", e);
            } catch (InvocationTargetException e) {
                throw new IllegalStateException(
                        "Could not execute method for android:onClick", e);
            }
        }

        @NonNull
        private void resolveMethod(@Nullable Context context) {
            while (context != null) {
                try {
                    if (!context.isRestricted()) {
                        final Method method = context.getClass().getMethod(mMethodName, ViewSourceCodeDemoActivity.class);
                        if (method != null) {
                            mResolvedMethod = method;
                            mResolvedContext = context;
                            return;
                        }
                    }
                } catch (NoSuchMethodException e) {
                    // Failed to find method, keep searching up the hierarchy.
                }

                if (context instanceof ContextWrapper) {
                    context = ((ContextWrapper) context).getBaseContext();
                } else {
                    // Can't search up the hierarchy, null out and fail.
                    context = null;
                }
            }

//            final int id = mHostView.getId();
//            final String idText = id == View.NO_ID ? "" : " with id '"
//                    + mHostView.getContext().getResources().getResourceEntryName(id) + "'";
            throw new IllegalStateException("Could not find method " + mMethodName
                    + "(View) in a parent or ancestor Context for android:onClick "
                    + "attribute defined on view " + mHostView.getClass());
        }
    }

}
