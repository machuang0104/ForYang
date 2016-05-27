package com.ma.text.module;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.ma.text.MainActivity;
import com.ma.text.R;
import com.ma.text.base.BaseActivity;
import com.ma.text.tools.tip.ToastUtil;
import com.ma.text.widget.annoview.InjectLayout;
import com.ma.text.widget.annoview.InjectView;
import com.ma.text.widget.cache.UserCache;

/**
 * Description: Login Page
 * 
 * 
 */
@InjectLayout(id = R.layout.activity_login)
public class LoginActivity extends BaseActivity {

	@InjectView(id = R.id.etLoginAccount)
	public EditText etAccount;

	@InjectView(id = R.id.btn_login)
	public TextView btn_login;

	private String accountStr;

	@Override
	protected void afterOnCreate() {
		findViewById(R.id.title_left).setVisibility(View.GONE);
		updateTitle(R.string.title_login, false);
		btn_login.setOnClickListener(mListener);
	}

	OnClickListener mListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.btn_login:
				accountStr = etAccount.getText().toString().trim();

				if (isStrEmpty(accountStr)) {
					ToastUtil.show(R.string.login_tip_pwd_null);
				} else
					doLogin(accountStr);
				break;
			default:
				break;
			}

		}
	};

	private void doLogin(String account) {
		if (account.equals(UserCache.getPWD())) {
			ToastUtil.show(R.string.login_sucess);
			doActivity(MainActivity.class);
			finish();
		} else {
			ToastUtil.show(R.string.login_tip_pwd_wrong);
		}
	}

	private long firstTime;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (System.currentTimeMillis() - firstTime < 2500) {
				finish();
				System.exit(0);
			} else {
				firstTime = System.currentTimeMillis();
				ToastUtil.show(R.string.tip_exit);
			}
		}
		return true;
	}

}