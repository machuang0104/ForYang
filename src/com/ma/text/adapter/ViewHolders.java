/**
 * Project Name:LightingExpress
 * File Name:ViewHolders.java
 * Package Name:com.express.main.adapter
 * Date:2015-2-6下午4:39:32
 * Copyright (c) 2015, machuang0104@126.com All Rights Reserved.
 *
 */

package com.ma.text.adapter;

import android.util.SparseArray;
import android.view.View;

/**
 * ClassName:ViewHolders <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Date: 2015-2-6 下午4:39:32 <br/>
 * 
 * @author Carlton
 */
public class ViewHolders {
	@SuppressWarnings("unchecked")
	public static <T extends View> T get(View view, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			view.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = view.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}
}
