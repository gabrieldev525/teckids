package com.appedu.teckids.screen;

import android.app.*;
import android.content.*;
import android.net.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.appedu.teckids.*;
import com.appedu.teckids.gui.*;
import java.io.*;
import android.graphics.*;

public class StartScreen extends Gui {
	private Activity ctx;
	private Layout startScreenLayout;
	private LinearLayout layout;
	
	public Sounds sounds;
	
	public SelectGame selectGame;
	
	public StartScreen(Activity ctx, LinearLayout layout) {
		super(ctx);
		this.ctx = ctx;
		this.layout = layout;
		
		sounds = new Sounds(ctx);
		sounds.startScreen();
		
		screen();
		
		data.current_screen = "home_screen";
		data.save(appFolderDir + "/current_screen.dat", data.current_screen.getBytes());
	}

	public void screen() {
		try {
			startScreenLayout = new Layout(ctx, 0, 0, getScreenWidth(), getScreenHeight());
			startScreenLayout.setGravity(Gravity.CENTER);
			startScreenLayout.setBackgroundDrawable(loadTexture(ctx, "gui/background.png"));
			startScreenLayout.setOrientation(LinearLayout.VERTICAL);
			layout.addView(startScreenLayout);
			
			JButton playBtn = new JButton(ctx, 0, getScreenHeight() / 4, getScreenWidth() / 3, getScreenHeight() / 5);
			playBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View p1) {
						layout.removeAllViews();
						selectGame = new SelectGame(ctx, layout);
						sounds.player.stop();
					}
			});
			playBtn.setBackgroundDrawable(loadTexture(ctx, "gui/play.png"));
			startScreenLayout.addView(playBtn);
			

			JButton privacyPolicyButton = new JButton(ctx, 0, getScreenHeight() / 6, getScreenWidth() / 4, getScreenHeight() / 7);
			privacyPolicyButton.setBackgroundDrawable(loadTexture(ctx, "gui/info_button.png"));
			privacyPolicyButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View p1)
					{
						Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gabrieldev525.github.io/me/privacy-policy-teckids.html"));
						ctx.startActivity(browserIntent);
					}
				});
			startScreenLayout.addView(privacyPolicyButton);
			
		} catch(IOException e) {
			Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	
}
