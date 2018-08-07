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

  