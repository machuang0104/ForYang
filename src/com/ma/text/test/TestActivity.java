package com.ma.text.test;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.ma.text.R;
import com.ma.text.anno.view.InjectLayout;
import com.ma.text.anno.view.InjectView;
import com.ma.text.base.BaseActivity;
import com.ma.text.httpclient.action.Task;
import com.ma.text.httpclient.http.MCallBack;
import com.ma.text.httpclient.http.Response;
import com.ma.text.module.weather.vo.WeatherStatusVo;

@InjectLayout(id = R.layout.activity_test)
public class TestActivity extends BaseActivity {

	@InjectView(id = R.id.add)
	TextView add;
	@InjectView(id = R.id.get)
	TextView get;
	@InjectView(id = R.id.result)
	TextView result;

	@Override
	protected void afterOnCreate() {
		add.setOnClickListener(mListenr);
		get.setOnClickListener(mListenr);
	}

	OnClickListener mListenr = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.add:
				Task.getInstance().getWeather("苏州", new MCallBack<WeatherStatusVo>() {

					@Override
					public void onSuccess(Response<WeatherStatusVo> res) {
					}

					@Override
					public void onFailure(HttpException e, String msg) {
					}
				});
				break;
			case R.id.get:
				break;
			default:
				break;
			}
		}
	};

}
