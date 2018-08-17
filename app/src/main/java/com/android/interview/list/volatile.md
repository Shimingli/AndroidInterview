## Java 多线程的三大核心 


####  原子性 

  * volatile 关键字只能保证可见性，顺序性，不能保证原子性。
  * java原子性和数据库事务的原子性差不多，一个操作要么是全部执行成功或者是失败
  * JVM 只保证了基本的原子性，但是类似 i++ 之类的操作，看着好像是原子的操作，其实里面涉及到了三个步骤
    *  获取 i 的值
    *  自增
    *   在赋值给 i
  * 这三个步骤 要实现i++ 这样的原子操作就需要用到 synchronized 或者是 了lock  进行加锁处理。
  *  如果是基础类的自增操作可以使用 AtomicInteger 这样的原子类来实现(其本质是利用了 CPU 级别的 的 CAS 指令来完成的)。 AtomicInteger 是线程安全的
     
     其中用的最多的方法就是: incrementAndGet() 以原子的方式自增
  ```
  AtomicInteger atomicInteger=new    AtomicInteger();
        int i = atomicInteger.incrementAndGet();
        System.out.println("i="+i); 
       
           public final int incrementAndGet() {
                return U.getAndAddInt(this, VALUE, 1) + 1;
            }

```  
  
 ####  可见性
 
  *  现在的计算机，由于 cpu 直接从 主内存中读取数据的效率不高。所以都会对应的 cpu高速缓存，先将主内存中的数据读取到缓存中，线程修改数据之后首先更新到缓存中，之后才会更新到主内存。如果此时还没有将数据更新到主内存其他的线程此时读取就是修改之前的数据 
  
  *  volatile 关键字就是用于保存内存的可见性，当线程A更新了 volatite的修饰的变量的话，他会立即刷新到主线程，并且将其余缓存中该变量的值清空，导致其余线程只能去主内存读取最新的值
  
  * synchronized 和加锁也能保证可见性，实现原理就是在释放锁之前其余线程是访问不到这个共享变量的。但是和 volatile 相比较起来开销比较大 ！
  
  * 但是 volatile 不能够替换 synchronized 因为volatile 不能够保证原子性  
  
  
  
####  顺序性 

```

int a = 100 ; //1
int b = 200 ; //2
int c = a + b ; //3
```
 
  * 正常的代码的执行顺序应该是 1》》2》》3 。但是有时候 jvm 为了提高整体的效率会进行指令重排导致执行顺序可能是 2》》1》》3 。但是jvm 也不能是 什么都进行重排，是在保证最终结果和代码顺序执行结果是一致的情况下才可能会进行重排
  * 重排在单线程中不会出现问题，但是在多线程中就会出现顺序不一致的问题 
  *  java 中可以使用 volatile 关键字来保证顺序性，synchronized 和lock 也可以来保证有序性，和保证 原子性的方式一样，通过同一段时间只能一个线程访问来实现的 
  * 除了 volatile 关键字显式的保证顺序之外，jvm HIA通过 happen-before 原则来隐式来保证顺序性。
  
  
  #### volitle的应用 
  *  volatile 实现一个双重检查锁的单例模式
  ```

    public class Singleton {
        private static volatile Singleton singleton;

        private Singleton() {
        }

        public static Singleton getInstance() {
            if (singleton == null) {
                synchronized (Singleton.class) {
                    if (singleton == null) {
                        singleton = new Singleton();
                    }
                }
            }
            return singleton;
        }

    }
```
  
  * 这里的 volatile 关键字主要是为了防止指令重排。 如果不用 volatile ，singleton = new Singleton();，这段代码其实是分为三步：
  
    * 分配内存空间。(1)
    *  初始化对象。(2)
     * 将 singleton 对象指向分配的内存地址。(3)
  * 加上 volatile 是为了让以上的三步操作顺序执行，反之有可能第三步在第二步之前被执行就有可能导致某个线程拿到的单例对象还没有初始化，以致于使用报错。
  
  
  
 *  控制停止线程的标记 
  
  
 ```

 private volatile boolean flag ;
    private void run(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                doSomeThing();
            }
        });
    }

    private void stop(){
        flag = false ;
    }
``` 
 * 如果没有用 volatile 来修饰 flag ，就有可能其中一个线程调用了 stop()方法修改了 flag 的值并不会立即刷新到主内存中，导致这个循环并不会立即停止.这里主要利用的是 volatile 的内存可见性 .
 
 
 
 
 
 
 
 
 
 
 
 
 