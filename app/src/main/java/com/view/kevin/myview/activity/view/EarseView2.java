package com.view.kevin.myview.activity.view;

/**
 * Created by Kevin on 2017/3/27.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by anna on 2016/5/14.
 */
public class EarseView2 extends View {
    private static final float RADIUS = 90;

    private Paint mBackgroundPaint;
    private float mCx = -1;
    private float mCy = -1;

    private int mTutorialColor = Color.parseColor("#90000000");

    public EarseView2(Context context) {
        super(context);
        init();
    }

    public EarseView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EarseView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EarseView2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        setLayerType(LAYER_TYPE_HARDWARE, null);
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCx = event.getX();
        mCy = event.getY();
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(mTutorialColor);
        if (mCx >= 0 && mCy >= 0) {
            canvas.drawRect(mCx, mCy, mCx + RADIUS, mCy + RADIUS, mBackgroundPaint);
        }
    }
}
