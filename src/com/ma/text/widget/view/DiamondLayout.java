package com.ma.text.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.RelativeLayout;

/**
 * ClassName: DiamondLayout Function: 菱形的RealtiveLayout date: 2015-10-12
 * 下午1:51:54
 * 
 * @author machuang
 */
public class DiamondLayout extends RelativeLayout {
	// private Paint mPaint;
	// private Path mPath;

	private float cenX;
	private float cenY;

	public DiamondLayout(Context context) {
		super(context);
		init(context);
	}

	@Override
	public void setLayoutParams(android.view.ViewGroup.LayoutParams params) {
		super.setLayoutParams(params);
		invalidate();
	}

	public DiamondLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		setClickable(true);
		// mPath = new Path();

		getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
			public boolean onPreDraw() {
				// mPath.moveTo(getWidth(), getHeight() / 2);
				// mPath.lineTo(getWidth() / 2, 0);
				// mPath.lineTo(0, getHeight() / 2);
				// mPath.lineTo(getWidth() / 2, getHeight());
				// mPath.close();
				cenX = getWidth() / 2;
				cenY = getHeight() / 2;
				return true;
			}
		});

		// mPaint = new Paint();
		// mPaint.setAntiAlias(true);
		// mPaint.setStyle(Style.FILL);
	}

	@Override
	public void setFocusable(boolean focusable) {
		super.setFocusable(false);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_CANCEL) {
			ev.setAction(MotionEvent.ACTION_UP);
		} else {
			float x = ev.getX();
			float y = ev.getY();
			if (x < cenX) {
				// 左侧
				if (y < cenY - x)
					return false;
				if (y > cenY + x)
					return false;
			} else if (cenX < x) {
				// 右侧
				if (y < x - cenX)
					return false;
				if (y > cenY + getWidth() - x)
					return false;
			}
		}
		return super.dispatchTouchEvent(ev);
	}
	// @Override
	// protected void onDraw(Canvas canvas) {
	// canvas.drawPath(mPath, mPaint);
	// super.onDraw(canvas);
	// }
}