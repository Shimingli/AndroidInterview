//
//
//import com.android.internal.util.ArrayUtils;
//import com.android.internal.util.GrowingArrayUtils;
//
//import libcore.util.EmptyArray;
//
///**
// * SparseArrays map integers to Objects.  Unlike a normal array of Objects,
// * there can be gaps in the indices.  It is intended to be more memory efficient
// * than using a HashMap to map Integers to Objects, both because it avoids
// * auto-boxing keys and its data structure doesn't rely on an extra entry object
// * for each mapping.
// *
// * <p>Note that this container keeps its mappings in an array data structure,
// * using a binary search to find keys.  The implementation is not intended to be appropriate for
// * data structures
// * that may contain large numbers of items.  It is generally slower than a traditional
// * HashMap, since lookups require a binary search and adds and removes require inserting
// * and deleting entries in the array.  For containers holding up to hundreds of items,
// * the performance difference is not significant, less than 50%.</p>
// *
// * <p>To help with performance, the container includes an optimization when removing
// * keys: instead of compacting its array immediately, it leaves the removed entry marked
// * as deleted.  The entry can then be re-used for the same key, or compacted later in
// * a single garbage collection step of all removed entries.  This garbage collection will
// * need to be performed at any time the array needs to be grown or the the map size or
// * entry values are retrieved.</p>
// *
// * <p>It is possible to iterate over the items in this container using
// * {@link #keyAt(int)} and {@link #valueAt(int)}. Iterating over the keys using
// * <code>keyAt(int)</code> with ascending values of the index will return the
// * keys in ascending order, or the values corresponding to the keys in ascending
// * order in the case of <code>valueAt(int)</code>.</p>
// */
//public class SparseArray<E> implements Cloneable {
//
//    private static final Object DELETED = new Object();
//
//    //是否可以回收，即清理mValues中标记为DELETED的值的元素
//    private boolean mGarbage = false;
//
//    private int[] mKeys;        //保存键的数组
//    private Object[] mValues;   //保存值的数组
//    private int mSize;          //当前已经保存的数据个数
//
//
//    /**
//     * Creates a new SparseArray containing no mappings.
//     */
//    public SparseArray() {
//        this(10);
//    }
//
//    /**
//     * Creates a new SparseArray containing no mappings that will not
//     * require any additional memory allocation to store the specified
//     * number of mappings.  If you supply an initial capacity of 0, the
//     * sparse array will be initialized with a light-weight representation
//     * not requiring any additional array allocations.
//     */
//    /*
//    如果initialCapacity=0那么mKeys,mValuse都初始化为size=0的数组，当initialCapacity>0时，系统生成length=initialCapacity的value数组，同时新建一个同样长度的key数组
//     */
//    public SparseArray(int initialCapacity) {
//        if (initialCapacity == 0) {
//            mKeys = EmptyArray.INT;
//            mValues = EmptyArray.OBJECT;
//        } else {
//            /*
//     public static Object[] newUnpaddedObjectArray(int minLen) {
//      return (Object[])VMRuntime.getRuntime().newUnpaddedArray(Object.class, minLen);
//   }
//             */
//            mValues = ArrayUtils.newUnpaddedObjectArray(initialCapacity);
//            mKeys = new int[mValues.length];
//        }
//        mSize = 0;
//    }
//
//    @Override
//    @SuppressWarnings("unchecked")
//    public SparseArray<E> clone() {
//        SparseArray<E> clone = null;
//        try {
//            clone = (SparseArray<E>) super.clone();
//            //  原型模式的深拷贝   两个容器的拷贝的过程----！！！
//            clone.mKeys = mKeys.clone();
//            clone.mValues = mValues.clone();
//        } catch (CloneNotSupportedException cnse) {
//            /* ignore */
//        }
//        return clone;
//    }
//
//    /**
//     * Gets the Object mapped from the specified key, or <code>null</code>
//     * if no such mapping has been made.
//     */
//    public E get(int key) {
//        return get(key, null);
//    }
//
//    /**
//     * Gets the Object mapped from the specified key, or the specified Object
//     * if no such mapping has been made.
//     */
//    /*
//    i肯定就是key对应的value在values数组中的下标，找到就返回mvaluses[i]，找不到就返回valueIfKeyNotFound
//     valueIfKeyNotFound =null
//     todo
//    //通过二分查找法，在mKeys数组中查询key的位置，然后返回mValues数组中对应位置的值，找不到则返回默认值
//     */
//    @SuppressWarnings("unchecked")
//    public E get(int key, E valueIfKeyNotFound) {
//        /*
//    static int binarySearch(int[] array, int size, int value) {
//    //除了没做参数校验，其余的和Arrays.binarySearch()一样
//        int low = 0;
//        int hight = size - 1;
//
//        while (low <= hight) {
//            //无符号右移运算，相当于除以2，左边补0
//            final int mid = (low + hight) >>> 1;
//            final int midVal = array[mid];
//
//            if (midVal < value) {
//                lo = mid + 1;
//            } else if (midVal > value) {
//                hight = mid - 1;
//            } else {
//                return mid;  // value found
//            }
//        }
//        return ~low;  // value not present
//    }
//         */
//        // 二分查找  感觉不像啊 卧槽
//        int i = ContainerHelpers.binarySearch(mKeys, mSize, key);
//
//        if (i < 0 || mValues[i] == DELETED) {
//            return valueIfKeyNotFound;
//        } else {
//            return (E) mValues[i];
//        }
//    }
//
//    /**
//     * Removes the mapping from the specified key, if there was any.
//     */
//    public void delete(int key) {
//        int i = ContainerHelpers.binarySearch(mKeys, mSize, key);
//       /*
//       i>0表示，找到了key对应的下标，否则应该是负数。同时判断mValues[i] 是不是Object这个对象，如果不是，直接替换为Object（DELETE起到标记删除位置的作用）,并标记 mGarbage=true，注意：这里delete只操作了values数组，并没有去操作key数组;
//        */
//        if (i >= 0) {
//            if (mValues[i] != DELETED) {
//                mValues[i] = DELETED;
//                mGarbage = true;
//            }
//        }
//    }
//
//    /**
//     * @hide
//     * Removes the mapping from the specified key, if there was any, returning the old value.
//     */
//    ////其实就是多了一步，把要删除的值返回，其余同delete一样
//    public E removeReturnOld(int key) {
//        int i = ContainerHelpers.binarySearch(mKeys, mSize, key);
//
//        if (i >= 0) {
//            if (mValues[i] != DELETED) {
//                final E old = (E) mValues[i];
//                mValues[i] = DELETED;
//                mGarbage = true;
//                return old;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * Alias for {@link #delete(int)}.
//     */
//    public void remove(int key) {
//        delete(key);
//    }
//
//    /**
//     * Removes the mapping at the specified index.
//     *
//     * <p>For indices outside of the range <code>0...size()-1</code>,
//     * the behavior is undefined.</p>
//     */
//    public void removeAt(int index) {
//        if (mValues[index] != DELETED) {
//            mValues[index] = DELETED;
//            mGarbage = true;
//        }
//    }
//
//    /**
//     * Remove a range of mappings as a batch.
//     *
//     * @param index Index to begin at
//     * @param size Number of mappings to remove
//     *
//     * <p>For indices outside of the range <code>0...size()-1</code>,
//     * the behavior is undefined.</p>
//     */
//    public void removeAtRange(int index, int size) {
//        final int end = Math.min(mSize, index + size);
//        for (int i = index; i < end; i++) {
//            removeAt(i);
//        }
//    }
//    // 通过gc的方法，把DELETED值的 values 清空
//    private void gc() {
//        // Log.e("SparseArray", "gc start with " + mSize);
//
//        int n = mSize;
//        int o = 0;
//        int[] keys = mKeys;
//        Object[] values = mValues;
//
//        for (int i = 0; i < n; i++) {
//            Object val = values[i];
//
//            if (val != DELETED) {
//                if (i != o) {
//                    keys[o] = keys[i];
//                    values[o] = val;
//                    values[i] = null;
//                }
//
//                o++;
//            }
//        }
//
//        mGarbage = false;
//        mSize = o;
//
//        // Log.e("SparseArray", "gc end with " + mSize);
//    }
//
//    /**
//     * Adds a mapping from the specified key to the specified value,
//     * replacing the previous mapping from the specified key if there
//     * was one.
//     */
//    public void put(int key, E value) {
//        // 二分查找，这个i的值,
//        int i = ContainerHelpers.binarySearch(mKeys, mSize, key);
//        //如果找到了，就把这个值给替换上去 ，或者是赋值上去
//        //  这里 也就可以解释出为啥 替换为最新的值
//        if (i >= 0) {
//            mValues[i] = value;
//        } else {
//            //这里就是key要插入的位置，上面二分查找方法提到过
//            //位非运算符（~）
//            i = ~i;
//            if (i < mSize && mValues[i] == DELETED) {
//                mKeys[i] = key;
//                mValues[i] = value;
//                return;
//            }
//
//            if (mGarbage && mSize >= mKeys.length) {
//                gc();
//
//                // Search again because indices may have changed.
//                i = ~ContainerHelpers.binarySearch(mKeys, mSize, key);
//            }
//           // 一个新的值  ，就会把key 和 value 和 i值插入到两个数组中
//            mKeys = GrowingArrayUtils.insert(mKeys, mSize, i, key);
//            mValues = GrowingArrayUtils.insert(mValues, mSize, i, value);
//            // todo    然后长度 加上 1   nice
//            mSize++;
//        }
//    }
//
//    /**
//     * Returns the number of key-value mappings that this SparseArray
//     * currently stores.
//     */
//    public int size() {
//        if (mGarbage) {
//            gc();
//        }
//
//        return mSize;
//    }
//
//    /**
//     * Given an index in the range <code>0...size()-1</code>, returns
//     * the key from the <code>index</code>th key-value mapping that this
//     * SparseArray stores.
//     *
//     * <p>The keys corresponding to indices in ascending order are guaranteed to
//     * be in ascending order, e.g., <code>keyAt(0)</code> will return the
//     * smallest key and <code>keyAt(size()-1)</code> will return the largest
//     * key.</p>
//     *
//     * <p>For indices outside of the range <code>0...size()-1</code>,
//     * the behavior is undefined.</p>
//     */
//    public int keyAt(int index) {
//        if (mGarbage) {
//            gc();
//        }
//
//        return mKeys[index];
//    }
//
//    /**
//     * Given an index in the range <code>0...size()-1</code>, returns
//     * the value from the <code>index</code>th key-value mapping that this
//     * SparseArray stores.
//     *
//     * <p>The values corresponding to indices in ascending order are guaranteed
//     * to be associated with keys in ascending order, e.g.,
//     * <code>valueAt(0)</code> will return the value associated with the
//     * smallest key and <code>valueAt(size()-1)</code> will return the value
//     * associated with the largest key.</p>
//     *
//     * <p>For indices outside of the range <code>0...size()-1</code>,
//     * the behavior is undefined.</p>
//     */
//    @SuppressWarnings("unchecked")
//    public E valueAt(int index) {
//        if (mGarbage) {
//            gc();
//        }
//
//        return (E) mValues[index];
//    }
//
//    /**
//     * Given an index in the range <code>0...size()-1</code>, sets a new
//     * value for the <code>index</code>th key-value mapping that this
//     * SparseArray stores.
//     *
//     * <p>For indices outside of the range <code>0...size()-1</code>, the behavior is undefined.</p>
//     */
//    public void setValueAt(int index, E value) {
//        if (mGarbage) {
//            gc();
//        }
//
//        mValues[index] = value;
//    }
//
//    /**
//     * Returns the index for which {@link #keyAt} would return the
//     * specified key, or a negative number if the specified
//     * key is not mapped.
//     */
//    public int indexOfKey(int key) {
//        if (mGarbage) {
//            gc();
//        }
//
//        return ContainerHelpers.binarySearch(mKeys, mSize, key);
//    }
//
//    /**
//     * Returns an index for which {@link #valueAt} would return the
//     * specified key, or a negative number if no keys map to the
//     * specified value.
//     * <p>Beware that this is a linear search, unlike lookups by key,
//     * and that multiple keys can map to the same value and this will
//     * find only one of them.
//     * <p>Note also that unlike most collections' {@code indexOf} methods,
//     * this method compares values using {@code ==} rather than {@code equals}.
//     */
//    public int indexOfValue(E value) {
//        if (mGarbage) {
//            gc();
//        }
//
//        for (int i = 0; i < mSize; i++) {
//            if (mValues[i] == value) {
//                return i;
//            }
//        }
//
//        return -1;
//    }
//
//    /**
//     * Returns an index for which {@link #valueAt} would return the
//     * specified key, or a negative number if no keys map to the
//     * specified value.
//     * <p>Beware that this is a linear search, unlike lookups by key,
//     * and that multiple keys can map to the same value and this will
//     * find only one of them.
//     * <p>Note also that this method uses {@code equals} unlike {@code indexOfValue}.
//     * @hide
//     */
//    public int indexOfValueByValue(E value) {
//        if (mGarbage) {
//            gc();
//        }
//
//        for (int i = 0; i < mSize; i++) {
//            if (value == null) {
//                if (mValues[i] == null) {
//                    return i;
//                }
//            } else {
//                if (value.equals(mValues[i])) {
//                    return i;
//                }
//            }
//        }
//        return -1;
//    }
//
//    /**
//     * Removes all key-value mappings from this SparseArray.
//     */
//    //  todo   这里要留意，clear只是清空了values数组，并没有操作keys数组
//    public void clear() {
//        int n = mSize;
//        Object[] values = mValues;
//
//        for (int i = 0; i < n; i++) {
//            values[i] = null;
//        }
//
//        mSize = 0;
//        mGarbage = false;
//    }
//
//    /**
//     * Puts a key/value pair into the array, optimizing for the case where
//     * the key is greater than all existing keys in the array.
//     */
//    // 要使用这个方法 好点 。
//    public void append(int key, E value) {
//        // 判断了是否 需要 二分查找，还是直接插入
//        if (mSize != 0 && key <= mKeys[mSize - 1]) {
//            put(key, value);
//            return;
//        }
//
//        if (mGarbage && mSize >= mKeys.length) {
//            // 通过gc的方法，把DELETED值的 values 清空
//            gc();
//        }
//        // 可以直接都要这里来 ，是最节约能量
//        mKeys = GrowingArrayUtils.append(mKeys, mSize, key);
//        mValues = GrowingArrayUtils.append(mValues, mSize, value);
//        mSize++;
//    }
//
//    /**
//     * {@inheritDoc}
//     *
//     * <p>This implementation composes a string by iterating over its mappings. If
//     * this map contains itself as a value, the string "(this Map)"
//     * will appear in its place.
//     */
//    @Override
//    public String toString() {
//        if (size() <= 0) {
//            return "{}";
//        }
//
//        StringBuilder buffer = new StringBuilder(mSize * 28);
//        buffer.append('{');
//        for (int i=0; i<mSize; i++) {
//            if (i > 0) {
//                buffer.append(", ");
//            }
//            int key = keyAt(i);
//            buffer.append(key);
//            buffer.append('=');
//            Object value = valueAt(i);
//            if (value != this) {
//                buffer.append(value);
//            } else {
//                buffer.append("(this Map)");
//            }
//        }
//        buffer.append('}');
//        return buffer.toString();
//    }
//}
