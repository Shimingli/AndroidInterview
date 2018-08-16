package com.android.interview.list;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.interview.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Vector;

public class SetPrincipleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_principle);


        demoArrayList();
        // 应该避免使用Vector ，它只存在支持遗留代码的类中（它能正常的工作的唯一原因是：因为为了向前兼容，它被适配成为了List）
        Vector<String> vector=new Vector<>();

        // TODO: 2018/8/16     LinkedList 插入，删除都是移动指针效率很高。查找需要进行遍历查询，效率较低。二分查找，如果查找的index的越接近size的一半的话，这样查找的效率很低






    }
   // 最佳的做法是将ArrayList作为默认的首选，当你需要而外的功能的时候，或者是当程序性能由于经常需要从表中间插入和删除而变差的时候，才会去选择LinkedList   来源于 THking in Java
    private void demoArrayList() {
        //ArrayList 实现于 List、RandomAccess 接口。可以插入空数据，也支持随机访问。


        // 2的二进制是10，>>代表右移，10右移1位是二进制的1，<<代表左移，10左移1位是二进制的100，也就是十进制的4。
        System.out.println("2>>1==="+(2>>1));
        System.out.println("100>>1=="+(100>>1));
        System.out.println("88>>1=="+(88>>1));
        System.out.println("45>>1=="+(45>>1));

        //证明list的线程不安全
        final ArrayList<String> lists=new ArrayList<>();
        Thread t1= new Thread(){
            @Override
            public void run() {
                super.run();
                for (int i=0;i<25;i++){
                    lists.add("我是i="+i);
                }
            }
        };
        Thread t2= new Thread(){
            @Override
            public void run() {
                super.run();
                for (int i=25;i<50;i++){
                    lists.add("我是i="+i);
                }

            }
        };
        //主线程休眠1秒钟，以便t1和t2两个线程将lists填装完毕。
        t1.start();
        t2.start();
        try {
            Thread.sleep(1000);
            for(int l=0;l<lists.size();l++){
                // todo   两个线程不断的插入的话，就会导致插入的是null     我是i=34   我是i=10   我是i=35   我是i=11   null   null   我是i=12   我是i=38   我是i=13   我是i=39
                System.out.print(lists.get(l)+"   ");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // TODO   2018/8/16  ArrayList 的主要消耗是数组扩容以及在指定位置添加数据，在日常使用时最好是指定大小，尽量减少扩容。更要减少在指定位置插入数据的操作。

//                 des：在java中，原型类必须实现下面两个条件的其中一个
//                * 1、实现Cloneable皆苦，这个接口只有一个作用，就是在运行时候通知虚拟机可以安全的实现
//                * 此接口上  clone的方法，在java的虚拟机中，只有实现了这个接口的类才可以被拷贝，否者
//                * 会抛出CloneNotSupportedException的
//                * 2、重写object类的clone的方法，java中，所有类的父类都是Object类,Object类中有个
//                * 方法clone，作用是返回一个拷贝的被吸纳该，但是它的作用域是是protected，只能子类拥有
//                * 因此 把clone的方法作用域修改为public


//        好处：使用原型的模式创建对象比直接new一个对象的性能上要好很多，因为object是一个本地的方法
//                * ，它直接操作的是内存中的二进制流，特别是复制大的对象，性能上特别明显。如果需要循环在体内创建对象的话，
// * 假如对象的创建过程比较复杂，或者循环的次数特别多的话，使用原型模式不但可以简化创建的过程，而且可以使系统的
//                * 整体的性能提高很多
//                *
// * 注意的问题：使用原型模式复制对象，不会调用对象的构造方法，因为对象的复制是通过调用object类中
//                * clone的方法完成，他直接在内存中复制数据的，不会调用到类的构造的方法，而且访问的权限对原型模式无效
//                * 在单利模式中构造的方法访问权限是private的，但是原型模式直接无视构造方法，所以单利模式和原型模式是
//                * 冲突的，使用这个模式的时候，需要特别的注意


       // 对象中自定义了 writeObject 和 readObject 方法时，JVM 会调用这两个自定义方法来实现序列化与反序列化  ArrayList 只序列化了被使用的数据。




        //证明data的线程不安全
        final List<String> data=Collections.synchronizedList(new ArrayList<String>());
        Thread t3= new Thread(){
            @Override
            public void run() {
                super.run();
                for (int i=0;i<25;i++){
                    data.add("我是data i="+i);
                }
            }
        };
        Thread t4= new Thread(){
            @Override
            public void run() {
                super.run();
                for (int i=25;i<50;i++){
                    data.add("我是data i="+i);
                }

            }
        };
        //主线程休眠1秒钟，以便t1和t2两个线程将lists填装完毕。
        t3.start();
        t4.start();
        System.out.println("      ");
        System.out.println("   证明data的线程不安全   ");
        try {
            Thread.sleep(1000);
            for(int l=0;l<data.size();l++){
                System.out.print(data.get(l)+"   "); // 这里的 data中的元素 不会为null
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //  todo Collections.synchronizedList  的原理
//    ```
//            (list instanceof RandomAccess ?
//            new SynchronizedRandomAccessList<>(list) :
//            new SynchronizedList<>(list));
//    ```

     // 1 . SynchronizedRandomAccessList

//    static class SynchronizedRandomAccessList<E>
//            extends SynchronizedList<E>
//            implements RandomAccess {

    //2  SynchronizedList    在每个方法里面 加上了关键字   synchronized
    //
    // public void add(int index, E element) {
    //            synchronized (mutex) {list.add(index, element);}
    //        }
}
