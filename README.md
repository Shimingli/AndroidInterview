# AndroidInterview
安卓面试的题总结

* 完成的资料和博客
   * （Android源码分析(View的绘制流程)） https://www.jianshu.com/p/b63c6afa1844 
   * （Android源码分析(ViewStub源码解析)）https://www.jianshu.com/p/63a066e7a5a9
   * （Android源码分析（LayoutInflater.from(this).inflate(resId,null);源码解析））https://www.jianshu.com/p/38c6a9842efc
   * （Android源码分析（Activity.setContentView源码解析））https://www.jianshu.com/p/d9d919608842
   * （Android源码分析（Handler机制））https://www.jianshu.com/p/a2c53e96cae6
   * （Android源码分析（事件传递））https://www.jianshu.com/p/f7e3a14daf51
   *  （常用集合的原理分析）https://www.jianshu.com/p/a5f638bafd3b
   *  剑指offer中几道算法题的思考 https://www.jianshu.com/p/5c7188488fe3
   
   
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
   * 替换空格：通过对比发现，如果只使用一次的话，建议选择 string.replace()! 使用多次的话，建议选择replaceBlank 
   *  使用 replaceBlank中的消耗的时间 time= 202604、idoWorkReplace 实现的方式总共耗时time= 1800000、 使用 StringBuffer中的消耗的时间 time= 455729、 使用 replaceSpaces中的消耗的时间 time= 1447917
   
* 2018.8.22
   * 位与运算符（&） 两个数都转为二进制，然后从高位开始比较，如果两个数都为1则为1，否则为0。   
   * 完成博客 https://www.jianshu.com/p/a5f638bafd3b
   
* 2018.8.23
   * 完成输入个链表的头结点，从尾到头反过来打印出每个结点的值。使用的是 Java中的类    `Stack` 中有个方法 `pop` 把栈顶的移除，同时返回，
   * 二叉树的Demo    
   
* 2018.8.24 
    * 二叉树的insert图解
    * 过程的分析，如何插入数据的，原理解释    
    
* 2018.8.25    
    * 今天周六，解决二叉树的所有原理 
    * 二叉树的博客 和算法 还有 fluter的 Demo
    * 二叉树的中序遍历、前序遍历、后序遍历
* 2018.8.26
     *   面试题的原理解决：输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。例如：前序遍历序列｛ 50 10 20 25 30 60 80 85 90 100｝和中序遍历序列｛10 20 25 30 50 60 80 85 90 100}，重建二叉树并输出它的头结点。  
     *  面试题没有解答成功，提供了一份图面构思
     
* 2018.8.27 
   * 打个tag 去平安大厦门店去新零售技术支持     
* 2018.8.29
   * 二叉树的构建完全不正确 
* 腾讯⾳乐 
   *  Catch Main线程异常,然后 怎么处理
  
  
  