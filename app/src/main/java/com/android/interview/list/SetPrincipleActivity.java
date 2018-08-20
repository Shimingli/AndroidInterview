package com.android.interview.list;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.LruCache;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import com.android.interview.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SetPrincipleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_principle);


       // demoArrayList();
        // 应该避免使用Vector ，它只存在支持遗留代码的类中（它能正常的工作的唯一原因是：因为为了向前兼容，它被适配成为了List）
        Vector<String> vector=new Vector<>();

        // TODO: 2018/8/16     LinkedList 插入，删除都是移动指针效率很高。查找需要进行遍历查询，效率较低。二分查找，如果查找的index的越接近size的一半的话，这样查找的效率很低


      //  hashMapDemo();



      //  concurrentHashMapDemo();

       // hashSetDemo();

        linkedHashMapDemo();



        lrucacheDemo();



        sparseArrayDemo();


        binarySearchDemo();

    }
    /*
     二分查找也称折半查找（Binary Search），它是一种效率较高的查找方法。
     但是，折半查找要求线性表必须采用顺序存储结构，而且表中元素按关键字有序排列。
     */
    private void binarySearchDemo() {
        System.out.println("binary Search");
        // 线性的数组
        int[] ints={1,2,3,4,5};
        int i=binarySearch(ints,ints.length,87);
        int i1=binarySearch(ints,ints.length,105);
        int i2=binarySearch(ints,ints.length,211);
        int i3=binarySearch(ints,ints.length,5);

        System.out.println(" i="+i);
        System.out.println(" i1="+i1);
        System.out.println(" i2="+i2);
        System.out.println(" i3="+i3);
    }

    /**
     * 二分查找
     * @param ints  需要被查找的数组
     * @param length  数组的长度
     * @param value  查找的值
     */
    private int binarySearch(int[] ints, int length, int value) {

        int i = 0;
        int h = length - 1;
        while (i <= h) {
            /**
             * >>>与>>唯一的不同是它无论原来的最左边是什么数，统统都用0填充。
             * —比如你的例子，byte是8位的，-1表示为byte型是11111111(补码表示法）
             * b>>>4就是无符号右移4位，即00001111，这样结果就是15。
             * 这里相当移动一位，除以二
             */
            //中间的角标
            final int mid = (i + h) >>> 1;// 第一次 2 第二次 mid=3 第三次mid=4
            final int midVal = ints[mid];// 第一次 3 第二次 midVal=4 第三次mid=5
            if (midVal < value) {
                i = mid + 1;// 第一次 3  第二次 i=4
            } else if (value < midVal) {
                h = mid - 1;
            } else if (value == midVal) {
                return mid; //第三次mid=5 返回了
            }
        }
        // 这个取反 ，相当于 value +1 然后 取反  就可以了
        return ~value;
    }

    /*
    SparseArray是android里为<Interger,Object> 这样的Hashmap而专门写的类,目的是提高效率，其核心是折半查找函数（binarySearch）。
   SparseArray是android里为<Interger,Object>这样的Hashmap而专门写的类,目的是提高内存效率，其核心是折半查找函数（binarySearch） SparseArray  仅仅提高内存效率，而不是提高执行效率，所以也决定它只适用于android系统（内存对android项目有多重要）SparseArray不需要开辟内存空间来额外存储外部映射，从而节省内存。
     todo   SparseArray与HashMap无论是怎样进行插入,数据量相同时,前者都要比后者要省下一部分内存,但是效率呢？----在倒序插入的时候,SparseArray的插入时间和HashMap的插入时间远远不是一个数量级.由于SparseArray每次在插入的时候都要使用二分查找判断是否有相同的值被插入.因此这种倒序的情况是SparseArray效率最差的时候.
     */
    private void sparseArrayDemo() {
        SparseArray<String> sparseArray=new SparseArray<>();
        sparseArray.append(2,"shiming2");
        sparseArray.append(1,"shiming1");
        sparseArray.append(3,"shiming3");
        sparseArray.append(1,"shiming4");
        sparseArray.append(7,"shiming4");
        sparseArray.append(8,"shiming4");
        sparseArray.append(810,"shiming4");
        sparseArray.append(100,"shiming4");
        sparseArray.append(4,"shiming4");
        sparseArray.append(5,"shiming4");
        sparseArray.append(6,"shiming4");
        /*
08-20 15:59:40.209 11866-11866/com.android.interview I/System.out: ~10=-11
08-20 15:59:40.209 11866-11866/com.android.interview I/System.out: ~10=-12
08-20 15:59:40.209 11866-11866/com.android.interview I/System.out: ~10=-13
         */
        //位非运算符（~）
        int i = ~10;
        System.out.println("~10="+i);
        int i1 = ~11;
        System.out.println("~10="+i1);
        int i2 = ~12;
        System.out.println("~10="+i2);
        // todo  sparseArray={1=shiming4, 2=shiming2, 3=shiming3, 4=shiming4, 5=shiming4, 6=shiming4, 7=shiming4, 8=shiming4, 100=shiming4, 810=shiming4}
        /*
        key 是有序的排列的 ，然后  key的相同的话，会覆盖一个新的value值给他，
         */
        System.out.println(" sparseArray=" +sparseArray.toString());

       int[] mKeys={10,5,14,5,46};
       int[] newKeys=new int[5];
        /*
         * @param      src      源数组。
         * @param      srcPos    表示源数组要复制的起始位置，
         * @param      dest     目的地数组。
         * @param      destPos  在目标数据中的起始位置。
         * @param      length   要复制的数组元素的数目。
         */
        // todo  source of type android.util.SparseArray is not an array
        // destPsot +length  不能超过 新的数组的长度
        System.arraycopy(mKeys,0, newKeys, 2, 3);
        for (Integer str : newKeys) {
            System.out.print("newKeys="+str+"   ");
        }
    //    System.out.println(" newKeys= "+mKeys.toString());


        //SparseBooleanArray

        //SparseIntArray

       // SparseLongArray


    }

    /**
     * Android中提供了一种基本的缓存策略，即LRU（least recently used）。
     * 基于该种策略，当存储空间用尽时，缓存会清除最近最少使用的对象。
     */
    private void lrucacheDemo() {

        LruCache<Integer,String> lruCache=new LruCache<>(5);
        lruCache.put(1,"1");
        lruCache.put(2,"2");
        lruCache.put(3,"3");
        lruCache.put(4,"4");
        lruCache.put(5,"5");

        lruCache.get(1);
        lruCache.get(2);
        lruCache.get(3);
        lruCache.get(4);
        Map<Integer, String> snapshot = lruCache.snapshot();


        //lruCache={5=5, 1=1, 2=2, 3=3, 4=4}    5最少使用到
        System.out.println("lruCache="+snapshot.toString());
        //当多添加一个的话，那么5就会被删除，加入6上去
        lruCache.put(6,"6");
        // new  lruCache={1=1, 2=2, 3=3, 4=4, 6=6}
        Map<Integer, String> snapshot1 = lruCache.snapshot();
        System.out.println(" new  lruCache="+snapshot1.toString());

    }

    public class ImageCache {
        //定义LruCache，指定其key和保存数据的类型
        private LruCache<String, Bitmap> mImageCache;

        ImageCache() {
            //获取当前进程可以使用的内存大小，单位换算为KB
            final int maxMemory = (int)(Runtime.getRuntime().maxMemory() / 1024);

            //取总内存的1/4作为缓存
            final int cacheSize = maxMemory / 4;

            //初始化LruCache
            mImageCache = new LruCache<String, Bitmap>(cacheSize) {

                //定义每一个存储对象的大小
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
                }
            };
        }

        //获取数据
        public Bitmap getBitmap(String url) {
            return mImageCache.get(url);
        }

        //存储数据
        public void putBitmap(String url, Bitmap bitmap) {
            mImageCache.put(url, bitmap);
        }
    }





    private void linkedHashMapDemo() {
        /**
        众所周知 HashMap 是一个无序的 Map，因为每次根据 key 的 hashcode 映射到 Entry 数组上，所以遍历出来的顺序并不是写入的顺序。
        因此 JDK 推出一个基于 HashMap 但具有顺序的 LinkedHashMap 来解决有排序需求的场景。
        它的底层是继承于 HashMap 实现的，由一个双向链表所构成。
        LinkedHashMap 的排序方式有两种：
         ---> 根据写入顺序排序。
         ---> 根据访问顺序排序。
        其中根据访问顺序排序时，每次 get 都会将访问的值移动到链表末尾，这样重复操作就能得到一个按照访问顺序排序的链表。
         */
        LinkedHashMap<String, Integer> map = new LinkedHashMap<String, Integer>();
        map.put("1",1) ;
        map.put("2",2) ;
        map.put("3",3) ;
        map.put("4",4) ;
        map.put("5",5) ;
        map.put("6",6) ;
        map.put("7",7) ;
        map.put("8",8) ;
        map.put("9",9) ;
        map.put("10",10) ;
        System.out.println("map="+map.toString());  //map{1=1, 2=2, 3=3, 4=4, 5=5}
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("map   Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

        //LruCache  底层使用的就是 LinekHashMap  最近最少使用的    图片的加载框架
      //访问顺序排序的链表。
      // LRU” （Least Recently Used）最近最少使用的，看了源码才知道核心是LRUCache类，这个类的核心其实是 LinkedHashMap类
        LinkedHashMap<String, Integer> map1 = new LinkedHashMap<String, Integer>(10, (float) 0.75,true);
        map1.put("1",1) ;
        map1.put("2",2) ;
        map1.put("3",3) ;
        map1.put("4",4) ;
        map1.put("5",5) ;
        map1.put("6",6) ;
        map1.put("7",7) ;
        map1.put("8",8) ;
        map1.put("9",9) ;
        map1.put("10",10) ;
        map1.get("6");
        System.out.println("map1=="+map1);
        for (Map.Entry<String, Integer> entry : map1.entrySet()) {
            System.out.println("__________________--");
            System.out.println("map1   Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }

    /*
    HashSet 的原理比较简单，几乎全部借助于 HashMap 来实现的。
所以 HashMap 会出现的问题 HashSet 依然不能避免。
     */
    private void hashSetDemo() {
        HashSet<String> set=new HashSet<>();
        set.add("10");
        set.add("10");
        set.add("11");
        set.add("12");
        set.add("0");

        System.out.println("set= "+set.toString());
    }

    /*
     ConcurrentHashMap 实现原理
    由于 HashMap 是一个线程不安全的容器，主要体现在容量大于总量*负载因子发生扩容时会出现环形链表从而导致死循环。
    因此需要支持线程安全的并发容器 ConcurrentHashMap 。
     */
    private void concurrentHashMapDemo() {
             //  1.8   采用了 CAS + synchronized 来保证并发安全性
         //  1.7 版本的对比 1.8 版本的  ConcurrentHashMap()  那就是查询遍历链表效率太低。

        // 1.8 在 1.7 的数据结构上做了大的改动，采用红黑树之后可以保证查询效率（O(logn)）
        AtomicInteger atomicInteger=new    AtomicInteger();
        int i = atomicInteger.incrementAndGet();
         i = atomicInteger.incrementAndGet();
         i = atomicInteger.incrementAndGet();
         i = atomicInteger.incrementAndGet();
         i = atomicInteger.incrementAndGet();
         i = atomicInteger.incrementAndGet();

        System.out.println("i="+i);




    }
    //  HashMap 底层是基于 数组 + 链表 组成的
    private void hashMapDemo() {
        HashMap<Integer,String> map=new HashMap<Integer,String>();
        //在 1.6 1.7 hashmap的类的代码一共1500行左右，在1.8 一共有2000行左右
         map.put(1,"1");
         map.put(1,"2");
         map.put(2,"1");
         map.put(3,"1");

        System.out.println("hashMap="+map.toString());
//        for (HashMap<Integer,String> r = map, p ;;) {
//             p.get(3);
//        }
           // cap 为默认的长度  a=a|b  a|=b的意思就是把a和b按位或然后赋值给a 按位或的意思就是先把a和b都换成2进制，然后用或操作
          int cap=10;
          int n = cap - 1;//9
          n |= n >>> 1;//9的二进制=1001  >>>表示无符号的右移 100 =十进制 4     n=  1001 |= 100
          System.out.println("n="+n); // n=13; 其实就是等于      n=  1001 |= 100 也就是n=1101 换成十进制等于13
          n |= n >>> 2;
          n |= n >>> 4;
          n |= n >>> 8;
          n |= n >>> 16;
          int i= (n < 0) ? 1 : (n >= 1000000) ? 1000000 : n + 1;
          System.out.println("设置hashmap的长度为10，那么他的新的扩容的临界值="+i);
         // 设置hashmap的长度为10，那么他的新的扩容的临界值=16

        HashMap<Integer,String> mapTwo=new HashMap<Integer,String>();
         mapTwo.put(1,"shiming");
        int size = mapTwo.size();
        System.out.println("hashMap size ="+size);
        // hashCode  在 object 中是一个本地的方法


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
