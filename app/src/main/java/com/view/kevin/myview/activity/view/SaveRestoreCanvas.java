package com.view.kevin.myview.activity.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/2/13.
 */

public class SaveRestoreCanvas extends View {
    private Paint mPaint;
    public SaveRestoreCanvas(Context context) {
        super(context);
        init();
    }

    public SaveRestoreCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SaveRestoreCanvas(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPaint.setColor(Color.RED);// 设置画笔颜色为红色
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();  //保存当前canvas的状态

        canvas.translate(100, 100);
        canvas.drawCircle(50, 50, 50, mPaint);

        canvas.restore();  //恢复保存的Canvas的状态
        canvas.translate(100, 100);
        canvas.drawCircle(30, 30, 30, mPaint);
    }
}
