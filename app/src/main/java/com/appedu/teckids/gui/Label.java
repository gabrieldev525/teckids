package com.appedu.teckids.gui;

import android.app.*;
import android.widget.*;
import android.widget.LinearLayout.*;

public class Label extends TextView 
{
	public Label(Activity ctx, int x, int y, String text) {
		super(ctx);
		
		setText(text);
		
		LayoutParams params = new LinearLayout.LayoutParams(Layout.LayoutParams.WRAP_CONTENT, Layout.LayoutParams.WRAP_CONTENT);
		setLayoutParams(params);
		params.setMargins(x, y, 0, 0);
	}
}
