package com.view.kevin.myview.activity.view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;

import com.view.kevin.myview.R;

public class ArcLayout extends ViewGroup {
	private int mChildSize;
	private float mFromDegrees;
	private float mToDegrees;
	private int mRadius;
	private int mCenterLayoutId;
	int mPadding = 5;

	private View mCenterView;
	private float mCenterHeightRatio = 0.525f;
	private boolean mExpanded = true;

	private OnItemClickListener mOnItemClickListener;
	private OnMenuStateChangedListener mOnMenuStateChangedListener;

	public ArcLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		if (attrs != null) {
			TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ArcLayout);
			mChildSize = Math.max(ta.getDimensionPixelSize(R.styleable.ArcLayout_childSize, 0), 0);
			mFromDegrees = ta.getFloat(R.styleable.ArcLayout_fromDegrees, 270.0f);
			mToDegrees = ta.getFloat(R.styleable.ArcLayout_toDegrees, 360.0f);
			mRadius = ta.getDimensionPixelSize(R.styleable.ArcLayout_minRadius, 110);
			mCenterLayoutId = ta.getResourceId(R.styleable.ArcLayout_centerLayout, 0);
			ta.recycle();
		}
		setCenterLayout(mCenterLayoutId);
		getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				switchState(false, false);
				getViewTreeObserver().removeOnGlobalLayoutListener(this);
				int childCount = getChildCount();
				for (int i = 0; i < childCount; i++) {
					final View child = getChildAt(i);
					final int position = i;
					child.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							if (mCenterView != null && child == mCenterView) {
								switchState(true);
							}
							if (child != mCenterView) {
								bindClickItemAnimation(child, 300);
								bindUnClickItemAnimation(position);
								postInvalidate();
							}
							if (mOnItemClickListener != null) {
								mOnItemClickListener.onItemClickListener(v, position);
							}
						}
					});
				}
			}
		});
	}

	public ArcLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ArcLayout(Context context) {
		this(context, null);
	}

	public void addItem(View item) {
		addView(item, 0);
	}

	public void setCenterLayout(int layoutId) {
		if (mCenterView != null && indexOfChild(mCenterView) != -1) {
			removeView(mCenterView);
			mCenterView = null;
		}
		if (layoutId != 0) {
			mCenterLayoutId = layoutId;
			mCenterView = LayoutInflater.from(getContext()).inflate(mCenterLayoutId, null, false);
			addView(mCenterView, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public void switchState(boolean showAnimator) {
		switchState(showAnimator, true);
	}

	private void switchState(boolean showAnimator, boolean callback) {
		if (mCenterView != null) {
			mCenterView.setEnabled(false);
		}
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View child = getChildAt(i);
			if (child != mCenterView) {
				bindChildTranslation(child, mExpanded, i, 300, showAnimator);
			}
		}
		mExpanded = !mExpanded;
		if (mOnMenuStateChangedListener != null && callback) {
			mOnMenuStateChangedListener.onMenuStateChanged(mExpanded);
		}
	}

	public void setOnItemClickListener(OnItemClickListener l) {
		mOnItemClickListener = l;
	}

	public void setOnMenuStateChangedListener(OnMenuStateChangedListener l) {
		mOnMenuStateChangedListener = l;
	}

	public View getCenterView() {
		return mCenterView;
	}

	public boolean isExpanded() {
		return mExpanded;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View child = getChildAt(i);
			if (child == mCenterView) {
				measureChild(child, widthMeasureSpec, heightMeasureSpec);
				continue;
			}
			child.measure(MeasureSpec.makeMeasureSpec(mChildSize, MeasureSpec.EXACTLY),
					MeasureSpec.makeMeasureSpec(mChildSize, MeasureSpec.EXACTLY));
		}
		int width = mRadius * 2 + mChildSize + getPaddingLeft() + getPaddingRight() + mPadding * 2;
		int height = mRadius + getPaddingBottom() + getPaddingTop()
				+ (mCenterView != null ? mCenterView.getMeasuredHeight() : mChildSize) + mPadding;
		setMeasuredDimension(width, height);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int centerX = getWidth() / 2;
		int childCount = getChildCount();
		int itemCount = mCenterView == null ? childCount - 1 : childCount - 2;
		float perDegrees = (mToDegrees - mFromDegrees) / itemCount;
		float degrees = mFromDegrees;
		for (int i = 0; i < childCount; i++) {
			final View child = getChildAt(i);
			if (child == mCenterView) {
				child.layout(centerX - child.getMeasuredWidth() / 2, getHeight() - child.getMeasuredHeight(),
						centerX + child.getMeasuredWidth() / 2, getHeight());
			} else {
				int centerY = mCenterView == null ? getHeight() - child.getMeasuredHeight() / 2
						: (int) (getHeight() - mCenterView.getMeasuredHeight() * mCenterHeightRatio);
				Rect rect = computeChildFrame(centerX, centerY, mRadius, degrees, mChildSize);
				child.layout(rect.left, rect.top, rect.right, rect.bottom);
				degrees += perDegrees;
			}
		}
	}

	private static Rect computeChildFrame(final int centerX, final int centerY, final int radius, final float degrees,
			final int size) {

		final double childCenterX = centerX + radius * Math.cos(Math.toRadians(degrees));
		final double childCenterY = centerY + radius * Math.sin(Math.toRadians(degrees));

		return new Rect((int) (childCenterX - size / 2), (int) (childCenterY - size / 2),
				(int) (childCenterX + size / 2), (int) (childCenterY + size / 2));
	}

	private void bindChildTranslation(final View child, boolean expanded, final int index, final long duration,
			boolean showAnimator) {
		final int centerX = getWidth() / 2;
		int centerY = mCenterView == null ? getHeight() - child.getMeasuredHeight() / 2
				: (int) (getHeight() - mCenterView.getMeasuredHeight() * mCenterHeightRatio);
		final int childCount = getChildCount();
		final int itemCount = mCenterView == null ? childCount - 1 : childCount - 2;
		float perDegrees = (mToDegrees - mFromDegrees) / itemCount;
		Rect frame = computeChildFrame(centerX, centerY, expanded ? 0 : mRadius, mFromDegrees + index * perDegrees,
				mChildSize);
		Rect toFrame = computeChildFrame(centerX, centerY, !expanded ? 0 : mRadius, mFromDegrees + index * perDegrees,
				mChildSize);
		float toXDelta = frame.left - toFrame.left;
		float toYDelta = frame.top - toFrame.top;
		if (showAnimator) {
			Interpolator interpolator = expanded ? new AccelerateInterpolator() : new OvershootInterpolator(1.5f);
			final long startOffset = computeStartOffset(itemCount + 1, expanded, index, 0.1f, duration, interpolator);
			Animator animator = expanded
					? createShrinkAnimator(child, 0, toXDelta, 0, toYDelta, startOffset, duration, interpolator)
					: createExpandAnimator(child, 0, toXDelta, 0, toYDelta, startOffset, duration, interpolator);
			final boolean isLast = getTransformedIndex(expanded, itemCount + 1, index) == (!expanded ? itemCount : 0);
			if (isLast) {
				animator.addListener(mAnimatorListener);
			}
			animator.start();
		} else {
			if (expanded) {
				child.setTranslationX(toXDelta);
				child.setTranslationY(toYDelta);
			} else {
				child.setTranslationX(0);
				child.setTranslationY(0);
			}
			if (mCenterView != null) {
				mCenterView.setEnabled(true);
			}
		}
	}

	private Animator createExpandAnimator(final View view, float fromXDelta, final float toXDelta, float fromYDelta,
			final float toYDelta, long startOffset, long duration, Interpolator interpolator) {
		ValueAnimator va = ValueAnimator.ofFloat(-1, 0);
		va.setStartDelay(startOffset);
		va.setDuration(duration);
		va.setInterpolator(interpolator);
		va.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value = (Float) animation.getAnimatedValue();
				view.setTranslationX(toXDelta * value);
				view.setTranslationY(toYDelta * value);
				view.setRotation(720 * value);
			}
		});
		return va;
	}

	private Animator createShrinkAnimator(final View view, float fromXDelta, final float toXDelta, float fromYDelta,
			final float toYDelta, long startOffset, long duration, Interpolator interpolator) {

		final long preDuration = duration / 2;
		AnimatorSet set = new AnimatorSet();
		ValueAnimator va1 = ValueAnimator.ofFloat(0, 2);
		va1.setStartDelay(startOffset);
		va1.setDuration(duration);
		va1.setInterpolator(new LinearInterpolator());
		va1.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value = (Float) animation.getAnimatedValue();
				view.setRotation(value * 360);
			}
		});

		ValueAnimator va2 = ValueAnimator.ofFloat(0, 1);
		va2.setStartDelay(startOffset + preDuration);
		va2.setDuration(duration - preDuration);
		va2.setInterpolator(interpolator);
		va2.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float value = (Float) animation.getAnimatedValue();
				view.setTranslationX(toXDelta * value);
				view.setTranslationY(toYDelta * value);
			}
		});
		set.play(va1).with(va2);
		return set;
	}

	private AnimatorListener mAnimatorListener = new AnimatorListener() {

		@Override
		public void onAnimationStart(Animator animation) {
			if (mCenterView != null) {
				mCenterView.setEnabled(false);
			}
		}

		@Override
		public void onAnimationRepeat(Animator animation) {

		}

		@Override
		public void onAnimationEnd(Animator animation) {
			if (mCenterView != null) {
				mCenterView.setEnabled(true);
			}
		}

		@Override
		public void onAnimationCancel(Animator animation) {

		}
	};

	private static long computeStartOffset(final int childCount, final boolean expanded, final int index,
			final float delayPercent, final long duration, Interpolator interpolator) {
		final float delay = delayPercent * duration;
		final long viewDelay = (long) (getTransformedIndex(expanded, childCount, index) * delay);
		final float totalDelay = delay * childCount;
		float normalizedDelay = viewDelay / totalDelay;
		normalizedDelay = interpolator.getInterpolation(normalizedDelay);

		return (long) (normalizedDelay * totalDelay);
	}

	private static int getTransformedIndex(final boolean expanded, final int count, final int index) {
		if (expanded) {
			return count - 1 - index;
		}
		return index;
	}

	private void bindClickItemAnimation(final View child, final long duration) {
		Animation animation = createItemDisapperAnimation(duration, true);
		child.setAnimation(animation);
		mExpanded = !mExpanded;
		if (mOnMenuStateChangedListener != null) {
			mOnMenuStateChangedListener.onMenuStateChanged(mExpanded);
		}
		postDelayed(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < getChildCount(); i++) {
					final View child = getChildAt(i);
					child.clearAnimation();
					if (child != mCenterView)
						bindChildTranslation(child, !mExpanded, i, 300, false);
				}
			}
		}, duration);
	}

	private void bindUnClickItemAnimation(int clickPosition) {
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			if (i != clickPosition) {
				View child = getChildAt(i);
				if (child != mCenterView) {
					Animation animation = createItemDisapperAnimation(250, false);
					child.setAnimation(animation);
				}
			}
		}
	}

	private static Animation createItemDisapperAnimation(final long duration, final boolean isClicked) {
		AnimationSet animationSet = new AnimationSet(true);
		animationSet.addAnimation(new ScaleAnimation(1.0f, isClicked ? 1.2f : 0.0f, 1.0f, isClicked ? 1.2f : 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
		animationSet.addAnimation(new AlphaAnimation(1.0f, 0.0f));

		animationSet.setDuration(duration);
		animationSet.setInterpolator(new DecelerateInterpolator());
		animationSet.setFillEnabled(true);
		animationSet.setFillAfter(true);

		return animationSet;
	}

	public interface OnItemClickListener {
		void onItemClickListener(View view, int position);
	}

	public interface OnMenuStateChangedListener {
		void onMenuStateChanged(boolean expanded);
	}
}
