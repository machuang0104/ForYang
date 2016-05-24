package com.ma.text.widget.swipe.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ma.text.widget.swipe.DeleteListener;
import com.ma.text.widget.swipe.Mode;
import com.ma.text.widget.swipe.SwipeLayout;
import com.ma.text.widget.swipe.implement.SwipeItemMangerImpl;
import com.ma.text.widget.swipe.interfaces.SwipeAdapterInterface;
import com.ma.text.widget.swipe.interfaces.SwipeItemMangerInterface;

public abstract class BaseSwipeAdapter extends BaseAdapter implements
		SwipeItemMangerInterface, SwipeAdapterInterface {

	protected SwipeItemMangerImpl mItemManger = new SwipeItemMangerImpl(this);
	DeleteListener deleteListener = null;

	public void setDeleteListener(DeleteListener listener) {
		if (listener != null)
			this.deleteListener = listener;
	}

	protected void onItemDelete(int position) {
		if (deleteListener != null)
			deleteListener.onDelete(position);
	}

	/**
	 * return the {@link com.daimajia.swipe.SwipeLayout} resource id, int the
	 * view item.
	 * 
	 * @param position
	 * @return
	 */
	public abstract int getSwipeLayoutResourceId(int position);

	/**
	 * generate a new view item. Never bind SwipeListener or fill values here,
	 * every item has a chance to fill value or bind listeners in fillValues. to
	 * fill it in {@code fillValues} method.
	 * 
	 * @param position
	 * @param parent
	 * @return
	 */
	public abstract View generateView(int position, ViewGroup parent);

	/**
	 * fill values or bind listeners to the view.
	 * 
	 * @param position
	 * @param convertView
	 */
	public abstract void fillValues(int position, View convertView);

	@Override
	public final View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			v = generateView(position, parent);
			mItemManger.initialize(v, position);
		} else {
			mItemManger.updateConvertView(v, position);
		}
		fillValues(position, v);
		return v;
	}

	@Override
	public void openItem(int position) {
		mItemManger.openItem(position);
	}

	@Override
	public void closeItem(int position) {
		mItemManger.closeItem(position);
	}

	@Override
	public void closeAllExcept(SwipeLayout layout) {
		mItemManger.closeAllExcept(layout);
	}

	@Override
	public List<Integer> getOpenItems() {
		return mItemManger.getOpenItems();
	}

	@Override
	public List<SwipeLayout> getOpenLayouts() {
		return mItemManger.getOpenLayouts();
	}

	@Override
	public void removeShownLayouts(SwipeLayout layout) {
		mItemManger.removeShownLayouts(layout);
	}

	@Override
	public boolean isOpen(int position) {
		return mItemManger.isOpen(position);
	}

	@Override
	public Mode getMode() {
		return mItemManger.getMode();
	}

	@Override
	public void setMode(Mode mode) {
		mItemManger.setMode(mode);
	}

}