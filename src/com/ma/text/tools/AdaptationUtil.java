package com.ma.text.tools;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.view.View;

public class AdaptationUtil {
	public static void setBack(View v, Drawable dra) {
		if (android.os.Build.VERSION.SDK_INT > 15)
			setBackHigh(v, dra);
		else
			setBackLow(v, dra);
	}

	@TargetApi(16)
	public static void setBackHigh(View v, Drawable dra) {
		v.setBackground(dra);
	}

	@Deprecated
	public static void setBackLow(View v, Drawable dra) {
		v.setBackgroundDrawable(dra);
	}
}
