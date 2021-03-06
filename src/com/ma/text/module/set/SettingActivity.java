package com.ma.text.module.set;

import com.ma.text.R;
import com.ma.text.base.AbsActivity;
import com.ma.text.base.BaseActivity;
import com.ma.text.widget.annoview.InjectLayout;
import com.ma.text.widget.annoview.InjectView;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * Description: Login Page
 * 
 * 
 */
@InjectLayout(id = R.layout.activity_setting)
public class SettingActivity extends AbsActivity {

	@InjectView(id = R.id.set_pwd)
	public TextView set_pwd;

	@Override
	protected void afterOnCreate() {
		updateTitle(R.string.menu_set,true);
		set_pwd.setOnClickListener(mListener);
	}

	OnClickListener mListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.set_pwd:
				doActivity(SetPwdActivity.class);
				break;
			default:
				break;
			}

		}
	};

}