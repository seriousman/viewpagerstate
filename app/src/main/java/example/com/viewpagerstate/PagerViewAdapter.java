package example.com.viewpagerstate;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import org.parceler.Parcel;
import org.parceler.Parcels;

public abstract class PagerViewAdapter extends PagerAdapter {

	private SparseArray<PagerView> mActiveViews = new SparseArray<>();

	private State mState = new State();
	@Parcel public static class State {

		SparseArray<Parcelable> mStateArray = new SparseArray<>();
	}

	abstract protected PagerView instantiateView(ViewGroup container, int position);
	abstract public int getCount();

	@Override final public Object instantiateItem(ViewGroup container, int position) {
		PagerView pagerView = instantiateView(container, position);
		Parcelable state = mState.mStateArray.get(position);
		if (state != null) {
			pagerView.restoreState(mState.mStateArray.get(position));
		}
		mActiveViews.put(position, pagerView);
		return pagerView;
	}

	@Override final public void destroyItem(ViewGroup container, int position, Object object) {
		PagerView pagerView = (PagerView) object;
		mState.mStateArray.put(position, pagerView.saveState());
		mActiveViews.remove(position);
		container.removeView(pagerView.getRoot());
		pagerView.destroyView();
	}

	@Override final public boolean isViewFromObject(View view, Object object) {
		return object instanceof PagerView
				&& ((PagerView) object).getRoot() == view;
	}

	/**
	 * Returns PagerView item if is active, null otherwise
	 * @param position
	 * @return PagerView if it is active, null otherwise
	 */
	public PagerView getActiveItem(int position) {
		return mActiveViews.get(position);
	}

	@Override public Parcelable saveState() {
		for (int i = 0; i < mActiveViews.size(); ++i) {
			int position = mActiveViews.keyAt(i);
			PagerView pagerView = mActiveViews.get(position);
			mState.mStateArray.put(position, pagerView.saveState());
		}
		return Parcels.wrap(mState);
	}

	@Override public void restoreState(Parcelable savedState, ClassLoader loader) {
		State state = Parcels.unwrap(savedState);
		if (state != null) mState = state;
	}
}
