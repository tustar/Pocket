package com.tustar.pocket.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ExtendedListView extends ListView {

	public ExtendedListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ExtendedListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ExtendedListView(Context context) {
		super(context);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
