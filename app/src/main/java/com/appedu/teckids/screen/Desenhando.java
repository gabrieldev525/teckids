package com.appedu.teckids.screen;

import android.app.*;
import android.graphics.*;
import android.widget.*;
import com.appedu.teckids.gui.*;
import android.graphics.drawable.*;
import android.view.View.*;
import android.view.*;
import com.appedu.teckids.*;

public class Desenhando extends Gui 
{
	private Activity ctx;
	private LinearLayout layout;
	
	private LinearLayout desenhandoLayout;
	
	Canvas canvas;
	Paint paint;
	Bitmap blank;
	Path path;
	
	private float mX, mY;
	private static final float TOLERANCE = 5;
	
	private Layout panel;
	
	String cores[][] = {
        {
            "Vermelho", "#FF1525"
        }, {
            "Verde", "#A4FF45"
        }, {
            "Azul", "#00DAFF"
        }, {
            "Roxo", "#DA00FF"
        }, {
            "Rosa", "#FF00C3"
        }, {
            "Laranja", "#FF9100"
        }, {
            "Amarelo", "#FFF500"
        }, {
            "Marrom", "#823400"
        }, {
            "Preto", "#000000"
        }
    };
	
	int currentColor = -1;
	public Sounds sounds;
	
	public Desenhando(Activity ctx, LinearLayout layout) {
		super(ctx);
		
		this.ctx = ctx;
		this.layout = layout;
		
		path = new Path();
		blank = Bitmap.createBitmap(getScreenWidth(), getScreenHeight(), Bitmap.Config.ARGB_8888);
		canvas = new Canvas(blank);
		canvas.drawColor(Color.WHITE);
		
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeWidth(4f);
		
		sounds = new Sounds(ctx);
		sounds.gameScreen();
		
		data.current_screen = "desenhando";
		data.save(appFolderDir + "/current_screen.dat", data.current_screen.getBytes());
		
		screen();
		drawPanel();
	}
	
	public void screen() {
		desenhandoLayout = new Layout(ctx, 0, 0, getScreenWidth(), getScreenHeight());
		desenhandoLayout.setOrientation(Layout.VERTICAL);
		layout.addView(desenhandoLayout);
		
		Layout colorLayout = new Layout(ctx, 0, 0, getScreenWidth(), getScreenHeight() / 19);
		colorLayout.setGravity(Gravity.CENTER);
		desenhandoLayout.addView(colorLayout);
		
		final JButton colorBtn[] = new JButton[cores.length];
		for(int i = 0; i < cores.length; i++) {
			colorBtn[i] = new JButton(ctx, 0, 0, getScreenWidth() / 12, getScreenHeight() / 19);
			colorBtn[i].setBackgroundColor(Color.parseColor(cores[i][1]));
			colorBtn[i].setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View p1) {
						if(currentColor != -1)
							colorBtn[currentColor].setBackgroundColor(Color.parseColor(cores[currentColor][1]));
						currentColor = p1.getId();
						colorBtn[currentColor].getBackground().setAlpha(121);
						paint.setColor(Color.parseColor(cores[p1.getId()][1]));
					}
				
			});
			colorBtn[i].setId(i);
			colorLayout.addView(colorBtn[i]);
		}
		
		panel = new Layout(ctx, 0, 0, getScreenWidth(), getScreenHeight());
		panel.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View p1, MotionEvent p2) {
					float x = p2.getX();
					float y = p2.getY();
					
					switch(p2.getAction()) {
						case MotionEvent.ACTION_DOWN:
							onStartTouch(x, y);
							canvas.drawPath(path, paint);
							drawPanel();
							break;
						case MotionEvent.ACTION_MOVE:
							moveTouch(x, y);
							canvas.drawPath(path, paint);
							drawPanel();
							break;
						case MotionEvent.ACTION_UP:
							upTouch();
							canvas.drawPath(path, paint);
							path = new Path();
							drawPanel();
							break;
					}
					return true;
				}
		});
		desenhandoLayout.addView(panel);
	}
	
	private void onStartTouch(float x, float y) {
		path.moveTo(x, y);
		mX = x;
		mY = y;
	}
	
	private void moveTouch(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		
		if(dx >= TOLERANCE || dy >= TOLERANCE) {
			path.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
			mX = x;
			mY = y;
		}
	}
	
	private void upTouch() {
		path.lineTo(mX, mY);
	}
	
	private void drawPanel() {
		panel.setBackgroundDrawable(new BitmapDrawable(blank));
	}
}
