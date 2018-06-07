///*
// * Copyright (C) 2007 The Android Open Source Project
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package com.android.interview.merge_and_viewstub_demo;
//
//import android.annotation.LayoutRes;
//import android.annotation.Nullable;
//import android.annotation.SystemService;
//import android.content.Context;
//import android.content.res.Resources;
//import android.content.res.TypedArray;
//import android.content.res.XmlResourceParser;
//import android.graphics.Canvas;
//import android.os.Handler;
//import android.os.Message;
//import android.os.Trace;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.util.TypedValue;
//import android.util.Xml;
//import android.view.ContextThemeWrapper;
//import android.view.InflateException;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.ViewStub;
//import android.widget.FrameLayout;
//import com.android.interview.R;
//
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserException;
//
//import java.io.IOException;
//import java.lang.reflect.Constructor;
//import java.util.HashMap;
//
///**
// * Instantiates a layout XML file into its corresponding {@link View}
// * objects. It is never used directly. Instead, use
// * {@link android.app.Activity#getLayoutInflater()} or
// * {@link Context#getSystemService} to retrieve a standard LayoutInflater instance
// * that is already hooked up to the current context and correctly configured
// * for the device you are running on.
// *
// * <p>
// * To create a new LayoutInflater with an additional {@link Factory} for your
// * own views, you can use {@link #cloneInContext} to clone an existing
// * ViewFactory, and then call {@link #setFactory} on it to include your
// * Factory.
// *
// * <p>
// * For performance reasons, view inflation relies heavily on pre-processing of
// * XML files that is done at build time. Therefore, it is not currently possible
// * to use LayoutInflater with an XmlPullParser over a plain XML file at runtime;
// * it only works with an XmlPullParser returned from a compiled resource
// * (R.<em>something</em> file.)
// */
//@SystemService(Context.LAYOUT_INFLATER_SERVICE)
//public abstract class LayoutInflater {
//
//    private static final String TAG = LayoutInflater.class.getSimpleName();
//    private static final boolean DEBUG = false;
//
//    /** Empty stack trace used to avoid log spam in re-throw exceptions. */
//    private static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];
//
//    /**
//     * This field should be made private, so it is hidden from the SDK.
//     * {@hide}
//     */
//    protected final Context mContext;
//
//    // these are optional, set by the caller
//    private boolean mFactorySet;
//    private Factory mFactory;
//    private Factory2 mFactory2;
//    private Factory2 mPrivateFactory;
//    private Filter mFilter;
//
//    final Object[] mConstructorArgs = new Object[2];
//
//    static final Class<?>[] mConstructorSignature = new Class[] {
//            Context.class, AttributeSet.class};
//
//    private static final HashMap<String, Constructor<? extends View>> sConstructorMap =
//            new HashMap<String, Constructor<? extends View>>();
//
//    private HashMap<String, Boolean> mFilterMap;
//
//    private TypedValue mTempValue;
//
//    private static final String TAG_MERGE = "merge";
//    private static final String TAG_INCLUDE = "include";
//    private static final String TAG_1995 = "blink";
//    private static final String TAG_REQUEST_FOCUS = "requestFocus";
//    private static final String TAG_TAG = "tag";
//
//    private static final String ATTR_LAYOUT = "layout";
//
//    private static final int[] ATTRS_THEME = new int[] {
//            com.android.internal.R.attr.theme };
//
//    /**
//     * Hook to allow clients of the LayoutInflater to restrict the set of Views that are allowed
//     * to be inflated.
//     *
//     */
//    public interface Filter {
//        /**
//         * Hook to allow clients of the LayoutInflater to restrict the set of Views
//         * that are allowed to be inflated.
//         *
//         * @param clazz The class object for the View that is about to be inflated
//         *
//         * @return True if this class is allowed to be inflated, or false otherwise
//         */
//        @SuppressWarnings("unchecked")
//        boolean onLoadClass(Class clazz);
//    }
//
//    public interface Factory {
//        /**
//         * Hook you can supply that is called when inflating from a LayoutInflater.
//         * You can use this to customize the tag names available in your XML
//         * layout files.
//         *
//         * <p>
//         * Note that it is good practice to prefix these custom names with your
//         * package (i.e., com.coolcompany.apps) to avoid conflicts with system
//         * names.
//         *
//         * @param name Tag name to be inflated.
//         * @param context The context the view is being created in.
//         * @param attrs Inflation attributes as specified in XML file.
//         *
//         * @return View Newly created view. Return null for the default
//         *         behavior.
//         */
//        public View onCreateView(String name, Context context, AttributeSet attrs);
//    }
//
//    public interface Factory2 extends Factory {
//        /**
//         * Version of {@link #onCreateView(String, Context, AttributeSet)}
//         * that also supplies the parent that the view created view will be
//         * placed in.
//         *
//         * @param parent The parent that the created view will be placed
//         * in; <em>note that this may be null</em>.
//         * @param name Tag name to be inflated.
//         * @param context The context the view is being created in.
//         * @param attrs Inflation attributes as specified in XML file.
//         *
//         * @return View Newly created view. Return null for the default
//         *         behavior.
//         */
//        public View onCreateView(View parent, String name, Context context, AttributeSet attrs);
//    }
//
//    private static class FactoryMerger implements Factory2 {
//        private final Factory mF1, mF2;
//        private final Factory2 mF12, mF22;
//
//        FactoryMerger(Factory f1, Factory2 f12, Factory f2, Factory2 f22) {
//            mF1 = f1;
//            mF2 = f2;
//            mF12 = f12;
//            mF22 = f22;
//        }
//
//        public View onCreateView(String name, Context context, AttributeSet attrs) {
//            View v = mF1.onCreateView(name, context, attrs);
//            if (v != null) return v;
//            return mF2.onCreateView(name, context, attrs);
//        }
//
//        public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//            View v = mF12 != null ? mF12.onCreateView(parent, name, context, attrs)
//                    : mF1.onCreateView(name, context, attrs);
//            if (v != null) return v;
//            return mF22 != null ? mF22.onCreateView(parent, name, context, attrs)
//                    : mF2.onCreateView(name, context, attrs);
//        }
//    }
//
//    /**
//     * Create a new LayoutInflater instance associated with a particular Context.
//     * Applications will almost always want to use
//     * {@link Context#getSystemService Context.getSystemService()} to retrieve
//     * the standard {@link Context#LAYOUT_INFLATER_SERVICE Context.INFLATER_SERVICE}.
//     *
//     * @param context The Context in which this LayoutInflater will create its
//     * Views; most importantly, this supplies the theme from which the default
//     * values for their attributes are retrieved.
//     */
//    protected LayoutInflater(Context context) {
//        mContext = context;
//    }
//
//    /**
//     * Create a new LayoutInflater instance that is a copy of an existing
//     * LayoutInflater, optionally with its Context changed.  For use in
//     * implementing {@link #cloneInContext}.
//     *
//     * @param original The original LayoutInflater to copy.
//     * @param newContext The new Context to use.
//     */
//    protected LayoutInflater(LayoutInflater original, Context newContext) {
//        mContext = newContext;
//        mFactory = original.mFactory;
//        mFactory2 = original.mFactory2;
//        mPrivateFactory = original.mPrivateFactory;
//        setFilter(original.mFilter);
//    }
//
//    /**
//     * Obtains the LayoutInflater from the given context.
//     */
//    // TODO: 2018/6/5  从给定上下文中获取LayoutInflater
//    public static LayoutInflater from(Context context) {
//        LayoutInflater LayoutInflater =
//                (com.android.interview.merge_and_viewstub_demo.LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        if (LayoutInflater == null) {
//            throw new AssertionError("LayoutInflater not found.");
//        }
//        return LayoutInflater;
//    }
//
//    /**
//     * Create a copy of the existing LayoutInflater object, with the copy
//     * pointing to a different Context than the original.  This is used by
//     * {@link ContextThemeWrapper} to create a new LayoutInflater to go along
//     * with the new Context theme.
//     *
//     * @param newContext The new Context to associate with the new LayoutInflater.
//     * May be the same as the original Context if desired.
//     *
//     * @return Returns a brand spanking new LayoutInflater object associated with
//     * the given Context.
//     */
//    public abstract LayoutInflater cloneInContext(Context newContext);
//
//    /**
//     * Return the context we are running in, for access to resources, class
//     * loader, etc.
//     */
//    public Context getContext() {
//        return mContext;
//    }
//
//    /**
//     * Return the current {@link Factory} (or null). This is called on each element
//     * name. If the factory returns a View, add that to the hierarchy. If it
//     * returns null, proceed to call onCreateView(name).
//     */
//    public final Factory getFactory() {
//        return mFactory;
//    }
//
//    /**
//     * Return the current {@link Factory2}.  Returns null if no factory is set
//     * or the set factory does not implement the {@link Factory2} interface.
//     * This is called on each element
//     * name. If the factory returns a View, add that to the hierarchy. If it
//     * returns null, proceed to call onCreateView(name).
//     */
//    public final Factory2 getFactory2() {
//        return mFactory2;
//    }
//
//    /**
//     * Attach a custom Factory interface for creating views while using
//     * this LayoutInflater.  This must not be null, and can only be set once;
//     * after setting, you can not change the factory.  This is
//     * called on each element name as the xml is parsed. If the factory returns
//     * a View, that is added to the hierarchy. If it returns null, the next
//     * factory default {@link #onCreateView} method is called.
//     *
//     * <p>If you have an existing
//     * LayoutInflater and want to add your own factory to it, use
//     * {@link #cloneInContext} to clone the existing instance and then you
//     * can use this function (once) on the returned new instance.  This will
//     * merge your own factory with whatever factory the original instance is
//     * using.
//     */
//    public void setFactory(Factory factory) {
//        if (mFactorySet) {
//            throw new IllegalStateException("A factory has already been set on this LayoutInflater");
//        }
//        if (factory == null) {
//            throw new NullPointerException("Given factory can not be null");
//        }
//        mFactorySet = true;
//        if (mFactory == null) {
//            mFactory = factory;
//        } else {
//            mFactory = new FactoryMerger(factory, null, mFactory, mFactory2);
//        }
//    }
//
//    /**
//     * Like {@link #setFactory}, but allows you to set a {@link Factory2}
//     * interface.
//     */
//    public void setFactory2(Factory2 factory) {
//        if (mFactorySet) {
//            throw new IllegalStateException("A factory has already been set on this LayoutInflater");
//        }
//        if (factory == null) {
//            throw new NullPointerException("Given factory can not be null");
//        }
//        mFactorySet = true;
//        if (mFactory == null) {
//            mFactory = mFactory2 = factory;
//        } else {
//            mFactory = mFactory2 = new FactoryMerger(factory, factory, mFactory, mFactory2);
//        }
//    }
//
//    /**
//     * @hide for use by framework
//     */
//    public void setPrivateFactory(Factory2 factory) {
//        if (mPrivateFactory == null) {
//            mPrivateFactory = factory;
//        } else {
//            mPrivateFactory = new FactoryMerger(factory, factory, mPrivateFactory, mPrivateFactory);
//        }
//    }
//
//    /**
//     * @return The {@link Filter} currently used by this LayoutInflater to restrict the set of Views
//     * that are allowed to be inflated.
//     */
//    public Filter getFilter() {
//        return mFilter;
//    }
//
//    /**
//     * Sets the {@link Filter} to by this LayoutInflater. If a view is attempted to be inflated
//     * which is not allowed by the {@link Filter}, the {@link #inflate(int, ViewGroup)} call will
//     * throw an {@link InflateException}. This filter will replace any previous filter set on this
//     * LayoutInflater.
//     *
//     * @param filter The Filter which restricts the set of Views that are allowed to be inflated.
//     *        This filter will replace any previous filter set on this LayoutInflater.
//     */
//    public void setFilter(Filter filter) {
//        mFilter = filter;
//        if (filter != null) {
//            mFilterMap = new HashMap<String, Boolean>();
//        }
//    }
//
//    /**
//     * Inflate a new view hierarchy from the specified xml resource. Throws
//     * {@link InflateException} if there is an error.
//     *
//     * @param resource ID for an XML layout resource to load (e.g.,
//     *        <code>R.layout.main_page</code>)
//     * @param root Optional view to be the parent of the generated hierarchy.
//     * @return The root View of the inflated hierarchy. If root was supplied,
//     *         this is the root View; otherwise it is the root of the inflated
//     *         XML file.
//     */
//    // TODO: 2018/6/4  入口的函数  把指定的布局资源填充成View。如果root不为空则把填充的View添加到root上，如果root为空则不添加。
//    public View inflate(@LayoutRes int resource, @Nullable ViewGroup root) {
//        return inflate(resource, root, root != null);
//    }
//
//    /**
//     * Inflate a new view hierarchy from the specified xml node. Throws
//     * {@link InflateException} if there is an error. *
//     * <p>
//     * <em><strong>Important</strong></em>&nbsp;&nbsp;&nbsp;For performance
//     * reasons, view inflation relies heavily on pre-processing of XML files
//     * that is done at build time. Therefore, it is not currently possible to
//     * use LayoutInflater with an XmlPullParser over a plain XML file at runtime.
//     *
//     * @param parser XML dom node containing the description of the view
//     *        hierarchy.
//     * @param root Optional view to be the parent of the generated hierarchy.
//     * @return The root View of the inflated hierarchy. If root was supplied,
//     *         this is the root View; otherwise it is the root of the inflated
//     *         XML file.
//     */
//    //通过布局xml资源的解析器把布局资源填充成View。如果root不为空则把填充的View添加到root上，如果root为空则不添加。
//    public View inflate(XmlPullParser parser, @Nullable ViewGroup root) {
//        return inflate(parser, root, root != null);
//    }
//
//    /**
//     * Inflate a new view hierarchy from the specified xml resource. Throws
//     * {@link InflateException} if there is an error.
//     *
//     * @param resource ID for an XML layout resource to load (e.g.,
//     *        <code>R.layout.main_page</code>)
//     * @param root Optional view to be the parent of the generated hierarchy (if
//     *        <em>attachToRoot</em> is true), or else simply an object that
//     *        provides a set of LayoutParams values for root of the returned
//     *        hierarchy (if <em>attachToRoot</em> is false.)
//     * @param attachToRoot Whether the inflated hierarchy should be attached to
//     *        the root parameter? If false, root is only used to create the
//     *        correct subclass of LayoutParams for the root view in the XML.
//     * @return The root View of the inflated hierarchy. If root was supplied and
//     *         attachToRoot is true, this is root; otherwise it is the root of
//     *         the inflated XML file.
//     */
//    //把指定的布局资源填充成View。如果root不为空并且attachToRoot为true，则把填充的View添加到root上，否则不添加。
//    public View inflate(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot) {
//        final Resources res = getContext().getResources();
//        if (DEBUG) {
//            Log.d(TAG, "INFLATING from resource: \"" + res.getResourceName(resource) + "\" ("
//                    + Integer.toHexString(resource) + ")");
//        }
//        //这里通过底层的方法，得到一个XmlResourceParser对象
//        final XmlResourceParser parser = res.getLayout(resource);
//        try {
//            return inflate(parser, root, attachToRoot);
//        } finally {
//            parser.close();
//        }
//    }
//
//    /**
//     * Inflate a new view hierarchy from the specified XML node. Throws
//     * {@link InflateException} if there is an error.
//     * <p>
//     * <em><strong>Important</strong></em>&nbsp;&nbsp;&nbsp;For performance
//     * reasons, view inflation relies heavily on pre-processing of XML files
//     * that is done at build time. Therefore, it is not currently possible to
//     * use LayoutInflater with an XmlPullParser over a plain XML file at runtime.
//     *
//     * @param parser XML dom node containing the description of the view
//     *        hierarchy.
//     * @param root Optional view to be the parent of the generated hierarchy (if
//     *        <em>attachToRoot</em> is true), or else simply an object that
//     *        provides a set of LayoutParams values for root of the returned
//     *        hierarchy (if <em>attachToRoot</em> is false.)
//     * @param attachToRoot Whether the inflated hierarchy should be attached to
//     *        the root parameter? If false, root is only used to create the
//     *        correct subclass of LayoutParams for the root view in the XML.
//     * @return The root View of the inflated hierarchy. If root was supplied and
//     *         attachToRoot is true, this is root; otherwise it is the root of
//     *         the inflated XML file.
//     */
//    //传入的父View为空或者不添加到父View上，则返回根节点的View。
//    //其他（也就是根节点是merge、父View不为空且添加到父View上，返回的是父View）。
//    public View inflate(XmlPullParser parser, @Nullable ViewGroup root, boolean attachToRoot) {
//        synchronized (mConstructorArgs) {
//            //底层的方法，不知道原理
//            Trace.traceBegin(Trace.TRACE_TAG_VIEW, "inflate");
//            // TODO: 2018/6/5  我没有搞明白 为啥整个值在这里做什么的， 在这个方法里面都没有使用
//            // from传入的Context
//            final Context inflaterContext = mContext;
//            // 判断parser是否是AttributeSet，如果不是则用XmlPullAttributes去包装一下。
//            final AttributeSet attrs = Xml.asAttributeSet(parser);
//            // 保存之前的Context
//            Context lastContext = (Context) mConstructorArgs[0];
//            // 赋值为传入的Context
//            mConstructorArgs[0] = inflaterContext;
//            // 默认返回的是传入的Parent
//            View result = root;
//            try {
//                // Look for the root node.
//                int type;// 迭代xml中的所有元素，挨个解析
//                while ((type = parser.next()) != XmlPullParser.START_TAG &&
//                        type != XmlPullParser.END_DOCUMENT) {
//                    // Empty
//                }
//               // //如果没找到有效的开始标签则抛出InflateException
//                if (type != XmlPullParser.START_TAG) {
//                    throw new InflateException(parser.getPositionDescription()
//                            + ": No start tag found!");
//                }
//
//                final String name = parser.getName();
//
//                if (DEBUG) {
//                    System.out.println("**************************");
//                    System.out.println("Creating root view: "
//                            + name);
//                    System.out.println("**************************");
//                }
//                //// 如果根节点是“merge”标签
//                if (TAG_MERGE.equals(name)) {
//                    // 根节点为空或者不添加到根节点上，则抛出异常。
//                    // 因为“merge”标签必须是要被添加到父节点上的，不能独立存在。
//                    if (root == null || !attachToRoot) {
//                        throw new InflateException("<merge /> can be used only with a valid "
//                                + "ViewGroup root and attachToRoot=true");
//                    }
//                    // 递归实例化root（也就是传入Parent）下所有的View
//                    // 如果xml中的节点是merge节点，则调用rInflate()--方法  //
//                    // 递归实例化根节点的子View
//                    rInflate(parser, root, inflaterContext, attrs, false);
//                } else {
//                    // Temp is the root view that was found in the xml
//                    //通过View的父View，View的名称、attrs属性实例化View（内部调用onCreateView()和createView()）。
//                    // TODO: 2018/6/6  // 实例化根节点的View
//                    final View temp = createViewFromTag(root, name, inflaterContext, attrs);
//
//                    ViewGroup.LayoutParams params = null;
//
//                    if (root != null) {
//                        if (DEBUG) {
//                            System.out.println("Creating params from root: " +
//                                    root);
//                        }
//                        // Create layout params that match root, if supplied
//                        params = root.generateLayoutParams(attrs);
//                        if (!attachToRoot) {
//                            // Set the layout params for temp if we are not
//                            // attaching. (If we are, we use addView, below)
//                            temp.setLayoutParams(params);
//                        }
//                    }
//
//                    if (DEBUG) {
//                        System.out.println("-----> start inflating children");
//                    }
//
//                    // Inflate all children under temp against its context.
//                    // 递归实例化跟节点的子View
//                    rInflateChildren(parser, temp, attrs, true);
//
//                    if (DEBUG) {
//                        System.out.println("-----> done inflating children");
//                    }
//
//                    // We are supposed to attach all the views we found (int temp)
//                    // to root. Do that now.\
//
//                    if (root != null && attachToRoot) {
//                        root.addView(temp, params); // TODO: 2018/6/6      返回父View
//                    }
//
//                    // Decide whether to return the root that was passed in or the
//                    // top view found in xml.
//                    // TODO: 2018/6/6  父View是空或者不把填充的View添加到父View)
//                    if (root == null || !attachToRoot) {
//                        result = temp;  // TODO: 2018/6/6 返回根节点View
//                    }
//                }
//
//            } catch (XmlPullParserException e) {
//                final InflateException ie = new InflateException(e.getMessage(), e);
//                ie.setStackTrace(EMPTY_STACK_TRACE);
//                throw ie;
//            } catch (Exception e) {
//                final InflateException ie = new InflateException(parser.getPositionDescription()
//                        + ": " + e.getMessage(), e);
//                ie.setStackTrace(EMPTY_STACK_TRACE);
//                throw ie;
//            } finally {
//                // Don't retain static reference on context.
//                //不要在上下文中保留静态引用。
//                // 把这之前保存的Context从新放回全局变量中。
//                mConstructorArgs[0] = lastContext;
//                mConstructorArgs[1] = null;
//
//                Trace.traceEnd(Trace.TRACE_TAG_VIEW);
//            }
//
//            return result;
//        }
//    }
//
//    private static final ClassLoader BOOT_CLASS_LOADER = LayoutInflater.class.getClassLoader();
//
//    private final boolean verifyClassLoader(Constructor<? extends View> constructor) {
//        final ClassLoader constructorLoader = constructor.getDeclaringClass().getClassLoader();
//        if (constructorLoader == BOOT_CLASS_LOADER) {
//            // fast path for boot class loader (most common case?) - always ok
//            return true;
//        }
//        // in all normal cases (no dynamic code loading), we will exit the following loop on the
//        // first iteration (i.e. when the declaring classloader is the contexts class loader).
//        ClassLoader cl = mContext.getClassLoader();
//        do {
//            if (constructorLoader == cl) {
//                return true;
//            }
//            cl = cl.getParent();
//        } while (cl != null);
//        return false;
//    }
//
//    /**
//     * Low-level function for instantiating a view by name. This attempts to
//     * instantiate a view class of the given <var>name</var> found in this
//     * LayoutInflater's ClassLoader.
//     *
//     * <p>
//     * There are two things that can happen in an error case: either the
//     * exception describing the error will be thrown, or a null will be
//     * returned. You must deal with both possibilities -- the former will happen
//     * the first time createView() is called for a class of a particular name,
//     * the latter every time there-after for that class name.
//     *
//     * @param name The full name of the class to be instantiated.
//     * @param attrs The XML attributes supplied for this instance.
//     *
//     * @return View The newly instantiated view, or null.
//     */
//    //通过View的名称，前缀和attrs属性实例化View。
//    public final View createView(String name, String prefix, AttributeSet attrs)
//            throws ClassNotFoundException, InflateException {
//        Constructor<? extends View> constructor = sConstructorMap.get(name);
//        if (constructor != null && !verifyClassLoader(constructor)) {
//            constructor = null;
//            sConstructorMap.remove(name);
//        }
//        Class<? extends View> clazz = null;
//
//        try {
//            Trace.traceBegin(Trace.TRACE_TAG_VIEW, name);
//
//            if (constructor == null) {
//                // Class not found in the cache, see if it's real, and try to add it
//                clazz = mContext.getClassLoader().loadClass(
//                        prefix != null ? (prefix + name) : name).asSubclass(View.class);
//
//                if (mFilter != null && clazz != null) {
//                    boolean allowed = mFilter.onLoadClass(clazz);
//                    if (!allowed) {
//                        failNotAllowed(name, prefix, attrs);
//                    }
//                }
//                constructor = clazz.getConstructor(mConstructorSignature);
//                constructor.setAccessible(true);
//                sConstructorMap.put(name, constructor);
//            } else {
//                // If we have a filter, apply it to cached constructor
//                if (mFilter != null) {
//                    // Have we seen this name before?
//                    Boolean allowedState = mFilterMap.get(name);
//                    if (allowedState == null) {
//                        // New class -- remember whether it is allowed
//                        clazz = mContext.getClassLoader().loadClass(
//                                prefix != null ? (prefix + name) : name).asSubclass(View.class);
//
//                        boolean allowed = clazz != null && mFilter.onLoadClass(clazz);
//                        mFilterMap.put(name, allowed);
//                        if (!allowed) {
//                            failNotAllowed(name, prefix, attrs);
//                        }
//                    } else if (allowedState.equals(Boolean.FALSE)) {
//                        failNotAllowed(name, prefix, attrs);
//                    }
//                }
//            }
//
//            Object lastContext = mConstructorArgs[0];
//            if (mConstructorArgs[0] == null) {
//                // Fill in the context if not already within inflation.
//                mConstructorArgs[0] = mContext;
//            }
//            Object[] args = mConstructorArgs;
//            args[1] = attrs;
//            //native 方法，创建View
//            final View view = constructor.newInstance(args);
//            if (view instanceof ViewStub) {
//                // Use the same context when inflating ViewStub later.
//                final ViewStub viewStub = (ViewStub) view;
//                viewStub.setLayoutInflater(cloneInContext((Context) args[0]));
//            }
//            mConstructorArgs[0] = lastContext;
//            return view;
//
//        } catch (NoSuchMethodException e) {
//            final InflateException ie = new InflateException(attrs.getPositionDescription()
//                    + ": Error inflating class " + (prefix != null ? (prefix + name) : name), e);
//            ie.setStackTrace(EMPTY_STACK_TRACE);
//            throw ie;
//
//        } catch (ClassCastException e) {
//            // If loaded class is not a View subclass
//            final InflateException ie = new InflateException(attrs.getPositionDescription()
//                    + ": Class is not a View " + (prefix != null ? (prefix + name) : name), e);
//            ie.setStackTrace(EMPTY_STACK_TRACE);
//            throw ie;
//        } catch (ClassNotFoundException e) {
//            // If loadClass fails, we should propagate the exception.
//            throw e;
//        } catch (Exception e) {
//            final InflateException ie = new InflateException(
//                    attrs.getPositionDescription() + ": Error inflating class "
//                            + (clazz == null ? "<unknown>" : clazz.getName()), e);
//            ie.setStackTrace(EMPTY_STACK_TRACE);
//            throw ie;
//        } finally {
//            Trace.traceEnd(Trace.TRACE_TAG_VIEW);
//        }
//    }
//
//    /**
//     * Throw an exception because the specified class is not allowed to be inflated.
//     */
//    private void failNotAllowed(String name, String prefix, AttributeSet attrs) {
//        throw new InflateException(attrs.getPositionDescription()
//                + ": Class not allowed to be inflated "+ (prefix != null ? (prefix + name) : name));
//    }
//
//    /**
//     * This routine is responsible for creating the correct subclass of View
//     * given the xml element name. Override it to handle custom view objects. If
//     * you override this in your subclass be sure to call through to
//     * super.onCreateView(name) for names you do not recognize.
//     *
//     * @param name The fully qualified class name of the View to be create.
//     * @param attrs An AttributeSet of attributes to apply to the View.
//     *
//     * @return View The View created.
//     */
//    protected View onCreateView(String name, AttributeSet attrs)
//            throws ClassNotFoundException {
//        return createView(name, "android.view.", attrs);
//    }
//
//    /**
//     * Version of {@link #onCreateView(String, AttributeSet)} that also
//     * takes the future parent of the view being constructed.  The default
//     * implementation simply calls {@link #onCreateView(String, AttributeSet)}.
//     *
//     * @param parent The future parent of the returned view.  <em>Note that
//     * this may be null.</em>
//     * @param name The fully qualified class name of the View to be create.
//     * @param attrs An AttributeSet of attributes to apply to the View.
//     *
//     * @return View The View created.
//     */
//    protected View onCreateView(View parent, String name, AttributeSet attrs)
//            throws ClassNotFoundException {
//        return onCreateView(name, attrs);
//    }
//
//    /**
//     * Convenience method for calling through to the five-arg createViewFromTag
//     * method. This method passes {@code false} for the {@code ignoreThemeAttr}
//     * argument and should be used for everything except {@code &gt;include>}
//     * tag parsing.
//     */
//    private View createViewFromTag(View parent, String name, Context context, AttributeSet attrs) {
//        return createViewFromTag(parent, name, context, attrs, false);
//    }
//
//    /**
//     * Creates a view from a tag name using the supplied attribute set.
//     * <p>
//     * <strong>Note:</strong> Default visibility so the BridgeInflater can
//     * override it.
//     *
//     * @param parent the parent view, used to inflate layout params
//     * @param name the name of the XML tag used to define the view
//     * @param context the inflation context for the view, typically the
//     *                {@code parent} or base layout inflater context
//     * @param attrs the attribute set for the XML tag used to define the view
//     * @param ignoreThemeAttr {@code true} to ignore the {@code android:theme}
//     *                        attribute (if set) for the view being inflated,
//     *                        {@code false} otherwise
//     */
//    // TODO: 2018/6/6
//    View createViewFromTag(View parent, String name, Context context, AttributeSet attrs,
//            boolean ignoreThemeAttr) {
//        if (name.equals("view")) {
//            //我自己打的断点发现，如果根标签是view的，注意是小写的，view，这个name会为null ，接着下面就会出现空指针的异常---> 我很聪明 很牛逼
//            name = attrs.getAttributeValue(null, "class");
//        }
//
//        // Apply a theme wrapper, if allowed and one is specified.
//        //如果允许的话，应用一个主题包装器，并指定一个。
//        if (!ignoreThemeAttr) {
//            final TypedArray ta = context.obtainStyledAttributes(attrs, ATTRS_THEME);
//            final int themeResId = ta.getResourceId(0, 0);
//            if (themeResId != 0) {
//                context = new ContextThemeWrapper(context, themeResId);
//            }
//            ta.recycle();
//        }
//        // TODO: 2018/6/6         //java.lang.NullPointerException: Attempt to invoke virtual method 'boolean java.lang.String.equals(java.lang.Object)' on a null object reference   这个产生的原因是根标签等于view了，
//        //如果有这个标签的话，就出现这个布局，闪动的布局
//        if (name.equals(TAG_1995)) {
//            // Let's party like it's 1995! 写这个代码的哥们，在1995年经历了一件很开心的事情
//            return new BlinkLayout(context, attrs);
//        }
//
//        try {
//            View view;
//            if (mFactory2 != null) {
//                //各个工厂先onCreateView()
//                view = mFactory2.onCreateView(parent, name, context, attrs);
//            } else if (mFactory != null) {
//                view = mFactory.onCreateView(name, context, attrs);
//            } else {
//                view = null;
//            }
//
//            if (view == null && mPrivateFactory != null) {
//                view = mPrivateFactory.onCreateView(parent, name, context, attrs);
//            }
//
//            if (view == null) {
//                final Object lastContext = mConstructorArgs[0];
//                mConstructorArgs[0] = context;
//                try {
//                    if (-1 == name.indexOf('.')) {
//                        view = onCreateView(parent, name, attrs);
//                    } else {
//                        view = createView(name, null, attrs);
//                    }
//                } finally {
//                    mConstructorArgs[0] = lastContext;
//                }
//            }
//
//            return view;
//        } catch (InflateException e) {
//            throw e;
//
//        } catch (ClassNotFoundException e) {
//            // java.lang.ClassNotFoundException: Didn't find class "android.view.viewdd"   Binary XML file line #6: Binary XML file line #6: Error
//            // TODO: 2018/6/6 如果便签是错误的话，就会走到这里来
//            final InflateException ie = new InflateException(attrs.getPositionDescription()
//                    + ": Error inflating class " + name, e);
//            ie.setStackTrace(EMPTY_STACK_TRACE);
//            throw ie;
//
//        } catch (Exception e) {
//            final InflateException ie = new InflateException(attrs.getPositionDescription()
//                    + ": Error inflating class " + name, e);
//            ie.setStackTrace(EMPTY_STACK_TRACE);
//            throw ie;
//        }
//    }
//
//    /**
//     * Recursive method used to inflate internal (non-root) children. This
//     * method calls through to {@link #rInflate} using the parent context as
//     * the inflation context.
//     * <strong>Note:</strong> Default visibility so the BridgeInflater can
//     * call it.
//     */
//    final void rInflateChildren(XmlPullParser parser, View parent, AttributeSet attrs,
//            boolean finishInflate) throws XmlPullParserException, IOException {
//        rInflate(parser, parent, parent.getContext(), attrs, finishInflate);
//    }
//
//    /**
//     * Recursive method used to descend down the xml hierarchy and instantiate
//     * views, instantiate their children, and then call onFinishInflate().
//     * <p>
//     * <strong>Note:</strong> Default visibility so the BridgeInflater can
//     * override it.
//     */
//    /*
//    方法其实就是遍历xml中的所有元素，然后挨个进行解析。例如解析到一个标签，
//    那么就根据用户设置的一些layout_width、layout_height、id等属性来构造一个TextView对象，
//    然后添加到父控件(ViewGroup类型)中。标签也是一样的，我们看到遇到include标签时，
//    会调用parseInclude函数，这就是对标签的解析
//     */
//    // TODO: 2018/6/6 递归方法用于降序XML层次结构并实例化视图，实例化它们的子节点，然后调用onFinishInflate()。 完成填充
//    void rInflate(XmlPullParser parser, View parent, Context context,
//            AttributeSet attrs, boolean finishInflate) throws XmlPullParserException, IOException {
//
//        final int depth = parser.getDepth();
//        int type;
//        boolean pendingRequestFocus = false;
//        // 迭代xml中的所有元素，挨个解析
//        while (((type = parser.next()) != XmlPullParser.END_TAG ||
//                parser.getDepth() > depth) && type != XmlPullParser.END_DOCUMENT) {
//
//            if (type != XmlPullParser.START_TAG) {
//                continue;
//            }
//
//            final String name = parser.getName();
//            //1、解析请求焦点 ，这个控件一直有焦点 requestFocus
//            if (TAG_REQUEST_FOCUS.equals(name)) {
//                pendingRequestFocus = true;
//                consumeChildElements(parser);
//            } else if (TAG_TAG.equals(name)) {
//                //在包含的视图上设置键标记。
//                parseViewTag(parser, parent, attrs);
//            } else if (TAG_INCLUDE.equals(name)) {// 如果xml中的节点是include节点，则调用parseInclude方法
//                if (parser.getDepth() == 0) {
//                    throw new InflateException("<include /> cannot be the root element");
//                }
//                //解析include标签
//                parseInclude(parser, context, parent, attrs);
//            } else if (TAG_MERGE.equals(name)) {
//                // TODO: 2018/5/23 merge  作为布局里面的元素 会报错的哦 ，注意哦
//                //想象下 两个merge 标签重合在一起
//                throw new InflateException("<merge /> must be the root element");
//            } else {
//                // TODO: 2018/5/23 其实就是如果是merge标签，
//                // TODO: 2018/5/23  那么直接将其中的子元素添加到merge标签parent中，这样就保证了不会引入额外的层级。
//                // 1、我们的例子会进入这里  // 通过View的名称实例化View
//                final View view = createViewFromTag(parent, name, context, attrs);
//                // 2、获取merge标签的parent
//                final ViewGroup viewGroup = (ViewGroup) parent;
//                // 3、获取布局参数
//                final ViewGroup.LayoutParams params = viewGroup.generateLayoutParams(attrs);
//                // 4、递归解析每个子元素
//                rInflateChildren(parser, view, attrs, true);
//                // 5、将子元素直接添加到merge标签的parent view中
//                viewGroup.addView(view, params);
//            }
//        }
//
//        if (pendingRequestFocus) {
//            parent.restoreDefaultFocus();
//        }
//        /**
//         * onFinishInflate() View 中的一个空实现，标记完全填充完成了
//         * The method onFinishInflate() will be called after all children have been added.
//         * 以后是所有的孩子调用完成了，之后就调用这个方法
//         */
//        if (finishInflate) {
//            parent.onFinishInflate();
//        }
//    }
//
//    /**
//     * Parses a <code>&lt;tag&gt;</code> element and sets a keyed tag on the
//     * containing View.
//     */
//     //在包含的视图上设置键标记。
//    private void parseViewTag(XmlPullParser parser, View view, AttributeSet attrs)
//            throws XmlPullParserException, IOException {
//        final Context context = view.getContext();
//        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ViewTag);
//        final int key = ta.getResourceId(R.styleable.ViewTag_id, 0);
//        final CharSequence value = ta.getText(R.styleable.ViewTag_value);
//        view.setTag(key, value);
//        ta.recycle();
//
//        consumeChildElements(parser);
//    }
//    //include 的接点 就会走这里
//    private void parseInclude(XmlPullParser parser, Context context, View parent,
//            AttributeSet attrs) throws XmlPullParserException, IOException {
//        int type;
//
//        if (parent instanceof ViewGroup) {
//            // Apply a theme wrapper, if requested. This is sort of a weird
//            // edge case, since developers think the <include> overwrites
//            // values in the AttributeSet of the included View. So, if the
//            // included View has a theme attribute, we'll need to ignore it.
//            //如果VIEW视图有一个主题属性，我们需要忽略它。
//            final TypedArray ta = context.obtainStyledAttributes(attrs, ATTRS_THEME);
//            final int themeResId = ta.getResourceId(0, 0);
//            final boolean hasThemeOverride = themeResId != 0;
//            if (hasThemeOverride) {
//                context = new ContextThemeWrapper(context, themeResId);
//            }
//            ta.recycle();
//
//            //如果布局指向主题属性，我们必须
//            //按摩该值以从中获取资源标识符。
//            int layout = attrs.getAttributeResourceValue(null, ATTR_LAYOUT, 0);
//            if (layout == 0) {
//                final String value = attrs.getAttributeValue(null, ATTR_LAYOUT);
//                // TODO: 2018/6/6 include标签中没有设置layout属性，会抛出异常
//                if (value == null || value.length() <= 0) {//
//                    throw new InflateException("You must specify a layout in the"
//                            + " include tag: <include layout=\"@layout/layoutID\" />");
//                }
//
//                // Attempt to resolve the "?attr/name" string to an attribute
//                // within the default (e.g. application) package.
//                layout = context.getResources().getIdentifier(
//                        value.substring(1), "attr", context.getPackageName());
//
//            }
//
//            // The layout might be referencing a theme attribute.
//            if (mTempValue == null) {
//                mTempValue = new TypedValue();
//            }
//            if (layout != 0 && context.getTheme().resolveAttribute(layout, mTempValue, true)) {
//                layout = mTempValue.resourceId;
//            }
//
//            if (layout == 0) {
//                final String value = attrs.getAttributeValue(null, ATTR_LAYOUT);
//                throw new InflateException("You must specify a valid layout "
//                        + "reference. The layout ID " + value + " is not valid.");
//            } else {
//                final XmlResourceParser childParser = context.getResources().getLayout(layout);
//
//                try {// 获取属性集，即在include标签中设置的属性
//                    final AttributeSet childAttrs = Xml.asAttributeSet(childParser);
//
//                    while ((type = childParser.next()) != XmlPullParser.START_TAG &&
//                            type != XmlPullParser.END_DOCUMENT) {
//                        // Empty.
//                    }
//
//                    if (type != XmlPullParser.START_TAG) {
//                        throw new InflateException(childParser.getPositionDescription() +
//                                ": No start tag found!");
//                    }
//                   // 1、解析include中的第一个元素
//                    final String childName = childParser.getName();
//                   // 如果第一个元素是merge标签，那么调用rInflate函数解析
//                    if (TAG_MERGE.equals(childName)) {
//                        // The <merge> tag doesn't support android:theme, so
//                        // nothing special to do here.
//                        rInflate(childParser, parent, context, childAttrs, false);
//                    } else {
//                        //2、我们例子中的情况会走到这一步,首先根据include的属性集创建被include进来的xml布局的根view
//                        // 这里的根view对应为my_title_layout.xml中的RelativeLayout
//                        final View view = createViewFromTag(parent, childName,
//                                context, childAttrs, hasThemeOverride);
//                        final ViewGroup group = (ViewGroup) parent;
//
//                        final TypedArray a = context.obtainStyledAttributes(
//                                attrs, R.styleable.Include);
//                        //这里就是设置的<include>布局设置的id ,如果include设置了这个id ，那么这个id就不等于 View.No_ID=-1
//                        final int id = a.getResourceId(R.styleable.Include_id, View.NO_ID);
//                        final int visibility = a.getInt(R.styleable.Include_visibility, -1);
//                        a.recycle();
//
//                        // We try to load the layout params set in the <include /> tag.
//                        // If the parent can't generate layout params (ex. missing width
//                        // or height for the framework ViewGroups, though this is not
//                        // necessarily true of all ViewGroups) then we expect it to throw
//                        // a runtime exception.
//                        // We catch this exception and set localParams accordingly: true
//                        // means we successfully loaded layout params from the <include>
//                        // tag, false means we need to rely on the included layout params.
//                        ViewGroup.LayoutParams params = null;
//                        try {
//                            // 获3、取布局属性
//                            params = group.generateLayoutParams(attrs);
//                        } catch (RuntimeException e) {
//                            // Ignore, just fail over to child attrs.
//                        }
//                        // 被inlcude进来的根view设置布局参数
//                        if (params == null) {
//                            params = group.generateLayoutParams(childAttrs);
//                        }
//                        view.setLayoutParams(params);
//                         // 4、Inflate all children. 解析所有子控件
//                        // Inflate all children.
//                        rInflateChildren(childParser, view, childAttrs, true);
//
//                        if (id != View.NO_ID) {
//                         // 5、将include中设置的id设置给根view,因此实际上my_title_layout.xml中
//                          // 的RelativeLayout的id会变成include标签中的id，include不设置id，那么也可以通过relative的找到.
//                            view.setId(id);
//                        }
//
//                        switch (visibility) {
//                            case 0:
//                                view.setVisibility(View.VISIBLE);
//                                break;
//                            case 1:
//                                view.setVisibility(View.INVISIBLE);
//                                break;
//                            case 2:
//                                view.setVisibility(View.GONE);
//                                break;
//                        }
//                        // 6、将根view添加到父控件中
//                        group.addView(view);
//                    }
//                } finally {
//                    childParser.close();
//                }
//            }
//        } else {
//            throw new InflateException("<include /> can only be used inside of a ViewGroup");
//        }
//        //默认的可见性使得LayoutInflater_Delegate代表可以调用它。
//        LayoutInflater.consumeChildElements(parser);
//    }
//
//    /**
//     * <strong>Note:</strong> default visibility so that
//     * LayoutInflater_Delegate can call it.
//     */
//   // 默认的可见性使得LayoutInflater_Delegate代表可以调用它。
//    final static void consumeChildElements(XmlPullParser parser)
//            throws XmlPullParserException, IOException {
//        int type;
//        final int currentDepth = parser.getDepth();
//        while (((type = parser.next()) != XmlPullParser.END_TAG ||
//                parser.getDepth() > currentDepth) && type != XmlPullParser.END_DOCUMENT) {
//            // Empty
//        }
//    }
//
//    private static class BlinkLayout extends FrameLayout {
//        private static final int MESSAGE_BLINK = 0x42;
//        private static final int BLINK_DELAY = 500;
//
//        private boolean mBlink;
//        private boolean mBlinkState;
//        private final Handler mHandler;
//
//        public BlinkLayout(Context context, AttributeSet attrs) {
//            super(context, attrs);
//            mHandler = new Handler(new Handler.Callback() {
//                @Override
//                public boolean handleMessage(Message msg) {
//                    if (msg.what == MESSAGE_BLINK) {
//                        if (mBlink) {
//                            mBlinkState = !mBlinkState;
//                            makeBlink();
//                        }
//                        invalidate();
//                        return true;
//                    }
//                    return false;
//                }
//            });
//        }
//
//        private void makeBlink() {
//            Message message = mHandler.obtainMessage(MESSAGE_BLINK);
//            mHandler.sendMessageDelayed(message, BLINK_DELAY);
//        }
//
//        @Override
//        protected void onAttachedToWindow() {
//            super.onAttachedToWindow();
//
//            mBlink = true;
//            mBlinkState = true;
//
//            makeBlink();
//        }
//
//        @Override
//        protected void onDetachedFromWindow() {
//            super.onDetachedFromWindow();
//
//            mBlink = false;
//            mBlinkState = true;
//
//            mHandler.removeMessages(MESSAGE_BLINK);
//        }
//
//        @Override
//        protected void dispatchDraw(Canvas canvas) {
//            if (mBlinkState) {
//                super.dispatchDraw(canvas);
//            }
//        }
//    }
//}
