package com.appedu.teckids.gui;

import android.app.*;
import android.widget.*;

public class Layout extends LinearLayout
{
	public Layout(Activity ctx, int x, int y, int width, int height) {
		super(ctx);
		
		LayoutParams params = new LinearLayout.LayoutParams(width, height);
		setLayoutParams(params);
		params.setMargins(x, y, 0, 0);
	}
}
