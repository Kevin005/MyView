package com.view.kevin.myview.activity.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.view.kevin.myview.R;
import com.view.kevin.myview.activity.util.MeasureUtil;

/**
 * Created by Administrator on 2017/2/13.
 */

public class DrawImage extends View {
    private Paint mPaint;// 画笔
    private Bitmap bitmapDis;// 位图
    private int x, y;// 位图绘制时左上角的起点坐标
    private int screenW, screenH;// 屏幕尺寸

    public DrawImage(Context context) {
        this(context, null);
    }

    public DrawImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化画笔
        initPaint();
        // 初始化资源
        initRes(context);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        // 实例化画笔
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    /**
     * 初始化资源
     */
    private void initRes(Context context) {
        // 获取位图
        bitmapDis = BitmapFactory.decodeResource(context.getResources(), R.mipmap.eye);

        int height = MeasureUtil.getScreenHeight(context);
        int width = MeasureUtil.getScreenWidth(context);
        // 获取屏幕尺寸
        screenW = width;
        screenH = height;

        /*
         * 计算位图绘制时左上角的坐标使其位于屏幕中心
         * 屏幕坐标x轴向左偏移位图一半的宽度
         * 屏幕坐标y轴向上偏移位图一半的高度
         */
        x = screenW / 2 - bitmapDis.getWidth() / 2;
        y = screenH / 2 - bitmapDis.getHeight() / 2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制美女图
        canvas.drawBitmap(bitmapDis, x, y, mPaint);
    }
}
