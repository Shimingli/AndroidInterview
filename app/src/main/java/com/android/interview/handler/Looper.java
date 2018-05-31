//
///*
// * Copyright (C) 2006 The Android Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.android.interview.handler;
//
//
//import android.bluetooth.BluetoothDevice;
//import android.os.Binder;
//import android.os.MessageQueue;
//import android.os.SystemClock;
//import android.os.Trace;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.util.Log;
//import android.util.Printer;
//
///**
//  * Class used to run a message loop for a thread.  Threads by default do
//  * not have a message loop associated with them; to create one, call
//  * {@link #prepare} in the thread that is to run the loop, and then
//  * {@link #loop} to have it process messages until the loop is stopped.
//  *
//  * <p>Most interaction with a message loop is through the
//  * {@link Handler} class.
//  *
//  * <p>This is a typical example of the implementation of a Looper thread,
//  * using the separation of {@link #prepare} and {@link #loop} to create an
//  * initial Handler to communicate with the Looper.
//  *
//  * <pre>
//  *  class LooperThread extends Thread {
//  *      public Handler mHandler;
//  *
//  *      public void run() {
//  *          Looper.prepare();
//  *
//  *          mHandler = new Handler() {
//  *              public void handleMessage(Message msg) {
//  *                  // process incoming messages here
//  *              }
//  *          };
//  *
//  *          Looper.loop();
//  *      }
//  *  }</pre>
//  */
//public final class Looper {
//    /*
//     * API Implementation Note:
//     *
//     * This class contains the code required to set up and manage an event loop
//     * based on MessageQueue.  APIs that affect the state of the queue should be
//     * defined on MessageQueue or Handler rather than on Looper itself.  For example,
//     * idle handlers and sync barriers are defined on the queue whereas preparing the
//     * thread, looping, and quitting are defined on the looper.
//     */
//
//    private static final String TAG = "Looper";
//
//    // sThreadLocal.get() will return null unless you've called prepare().
//    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<Looper>();
//    private static Looper sMainLooper;  // guarded by Looper.class
//
//    final MessageQueue mQueue;
//    final Thread mThread;
//
//    private Printer mLogging;
//    private long mTraceTag;
//
//    /* If set, the looper will show a warning log if a message dispatch takes longer than time. */
//    private long mSlowDispatchThresholdMs;
//
//     /** Initialize the current thread as a looper.
//      * This gives you a chance to create handlers that then reference
//      * this looper, before actually starting the loop. Be sure to call
//      * {@link #loop()} after calling this method, and end it by calling
//      * {@link #quit()}.
//      */
//    public static void prepare() {
//        prepare(true);
//    }
//
//    private static void prepare(boolean quitAllowed) {
//        if (sThreadLocal.get() != null) {
//            throw new RuntimeException("Only one Looper may be created per thread");
//        }
//        sThreadLocal.set(new Looper(quitAllowed));
//    }
//
//    /**
//     * Initialize the current thread as a looper, marking it as an
//     * application's main looper. The main looper for your application
//     * is created by the Android environment, so you should never need
//     * to call this function yourself.  See also: {@link #prepare()}
//     */
//    ////Looper的prepare方法，并且关联到主线程
//    public static void prepareMainLooper() {
//        //Only one Looper may be created per thread"
//        // false意思不允许我们程序员退出（面向我们开发者），因为这是在主线程里面
//        // TODO: 2018/5/17
//        prepare(false);
//        synchronized (Looper.class) {
//            if (sMainLooper != null) {
//                throw new IllegalStateException("The main Looper has already been prepared.");
//            }
//            //把Looper设置为主线程的Looper
//            sMainLooper = myLooper();
//        }
//    }
//
//    /**
//     * Returns the application's main looper, which lives in the main thread of the application.
//     */
//    public static Looper getMainLooper() {
//        synchronized (Looper.class) {
//            return sMainLooper;
//        }
//    }
//
//    /**
//     * Run the message queue in this thread. Be sure to call
//     * {@link #quit()} to end the loop.
//     */
//    // TODO: 2018/5/17
//    // 根据我们的常识知道，如果程序没有死循环的话，执行完main函数（比如构建视图等等代码）以后就会立马退出了。
//    // 之所以我们的APP能够一直运行着，就是因为Looper.loop()里面是一个死循环
//    public static void loop() {
//        final Looper me = myLooper();
//        if (me == null) {
//            // TODO: 2018/5/17   在子线程中创建handler的话，需要looper也要准备好 ，要不然会报错
//            // 1、 首先拿到Looper对象（me），如果当前的线程没有Looper，那么就会抛出异常，
//            // 这就是为什么在子线程里面创建Handler如果不手动创建和启动Looper会报错的原因
//            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
//        }
//        final MessageQueue queue = me.mQueue;
//
//        // Make sure the identity of this thread is that of the local process,
//        // and keep track of what that identity token actually is.
//        Binder.clearCallingIdentity();
//        final long ident = Binder.clearCallingIdentity();
//
//        for (; ; ) {
//            // TODO: 2018/5/17   Message
//           // 2、然后拿到Looper的成员变量MessageQueue，在MessageQueue里面不断地去取消息，关于MessageQueue的next方法如下：
//            Message msg = queue.next(); // might block
//            if (msg == null) {
//                // No message indicates that the message queue is quitting.
//                return;
//            }
//
//            // This must be in a local variable, in case a UI event sets the logger
//            final Printer logging = me.mLogging;
//            if (logging != null) {
//                logging.println(">>>>> Dispatching to " + msg.target + " " +
//                        msg.callback + ": " + msg.what);
//            }
//
//            final long slowDispatchThresholdMs = me.mSlowDispatchThresholdMs;
//
//            final long traceTag = me.mTraceTag;
//            if (traceTag != 0 && Trace.isTagEnabled(traceTag)) {
//                Trace.traceBegin(traceTag, msg.target.getTraceName(msg));
//            }
//            final long start = (slowDispatchThresholdMs == 0) ? 0 : SystemClock.uptimeMillis();
//            final long end;
//          //  msg.target.dispatchMessage(msg)就是处理消息，紧接着在loop方法的最后调用了msg.recycleUnchecked()这就是回收了Message。
//            // TODO: 2018/5/17
//            处理消息
//            try {
//                处理消息
//                // TODO: 2018/5/17  msg中的target 就是handler的本体的对象  ，直接去handler中发送这个对象
//                msg.target.dispatchMessage(msg);
//                end = (slowDispatchThresholdMs == 0) ? 0 : SystemClock.uptimeMillis();
//            } finally {
//                if (traceTag != 0) {
//                    Trace.traceEnd(traceTag);
//                }
//            }
//            if (slowDispatchThresholdMs > 0) {
//                final long time = end - start;
//                if (time > slowDispatchThresholdMs) {
//                    Slog.w(TAG, "Dispatch took " + time + "ms on "
//                            + Thread.currentThread().getName() + ", h=" +
//                            msg.target + " cb=" + msg.callback + " msg=" + msg.what);
//                }
//            }
//
//            if (logging != null) {
//                logging.println("<<<<< Finished to " + msg.target + " " + msg.callback);
//            }
//
//            // Make sure that during the course of dispatching the
//            // identity of the thread wasn't corrupted.
//            final long newIdent = Binder.clearCallingIdentity();
//            if (ident != newIdent) {
//                Log.wtf(TAG, "Thread identity changed from 0x"
//                        + Long.toHexString(ident) + " to 0x"
//                        + Long.toHexString(newIdent) + " while dispatching to "
//                        + msg.target.getClass().getName() + " "
//                        + msg.callback + " what=" + msg.what);
//            }
//            // TODO: 2018/5/17
//            我们平时写Handler的时候不需要我们手动回收，因为谷歌的工程师已经有考虑到这方面的问题了。消息是在Handler分发处理之后就会被自动回收的：
//            msg.recycleUnchecked();
//        }
//    }
//
//    /**
//     * Return the Looper object associated with the current thread.  Returns
//     * null if the calling thread is not associated with a Looper.
//     */
//    public static @Nullable
//    Looper myLooper() {
//        return sThreadLocal.get();
//    }
//
//    /**
//     * Return the {@link MessageQueue} object associated with the current
//     * thread.  This must be called from a thread running a Looper, or a
//     * NullPointerException will be thrown.
//     */
//    public static @NonNull
//    MessageQueue myQueue() {
//        return myLooper().mQueue;
//    }
//
//    private Looper(boolean quitAllowed) {
//        mQueue = new MessageQueue(quitAllowed);
//        mThread = Thread.currentThread();
//    }
//
//    /**
//     * Returns true if the current thread is this looper's thread.
//     */
//    public boolean isCurrentThread() {
//        return Thread.currentThread() == mThread;
//    }
//
//    /**
//     * Control logging of messages as they are processed by this Looper.  If
//     * enabled, a log message will be written to <var>printer</var>
//     * at the beginning and ending of each message dispatch, identifying the
//     * target Handler and message contents.
//     *
//     * @param printer A Printer object that will receive log messages, or
//     * null to disable message logging.
//     */
//    public void setMessageLogging(@Nullable Printer printer) {
//        mLogging = printer;
//    }
//
//    /** {@hide} */
//    public void setTraceTag(long traceTag) {
//        mTraceTag = traceTag;
//    }
//
//    /** {@hide} */
//    public void setSlowDispatchThresholdMs(long slowDispatchThresholdMs) {
//        mSlowDispatchThresholdMs = slowDispatchThresholdMs;
//    }
//
//    /**
//     * Quits the looper.
//     * <p>
//     * Causes the {@link #loop} method to terminate without processing any
//     * more messages in the message queue.
//     * </p><p>
//     * Any attempt to post messages to the queue after the looper is asked to quit will fail.
//     * For example, the {@link Handler#sendMessage(Message)} method will return false.
//     * </p><p class="note">
//     * Using this method may be unsafe because some messages may not be delivered
//     * before the looper terminates.  Consider using {@link #quitSafely} instead to ensure
//     * that all pending work is completed in an orderly manner.
//     * </p>
//     *
//     * @see #quitSafely
//     */
//    public void quit() {
//        mQueue.quit(false);
//    }
//
//    /**
//     * Quits the looper safely.
//     * <p>
//     * Causes the {@link #loop} method to terminate as soon as all remaining messages
//     * in the message queue that are already due to be delivered have been handled.
//     * However pending delayed messages with due times in the future will not be
//     * delivered before the loop terminates.
//     * </p><p>
//     * Any attempt to post messages to the queue after the looper is asked to quit will fail.
//     * For example, the {@link Handler#sendMessage(Message)} method will return false.
//     * </p>
//     */
//    public void quitSafely() {
//        mQueue.quit(true);
//    }
//
//    /**
//     * Gets the Thread associated with this Looper.
//     *
//     * @return The looper's thread.
//     */
//    public @NonNull Thread getThread() {
//        return mThread;
//    }
//
//    /**
//     * Gets this looper's message queue.
//     *
//     * @return The looper's message queue.
//     */
//    public @NonNull MessageQueue getQueue() {
//        return mQueue;
//    }
//
//    /**
//     * Dumps the state of the looper for debugging purposes.
//     *
//     * @param pw A printer to receive the contents of the dump.
//     * @param prefix A prefix to prepend to each line which is printed.
//     */
//    public void dump(@NonNull Printer pw, @NonNull String prefix) {
//        pw.println(prefix + toString());
//        mQueue.dump(pw, prefix + "  ", null);
//    }
//
//    /**
//     * Dumps the state of the looper for debugging purposes.
//     *
//     * @param pw A printer to receive the contents of the dump.
//     * @param prefix A prefix to prepend to each line which is printed.
//     * @param handler Only dump messages for this Handler.
//     * @hide
//     */
//    public void dump(@NonNull Printer pw, @NonNull String prefix, Handler handler) {
//        pw.println(prefix + toString());
//        mQueue.dump(pw, prefix + "  ", handler);
//    }
//
//    /** @hide */
//    public void writeToProto(ProtoOutputStream proto, long fieldId) {
//        final long looperToken = proto.start(fieldId);
//        proto.write(LooperProto.THREAD_NAME, mThread.getName());
//        proto.write(LooperProto.THREAD_ID, mThread.getId());
//        proto.write(LooperProto.IDENTITY_HASH_CODE, System.identityHashCode(this));
//        mQueue.writeToProto(proto, LooperProto.QUEUE);
//        proto.end(looperToken);
//    }
//
//    @Override
//    public String toString() {
//        return "Looper (" + mThread.getName() + ", tid " + mThread.getId()
//                + ") {" + Integer.toHexString(System.identityHashCode(this)) + "}";
//    }
//}
