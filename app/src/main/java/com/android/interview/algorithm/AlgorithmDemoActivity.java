package com.android.interview.algorithm;

import android.appwidget.AppWidgetHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.interview.R;

/**
 * algorithm  算法原理
 */
public class AlgorithmDemoActivity extends AppCompatActivity {

    public String TAG=this.getClass().getSimpleName()+":  ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_algorithm_demo);

        // 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
        int[][] a={{2,3,4,5},{3,4,5,6},{4,5,6,7}};
        int[][] a1={{1,2,8,9},{2,4,9,12},{4,7,10,13},{6,8,11,15}};
        boolean demo = lookUpInTwoDimensionalArrays(a, 7);
        System.out.println(TAG+"是否找到了7 "+demo);
        boolean demo1 = lookUpInTwoDimensionalArrays(a, 8);
        System.out.println(TAG+"是否找到了8 "+demo1);
        //  一共循环多少次：16  才能找到15，所以说效率低下
        boolean demo2 = lookUpInTwoDimensionalArrays(a1, 15);
        System.out.println(TAG+"是否找到了15 "+demo2);

        // 上面一共执行了 12次  ，效率低下
        // newLookUpInTwoDimensionalArrays 执行了多少次3
        boolean f=newLookUpInTwoDimensionalArrays(a,7);
        System.out.println(TAG+"是否找到了7  newLookUpInTwoDimensionalArrays="+f);

        //todo 这个方法执行了15次
        boolean f1 = lookUpInTwoDimensionalArrays(a1, 11);
        System.out.println(TAG+"是否找到了11== "+f1);
        // todo 这个方法执行了 5次
        boolean f2=newLookUpInTwoDimensionalArrays(a1,11);
        System.out.println(TAG+"是否找到了11  newLookUpInTwoDimensionalArrays="+f2);
    }

    /**
     * 数组是递增的 ，从左到右  从上到下,那么数组一定是个正方形的样子
     * @param arr  原始的数组
     * @param num 需要查找的数字
     * @return 是否找到了
     */
    private boolean newLookUpInTwoDimensionalArrays(int[][] arr, int num) {
        if (arr==null||arr.length<1||arr[0].length<1){
            return false;
        }
        int rowTotal=arr.length;// 数组的行数
        int colTolal=arr[0].length;// 数组的列数
        //开始的角标
        int row=0;
        int col=colTolal-1;
        int i=0;
        while (row>=0&&row<rowTotal&&col>=0&&col<colTolal){
            // 是二维数组的 arr[0][arr[0].length-1] 的值，就是最右边的值
            i++;
            if (arr[row][col]==num){
                System.out.println(TAG+"newLookUpInTwoDimensionalArrays 执行了多少次"+i);
                return true;
            }else if (arr[row][col]>num){// 如果找到的值 比目标的值大的话，就把查找的列数减去1
                col--;//列数减去1 ，代表向左移动
            }else {// 比目标的num大的，就把行数加上1，然后往下移动
                row++;
            }
        }

       return false;
    }

    private boolean lookUpInTwoDimensionalArrays(int[][] a, int num) {
        // 至少保证 长度至少为1，且还有元素，
        if (a==null||a.length<1||a[0].length<1){
            return false;
        }
        int b=0;
        for (int i=0;i<a.length;i++){
            for (int j=0;j<a[i].length;j++){
                b++;
                if (num==a[i][j]){
                    // 一共循环多少次：12
                    System.out.println(TAG+"一共循环多少次：" +b);
                    return true;
                }
            }
        }
        return false;
    }


}
