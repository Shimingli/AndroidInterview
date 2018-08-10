package com.android.interview;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.textclassifier.TextClassifier;
import android.widget.LinearLayout;

import com.android.interview.dp_px_dip_sp.UnitDemoActivity;
import com.android.interview.handler.HandlerActivity;
import com.android.interview.invalidate_demo.InValidateDemoActivity;
import com.android.interview.layout_performance_comparison.LayoutPerformanceComparison;
import com.android.interview.listview_demo.ListViewDemoActivity;
import com.android.interview.merge_and_viewstub_demo.MergePrincipleActivity;
import com.android.interview.view_source_code.ViewSourceCodeDemoActivity;

/**
 * 安卓面试的资料
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        android中的dp、px、dip相关概念
        findViewById(R.id.tv_dp_unit_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UnitDemoActivity.class));
            }
        });
//        handler机制，四个组成部分及源码解析
        Handler handler = new Handler();
        findViewById(R.id.btn_handler_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HandlerActivity.class));
            }
        });
//        布局相关的<merge>、<viewstub>控件作用及实现原理
        findViewById(R.id.btn_principle_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MergePrincipleActivity.class));
            }
        });
//        android中的布局优化
        // 请看我的文章：https://www.jianshu.com/p/82b76e0cb41e

        //        view的工作原理及measure、layout、draw流程，要求了解源码
        findViewById(R.id.btn_source_code_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewSourceCodeDemoActivity.class));
            }
        });

//        relativelayout和LinearLayout在实现效果同等情况下选择使用哪个？为什么？
        /*
          RelativeLayout和LinearLayout是Android中常用的布局，两者的使用会极大的影响程序生成每一帧的性能，因此，正确的使用它们是提升程序性能的重要工作。记得以前，较低的SDK版本新建Android项目时，默认的布局文件是采用线性布局LinearLayout，但现在自动生成的布局文件都是RelativeLayout，或许你会认为这是IDE的默认设置问题，其实不然，这由 android-sdk\tools\templates\activities\BlankActivity\root\res\layout\activity_simple.xml.ftl 这个文件事先就定好了的，也就是说这是Google的选择，而非IDE的选择。那SDK为什么会默认给开发者新建一个默认的RelativeLayout布局呢？<-----原因见最后小结
     当然是因为RelativeLayout的性能更优，性能至上嘛。但是我们再看看默认新建的这个RelativeLayout的父容器，也就是当前窗口的顶级View——DecorView，它却是个垂直方向的LinearLayout，上面是标题栏，下面是内容栏。那么问题来了，Google为什么给开发者默认新建了个RelativeLayout，而自己却偷偷用了个LinearLayout，到底谁的性能更高，开发者该怎么选择呢？
       下面将通过分析它们的源码来探讨其View绘制性能，并得出其正确的使用方法。
         */
//（1）RelativeLayout会让子View调用2次onMeasure，LinearLayout 在有weight时，也会调用子View 2次onMeasure
//（2）RelativeLayout的子View如果高度和RelativeLayout不同，则会引发效率问题，当子View很复杂时，这个问题会更加严重。如果可以，尽量使用padding代替margin。
//（3）在不影响层级深度的情况下,使用LinearLayout和FrameLayout而不是RelativeLayout。
//（4）提高绘制性能的使用方式
//        根据上面源码的分析，RelativeLayout将对所有的子View进行两次measure，而LinearLayout在使用weight属性进行布局时也会对子View进行两次measure，如果他们位于整个View树的顶端时并可能进行多层的嵌套时，位于底层的View将会进行大量的measure操作，大大降低程序性能。因此，应尽量将RelativeLayout和LinearLayout置于View树的底层，并减少嵌套。
        // TODO: 2018/5/30  性能对比
        findViewById(R.id.btn_relative_linear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LayoutPerformanceComparison.class));
            }
        });

//        怎样自定义一个弹幕控件？
//        如果控件内部卡顿你如何去解决并优化？
        /**
         * 开发app的性能目标就是保持60fps，这意味着每一帧你只有16ms≈1000/60的时间来处理所有的任务。
         * Android系统每隔16ms发出VSYNC信号，触发对UI进行渲染，如果每次渲染都成功，这样就能够达到流畅的画面所需要的60fps。
         *
         */
        // TODO: 2018/6/20 建议去使用的方法
//        1、常规做法
//        没有用的父布局——没有背景绘制或没有大小限制的父布局，不会对界面效果产生任何影响。特别是进来的布局，很容易产生问题。可以通过标签替代。
//        在布局层次一样的情况下，建议使用LinearLayout代替RelativeLayout。
//        使用LinearLayout导致的层次变深，可以使用RelativeLayout进行替换。同样的界面我们可以使用不同的方式去实现，选择一个层级最少的方案。
//        不常用的UI被设置成了GONE，尝试使用代替。
//        去掉多余的背景颜色，减少过渡绘制，对于有多层背景色的布局来说，留最上面的一层即可。谨慎使用alpha，如果后渲染的元素有设置alpha值，那么这个元素就会和屏幕上已经渲染好的元素做blend处理，这样会导致不少性能问题，特别是出现在列表的Item中。
//        对于使用Selector当背景的布局，可以将normal状态的color设置为透明。
//        我们不能因为提高性能而忽略了界面需要达到的效果（平衡Design与Performance）
//          2：代码问题查找
//        在绘制时实例化对象（onDraw）
//        手机不能进入休眠状态（Wake lock）
//        资源忘记回收
//                Handler使用不当倒置内存泄漏
//        没有使用SparseArray代替HashMap
//                未被使用的资源
//        布局中无用的参数
//        可优化布局（如：ImageView与TextView的组合是否可以使用TextView独立完成）
//        效率低下的 无用的命名空间等
//        3：优化App的逻辑层
//        主线程里占用CUP时间很长的函数，特别关注IO操作（文件IO、网络IO、数据库操作等）
//        主线程调用次数多的函数
        // TODO: 2018/6/20     检查性能优化的三方的工具    ：AndroidPerformanceMonitor https://github.com/Shimingli/AndroidPerformanceMonitor
//         4： GitHub BlockCanary — 轻松找出Android App界面卡顿元凶
//        BlockCanary是一个Android平台的一个非侵入式的性能监控组件，应用只需要实现一个抽象类，提供一些该组件需要的上下文环境，就可以在平时使用应用的时候检测主线程上的各种卡慢问题，并通过组件提供的各种信息分析出原因并进行修复。
//        取名为BlockCanary则是为了向LeakCanary致敬，顺便本库的UI部分是从LeakCanary改来的，之后可能会做一些调整。


//        listview的缓存机制
        findViewById(R.id.btn_list_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ListViewDemoActivity.class));
            }
        });

//        Invalidate、postInvalidate、requestLayout应用场景
        findViewById(R.id.btn_invalidate_demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InValidateDemoActivity.class));
            }
        });

//        多线程，5个线程内部打印hello和word，hello在前，要求提供一种方法使得5个线程先全部打印出hello后再打印5个word。
        demo();
//        实现一个自定义view，其中含有若干textview，textview文字可换行且自定义- - view的高度可自适应拓展


//        编程题：将元素均为0、1、2的数组排序。在手打了一种直接遍历三种数目并打印的方法后让手写实现，手写实现后让再说一种稳定的方法，说了一种通过三个下标遍历一遍实现的方法，读者可自行百度，在此不赘述。
//        java内存模型，五个部分，程序计数器、栈、本地栈、堆、方法区。


//        每个部分的概念、特点、作用。
//        类加载的过程，加载、验证、准备、解析、初始化。每个部分详细描述。
//        加载阶段读入.class文件，class文件时二进制吗，为什么需要使用二进制的方式？
//        验证过程是防止什么问题？验证过程是怎样的？加载和验证的执行顺序？符号引用的含义？
//        准备过程的静态成员变量分配空间和设置初始值问题。
//        解析过程符号引用替代为直接引用细节相关。
//        初始化过程jvm的显式初始化相关。
//        类卸载的过程及触发条件。
//        三种类加载器，如何自定义一个类加载器？
//        双亲委派机制。
//        JVM内存分配策略，优先放于eden区、动态对象年龄判断、分配担保策略等。
//        JVM垃圾回收策略，怎样判对象、类需要被回收？
//        四种垃圾回收算法标记-清除、复制、标记-整理、分代收集。
//        JVM中的垃圾回收器，新生代回收器、老年代回收器、stop-the-world概念及解决方法。
//        四类引用及使用场景？
//        hashmap实现的数据结构，数组、桶等。
//        hashmap的哈希冲突解决方法：拉链法等。拉链法的优缺点。
//        hashmap的参数及影响性能的关键参数：加载因子和初始容量。
//        Resize操作的过程。
//        hashmap容量为2次幂的原因。
//        hashtable线程安全、synchronized加锁。
//        hashtable和hashmap异同。
//        为什么hashtable被弃用？
//        容器类中fastfail的概念。
//        concurrenthashmap的插入操作是直接操作数组中的链表吗？
//        集合类相关over，由于都是自己主动在说，把握了主动权，相谈甚欢。
//        为什么要使用多线程？多线程需要注意的问题。上下文开销、死锁等。
//        java内存模型、导致线程不安全的原因。
//        volatile关键字，缓存一致性、指令重排序概念。
//        synchronize关键字，java对象头、Markword概念、synchronize底层monitorenter和moniterexit指令。
//        lock语句和synchronize对比。
//        原子操作，CAS概念、相关参数。
//        乐观锁、悲观锁概念及使用场景。
//        线程池概念、实现原理等。
//        JVM锁的优化，偏向锁、轻量级锁概念及原理。
//        SQL语句中对表或者字段取别名有什么好处？
//        TCP三次握手、四次挥手。
//        http请求报文结构、响应报文，状态码。
//        http2.0相比于http1.0的新特性，推送、多路复用、消息头压缩等。
//        handler机制组成，handler机制每一部分的源码包括looper中的loop方法、threadlocal概念、dispatchmessage方法源码，runnable封装message等。
//        listview缓存机制、recycleview缓存机制。
//        bitmap高效加载，三级缓存等。
//        binder机制原理。
//        view的工作原理及measure、layout、draw流程。哪一个流程可以放在子线程中去执行？
//        draw方法中需要注意的问题？
//        view的事件分发机制。
//        android性能优化：布局优化、绘制优化、内存泄露优化、bitmap、内存泄露等。
//        内存泄露的概念？android中发生的场景？怎么解决？讲了handler、动画等。
//        索引的种类。
//        B树、B+树、红黑树。
//        B+树和B树相比有什么优点，应用场景？
//        红黑树的一些特点？怎样保持平衡？


    }

    //多线程，5个线程内部打印hello和word，hello在前，要求提供一种方法使得5个线程先全部打印出hello后再打印5个word。
    private void demo() {
        CanRun canRun = new CanRun(1);
        Thread t1 = new Thread(new MyRunnable(canRun, 1, 2), "线程-1");
        Thread t2 = new Thread(new MyRunnable(canRun, 2, 3), "线程-2");
        Thread t3 = new Thread(new MyRunnable(canRun, 3, 4), "线程-3");
        Thread t4 = new Thread(new MyRunnable(canRun, 4, 5), "线程-4");
        Thread t5 = new Thread(new MyRunnable(canRun, 5, 1), "线程-5");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    public class MyRunnable implements Runnable {
        //公共锁对象
        private CanRun mObject;
        //线程标志，创建线程时初始化该值，并在run()方法中判断lock对象的status变量是否与其相等，是则运行
        private int mCurrent;
        //若该子线程得以运行并打印完后，将lock对象的status设置为该值，之后唤醒所有使用该锁的子线程
        private int mNext;

        public MyRunnable(CanRun obj, int current, int next) {
            mObject = obj;
            mCurrent = current;
            mNext = next;
        }

        @Override
        public void run() {
            for (int i = 0; i < 2; i++) {
                synchronized (mObject) {
                    while (mObject.getStatus() != mCurrent) {
                        try {
                            //调用某个对象的wait()方法能让当前线程阻塞
                            mObject.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mObject.setStatus(mNext);
                    if (i == 0) {
                        Log.d("MainActivity","Hello");
                        System.out.print(Thread.currentThread().getName() + "  Hello");
                    } else {
                        Log.d("MainActivity","World");
                        System.out.print(Thread.currentThread().getName() + "  World");
                    }
                    //调用notifyAll()方法能够唤醒所有正在等待这个对象的monitor的线程
                    mObject.notifyAll();

                }
            }
        }
    }

    public class CanRun {
        public CanRun(int i) {
            status = i;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int status;

    }
}
