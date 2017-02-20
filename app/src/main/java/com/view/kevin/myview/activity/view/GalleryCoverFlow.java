package com.view.kevin.myview.activity.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.Gallery;

@SuppressLint("ClickableViewAccessibility")
@SuppressWarnings("deprecation")
public class GalleryCoverFlow extends Gallery {
    private static final String TAG = "GalleryCoverFlow";

    public static final int ACTION_DISTANCE_AUTO = Integer.MAX_VALUE;

    private Camera mCamera;
    private float unselectedScale;
    private float unselectedAlpha;
    private float unselectedSaturation;
    private int actionDistance;
    private int maxRotation;

    private int mLastSelectItem = -1;
    private float mLastX = 0;
    private ViewPager mViewPager;

    private Interpolator interpolator = new CycleInterpolator(1.0f);
    private boolean shakeEnable;
    private long startTime;

    public GalleryCoverFlow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mCamera = new Camera();
        actionDistance = ACTION_DISTANCE_AUTO;
        startTime = SystemClock.elapsedRealtime();
    }

    public GalleryCoverFlow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalleryCoverFlow(Context context) {
        this(context, null);
    }

    public void setTouchViewPager(ViewPager pager) {
        mViewPager = pager;
    }

    @Override
    public void setUnselectedAlpha(float unselectedAlpha) {
        super.setUnselectedAlpha(unselectedAlpha);
        this.unselectedAlpha = unselectedAlpha;
    }

    public float getUnselectedSaturation() {
        return unselectedSaturation;
    }

    public void setUnselectedSaturation(float unselectedSaturation) {
        this.unselectedSaturation = unselectedSaturation;
    }

    public float getUnselectedAlpha() {
        return unselectedAlpha;
    }

    public float getUnselectedScale() {
        return unselectedScale;
    }

    public void setUnselectedScale(float unselectedScale) {
        this.unselectedScale = unselectedScale;
    }

    public int getMaxRotation() {
        return maxRotation;
    }

    public void setMaxRotation(int maxRotation) {
        this.maxRotation = maxRotation;
    }

    public int getActionDistance() {
        return actionDistance;
    }

    public void setActionDistance(int actionDistance) {
        this.actionDistance = actionDistance;
    }

    public void setShakeEnable(boolean enable) {
        shakeEnable = enable;
    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            child.invalidate();
        }
        final int childWidth = child.getWidth();
        final int childCenter = child.getLeft() + childWidth / 2;
        final int coverFlowCenter = getWidth() / 2;
        final int actionDistance = this.actionDistance == ACTION_DISTANCE_AUTO
                ? (int) ((getWidth() + child.getWidth()) / 2.0f) : this.actionDistance;
        final float effectsAmount = Math.min(1.0f,
                Math.max(-1.0f, (1.0f / actionDistance) * (childCenter - coverFlowCenter)));
        t.clear();
        t.setTransformationType(Transformation.TYPE_MATRIX);
        if (unselectedAlpha != 1) {
            final float alphaAmount = (unselectedAlpha - 1) * Math.abs(effectsAmount) + 1;
            t.setAlpha(alphaAmount);
            child.setAlpha(alphaAmount);
        }

        final Matrix imageMatrix = t.getMatrix();
        if (maxRotation != 0) {
            final int rotationAngle = (int) (-effectsAmount * maxRotation);
            mCamera.save();
            mCamera.rotateY(rotationAngle);
            mCamera.getMatrix(imageMatrix);
            mCamera.restore();
        }

        if (unselectedScale != 1) {
            final float zoomAmount = (unselectedScale - 1) * Math.abs(effectsAmount) + 1;
            final float translateX = child.getWidth() / 2.0f;
            final float translateY = child.getHeight() / 2.0f;
            imageMatrix.preTranslate(-translateX, -translateY);
            imageMatrix.postScale(zoomAmount, zoomAmount);
            shake(imageMatrix);
            imageMatrix.postTranslate(translateX, translateY);
        }
        return true;
    }

    private void shake(Matrix matrix) {
        float degrees = 0;
        if (shakeEnable) {
            long current = SystemClock.elapsedRealtime();
            degrees = 1.5f * interpolator.getInterpolation((current - startTime) / 300.0f);
        }
        matrix.postRotate(degrees);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mViewPager != null) {
            mViewPager.requestDisallowInterceptTouchEvent(getAdapter().getCount() > 0);
        }
        int action = event.getAction();
        float delta = 0;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = event.getRawX();
                mLastSelectItem = getSelectedItemPosition();
                break;
            case MotionEvent.ACTION_MOVE:
                delta = event.getRawX() - mLastX;
                mLastX = event.getRawX();
                int selectItem = getSelectedItemPosition();
                if (mViewPager != null) {
                    int lastVisiblePos = getLastVisiblePosition();
                    int firstVisiblePos = getFirstVisiblePosition();
                    if (selectItem == firstVisiblePos && mLastSelectItem == firstVisiblePos && delta > 0) {
                        mViewPager.requestDisallowInterceptTouchEvent(false);
                    } else if (selectItem == lastVisiblePos && mLastSelectItem == lastVisiblePos && delta < 0) {
                        mViewPager.requestDisallowInterceptTouchEvent(false);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mViewPager != null) {
            mViewPager.requestDisallowInterceptTouchEvent(getAdapter().getCount() > 0);
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mViewPager != null) {
            mViewPager.requestDisallowInterceptTouchEvent(getAdapter().getCount() > 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
