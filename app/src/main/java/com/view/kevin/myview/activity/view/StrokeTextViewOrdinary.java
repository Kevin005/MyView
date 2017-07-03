package com.view.kevin.myview.activity.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.view.kevin.myview.R;

import java.lang.reflect.Field;

/**
 * Created by Kevin on 2017/6/6.
 */

public class StrokeTextViewOrdinary extends AppCompatTextView {
    private TextPaint mTextPaint;

    private int mInnerColor;
    private int mOuterColor;
    private boolean isDrawInner;
    private boolean isDrawOuter;
    private float mWidthStroke;

    public StrokeTextViewOrdinary(Context context, int outerColor, int innnerColor) {
        super(context);
        mTextPaint = this.getPaint();
        mTextPaint.setAntiAlias(true);
        this.mInnerColor = innnerColor;
        this.mOuterColor = outerColor;
    }

    public StrokeTextViewOrdinary(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTextPaint = this.getPaint();
        mTextPaint.setAntiAlias(true);
        //获取自定义的XML属性名称
        TypedArray typeArry = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextOrdinary);
        //获取对应的属性值
        this.mInnerColor = typeArry.getColor(R.styleable.StrokeTextOrdinary_ordinaryInnerColor, 0xffffff);
        this.mOuterColor = typeArry.getColor(R.styleable.StrokeTextOrdinary_ordinaryOuterColor, 0xffffff);
        isDrawInner = typeArry.getBoolean(R.styleable.StrokeTextOrdinary_ordinaryIsDrawInner, false);
        isDrawOuter = typeArry.getBoolean(R.styleable.StrokeTextOrdinary_ordinaryIsDrawOuter, false);
        mWidthStroke = typeArry.getFloat(R.styleable.StrokeTextOrdinary_ordinaryStrokeWidth, 5);
    }

    public StrokeTextViewOrdinary(Context context, AttributeSet attrs, int defStyle, int outerColor, int innnerColor) {
        super(context, attrs, defStyle);
        mTextPaint = this.getPaint();
        mTextPaint.setAntiAlias(true);
        this.mInnerColor = innnerColor;
        this.mOuterColor = outerColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isDrawOuter) {
            // 描外层
            setTextColorUseReflection(mOuterColor);
            mTextPaint.setStrokeWidth(mWidthStroke); // 描边宽度
            mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE); // 描边种类
            mTextPaint.setFakeBoldText(true); // 外层text采用粗体
            mTextPaint.setShadowLayer(0, 0, 0, 0); // 字体的阴影效果
            super.onDraw(canvas);
        }
        if (isDrawInner) {
            // 描内层，恢复原先的画笔
            setTextColorUseReflection(mInnerColor);
            mTextPaint.setStrokeWidth(0);
            mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mTextPaint.setFakeBoldText(false);
            mTextPaint.setShadowLayer(0, 0, 0, 0);
        }

        super.onDraw(canvas);
    }

    /**
     * 使用反射的方法进行字体颜色的设置
     *
     * @param color
     */
    private void setTextColorUseReflection(int color) {
        Field textColorField;
        try {
            textColorField = TextView.class.getDeclaredField("mCurTextColor");
            textColorField.setAccessible(true);
            textColorField.set(this, color);
            textColorField.setAccessible(false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mTextPaint.setColor(color);
    }
}
