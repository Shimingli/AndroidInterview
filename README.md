# AndroidInterview
安卓面试的题总结

* 以前完成的资料和博客
   * （Android源码分析(View的绘制流程)） https://www.jianshu.com/p/b63c6afa1844 
   * （Android源码分析(ViewStub源码解析)）https://www.jianshu.com/p/63a066e7a5a9
   * （Android源码分析（LayoutInflater.from(this).inflate(resId,null);源码解析））https://www.jianshu.com/p/38c6a9842efc
   * （Android源码分析（Activity.setContentView源码解析））https://www.jianshu.com/p/d9d919608842
   * （Android源码分析（Handler机制））https://www.jianshu.com/p/a2c53e96cae6
   * （Android源码分析（事件传递））https://www.jianshu.com/p/f7e3a14daf51
   
   
   
* 2018.8.6 
  *  总结`requestLayout()`方法的原理，同时完成`xmind`，注意不在ui线程中使用，就会抛出异常
  *  原理明确，`postInvalidate`是在非UI线程中调用,但是底层使用的是 `invalidate()`,通过` ViewRootImpl的内部handler： ViewRootHandler`发送的消息，但是也可以在 主线程中使用，如果在强制在主线程中使用，内部有个 `handler` 在工作，是不是显得有点浪费 ，对吧
  *   `invalidate()`，原理清晰，注意不在ui线程中使用，就会抛出异常
  * 明天完成两张`xmind`,同时完成博客！嘿嘿 
* 2018.8.7 
  * requessLayout() 和invalidate()  最终的底层调用的是 ViewRootImpl.scheduleTraversals()的方法，区别是：在invalidate方法中，没有添加 measure 和 layout的标记位，因此measure和layout流程不会执行，直接从draw开始！
  * 完成 invalidate（）的xmind 流程图  
  * 完成 postInvalidate（） xmind 流程图
  * 完成博客（安卓invalidate()、postInvalidate()、requestLayout()源码分析）https://www.jianshu.com/p/11345ef2b1ef
* 2018.8.13
  * 对数组中只有[0,1,2]的排序，要求时间复杂度为 o(n) ,有点难以理解！两种的方法，一个是折中查找  ，时间复杂度
  
* 2018.8.16
  * 常见的集合的原理
   * ArrayList/Vector 的底层分析
   * Linckedlist 原理分析和底层原理图解
* 2018.8.17
  * transient :可以防止被自动序列化。   java语言的关键字，变量修饰符，如果用transient声明一个实例变量，当对象存储时，它的值不需要维持。换句话来说就是，用transient关键字标记的成员变量不参与序列化过程。   
  * 位异或运算（^）运算规则是：两个数转为二进制，然后从高位开始比较，如果相同则为0，不相同则为1。
  * HashMap HashSet(内部就是map的key) ConcurrentHashMap(线程安全) 的原理分析
  * volatile 关键字的md文档：原子性，顺序性和可见性 
  
* 2018.8.20 
   *  LinkedHashMap的原理分析，里面维护了一个Entry<K,V> ，然后LRUCache 底层使用的就是这个 linkedHashMap 
   * 分析了LinkedHashMap的底层原理，同时完成 使用的方法 
   * LruCache的底层的原理  
   * SparseArray与HashMap无论是怎样进行插入,数据量相同时,前者都要比后者要省下一部分内存,但是效率呢？----在倒序插入的时候,SparseArray的插入时间和HashMap的插入时间远远不是一个数量级.由于SparseArray每次在插入的时候都要使用二分查找判断是否有相同的值被插入.因此这种倒序的情况是SparseArray效率最差的时候.
   * SpareArray 底层就是两个数组 一个 int[] 和 一个 object[] ，对应着key值和value值，没有其他内存的开销 ，但是 HashMap 的内存模型里面有数组 链表 和红黑树 ，所以相对应起来，开销很大  
   * 倒序插入key值的话，会不断进行二分查找。尽量使用append 方法，不要使用 put方法。插入的值最好是有序的 ，比如key值为  1 2 3 4 5 6 这样的顺序，最好
   * SpareArray 目的是为了提高内存效率，内存对于安卓系统非常的关键，核心是折半查找，二分查找，SparseArray 不需要开辟额外的控件来额外储存外部的映射，从而节省内存
   * ~ 取反，~10=-11  ~105=-106  相当于加一取反
   * System.arraycopy(mKeys,0, newKeys, 2, 3);的用法
   * 二分查找的原理
   
* 2018.8.21
   * 算法 ： algorithm  对算法的不熟悉，导致对这个单词也不熟悉，牛逼 
   * 左右递增和上下递增的二维数组，查找一个num，如何快速的查找，已解决