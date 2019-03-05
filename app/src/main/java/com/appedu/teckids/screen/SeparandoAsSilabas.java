package com.appedu.teckids.screen;
import android.app.*;
import android.graphics.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.appedu.teckids.*;
import com.appedu.teckids.gui.*;
import java.io.*;
import java.util.*;
import android.os.*;

public class SeparandoAsSilabas extends Gui
{
	private Activity ctx;
	private LinearLayout layout;
	
	private LinearLayout separandoAsSilabasLayout;
	private JButton wordSilabaBox[];
	private JButton silabaBox[];
	
	private String silabas[][][] = {
		{
			{"CARRO"},
			{"CAR", "RO"},
			{"CA", "R", "RRO", "C", "A", "O"}
		},
		{
			{"CASA"},
			{"CA", "SA"},
			{"CAS", "A", "S", "ASA", "C", "AS"}
		},
		{
			{"AVIÃO"},
			{"A", "VI", "ÃO"},
			{"AVI", "AV", "Ã", "IÃO", "IÃ", "O"}
		},
		{
			{"BOLA"},
			{"BO", "LA"},
			{"O", "A", "BOL", "OL", "OLA", "B"}
		},
		{
			{"LÁPIS"},
			{"LÁ", "PIS"},
			{"Á", "IS", "PI", "LÁP", "S", "L"}
		},
		{
			{"MESA"},
			{"ME", "SA"},
			{"E", "ES", "A", "ESA", "MES", "M"}
		},
		{
			{"TOALHA"},
			{"TO", "A", "LHA"},
			{"O", "HA", "OA", "TOA", "LH", "AL"}
		},
		{
			{"TELEVISÃO"},
			{"TE", "LE", "VI", "SÃO"},
			{"E", "TEL", "EL", "IS", "\303O", "ELE"}
		},
		{
			{"BOLHA"},
			{"BO", "LHA"},
			{"B", "O", "LH", "A", "OL", "HA"}
		},
		{
			{"PAREDE"},
			{"PA", "RE", "DE"},
			{"PAR", "E", "ED", "RED", "AR", "P"}
		},
		{
			{"CELULAR"},
			{"CE", "LU", "LAR"},
			{"C", "E", "EL", "UL", "AR", "CEL"}
		},
		{
			{"CAMA"},
			{"CA", "MA"},
			{"C", "A", "CAM", "AM", "AMA", "M"}
		},
		{
			{"MOTO"},
			{"MO", "TO"},
			{"M", "O", "OT", "OTO", "MOT", "T"}
		},
		{
			{"ESPELHO"},
			{"ES", "PE", "LHO"},
			{"E", "S", "PEL", "HO", "SP", "SPE"}
		}
	};
	
	private int currentIndex = -1;
	private String currentOptions[] = { "", "", "", "", "", "" };
	
	public Sounds sounds;
	
	private int acertos = 0;
	private int health = 3;
	private JButton heart;
	
	private Vibrator vibrator;
	
	public GameOver gameOver;
	
	public SeparandoAsSilabas(final Activity ctx, final LinearLayout layout) {
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
					randomWord();
					acertos = 0;
					health = 3;
					heart.setText(health + "");
					gameOver.hide();
				}
			});
		
		vibrator = (Vibrator) ctx.getSystemService("vibrator");
		
		sounds = new Sounds(ctx);
		sounds.gameScreen();
		
		data.current_screen = "separandoassilabas";
		data.save(appFolderDir + "/current_screen.dat", data.current_screen.getBytes());

		//screen();
		randomWord();
	}

	private void screen() {
		try {
			separandoAsSilabasLayout = new Layout(ctx, 0, 0, getScreenWidth(), getScreenHeight());
			separandoAsSilabasLayout.setOrientation(Layout.VERTICAL);
			layout.addView(separandoAsSilabasLayout);

			//top
			Layout topBar = new Layout(ctx, 0, 0, getScreenWidth(), getScreenHeight() / 10);
			topBar.setBackgroundDrawable(loadTexture(ctx, "gui/barra_topo.png"));
			topBar.setGravity(Gravity.CENTER);
			separandoAsSilabasLayout.addView(topBar);
			
			heart = new JButton(ctx, 0, 0, getScreenWidth() / 13, getScreenHeight() / 18);
			heart.setBackgroundDrawable(loadTexture(ctx, "gui/heart.png"));
			heart.setText(health + "");
			heart.setTextColor(Color.WHITE);
			heart.setPadding(0, 0, 0, 0);
			topBar.addView(heart);
			
			//word name
			JButton wordNameLayout = new JButton(ctx, (getScreenWidth() - (getScreenWidth() / 2 - getScreenWidth() / 30)) / 2, getScreenHeight() / 7, getScreenWidth() / 2 - getScreenWidth() / 30, getScreenHeight() / 10);
			wordNameLayout.setBackgroundDrawable(loadTexture(ctx, "gui/Espaco_Silaba.png"));
			wordNameLayout.setText(silabas[currentIndex][0][0]);
			wordNameLayout.setTextSize(20);
			separandoAsSilabasLayout.addView(wordNameLayout);
			
			//word box
			Layout wordSilabaLayout = new Layout(ctx, 0, getScreenHeight() / 8, getScreenWidth(), getScreenHeight() / 13);
			wordSilabaLayout.setGravity(Gravity.CENTER_HORIZONTAL);
			separandoAsSilabasLayout.addView(wordSilabaLayout);


			wordSilabaBox = new JButton[silabas[currentIndex][1].length];
			for(int a = 0; a < silabas[currentIndex][1].length; a++) {
				wordSilabaBox[a] = new JButton(ctx, getScreenWidth() / 70, 0, getScreenWidth() / 7, getScreenHeight() / 13);
				wordSilabaBox[a].setBackgroundDrawable(loadTexture(ctx, "gui/Espaco_Silaba.png"));
				wordSilabaBox[a].setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View p1) {
							if(!wordSilabaBox[p1.getId()].getText().equals("")) {
								for(int n = 0; n < silabaBox.length; n++) {
									if(silabaBox[n].getText().equals("")) {
										silabaBox[n].setText(wordSilabaBox[p1.getId()].getText());
										wordSilabaBox[p1.getId()].setText("");
										break;
									}
								}
							}
						}
					});
				wordSilabaBox[a].setPadding(0, 0, 0, 0);
				wordSilabaBox[a].setId(a);
				wordSilabaLayout.addView(wordSilabaBox[a]);
			}
			
			//silaba
			int numSilabas = 6;
			Layout silabaLayout = new Layout(ctx, (getScreenWidth() - (getScreenWidth() / 7 * numSilabas + getScreenWidth() / 70 * numSilabas)) / 2, getScreenHeight() / 10, getScreenWidth() / 7 * numSilabas + getScreenWidth() / 70 * numSilabas, getScreenHeight() / 13);
			silabaLayout.setGravity(Gravity.CENTER);
			separandoAsSilabasLayout.addView(silabaLayout);

			silabaBox = new JButton[numSilabas];
			for(int a = 0; a < numSilabas; a++) {
				silabaBox[a] = new JButton(ctx, getScreenWidth() / 70, 0, getScreenWidth() / 7, getScreenHeight() / 13);
				silabaBox[a].setBackgroundDrawable(loadTexture(ctx, "gui/Silaba.png"));
				silabaBox[a].setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(final View p1) {
							if(!silabaBox[p1.getId()].getText().equals("")) {
								for(int n = 0; n < wordSilabaBox.length; n++) {
									if(wordSilabaBox[n].getText().equals("")) {
										wordSilabaBox[n].setText(silabaBox[p1.getId()].getText());
										silabaBox[p1.getId()].setText("");
										break;
									}
								}
							}
							
							if(isAllBoxFill() == true) {
							p1.postDelayed(new Runnable() {

									@Override
									public void run() {
										if(isGameOver()) {
											randomWord();
											acertos++;
										} else {
											health--;
											heart.setText(health + "");
											vibrator.vibrate(100);
											
											for(int m = 0; m < wordSilabaBox.length; m++) {
												wordSilabaBox[m].setTextColor(Color.RED);
											}
											p1.postDelayed(new Runnable() {
													@Override
													public void run() {
														for(int m = 0; m < wordSilabaBox.length; m++) {
															wordSilabaBox[m].setTextColor(Color.BLACK);
														}
													}
												}, 200);
											
											if(health == 0) {
												//gameover
												gameOver.show();
												gameOver.setResult(acertos, -1);
											}
										}
									}
								
							}, 200);
							}
						}
					});
				silabaBox[a].setPadding(0, 0, 0, 0);
				silabaBox[a].setText(currentOptions[a]);
				silabaBox[a].setId(a);
				silabaLayout.addView(silabaBox[a]);
			}
			
		} catch(IOException e) {}
	}
	
	private void randomWord() {
		currentIndex = (int) Math.floor(Math.random() * silabas.length);
		for(int i = 0; i < silabas[currentIndex][1].length; i++) {
			currentOptions[i] = silabas[currentIndex][1][i];
		}
		
		//Log.i("sla", silabas[currentIndex][2].length + " - " + silabas[currentIndex][1].length + " = " + (silabas[currentIndex][2].length - silabas[currentIndex][1].length));
		int num = silabas[currentIndex][2].length - silabas[currentIndex][1].length;
		int options[] = new int[num];
		//Log.i("sla 2", num + "");
		for(int i = 0; i < num; i++) {
			options[i] = (int) Math.floor(Math.random() * 6);
			while(isSilabaChosed(silabas[currentIndex][2][options[i]]) == true)
				options[i] = (int) Math.floor(Math.random() * 6);
			currentOptions[silabas[currentIndex][1].length + i] = silabas[currentIndex][2][options[i]];
			
			Log.i("sla", num + " + " + i + " = " + (num + i));
			//Log.i("sla", i + " / " + currentOptions[num + i]);
		}
		/*
		for(int m = 0; m < currentOptions.length; m++) {
			Log.i("sla", m + ": " + currentOptions[m]);
		}*/
		
		randomSilabaPosition();
		layout.removeAllViews();
		screen();
	}
	
	private void randomSilabaPosition() {
		ArrayList<String> list = new ArrayList<>();
		for(int m = 0; m < currentOptions.length; m++) {
			list.add(currentOptions[m]);
		}
		Collections.shuffle(list);

		for(int m = 0; m < list.size(); m++) {
			currentOptions[m] = list.get(m);
		}
	}
	
	
	private boolean isSilabaChosed(String value) {
		for(int n = 0; n < currentOptions.length; n++) {
			if(currentOptions[n].equals(value))
				return true;
		}
		return false;
	}
	
	private boolean isAllBoxFill() {
		for(int n = 0; n < wordSilabaBox.length; n++) {
			if(wordSilabaBox[n].getText().equals("")) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isGameOver() {
		for(int h = 0; h < wordSilabaBox.length; h++) {
			if(!wordSilabaBox[h].getText().equals(silabas[currentIndex][1][h])) {
				return false;
			}
		}
		return true;
	}
	
	
}
