package com.ma.text.widget.dialog;

import android.app.Dialog;
import android.view.View.OnClickListener;

public interface IDialogBuilder {

	public IDialogBuilder buildTitle(String title);

	public IDialogBuilder buildContent(String content);

	public IDialogBuilder setListener(OnClickListener okListener);

	public IDialogBuilder setTwoButton(boolean two);

	public Dialog getDialog();

	public IDialogBuilder setOkText(String okStr);

	public IDialogBuilder setCancelText(String cancleStr);

	public IDialogBuilder setCancelble(boolean cancelable);
}