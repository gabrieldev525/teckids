package com.appedu.teckids.gui;

import android.app.*;
import android.view.*;
import android.widget.*;
import android.widget.LinearLayout.*;

public class JButton extends Button {

	public JButton(final Activity ctx, int x, int y, int width, int height) {
		super(ctx);

		LayoutParams params = new LinearLayout.LayoutParams(width, height);
		setLayoutParams(params);
		params.setMargins(x, y, 0, 0);
		
		
		//setOnClickListener(tst);
	}
	

	//abstract void onclick(View v);

}
