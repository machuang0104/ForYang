package com.ma.text.module;

import java.util.ArrayList;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.ma.text.MainActivity;
import com.ma.text.base.BaseActivity;
import com.ma.text.client.db.manager.TypeManager;
import com.ma.text.test.TestActivity;
import com.ma.text.vo.db.TypeVo;
import com.ma.text.widget.cache.UserCache;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * 延时广告显示页面
 */
public class WelcomeActivity extends BaseActivity {

	ImageView img_homeAD;

	@Override
	protected void afterOnCreate() {
		img_homeAD = new ImageView(this);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		img_homeAD.setLayoutParams(lp);
		img_homeAD.setScaleType(ScaleType.CENTER_CROP);
		// img_homeAD.setImageResource(R.drawable.guide_0);
		setContentView(img_homeAD);
		boolean isTest = false;
		if (!isTest) {
			new ProgressBarAsyncTask().execute(1000);
		} else {
			doActivity(TestActivity.class);
			finish();

		}
		ArrayList<TypeVo> list = TypeManager.getInstance().findAll();
		if (list == null || list.size() == 0) {
			TypeVo v = new TypeVo();
			v.setCreatTime(System.currentTimeMillis());
			v.setTitle("默认分类");
			v.setType_id(0);
			TypeManager.getInstance().insert(v);
		}
	}

	public class ProgressBarAsyncTask extends AsyncTask<Integer, Void, String> {
		@Override
		protected String doInBackground(Integer... arg0) {
			try {
				Thread.sleep(arg0[0]);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			doMainActivity();
		}
	}

	private void doMainActivity() {
		if (TextUtils.isEmpty(UserCache.getPWD())) {
			Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
			startActivity(intent);
		}
		finish();
	}
}