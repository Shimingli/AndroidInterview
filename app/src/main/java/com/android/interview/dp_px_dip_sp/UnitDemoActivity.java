package com.android.interview.dp_px_dip_sp;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.interview.R;
/**
 * author： Created by shiming on 2018/5/14 17:53
 * mailbox：lamshiming@sina.com
 */
//sp: scaled pixels(放大像素). 主要用于字体显示best for textsize。
//dp(dip): device independent pixels(设备独立像素). 不同设备有不同的显示效果,这个和设备硬件有关，一般我们为了支持WVGA、HVGA和QVGA 推荐使用这个，不依赖像素。
// pt：point，是一个标准的长度单位，1pt＝1/72英寸，用于印刷业，非常简单易用； 1pt 大概等于 2.22sp
// px: pixels(像素). 不同设备显示效果相同，一般我们HVGA代表320x480像素，这个用的比较多。
public class UnitDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_demo);
//        Android支持下列所有单位。
//        px（像素）：屏幕上的点。
//        in（英寸）：长度单位。
//        mm（毫米）：长度单位。
//        pt（磅）：1/72英寸。
//        dp（与密度无关的像素）：一种基于屏幕密度的抽象单位。在每英寸160点的显示器上，1dp = 1px。
//        dip：与dp相同，多用于android/ophone示例中。
//        sp（与刻度无关的像素）：与dp类似，但是可以根据用户的字体大小首选项进行缩放。


        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
        float scaledDensity = metric.scaledDensity;  //显示在显示器上的字体缩放因子
        TextView tvMetric = findViewById(R.id.tv_metric);
        tvMetric.setText("屏幕宽度（像素）="+width+"屏幕高度（像素）="+height+"屏幕密度（0.75 / 1.0 / 1.5）="+density+" 屏幕密度DPI（120 / 160 / 240）="+densityDpi+"显示在显示器上的字体缩放因子"+scaledDensity);

    }
}
