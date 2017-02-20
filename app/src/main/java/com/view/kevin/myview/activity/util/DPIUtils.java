package com.view.kevin.myview.activity.util;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;

public class DPIUtils {

    private static Display defaultDisplay;
    private static float mDensity;
    private static Point displaySize;

    static class SdkVersionGreateThan13 {
        static void getSize(Point outSize) {
            defaultDisplay.getSize(outSize);
        }
    }

    static class SdkVersionLessThan13 {

        static void getSize(Point outSize) {
            outSize.x = defaultDisplay.getWidth();
            outSize.y = defaultDisplay.getHeight();
        }
    }

    public static void setSize(Point outSize) {
        if (Build.VERSION.SDK_INT >= 13) {
            SdkVersionGreateThan13.getSize(outSize);
        } else {
            SdkVersionLessThan13.getSize(outSize);
        }
        displaySize = outSize;

    }

    public static int getHeight() {
        if (displaySize != null)
            return displaySize.y;
        else
            return 0;
    }

    public static int getWidth() {
        if (displaySize != null)
            return displaySize.x;
        else
            return 0;

    }

    public static int percentHeight(float paramFloat) {
        return (int) (displaySize.y * paramFloat);
    }

    public static int percentWidth(float paramFloat) {
        return (int) (displaySize.x * paramFloat);
    }

    public static void setDefaultDisplay(Display paramDisplay) {
        defaultDisplay = paramDisplay;
        displaySize = new Point();
        setSize(displaySize);
    }

    public static void setDensity(float paramFloat) {
        mDensity = paramFloat;
    }

    public static float getDensity() {
        return mDensity;
    }

    public static Display getDisplay() {
        return defaultDisplay;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
