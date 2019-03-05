package com.appedu.teckids.screen;

import com.appedu.teckids.gui.*;
import android.app.*;
import android.widget.*;
import android.graphics.*;
import android.view.*;

public class GameOver extends Gui {
	private Activity ctx;
	private PopupWindow window;
	private LinearLayout layout;
	
	public Layout homeBtn, againBtn;
	
	private Label errosTxt,acertosTxt;
	
	public boolean showed = false;

	public GameOver(Activity ctx) {
		super(ctx);

		this.ctx = ctx;

		try {
			layout = new LinearLayout(ctx);
			layout.setBackgroundColor(Color.argb(3000, 0, 0, 0));
			layout.setGravity(Gravity.CENTER);

			Layout content = new Layout(ctx, 0, 0, getScreenWidth() / 2, getScreenHeight() / 3);
			content.setBackgroundDrawable(loadTexture(ctx, "gui/gameover_background.png"));
			content.setGravity(Gravity.CENTER);
			content.setOrientation(Layout.VERTICAL);
			layout.addView(content);

			Label gameOverTxt = new Label(ctx, 0, 0, "Fim de jogo");
			gameOverTxt.setTextSize(20);
			content.addView(gameOverTxt);

			acertosTxt = new Label(ctx, 0, 0, "Acertos: 10");
			acertosTxt.setTextSize(20);
			content.addView(acertosTxt);

			errosTxt = new Label(ctx, 0, 0, "");
			errosTxt.setTextSize(20);
			content.addView(errosTxt);
			
			Layout buttonsLayout = new Layout(ctx, 0, getScreenHeight() / 70, getScreenWidth() / 2, getScreenHeight() / 14);
			buttonsLayout.setGravity(Gravity.CENTER_HORIZONTAL);
			content.addView(buttonsLayout);
			
			homeBtn = new Layout(ctx, -getScreenWidth() / 70, 0, getScreenWidth() / 9, getScreenHeight() / 14);
			homeBtn.setBackgroundDrawable(loadTexture(ctx, "gui/home.png"));
			//buttonsLayout.addView(homeBtn);
			
			againBtn = new Layout(ctx, /*getScreenWidth() / 70 * 2*/0, 0, getScreenWidth() / 9, getScreenHeight() / 14);
			againBtn.setBackgroundDrawable(loadTexture(ctx, "gui/restart.png"));
			buttonsLayout.addView(againBtn);

			window = new PopupWindow(layout, getScreenWidth(), getScreenHeight());
		} catch(Exception e) {}
	}

	public void show() {
		ctx.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					window.showAtLocation(ctx.getWindow().getDecorView(), Gravity.LEFT, 0, 0);
					showed = true;
				}
			});
	}
	
	public void hide() {
		ctx.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					window.dismiss();
					showed = false;
				}
			});
	}
	
	public void setResult(int acertos, int erros) {
		acertosTxt.setText("Acertos: " + acertos);
		if(erros != -1)
			errosTxt.setText("Erros: " + erros);
	}
}
