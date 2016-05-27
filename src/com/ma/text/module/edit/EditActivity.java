package com.ma.text.module.edit;

import com.ma.text.R;
import com.ma.text.base.AbsActivity;
import com.ma.text.client.db.manager.ContentManager;
import com.ma.text.common.K;
import com.ma.text.tools.tip.ToastUtil;
import com.ma.text.vo.db.ContentVo;
import com.ma.text.widget.annoview.InjectLayout;
import com.ma.text.widget.annoview.InjectView;
import com.ma.text.widget.cache.SharedUtil;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

@InjectLayout(id = R.layout.activity_edit)
public class EditActivity extends AbsActivity {

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
		initAdd();
	}

	private void initAdd() {
		typeId = getIntent().getIntExtra(K.intent.TYPE_ID, 0);
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
				SharedUtil.saveBoolean(K.intent.Need_Refresh, true);
				finish();
			}
		});
	}

}