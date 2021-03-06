package com.ma.text.base;

import com.ma.text.R;
import com.ma.text.tools.ExitApplication;
import com.ma.text.tools.LogUtil;
import com.ma.text.tools.ViewUtil;
import com.ma.text.widget.annoview.Injector;
import com.ma.text.widget.swipeback.ActivityHelper;
import com.ma.text.widget.swipeback.ISwipeBack;
import com.ma.text.widget.swipeback.SwipeBackLayout;
import com.ma.text.widget.swipeback.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * 所有acticity需要继承的基类activity
 * 
 * @author Administrator
 * 
 */
public abstract class BaseActivity extends FragmentActivity implements
		ISwipeBack {
	private ActivityHelper mHelper;

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mHelper.onPostCreate();
	}

	@Override
	public View findViewById(int id) {
		View v = super.findViewById(id);
		if (v == null && mHelper != null)
			return mHelper.findViewById(id);
		return v;
	}

	@Override
	public SwipeBackLayout getSwipeBackLayout() {
		return mHelper.getSwipeBackLayout();
	}

	@Override
	public void setSwipeBackEnable(boolean enable) {
		getSwipeBackLayout().setEnableGesture(enable);
	}

	@Override
	public void scrollToFinishActivity() {
		Utils.convertActivityToTranslucent(this);
		getSwipeBackLayout().scrollToFinishActivity();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Injector.injectLayout(this);
		Injector.injectViews(this);
		mHelper = new ActivityHelper(this);
		mHelper.onActivityCreate();
		ExitApplication.getInstance().addActivity(this);
		LogUtil.i("tag_activity", "oncreate-->"
				+ this.getClass().getSimpleName());
		setSwipeBackEnable(false);
		afterOnCreate();
	}

	protected abstract void afterOnCreate();

	/**
	 * addSwipeBack: 实现滑动返回，设置后需进行显示测试，activity主题需设置为trslucant
	 */
	private ProgressDialog progressDialog;
	private View progressView;

	protected void showProgress() {

		if (progressDialog == null)
			progressDialog = new ProgressDialog(this, R.style.MyDialogStyle);
		if (progressView == null)
			progressView = ViewUtil.buildView(R.layout.dialog_progress_car);
		progressDialog.setCancelable(true);// 设置按返回键是否关闭dialog
		if (!progressDialog.isShowing())
			progressDialog.show();
		progressDialog.setContentView(progressView);
	}

	public void dismissProgress() {
		if (progressDialog != null && progressDialog.isShowing())
			progressDialog.dismiss();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogUtil.i("tag_activity", "onDestroy-->"
				+ this.getClass().getSimpleName());

	}

	protected void changeBackGround(boolean dismiss) {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		if (dismiss)
			lp.alpha = 1.0f;
		else
			lp.alpha = 0.7f;
		getWindow().setAttributes(lp);
	}

	protected void setSoftInputVisible(View view, boolean visible) {
		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		boolean isOpen = imm.isActive();
		if (visible == isOpen)
			return;
		if (visible)
			imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
		else
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	protected void doActivity(Class<?> cls) {
		startActivity(new Intent(this, cls));
	}

	protected void doActivity(Intent intent) {
		startActivity(intent);
	}

	protected void hideInputMethod() {
		try {
			InputMethodManager iman = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			iman.hideSoftInputFromWindow(this.getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			// iman.showSoftInput(v, InputMethodManager.SHOW_FORCED);
			// iman.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
			// InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
		}
	}

}