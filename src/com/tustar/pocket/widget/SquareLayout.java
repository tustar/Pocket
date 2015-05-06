package com.tustar.pocket.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class SquareLayout extends RelativeLayout {

	public SquareLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SquareLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SquareLayout(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
				getDefaultSize(0, heightMeasureSpec));
		int width = getMeasuredWidth();
		getMeasuredHeight();
		int height = View.MeasureSpec.makeMeasureSpec(width,
				MeasureSpec.UNSPECIFIED);
		super.onMeasure(width, height);
	}
}
