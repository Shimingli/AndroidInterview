package com.android.interview.tenxunmusic;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : ShiMing
 * @editor :
 * @description :
 * @created : 2022-09-21 11:55
 */
public class RunnableTest {

    public static void main() {



        //1.创建线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue()) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                if (r instanceof Thread) {
                    if (t != null) {
                        //处理捕获的异常
                        System.out.println("shiming  "  + t.toString());
                    }
                } else if (r instanceof FutureTask) {
                    FutureTask futureTask = (FutureTask) r;
                    try {
                        futureTask.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        //处理捕获的异常
                    }
                }

            }
        };

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                int c = 1 / 0;
            }
        });

        threadPoolExecutor.execute(thread1);

//        Callable<Integer> callable = () -> 2 / 0;
//        threadPoolExecutor.submit(callable);



        //1.创建线程组
        ThreadGroup threadGroup = new ThreadGroup("group") {
            // 继承ThreadGroup并重新定义以下方法
            // 在线程成员抛出unchecked exception 会执行此方法
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                //4.处理捕获的线程异常
                System.out.println("RunTask2 捕获异常处理方法：" + e);
            }
        };
        //2.创建Thread
        Thread thread = new Thread(threadGroup, new Runnable() {
            @Override
            public void run() {
                System.out.println("RunTask2 捕获异常处理方法：" + Thread.currentThread().getName());
                System.out.println(1 / 0);
                System.out.println("RunTask2 捕获异常处理方法：" + Thread.currentThread().getName());

            }
        }, "my_thread");
        //3.启动线程
        thread.start();


//        Thread t = new Thread(new RunTask1());
//        t.setUncaughtExceptionHandler(new MyExceptionHandler());
//        t.start();

        ExecutorService exec = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setUncaughtExceptionHandler(new MyExceptionHandler());
                return thread;
            }
        });
//        exec.execute(new RunTask1());
        Future<?> submit = exec.submit(new RunTask1());

        try {
            submit.get();
        } catch (Exception e) {
            System.out.println("Test111 " + Thread.currentThread().getName());
            System.out.println("Test111 " + e.toString());
        }

        FutureTask futureTask1 = new FutureTask(new RunTask1(), null);
//        exec.execute(futureTask1);
//        exec.submit(new RunTask1());

//        FutureTask


        //主线程无法捕获子线程的异常
        System.out.println("Test " + Thread.currentThread().getName());
//        try {
//            new Thread(new RunTask()).start();
//        } catch (Exception e) {
//            System.out.println("Test " + "catch exception in main thread");
//        }


        //主线程捕获子线程返回的FutureTask抛出的异常
        FutureTask<String> futureTask = new FutureTask<>(new RunTask2());
        // 启动Callable子线程
        new Thread(futureTask).start();
        try {
            // 获取返回值时，会同时捕获子线程抛出的异常
            String s = futureTask.get();
            System.out.println("Test 1 " + s);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Test  2  " + e.toString());
        }

    }
}


class MyExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("RunTask1 捕获异常处理方法：" + e);
    }
}

class RunTask implements Runnable {
    @Override
    public void run() {
//        int a = 1 / 0; // 子线程抛异常
        try {
            int a1 = 1 / 0; // 子线程抛异常，并捕获
        } catch (Exception e) {
            System.out.println("catch exception in child thread");
        }
        System.out.println("Test " + "thread pool test:" + Thread.currentThread().getName());
    }
}

class RunTask1 implements Runnable {
    @Override
    public void run() {
        System.out.println("RunTask1  start " + "thread pool test:" + Thread.currentThread().getName());
        int a = 1 / 0; // 子线程抛异常
        System.out.println("RunTask1  end " + "thread pool test:" + Thread.currentThread().getName());
    }
}


class RunTask2 implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("Test 1 " + Thread.currentThread().getName());
        int a = 1 / 0; // 抛异常
        return "hello";
    }
}



