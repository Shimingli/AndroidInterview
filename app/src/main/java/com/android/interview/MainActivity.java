package com.android.interview;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.android.interview.dp_px_dip_sp.UnitDemoActivity;
import com.android.interview.merge_and_viewstub_demo.MergePrincipleActivity;

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
                startActivity(new Intent(MainActivity.this,UnitDemoActivity.class));
            }
        });
//        handler机制，四个组成部分及源码解析
        Handler handler = new Handler();
//        布局相关的<merge>、<viewstub>控件作用及实现原理
        findViewById(R.id.btn_principle_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MergePrincipleActivity.class));
            }
        });
//        android中的布局优化
//        relativelayout和LinearLayout在实现效果同等情况下选择使用哪个？为什么？
//        view的工作原理及measure、layout、draw流程，要求了解源码
//        怎样自定义一个弹幕控件？
//        如果控件内部卡顿你如何去解决并优化？
//        listview的缓存机制
//        Invalidate、postInvalidate、requestLayout应用场景
//        多线程，5个线程内部打印hello和word，hello在前，要求提供一种方法使得5个线程先全部打印出hello后再打印5个word。
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
}
