package com.ma.text.base;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @Description: Adapter的基类
 */
public abstract class AbsBaseAdapter <T> extends BaseAdapter {
	
	protected List<T> dataList;

	public AbsBaseAdapter(List<T> dataList) {
		if (dataList==null) {
			this.dataList = dataList;
		} else {
			this.dataList = new ArrayList<T>();
		}
		
	}
	public int getCount() {
		return dataList.size();
	}

	public Object getItem(int position) {
		return dataList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	// 这个方法必须要实现,item
	public abstract View getView(int position, View convertView,
			ViewGroup parent);
	
	/**
	 * 该方法为了防止dataList引用断掉
	 * @param dataList
	 */
	public void notifyDataSetChanged(List<T> dataList) {
		this.dataList = dataList;
		notifyDataSetChanged();
	}
	
	/**
	 * 获取一个数据
	 * @param index
	 * @return
	 */
	protected  T getData(int index) {
		
		if (dataList.size()>index) {
			return dataList.get(index);
		}
		return null;
	}
	
	/**
	 * @Description: 获得所有数据
	 * @return
	 */
	protected List<T> getData(){
		return dataList;
	}
	
}
