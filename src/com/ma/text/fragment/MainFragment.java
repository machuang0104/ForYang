package com.ma.text.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.ma.text.R;
import com.ma.text.adapter.ViewHolders;
import com.ma.text.base.BaseFragment;
import com.ma.text.client.db.manager.ContentManager;
import com.ma.text.client.db.manager.TypeManager;
import com.ma.text.common.K;
import com.ma.text.module.EditActivity;
import com.ma.text.vo.db.ContentVo;
import com.ma.text.vo.db.TypeVo;
import com.ma.text.widget.cache.SharedUtil;
import com.ma.text.widget.listview.XListView;
import com.ma.text.widget.listview.XListView.IXListViewListener;
import com.ma.text.widget.menu.SlidingMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MainFragment extends BaseFragment {

	SlidingMenu mMenu;

	public MainFragment() {
	}

	public MainFragment(SlidingMenu menu) {
		this.mMenu = menu;
	}

	View mainView;
	TextView mainTitle;
	XListView recordList;
	ArrayList<ContentVo> dataList = new ArrayList<ContentVo>();
	RecordAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainView = inflater.inflate(R.layout.fragment_main, container, false);
		init();
		return mainView;
	}

	private int pageNum = 1;

	public void onTypeChange(String typeTitle, int typeId) {
		mainTitle.setText(typeTitle);
		this.typeId = typeId;
		refreshList();
	}

	private void refreshList() {
		dataList.clear();
		ArrayList<ContentVo> temp = ContentManager.getInstance().findByTypeId(
				typeId);
		dataList.addAll(temp);
		mAdapter.notifyDataSetChanged();
	}

	private void init() {
		mainView.findViewById(R.id.img_menu).setOnClickListener(mListener);
		mainTitle = (TextView) mainView.findViewById(R.id.main_title);
		mainView.findViewById(R.id.img_menu_right)
				.setOnClickListener(mListener);
		recordList = (XListView) mainView.findViewById(R.id.record_list);
		recordList.setPullLoadEnable(false, false);
		recordList.setPullRefreshEnable(false);
		mAdapter = new RecordAdapter();
		recordList.setAdapter(mAdapter);
		// recordList.setXListViewListener(pullListener);
		recordList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.putExtra(K.intent.ADD_NEW, false);
				intent.putExtra("data", dataList.get(position - 1));
				intent.setClass(getActivity(), EditActivity.class);
				startActivity(intent);
			}
		});
		iniInfo();
	}

	int typeId = 0;

	/**
	 * 初始化，无数据，false
	 */
	private boolean iniInfo() {
		ArrayList<TypeVo> l = TypeManager.getInstance().findAll();
		if (l == null || l.size() == 0) {
			return false;
		}
		TypeVo t = l.get(0);
		onTypeChange(t.getTitle(), t.getType_id());
		return true;
	}

	IXListViewListener pullListener = new IXListViewListener() {
		@Override
		public void onRefresh() {
			pageNum = 1;
			dataList.clear();
			// dataList.addAll(temp);
			// refreshListState(false);
			// refreshListState(true);
			mAdapter.notifyDataSetChanged();
			recordList.stopRefresh();
		}

		@Override
		public void onLoadMore() {
			pageNum++;
			// dataList.addAll(temp);
			// if (temp.size() != 10) {
			// ToastUtils.show(R.string.no_more_data);
			// refreshListState(false);
			// } else
			// refreshListState(true);
			mAdapter.notifyDataSetChanged();
			recordList.stopLoadMore();
		}
	};

	private void refreshListState(boolean loadEnable) {
		recordList.setPullLoadEnable(loadEnable, true);
	}

	public void toggleMenu(View view) {
		if (mMenu != null)
			mMenu.toggleMenu();
	}

	OnClickListener mListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.img_menu:
				if (mMenu != null)
					mMenu.toggleMenu();
				break;
			case R.id.img_menu_right: {
				Intent intent = new Intent();
				intent.putExtra("typeid", typeId);
				intent.setClass(getActivity(), EditActivity.class);
				startActivity(intent);
				break;
			}
			default:
				break;
			}

		}
	};

	class RecordAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public Object getItem(int position) {
			return dataList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null)
				convertView = LayoutInflater.from(getActivity()).inflate(
						R.layout.item_list, parent, false);
			TextView title = ViewHolders.get(convertView, R.id.record_title);
			TextView time = ViewHolders.get(convertView, R.id.record_time);
			ContentVo r = dataList.get(position);
			title.setText(r.getTitle());
			time.setText(getAlarmTime(r.getCreatetime()));
			return convertView;
		}
	}

	public String getAlarmTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(time));
	}

	boolean isFirst = true;

	@Override
	public void onResume() {
		super.onResume();
		if (isFirst) {
			isFirst = false;
		} else {
			if (SharedUtil.getInt("have") == -2) {
				refreshList();
				SharedUtil.clear("have");
			}
		}

	}

}