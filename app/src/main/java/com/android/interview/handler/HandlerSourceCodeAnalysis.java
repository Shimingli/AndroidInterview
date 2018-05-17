package com.android.interview.handler;

import android.app.Activity;

/**
 * author： Created by shiming on 2018/5/15 11:07
 * mailbox：lamshiming@sina.com
 */
/*
Handler是和Looper以及MessageQueue一起工作的，在安卓中，一个 应用启动了，系统会默认创建一个主线程服务的Looper对象 ，该Looper对象处理主线程的所有的Message消息，他的生命周期贯穿整个应用。在主线程中使用的Handler的都会默认的绑定到这个looper的对象，咋主线程中创建handler的时候，它会立即关联主线程Looper对象的MessageQueue，这时发送到的MessageQueue 中的Message对象都会持有这个Handler的对象的引用，这样Looper处理消息时Handler的handlerMessage的方法，因此，如果Message还没有处理完成，那么handler的对象不会立即被垃圾回收
 */
public class HandlerSourceCodeAnalysis {
    public static void doFrist(){


    }
}
