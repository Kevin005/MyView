package com.xsfuture.xsfuture2.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

import com.view.kevin.myview.R;
import com.view.kevin.myview.activity.util.DPIUtils;

public class LineEditText extends EditText {
    private Paint mPaint;

    /**
     * @param context
     * @param attrs
     */
    public LineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(DPIUtils.dip2px(context, 1));
        mPaint.setColor(getResources().getColor(R.color.red));
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画底线
        canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1, this.getHeight() - 1, mPaint);
    }
}
