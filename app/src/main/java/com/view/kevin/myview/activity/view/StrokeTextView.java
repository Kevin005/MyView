package com.view.kevin.myview.activity.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.view.kevin.myview.R;

import java.lang.reflect.Field;

/**
 * Created by Kevin on 2017/2/13.
 */

public class StrokeTextView extends TextView {
    private TextPaint mTextPaint;

    private int mInnerColor;
    private int mOuterColor;
    public StrokeTextView(Context context, int outerColor, int innnerColor) {
        super(context);
        mTextPaint = this.getPaint();
        this.mInnerColor = innnerColor;
        this.mOuterColor = outerColor;
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTextPaint = this.getPaint();
        //获取自定义的XML属性名称
        TypedArray typeArry = context.obtainStyledAttributes(attrs, R.styleable.StrokeTextFactoryView);
        //获取对应的属性值
        this.mInnerColor = typeArry.getColor(R.styleable.StrokeTextFactoryView_innnerColor, 0xffffff);
        this.mOuterColor = typeArry.getColor(R.styleable.StrokeTextFactoryView_outerColor, 0xffffff);
        initText(attrs, context);
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyle, int outerColor, int innnerColor) {
        super(context, attrs, defStyle);
        mTextPaint = this.getPaint();
        this.mInnerColor = innnerColor;
        this.mOuterColor = outerColor;
    }

    private boolean mDrawSideLine = true; // 默认采用描边

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDrawSideLine) {
            // 描外层
            setTextColorUseReflection(mOuterColor);
            mTextPaint.setStrokeWidth(16); // 描边宽度
            mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE); // 描边种类
            mTextPaint.setFakeBoldText(true); // 外层text采用粗体
            mTextPaint.setShadowLayer(0, 0, 0, 0); // 字体的阴影效果
            super.onDraw(canvas);

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

    private void initText(AttributeSet attrs, Context context) {
        if (attrs != null) {
            Typeface mtf = Typeface.createFromAsset(context.getAssets(), "fonts/power_black.otf");
            setTypeface(mtf);
        }
    }
}
