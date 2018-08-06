# AndroidInterview
安卓面试的题总结
* 2018.8.6 
  *  总结`requestLayout()`方法的原理，同时完成`xmind`，注意不在ui线程中使用，就会抛出异常
  *  原理明确，`postInvalidate`是在非UI线程中调用,但是底层使用的是 `invalidate()`,通过` ViewRootImpl的内部handler： ViewRootHandler`发送的消息，但是也可以在 主线程中使用，如果在强制在主线程中使用，内部有个 `handler` 在工作，是不是显得有点浪费 ，对吧
  *   `invalidate()`，原理清晰，注意不在ui线程中使用，就会抛出异常
  * 明天完成两张`xmind`,同时完成博客！嘿嘿 