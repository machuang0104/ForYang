package com.ma.text.module;

import java.util.ArrayList;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.ma.text.R;
import com.ma.text.adapter.ViewHolders;
import com.ma.text.anno.view.InjectLayout;
import com.ma.text.anno.view.InjectView;
import com.ma.text.base.BaseActivity;
import com.ma.text.compoment.cache.UserCache;
import com.ma.text.compoment.dialoginput.DialogInputClient;
import com.ma.text.compoment.dialoginput.InputListener;
import com.ma.text.db.client.manager.TypeManager;
import com.ma.text.fragment.MainFragment;
import com.ma.text.httpclient.action.Task;
import com.ma.text.httpclient.http.MCallBack;
import com.ma.text.httpclient.http.Response;
import com.ma.text.module.weather.vo.WeatherDataVo;
import com.ma.text.module.weather.vo.WeatherForecastVo;
import com.ma.text.module.weather.vo.WeatherStatusVo;
import com.ma.text.tools.tip.ToastUtil;
import com.ma.text.view.menu.SlidingMenu;
import com.ma.text.view.menu.SlidingMenu.OpenStatusListener;
import com.ma.text.vo.db.TypeVo;

@InjectLayout(id = R.layout.activity_main)
public class MainActivity extends BaseActivity {
	@InjectView(id = R.id.sliding_menu)
	private SlidingMenu mMenu;
	@InjectView(id = R.id.typeList)
	ListView typeList;
	@InjectView(id = R.id.menu_weather_city)
	TextView city;
	@InjectView(id = R.id.menu_weather)
	TextView weather;

	ArrayList<TypeVo> dataList = new ArrayList<TypeVo>();

	TypeAdapter mAdappter = new TypeAdapter();

	@Override
	protected void afterOnCreate() {
		mainFragment = new MainFragment(mMenu);
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_root, mainFragment).commit();
		findViewById(R.id.add_type).setOnClickListener(menuListener);
		findViewById(R.id.set).setOnClickListener(menuListener);
		typeList.setAdapter(mAdappter);
		mMenu.setStatusListener(new OpenStatusListener() {
			@Override
			public void open() {
				if (firstOpen) {
					initMenu();
				}
				firstOpen = false;
			}

			@Override
			public void close() {
			}
		});
		typeList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mMenu.closeMenu();
				mainFragment.onTypeChange(dataList.get(position).getTitle(),
						dataList.get(position).getType_id());
			}
		});
		getWeather();
	}

	private void getWeather() {
		String cityName = "苏州";
		if (TextUtils.isEmpty(UserCache.getCity())) {
			cityName = UserCache.getCity();
		}
		Task.getInstance().getWeather(cityName,
				new MCallBack<WeatherStatusVo>() {

					@Override
					public void onSuccess(Response<WeatherStatusVo> res) {
						if (res.getData().getDesc().equals("OK")) {
							try {
								showWeather(res.getData().getData());
							} catch (Exception e) {
								ToastUtil.show(R.string.weather_fail);
							}
						} else {
							ToastUtil.show(R.string.weather_fail);
						}
					}

					@Override
					public void onFailure(HttpException e, String msg) {
						ToastUtil.show(R.string.weather_fail);
					}
				});
	}

	private void showWeather(WeatherDataVo wea) {
		ArrayList<WeatherForecastVo> list = wea.getForecast();
		StringBuffer w = new StringBuffer("\n");
		if (list != null && list.size() > 0) {
			WeatherForecastVo fo = list.get(0);
			w.append(fo.getHigh()).append(" - ").append(fo.getLow())
					.append("\n").append(fo.getFengxiang()).append(" - ")
					.append(fo.getFengli()).append("\n").append(fo.getType())
					.append("\n");
			city.setText(wea.getCity() + " - " + fo.getDate());
		}
		w.append(wea.getGanmao());
		weather.setText(w.toString());
	}

	private boolean firstOpen = true;

	MainFragment mainFragment;

	private void initMenu() {
		dataList.clear();
		dataList.addAll(TypeManager.getInstance().findAll());
		mAdappter.notifyDataSetChanged();
	}

	OnClickListener menuListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (!mMenu.getMenuState())
				return;
			switch (v.getId()) {
			case R.id.add_type:
				DialogInputClient.show(MainActivity.this, new InputListener() {
					@Override
					public void inputDone(String input) {
						if (!TextUtils.isEmpty(input)) {
							TypeVo type = new TypeVo();
							type.setCreatTime(System.currentTimeMillis());
							type.setTitle(input);
							TypeManager.getInstance().insert(type);
							dataList.clear();
							dataList.addAll(TypeManager.getInstance().findAll());
							mAdappter.notifyDataSetChanged();
						}
					}
				});
				break;
			case R.id.set:
				ToastUtil.show(R.string.tip_building);
				break;
			default:
				break;
			}
		}
	};

	public void toggleMenu(View view) {
		mMenu.toggleMenu();
	}

	private long firstTime;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 点击两次退出应用程序处理逻辑
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			// if (mainFragment.onBack())
			// return true;
			if (mMenu.getMenuState()) {
				mMenu.closeMenu();
				return true;
			}
			if (System.currentTimeMillis() - firstTime < 2500) {
				MainActivity.this.finish();
				System.exit(0);
			} else {
				firstTime = System.currentTimeMillis();
				ToastUtil.show(R.string.tip_exit);
			}
		}
		return true;
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		mainFragment.setUserVisibleHint(hasFocus);
	}

	class TypeAdapter extends BaseAdapter {

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
		public View getView(int position, View v, ViewGroup parent) {
			if (v == null) {
				v = LayoutInflater.from(MainActivity.this).inflate(
						R.layout.item_type, parent, false);
			}
			TextView typeTxt = ViewHolders.get(v, R.id.type_title);
			typeTxt.setText(dataList.get(position).getTitle());
			return v;
		}

	}

}