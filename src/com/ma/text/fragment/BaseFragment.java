package com.ma.text.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ma.text.http.global.Result;
import com.ma.text.tools.StrUtil;
import com.ma.text.vo.Response;

/**
 * 
 * @author libin
 * 
 */
public class BaseFragment extends Fragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/** * isStrEmpty: str空返回true，非空返回false */
	protected boolean isStrEmpty(String str) {
		return StrUtil.isEmpty(str);
	}
	protected final int CODE_OK = Result.SUCESS;

	protected <T> String getMsg(Response<T> res) {
		return Result.getMsg(res.getTaskId(), res.getResult(), res.getMessage());

	}
}
