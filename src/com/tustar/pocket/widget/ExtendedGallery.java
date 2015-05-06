package com.tustar.pocket.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Gallery;

import com.tustar.pocket.utils.Logger;

@SuppressWarnings("deprecation")
public class ExtendedGallery extends Gallery {

	private static final String TAG = ExtendedGallery.class.getSimpleName();

	public ExtendedGallery(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public ExtendedGallery(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public ExtendedGallery(Context context) {
		super(context);

	}

	public boolean canScrollHor(int direction) {
		final int offset = computeHorizontalScrollOffset();
		final int range = computeHorizontalScrollRange()
				- computeHorizontalScrollExtent();
		Logger.i(TAG, "offset:" + offset + " range:" + range);
		if (range == 0)
			return false;
		if (direction < 0) {
			Logger.d(TAG, "offset > 0 " + (offset > 0));
			return offset > 0;
		} else {
			Logger.d(TAG, "offset < range - 0 " + (offset < range - 0));
			return offset < range - 0;
		}
	}
}
