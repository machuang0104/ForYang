package com.ma.text.module;

import com.ma.text.R;
import com.ma.text.anno.view.InjectLayout;
import com.ma.text.anno.view.InjectView;
import com.ma.text.base.BaseActivity;
import com.ma.text.compoment.cache.UserCache;
import com.ma.text.tools.tip.ToastUtil;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

@InjectLayout(id = R.layout.activity_login)
public class SetPwdActivity extends BaseActivity {

	@InjectView(id = R.id.etLoginAccount)
	public EditText etPwd1;
	@InjectView(id = R.id.etLoginPwd)
	public EditText etPwd2;

	@InjectView(id = R.id.btn_login)
	public TextView btn_login;

	@Override
	protected void afterOnCreate() {
		etPwd2.setVisibility(View.VISIBLE);
		btn_login.setText(R.string.key_save);
		btn_login.setOnClickListener(mListener);
	}

	OnClickListener mListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.btn_login:
				set();
				break;
			default:
				break;
			}

		}
	};

	private void set() {
		String pwd1 = etPwd1.getText().toString().trim();
		String pwd2 = etPwd2.getText().toString().trim();
		if (pwd1.equals(pwd2)) {
			UserCache.savePWD(pwd1);
			ToastUtil.show(R.string.setting_add_pwd_sucess);
			finish();
		}else {
			ToastUtil.show(R.string.setting_add_pwd_fail);
		}
	}
}