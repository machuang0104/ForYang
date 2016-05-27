package com.ma.text.widget.dialoginput;

import android.app.Dialog;

public interface IDialogInputBuilder {

	public IDialogInputBuilder setInputListener(InputListener okListener);

	public Dialog getDialog();

	public IDialogInputBuilder setCancelble(boolean cancelable);
}