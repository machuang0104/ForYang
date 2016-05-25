package com.ma.text.module;

import com.ma.text.R;
import com.ma.text.anno.view.InjectLayout;
import com.ma.text.anno.view.InjectView;
import com.ma.text.base.BaseActivity;
import com.ma.text.compoment.cache.SharedUtil;
import com.ma.text.compoment.dialog.DialogClient;
import com.ma.text.db.client.manager.ContentManager;
import com.ma.text.tools.tip.ToastUtils;
import com.ma.text.vo.db.ContentVo;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

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
		int tag = getIntent().getIntExtra("tag", -1);
		if (tag == 1) {
			final ContentVo vo = (ContentVo) getIntent().getSerializableExtra("data");
			title.setText(vo.getTitle());
			content.setText(vo.getContent());
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
						}
					});
				}
			});
			return;
		}
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
				ToastUtils.show(R.string.save_sucess);
				SharedUtil.saveInt("have", -2);
				finish();
			}
		});
	}

}