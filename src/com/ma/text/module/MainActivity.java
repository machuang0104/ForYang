package com.ma.text.module;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import android.util.Log;
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

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.lidroid.xutils.exception.HttpException;
import com.ma.text.R;
import com.ma.text.adapter.ViewHolders;
import com.ma.text.base.BaseActivity;
import com.ma.text.client.db.manager.TypeManager;
import com.ma.text.client.http.action.Task;
import com.ma.text.fragment.MainFragment;
import com.ma.text.module.weather.vo.WeatherDataVo;
import com.ma.text.module.weather.vo.WeatherForecastVo;
import com.ma.text.module.weather.vo.WeatherStatusVo;
import com.ma.text.tools.tip.ToastUtil;
import com.ma.text.vo.db.TypeVo;
import com.ma.text.widget.annoview.InjectLayout;
import com.ma.text.widget.annoview.InjectView;
import com.ma.text.widget.cache.UserCache;
import com.ma.text.widget.dialoginput.DialogInputClient;
import com.ma.text.widget.dialoginput.InputListener;
import com.ma.text.widget.http.MCallBack;
import com.ma.text.widget.http.Response;
import com.ma.text.widget.menu.SlidingMenu;
import com.ma.text.widget.menu.SlidingMenu.OpenStatusListener;

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
				onMenuClicked(position);
			}
		});
		startLocation();
	}

	private void onMenuClicked(int position) {
		int size = dataList.size();
		if (position >= size || size == 0)
			return;
		if (position != -1) {
			mainFragment.onTypeChange(dataList.get(position).getTitle(),
					dataList.get(position).getType_id());
		} else {
			mainFragment.onTypeChange(dataList.get(size - 1).getTitle(),
					dataList.get(size - 1).getType_id());
		}
	}

	private void getWeather() {
		String cityName = UserCache.getCity();
		if (TextUtils.isEmpty(cityName)) {
			cityName = "苏州";
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
							onMenuClicked(-1);
						}
					}
				});
				break;
			case R.id.set:
				doActivity(SettingActivity.class);
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

	AMapLocationListener mLocListener = new AMapLocationListener() {
		@Override
		public void onLocationChanged(AMapLocation loc) {
			locationSucess(loc.getCity());
		}
	};

	private void locationSucess(String city) {
		if (!TextUtils.isEmpty(city)) {
			if (city.contains("市")) {
				city = city.replace("市", "");
			} else if (city.contains("县")) {
				city = city.replace("县", "");
			}
			UserCache.saveCity(city);
		} else {
		}
		sHA1(MainActivity.this);
		getWeather();
		if (client != null) {
			client.stopLocation();
		}
	}

	AMapLocationClientOption option;
	AMapLocationClient client;

	private void startLocation() {
		if (!UserCache.isNeddLocation()) {
			getWeather();
			return;
		}
		client = new AMapLocationClient(this.getApplicationContext());
		option = new AMapLocationClientOption();
		option.setLocationMode(AMapLocationMode.Hight_Accuracy);
		option.setOnceLocation(true);
		option.setMockEnable(false);
		option.setNeedAddress(true);
		option.setInterval(1000);
		client.setLocationListener(mLocListener);
		client.setLocationOption(option);
		client.startLocation();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (null != client) {
			client.onDestroy();
			client = null;
			option = null;
		}
	}

	public static String sHA1(Context context) {
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), PackageManager.GET_SIGNATURES);
			byte[] cert = info.signatures[0].toByteArray();
			MessageDigest md = MessageDigest.getInstance("SHA1");
			byte[] publicKey = md.digest(cert);
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < publicKey.length; i++) {
				String appendString = Integer.toHexString(0xFF & publicKey[i])
						.toUpperCase(Locale.US);
				if (appendString.length() == 1)
					hexString.append("0");
				hexString.append(appendString);
			}
			Log.d("machuang", " SHA1 = " + hexString);
			return hexString.toString();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}