package com.ma.text.module;

import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.ma.text.R;
import com.ma.text.base.BaseActivity;
import com.ma.text.compoment.annoview.InjectLayout;
import com.ma.text.compoment.annoview.InjectView;
import com.ma.text.compoment.cache.SharedUtil;
import com.ma.text.compoment.dialog.DialogClient;
import com.ma.text.db.client.manager.ContentManager;
import com.ma.text.tools.tip.ToastUtil;
import com.ma.text.vo.db.ContentVo;

@InjectLayout(id = R.layout.activity_edit)
public class EditActivity extends BaseActivity {

	@InjectView(id = R.id.input_title)
	EditText title;
	@InjectView(id = R.id.input_content)
	EditText content;

	@InjectView(id = R.id.save)
	TextView save;

	int typeId = -1;

	@Override
	protected void afterOnCreate() {
		setSwipeBackEnable(true);
		boolean isNew = getIntent().getBooleanExtra("isNew", true);
		if (isNew) {
			initAdd();
		} else {
			initView();
		}
	}

	private void initView() {
		final ContentVo vo = (ContentVo) getIntent().getSerializableExtra("data");
		typeId = vo.getType_id();
		title.setText(vo.getTitle());
		title.setGravity(Gravity.CENTER_HORIZONTAL);
		title.setBackgroundColor(getResources().getColor(R.color.common_bg));
		content.setText(vo.getContent());
		content.setBackgroundColor(getResources().getColor(R.color.common_bg));
		title.setClickable(false);
		title.setFocusableInTouchMode(false);
		content.setFocusableInTouchMode(false);
		save.setText(R.string.key_delete);
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogClient.showTwo(EditActivity.this, getString(R.string.tip_delete), new OnClickListener() {

					@Override
					public void onClick(View v) {
						ContentManager.getInstance().delete(vo.getId());
						ToastUtil.show(R.string.tip_delete_sucess);
						SharedUtil.saveInt("have", -2);
						finish();
					}
				});
			}
		});
	}

	/**
	 * 
	 */
	private void initAdd() {
		typeId = getIntent().getIntExtra("typeid", 0);
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ContentVo c = new ContentVo();
				c.setContent(content.getText().toString());
				c.setTitle(title.getText().toString());
				c.setCreatetime(System.currentTimeMillis());
				c.setType_id(typeId);
				ContentManager.getInstance().insertUpdate(c);

				ToastUtil.show(R.string.save_sucess);
				SharedUtil.saveInt("have", -2);

				finish();
			}
		});
	}

}