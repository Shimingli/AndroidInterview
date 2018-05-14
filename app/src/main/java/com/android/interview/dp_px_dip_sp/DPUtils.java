package com.android.interview.dp_px_dip_sp;

import com.android.interview.MyApplication;

import static android.provider.ContactsContract.FullNameStyle.CHINESE;

/**
 * author： Created by shiming on 2018/5/14 17:53
 * mailbox：lamshiming@sina.com
 */
//sp: scaled pixels(放大像素). 主要用于字体显示best for textsize。
//dp(dip): device independent pixels(设备独立像素). 不同设备有不同的显示效果,这个和设备硬件有关，一般我们为了支持WVGA、HVGA和QVGA 推荐使用这个，不依赖像素。
// pt：point，是一个标准的长度单位，1pt＝1/72英寸，用于印刷业，非常简单易用； 1pt 大概等于 2.22sp
// px: pixels(像素). 不同设备显示效果相同，一般我们HVGA代表320x480像素，这个用的比较多。
public class DPUtils {
    //// 屏幕密度（0.75 / 1.0 / 1.5）
    private static final float scale = MyApplication.getContext().getResources().getDisplayMetrics().density;
   //显示在显示器上的字体缩放因子
    private static final float scaledDensity = MyApplication.getContext().getResources().getDisplayMetrics().scaledDensity;

    /**
     * dp转成px
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转成dp
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue) {
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转成px
     * @param spValue
     * @param type
     * @return
     */
    public static float sp2px(float spValue, int type) {
        switch (type) {
            case CHINESE:
                return spValue * scaledDensity;
            default:
                return spValue * scaledDensity;
        }
    }
}
