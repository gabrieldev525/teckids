package com.appedu.teckids.screen;
import android.app.*;
import android.graphics.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.appedu.teckids.*;
import com.appedu.teckids.gui.*;
import java.io.*;
import java.util.*;

public class FormasGeometricas extends Gui {
	private Activity ctx;
	private LinearLayout layout;
	private LinearLayout formasGeometricasLayout;
	private Layout optionsFormas[] = new Layout[3];

	private String formas[][] = {
        {
            "CIRCULO", "formas/circulo.png"
        }, {
            "CUBO", "formas/cubo.png"
        }, {
            "HEXAGONO", "formas/hexagono.png"
        }, {
            "OCTOGONO", "formas/octogono.png"
        }, {
            "PENTAGONO", "formas/pentagono.png"
        }, {
            "QUADRADO", "formas/quadrado.png"
        }, {
            "RETANGULO", "formas/retangulo.png"
        }, {
            "TRIANGULO", "formas/triangulo.png"
        }
    };

	int currentIndex = -1;
	int currentOptions[] = { -1, -1, -1 };
	
	public Sounds sounds;
	
	private int acertos = 0;
	private int health = 3;
	private JButton heart;
	
	private Vibrator vibrator;
	
	public GameOver gameOver;

	public FormasGeometricas(final Activity ctx, final LinearLayout layout) {
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
					randomForma();
					acertos = 0;
					health = 3;
					heart.setText(health + "");
					gameOver.hide();
				}
			});
		
		vibrator = (Vibrator) ctx.getSystemService("vibrator");

		sounds = new Sounds(ctx);
		sounds.gameScreen();
		
		data.current_screen = "formasgeometricas";
		data.save(appFolderDir + "/current_screen.dat", data.current_screen.getBytes());
		
		//screen();
		randomForma();
	}

	private void screen() {
		try {
			formasGeometricasLayout = new Layout(ctx, 0, 0, getScreenWidth(), getScreenHeight());
			formasGeometricasLayout.setOrientation(Layout.VERTICAL);
			layout.addView(formasGeometricasLayout);

			//top
			Layout topBar = new Layout(ctx, 0, 0, getScreenWidth(), getScreenHeight() / 10);
			topBar.setBackgroundDrawable(loadTexture(ctx, "gui/barra_topo.png"));
			topBar.setGravity(Gravity.CENTER);
			formasGeometricasLayout.addView(topBar);
			
			heart = new JButton(ctx, 0, 0, getScreenWidth() / 13, getScreenHeight() / 18);
			heart.setBackgroundDrawable(loadTexture(ctx, "gui/heart.png"));
			heart.setText(health + "");
			heart.setTextColor(Color.WHITE);
			heart.setPadding(0, 0, 0, 0);
			topBar.addView(heart);

			//forma name
			final JButton formaNameLayout = new JButton(ctx, (getScreenWidth() - (getScreenWidth() / 2 - getScreenWidth() / 30)) / 2, getScreenHeight() / 5, getScreenWidth() / 2 - getScreenWidth() / 30, getScreenHeight() / 14);
			formaNameLayout.setBackgroundDrawable(loadTexture(ctx, "gui/Espaco_Silaba.png"));
			formaNameLayout.setText(formas[currentIndex][0]);
			formaNameLayout.setTextSize(20);
			formasGeometricasLayout.addView(formaNameLayout);

			//options
			Layout optionsLayout = new Layout(ctx, (getScreenWidth() - getScreenWidth() / 2) / 2, getScreenHeight() / 6, getScreenWidth() / 2, getScreenHeight() / 9);
			optionsLayout.setBackgroundColor(Color.BLACK);
			optionsLayout.setGravity(Gravity.CENTER);
			formasGeometricasLayout.addView(optionsLayout);

			for(int a = 0; a < 3; a++) {
				int width = getScreenWidth() / 10;
				int height = getScreenHeight() / 15;
				if(formas[currentOptions[a]][0].equals("RETANGULO")) {
					width = getScreenWidth() / 8;
				}
				optionsFormas[a] = new Layout(ctx, getScreenWidth() / 70, 0, width, height);
				optionsFormas[a].setBackgroundDrawable(loadTexture(ctx, formas[currentOptions[a]][1]));
				optionsFormas[a].setId(a);
				optionsFormas[a].setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View p1) {
							if(currentIndex == currentOptions[p1.getId()]) {
								randomForma();
								acertos++;
							} else {
								health--;
								heart.setText(health + "");
								vibrator.vibrate(100);
								
								formaNameLayout.setTextColor(Color.RED);
								p1.postDelayed(new Runnable() {
										@Override
										public void run() {
											formaNameLayout.setTextColor(Color.BLACK);
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
				optionsLayout.addView(optionsFormas[a]);
			}
		} catch(IOException e) {}
	}

	private void randomForma() {
		currentIndex = (int) Math.floor(Math.random() * formas.length);
		currentOptions[0] = currentIndex;

		int forma1 = (int) Math.floor(Math.random() * formas.length);
		while(isFormaChosed(forma1) == true)
			forma1 = (int) Math.floor(Math.random() * formas.length);
		currentOptions[1] = forma1;

		int forma2 = (int) Math.floor(Math.random() * formas.length);
		while(isFormaChosed(forma2) == true)
			forma2 = (int) Math.floor(Math.random() * formas.length);
		currentOptions[2] = forma2;

		randomFormaPosition();
		layout.removeAllViews();
		screen();
	}

	private boolean isFormaChosed(int value) {
		for(int n = 0; n < currentOptions.length; n++) {
			if(currentOptions[n] == value)
				return true;
		}
		return false;
	}

	private void randomFormaPosition() {
		ArrayList<Integer> list = new ArrayList<>();
		for(int m = 0; m < currentOptions.length; m++) {
			list.add(currentOptions[m]);
		}
		Collections.shuffle(list);

		for(int m = 0; m < list.size(); m++) {
			currentOptions[m] = list.get(m);
		}
	}
}
