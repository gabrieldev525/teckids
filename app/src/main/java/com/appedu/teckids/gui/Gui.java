package com.appedu.teckids.gui;
import android.app.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import com.appedu.teckids.*;
import java.io.*;

public class Gui extends Utils {
	private Activity ctx;
	
	public Data data = new Data();
	

	public Gui(Activity ctx) {
		this.ctx = ctx;
	}

	public int getScreenHeight() {
        return ctx.getWindowManager().getDefaultDisplay().getHeight();
    }

    public int getScreenWidth() {
        return ctx.getWindowManager().getDefaultDisplay().getWidth();
    }

    public BitmapDrawable loadTexture(Activity activity, String s) throws IOException {
        return new BitmapDrawable(BitmapFactory.decodeStream(activity.getAssets().open(s)));
    }
}
