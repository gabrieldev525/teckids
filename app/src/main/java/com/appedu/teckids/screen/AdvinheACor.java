package com.appedu.teckids.screen;
import android.app.*;
import android.graphics.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.appedu.teckids.gui.*;
import java.io.*;
import java.util.*;
import com.appedu.teckids.*;
import android.os.*;

public class AdvinheACor extends Gui
{
	private Activity ctx;
	private LinearLayout layout;
	
	private LinearLayout advinheACorLayout;
	
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
	int currentOptions[] = { -1, -1, -1, -1 };
	
	Layout coresBox[] = new Layout[4];
	JButton colorNameLayout;
	
	public Sounds sounds;
	
	private int acertos = 0;
	private int health = 3;
	private JButton heart;
	
	private Vibrator vibrator;
	
	public GameOver gameOver;
	
	public AdvinheACor(final Activity ctx, final LinearLayout layout) {
		super(ctx);

		this.ctx = ctx;
		this.layout = layout;
		
		gameOver = new GameOver(ctx);
		gameOver.homeBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1) {
					layout.removeAllViews();
					new StartScreen(ctx, layout);
					sounds.player.stop();
					gameOver.hide();
				}
			});
		gameOver.againBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1) {
					randomColor();
					acertos = 0;
					health = 3;
					heart.setText(health + "");
					gameOver.hide();
				}
			});
		
		vibrator = (Vibrator) ctx.getSystemService("vibrator");
		
		sounds = new Sounds(ctx);
		sounds.gameScreen();
		
		data.current_screen = "advinheacor";
		data.save(appFolderDir + "/current_screen.dat", data.current_screen.getBytes());

		screen();
		randomColor();
	}

	private void screen() {
		try {
			advinheACorLayout = new Layout(ctx, 0, 0, getScreenWidth(), getScreenHeight());
			advinheACorLayout.setOrientation(Layout.VERTICAL);
			layout.addView(advinheACorLayout);

			//top
			Layout topBar = new Layout(ctx, 0, 0, getScreenWidth(), getScreenHeight() / 10);
			topBar.setBackgroundDrawable(loadTexture(ctx, "gui/barra_topo.png"));
			topBar.setGravity(Gravity.CENTER);
			advinheACorLayout.addView(topBar);
			
			heart = new JButton(ctx, 0, 0, getScreenWidth() / 13, getScreenHeight() / 18);
			heart.setBackgroundDrawable(loadTexture(ctx, "gui/heart.png"));
			heart.setText(health + "");
			heart.setTextColor(Color.WHITE);
			heart.setPadding(0, 0, 0, 0);
			topBar.addView(heart);
			
			//cores 
			Layout coresLayout = new Layout(ctx, 0, getScreenHeight() / 6, getScreenWidth(), (getScreenHeight() / 7 + (getScreenHeight() / 70) * 2) * 2);
			coresLayout.setOrientation(Layout.VERTICAL);
			advinheACorLayout.addView(coresLayout);
			
			Layout coresLinha[] = new Layout[2];
			for(int i = 0; i < 2; i++) {
				coresLinha[i] = new Layout(ctx, 0, getScreenHeight() / 70, getScreenWidth(), getScreenHeight() / 7);
				coresLinha[i].setGravity(Gravity.CENTER);
				coresLayout.addView(coresLinha[i]);
			}
			
			
			int currentLinha = 0;
			for(int i = 0; i < 4; i++) {
				if(i % 2 == 0 && i != 0)
					currentLinha++;
					
				coresBox[i] = new Layout(ctx, getScreenWidth() / 70, 0, getScreenWidth() / 4, getScreenHeight() / 7);
				//coresBox[i].setBackgroundColor(Color.parseColor(cores[currentOptions[i]][1]));
				coresBox[i].setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View p1) {
							if(currentColor == currentOptions[p1.getId()]) {
								randomColor();
								acertos++;
							} else {
								health--;
								heart.setText(health + "");
								vibrator.vibrate(100);
								
								colorNameLayout.setTextColor(Color.RED);
								p1.postDelayed(new Runnable() {
										@Override
										public void run() {
											colorNameLayout.setTextColor(Color.BLACK);
										}
									}, 200);
								
								if(health == 0) {
									//gameover
									gameOver.show();
									gameOver.setResult(acertos, -1);
								}
							}
						}
					
				});
				coresBox[i].setId(i);
				coresLinha[currentLinha].addView(coresBox[i]);
			}
			
			colorNameLayout = new JButton(ctx, (getScreenWidth() - getScreenWidth() / 3) / 2, getScreenHeight() / 10, getScreenWidth() / 3, getScreenHeight() / 10);
			colorNameLayout.setBackgroundDrawable(loadTexture(ctx, "gui/Espaco_Silaba.png"));
			//colorNameLayout.setText(cores[currentColor][0]);
			advinheACorLayout.addView(colorNameLayout);
		} catch(IOException e) {
		}
	}
	
	private void randomColor() {
		currentColor = (int) Math.floor(Math.random() * cores.length);
		currentOptions[0] = currentColor;
		
		int color2 = (int) Math.floor(Math.random() * cores.length);
		while(isColorChosed(color2) == true)
			color2 = (int) Math.floor(Math.random() * cores.length);
		currentOptions[1] = color2;
		
		int color3 = (int) Math.floor(Math.random() * cores.length);
		while(isColorChosed(color3) == true)
			color3 = (int) Math.floor(Math.random() * cores.length);
		currentOptions[2] = color3;
		
		int color4 = (int) Math.floor(Math.random() * cores.length);
		while(isColorChosed(color4) == true)
			color4 = (int) Math.floor(Math.random() * cores.length);
		currentOptions[3] = color4;
		
		randomColorPosition();
		//layout.removeAllViews();
		update();
	}
	
	private boolean isColorChosed(int value) {
		for(int v = 0; v < currentOptions.length; v++) {
			if(currentOptions[v] == value) {
				return true;
			}
		}
		return false;
	}
	
	private void randomColorPosition() {
		ArrayList<Integer> list = new ArrayList<>();
		for(int m = 0; m < currentOptions.length; m++) {
			list.add(currentOptions[m]);
		}
		Collections.shuffle(list);

		for(int m = 0; m < list.size(); m++) {
			currentOptions[m] = list.get(m);
		}
	}
	
	private void update() {
		colorNameLayout.setText(cores[currentColor][0]);
		for(int h = 0; h < 4; h++) {
			coresBox[h].setBackgroundColor(Color.parseColor(cores[currentOptions[h]][1]));
		}
	}
}
