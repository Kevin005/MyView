package com.view.kevin.myview.activity.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by anna on 2016/5/14.
 */
public class BettryView extends View {
    private Paint mBackgroundPaint;
    private int left = 367;
    private int top = 1426;
    private int right = 367 + 400;
    private int bottom = 1426 + 400;

    public BettryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public BettryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BettryView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        setLayerType(LAYER_TYPE_HARDWARE, null);
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(left, top, right, bottom, mBackgroundPaint);
    }

    public void setType(int type) {
        switch (type) {
            case 1:
                left = 367;
                top = 1426;
                right = 367 + 400;
                bottom = 1426 + 400;
                break;
            case 2:
                left = 700;
                top = 35;
                right = 700 + 400;
                bottom = 35 + 400;
                break;
            case 3:
                left = 19;
                top = 30;
                right = 19 + 400;
                bottom = 30 + 400;
                break;
        }
        invalidate();
    }
}
