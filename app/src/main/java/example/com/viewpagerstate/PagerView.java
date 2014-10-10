package example.com.viewpagerstate;

import android.os.Parcelable;
import android.view.View;

public abstract class PagerView {

	protected View mViewRoot;

	public PagerView(View root) {
		mViewRoot = root;
	}
	public View getRoot() {
		return mViewRoot;
	}
	public Parcelable saveState() {
		return null;
	}
	public void restoreState(Parcelable state) {}
	public void destroyView() {}
}