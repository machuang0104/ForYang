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
import com.ma.text.widget.dialog.DialogClient;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

@InjectLayout(id = R.layout.activity_detail)
public class DetailActivity extends AbsActivity {

	@InjectView(id = R.id.input_title)
	TextView title;
	@InjectView(id = R.id.input_content)
	TextView content;

	ContentVo detail;

	@Override
	protected void afterOnCreate() {
		setSwipeBackEnable(true);
		detail = (ContentVo) getIntent().getSerializableExtra(K.intent.Detail_Data);
		title.setText(detail.getTitle());
		content.setText(detail.getContent());
		setTitleRightStr(R.string.key_delete);
	}

	@Override
	public void onClickTitleStr(View v) {
		super.onClickTitleStr(v);
		DialogClient.showTwo(DetailActivity.this, getString(R.string.tip_delete), new OnClickListener() {

			@Override
			public void onClick(View v) {
				ContentManager.getInstance().delete(detail.getId());
				ToastUtil.show(R.string.tip_delete_sucess);
				SharedUtil.saveBoolean(K.intent.Need_Refresh, true);
				finish();
			}
		});
	}

}