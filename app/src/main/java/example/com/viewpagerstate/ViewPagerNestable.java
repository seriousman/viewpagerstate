package example.com.viewpagerstate;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ViewPagerNestable extends ViewPager {

	private boolean mFlgParentSwipe = false;
	private boolean mFlgEnableSwipe = true;

	public ViewPagerNestable(Context context) {
		super(context);
	}
	public ViewPagerNestable(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerNestable);
		mFlgParentSwipe = typedArray.getBoolean(R.styleable.ViewPagerNestable_enable_swipe, false);
		mFlgParentSwipe = typedArray.getBoolean(R.styleable.ViewPagerNestable_enable_parent_swipe, false);
		typedArray.recycle();
	}

	@Override public boolean onInterceptTouchEvent(MotionEvent ev) {
		return mFlgEnableSwipe && super.onInterceptTouchEvent(ev);
	}

	@Override public boolean onTouchEvent(MotionEvent ev) {
		return mFlgEnableSwipe && super.onTouchEvent(ev);
	}

	public void enableSwipe(boolean flgEnable) {
		mFlgEnableSwipe = flgEnable;
	}

	public void forceEnableParentSwipe(boolean flgParentSwipe) {
		mFlgParentSwipe = flgParentSwipe;
	}

	@Override protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
		if (v instanceof ViewPagerNestable && ((ViewPagerNestable) v).mFlgParentSwipe) {
			return false;
		} else {
			return super.canScroll(v, checkV, dx, x, y);
		}
	}
}
