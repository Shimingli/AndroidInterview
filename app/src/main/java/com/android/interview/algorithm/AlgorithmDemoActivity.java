package com.android.interview.algorithm;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.interview.R;
import com.android.interview.algorithm.tree.BinaryTreeDemo;

import static com.android.interview.algorithm.MYDemoNum.str;

/**
 * algorithm  算法原理
 */
public class AlgorithmDemoActivity extends AppCompatActivity {

    public String TAG = this.getClass().getSimpleName() + ":  ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm_demo);


        arrDemo();


        // 请实现一个函数，把字符串中的每个空格替换成"%20"，例如“We are happy.”，则输出“We%20are%20happy.”。
        // todo 通过对比发现，如果只使用一次的话，建议选择 string.replace()! 使用多次的话，建议选择replaceBlank
        replaceSpacesDemo();


        //输入个链表的头结点，从尾到头反过来打印出每个结点的值。
        ListNodeDemo.demo();

        // 二叉树
        // tks 博客  ：https://www.cnblogs.com/ysocean/p/8032642.html
        BinaryTreeDemo.binaryTree();

    }
   /*
我自己idoWorkReplace实现的方式总共耗时time= 1401563
使用 StringBuffer中的消耗的时间 time= 596354
使用 replaceSpaces中的消耗的时间 time= 1450521
使用 replaceBlank中的消耗的时间 time= 200000

 使用 replaceBlank中的消耗的时间 time= 202604
  idoWorkReplace 实现的方式总共耗时time= 1800000
  使用 StringBuffer中的消耗的时间 time= 455729
 使用 replaceSpaces中的消耗的时间 time= 1447917
    */
    private void replaceSpacesDemo() {
        long l7 = System.nanoTime();
        char[] chars = replaceBlank(str.toCharArray(), str.length());
        long l8 = System.nanoTime();
        l8 = l8 - l7;
        System.out.println(TAG + "使用 replaceBlank中的消耗的时间 time= " + l8);
        for (char c :chars){
            System.out.print(c);
        }
        System.out.println();


        String tagStr = "%20";
        // 1纳秒=0.00000 0001秒
        long l1 = System.nanoTime();
        //todo  消耗的时间 很多  放弃这种的方法
        idoWorkReplace(str, tagStr);
        //替换我自己的方式s=We%20are%20happy.
       // System.out.println(TAG + "替换我自己的方式s=" + s);
        long l2 = System.nanoTime();
        l2 = l2 - l1;

        System.out.println(TAG + "idoWorkReplace 实现的方式总共耗时time= " + l2);

        long l3 = System.nanoTime();
        StringBuffer sb = new StringBuffer();
        sb.append(str);
        // todo  最节能的方法
        sb.toString().replace(" ", "%20");
        long l4 = System.nanoTime();
        l4 = l4 - l3;
        // todo  使用 StringBuffer中的消耗的时间 time= 85417 ！时间明显下降
        System.out.println(TAG + "使用 StringBuffer中的消耗的时间 time= " + l4);

        long l5 = System.nanoTime();
        replaceSpaces(str);
        long l6 = System.nanoTime();
        l6 = l6 - l5;
        System.out.println(TAG + "使用 replaceSpaces中的消耗的时间 time= " + l6);


    }


    /**
     * 请实现一个函数，把字符串中的每个空格替换成"%20"，例如“We are happy.“，则输出”We%20are%20happy.“。
     *
     * @param string     要转换的字符数组
     * @param usedLength 已经字符数组中已经使用的长度
     * @return 转换后使用的字符长度，-1表示处理失败
     */
    public char[]  replaceBlank(char[] string, int usedLength) {
        // 判断输入是否合法
        System.out.println(TAG+"string.length="+string.length);
        System.out.println(TAG+"usedLength="+usedLength);
        if (string == null || string.length < usedLength) {
            return null;
        }

        // 统计字符数组中的空白字符数
        int whiteCount = 0;
        for (int i = 0; i < usedLength; i++) {
            if (string[i] == ' ') {
                whiteCount++;
            }
        }
        // 如果没有空白字符就不用处理
        if (whiteCount == 0) {
            return string;
        }
        // 计算转换后的字符长度是多少
        int targetLength = whiteCount * 2 + usedLength;
         //新的保存的字符串的数组
        char[] newChars = new char[targetLength];
        int tmp = targetLength; // 保存长度结果用于返回
//        if (targetLength > string.length) { // 如果转换后的长度大于数组的最大长度，直接返回失败
//            return -1;
//        }
        // todo  必须先做 这个，注意体会  --i  和 i-- 的区别
        usedLength--; // 从后向前，第一个开始处理的字符
        targetLength--; // 处理后的字符放置的位置
        // 字符中有空白字符，一直处理到所有的空白字符处理完
        while (usedLength >= 0 && usedLength < targetLength) {
            // 如是当前字符是空白字符，进行"%20"替换
            if (string[usedLength--] == ' ') {
                newChars[targetLength--] = '0';
                newChars[targetLength--] = '2';
                newChars[targetLength--] = '%';
            } else { // 否则移动字符
                newChars[targetLength--] = string[usedLength];
            }
            usedLength--;
        }
        return newChars;
    }


    /**
     * 要转换的字符数组
     * 要转换的字符数组的长度
     */
    private String replaceSpaces(String string) {
        //判断是否 输入合法
        if (string == null || string.length() < 1) {
            return "";
        }
        char[] chars = string.toCharArray();
        // 统计有多少的空白的数组
        int whiteCount = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ') {
                whiteCount++;
            }
        }
        if (whiteCount == 0) {
            return string;
        }
        //最原本的长度
        int indexold = string.length() - 1;
        // 转换完成后的长度
        int newlength = string.length() + whiteCount * 2;
        //新的长度
        int indexnew = newlength - 1;
        StringBuffer stringBuffer = new StringBuffer(string);
        //设置新的buffer的长度
        stringBuffer.setLength(newlength);
        for (; indexold >= 0 && indexold < newlength; indexold--) {
            // 原来的 字符串的最后一位为空格
            if (string.charAt(indexold) == ' ') {
                stringBuffer.setCharAt(indexnew--, '0');
                stringBuffer.setCharAt(indexnew--, '2');
                stringBuffer.setCharAt(indexnew--, '%');
            } else {//不为空的话，就直接放进去 就行了
                stringBuffer.setCharAt(indexnew--, string.charAt(indexold));
            }
        }
        return stringBuffer.toString();

    }

    private String idoWorkReplace(String str, String tagStr) {
        if (str == null || str.length() < 1) {
            return "";
        }
        String[] split = str.split(" ");
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : split) {
            stringBuffer.append(s);
            stringBuffer.append(tagStr);
        }
        CharSequence charSequence = stringBuffer.subSequence(0, stringBuffer.length() - tagStr.length());
        return charSequence.toString();
    }

    private void arrDemo() {
        // 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
        int[][] a = {{2, 3, 4, 5}, {3, 4, 5, 6}, {4, 5, 6, 7}};
        int[][] a1 = {{1, 2, 8, 9}, {2, 4, 9, 12}, {4, 7, 10, 13}, {6, 8, 11, 15}};
        boolean demo = lookUpInTwoDimensionalArrays(a, 7);
        System.out.println(TAG + "是否找到了7 " + demo);
        boolean demo1 = lookUpInTwoDimensionalArrays(a, 8);
        System.out.println(TAG + "是否找到了8 " + demo1);
        //  一共循环多少次：16  才能找到15，所以说效率低下
        boolean demo2 = lookUpInTwoDimensionalArrays(a1, 15);
        System.out.println(TAG + "是否找到了15 " + demo2);

        // 上面一共执行了 12次  ，效率低下
        // newLookUpInTwoDimensionalArrays 执行了多少次3
        boolean f = newLookUpInTwoDimensionalArrays(a, 7);
        System.out.println(TAG + "是否找到了7  newLookUpInTwoDimensionalArrays=" + f);

        //todo 这个方法执行了15次
        boolean f1 = lookUpInTwoDimensionalArrays(a1, 11);
        System.out.println(TAG + "是否找到了11== " + f1);
        // todo 这个方法执行了 5次
        boolean f2 = newLookUpInTwoDimensionalArrays(a1, 11);
        System.out.println(TAG + "是否找到了11  newLookUpInTwoDimensionalArrays=" + f2);
    }

    /**
     * 数组是递增的 ，从左到右  从上到下,那么数组一定是个正方形的样子
     *
     * @param arr 原始的数组
     * @param num 需要查找的数字
     * @return 是否找到了
     */
    private boolean newLookUpInTwoDimensionalArrays(int[][] arr, int num) {
        if (arr == null || arr.length < 1 || arr[0].length < 1) {
            return false;
        }
        int rowTotal = arr.length;// 数组的行数
        int colTolal = arr[0].length;// 数组的列数
        //开始的角标
        int row = 0;
        int col = colTolal - 1;
        int i = 0;
        while (row >= 0 && row < rowTotal && col >= 0 && col < colTolal) {
            // 是二维数组的 arr[0][arr[0].length-1] 的值，就是最右边的值
            i++;
            if (arr[row][col] == num) {
                System.out.println(TAG + "newLookUpInTwoDimensionalArrays 执行了多少次" + i);
                return true;
            } else if (arr[row][col] > num) {// 如果找到的值 比目标的值大的话，就把查找的列数减去1
                col--;//列数减去1 ，代表向左移动
            } else {// 比目标的num大的，就把行数加上1，然后往下移动
                row++;
            }
        }

        return false;
    }

    private boolean lookUpInTwoDimensionalArrays(int[][] a, int num) {
        // 至少保证 长度至少为1，且还有元素，
        if (a == null || a.length < 1 || a[0].length < 1) {
            return false;
        }
        int b = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                b++;
                if (num == a[i][j]) {
                    // 一共循环多少次：12
                    System.out.println(TAG + "一共循环多少次：" + b);
                    return true;
                }
            }
        }
        return false;
    }


}
