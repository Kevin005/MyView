package com.view.kevin.myview.activity.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Shader;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ProgressBar;

/**
 * Created by Kevin on 2017/6/19.
 */

public class CustomProgressBar extends ProgressBar {
    @Override
    public Drawable getProgressDrawable() {
        return super.getProgressDrawable();
    }

    public CustomProgressBar(Context context) {
        super(context);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle,0);

    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyle, int styleRes) {

        super(context, attrs, defStyle);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec,
                                          int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public final int roundCorners = 15;
    Shape getDrawableShape() {
        final float[] roundedCorners = new float[] { 0, 0, 0, 0, 0, 0, 0, 0 };
        for(int i=0;i<roundedCorners.length;i++){
            roundedCorners[i] = dp2px(getContext(), roundCorners);
        }
        return new RoundRectShape(roundedCorners, null, null);
    }

    /**dpתpx*/
    public static float dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dp * scale;
    }
}
