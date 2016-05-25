package com.ma.text.module;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.ma.text.R;
import com.ma.text.anno.view.InjectLayout;
import com.ma.text.anno.view.InjectView;
import com.ma.text.base.BaseActivity;
import com.ma.text.db.client.manager.ContentManager;
import com.ma.text.tools.cache.ShareUtil;
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

	int typeId = 0;

	@Override
	protected void afterOnCreate() {
		int tag = getIntent().getIntExtra("tag", -1);
		if (tag == 1) {
			ContentVo v = (ContentVo) getIntent().getSerializableExtra("data");
			title.setText(v.getTitle());
			content.setText(v.getContent());
			title.setClickable(false);
			title.setFocusableInTouchMode(false);
			content.setFocusableInTouchMode(false);
			save.setVisibility(View.GONE);
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
				ToastUtil.show(R.string.save_sucess);
				ShareUtil.saveInt("have", -2);
				finish();
			}
		});
	}

}
