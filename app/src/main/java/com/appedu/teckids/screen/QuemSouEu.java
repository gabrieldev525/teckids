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

public class QuemSouEu extends Gui {
	private Activity ctx;
	private LinearLayout layout;

	private LinearLayout quemSouEuLayout;
	private JButton wordBox[];
	private JButton silabaBox[];

	private String animais[][][] = {
		{
            {
                "ARANHA", "aranha.png"
            }, {
                "A", "RA", "NHA"
            }
        },
		{
            {
                "ARARA", "arara.png"
            }, {
                "A", "RA", "RA"
            }
        },
		{
            {
                "BALEIA", "baleia.png"
            }, {
                "BA", "LEI", "A"
            }
        },
		{
			{
				"CACHORRO", "cachorro.png"
			}, {
				"CA", "CHOR", "RO"
			}
		},
		{
            {
                "CARANGUEJO", "caranguejo.png"
            }, {
                "CA", "RAN", "GUE", "JO"
            }
        },
		{
            {
                "CAVALO", "cavalo.png"
            }, {
                "CA", "VA", "LO"
            }
        },
		{
            {
                "COBRA", "cobra.png"
            }, {
                "CO", "BRA"
            }
        },
		{
            {
                "COELHO", "coelho.png"
            }, {
                "CO", "E", "LHO"
            }
        },
		{
			{
				"ELEFANTE", "elefante.png"
			}, {
				"E", "LE", "FAN", "TE"
			}
		},
		{
            {
                "FOCA", "foca.png"
            }, {
                "FO", "CA"
            }
        },
		{
            {
                "GALINHA", "galinha.png"
            }, {
                "GA", "LI", "NHA"
            }
        },
		{
            {
                "GATO", "gato.png"
            }, {
                "GA", "TO"
            }
        },
		{
            {
                "GIRAFA", "girafa.png"
            }, {
                "GI", "RA", "FA"
            }
        },
		{
            {
                "GOLFINHO", "golfinho.png"
            }, {
                "GOL", "FI", "NHO"
            }
        },
		{
			{
				"JACARÉ", "jacare.png"
			}, {
				"JA", "CA", "RÉ"
			}
		},
		{
            {
                "LEÃO", "leao.png"
            }, {
                "LE", "ÃO"
            }
        },
		{
			{
                "LOBO", "lobo.png"
            },
			{
				"LO", "BO"
			}
		}, 
		{
            {
                "MACACO", "macaco.png"
            }, {
                "MA", "CA", "CO"
            }
        },
		{
            {
                "PANDA", "panda.png"
            }, {
                "PAN", "DA"
            }
        },
		{
            {
                "PEIXE", "peixe.png"
            }, {
                "PEI", "XE"
            }
        },
		{
            {
                "PINGUIM", "penguim.png"
            }, {
                "PIN", "GUIM"
            }
        },
		{
            {
                "POLVO", "polvo.png"
            }, {
                "POL", "VO"
            }
        },
		{
            {
                "PORCO", "porco.png"
            }, {
                "POR", "CO"
            }
        },
		{
            {
                "PREGUIÇA", "preguica.png"
            }, {
                "PRE", "GUI", "ÇA"
            }
        },
		{
            {
                "RATO", "rato.png"
            }, {
                "RA", "TO"
            }
        },
		{
            {
                "SAPO", "sapo.png"
            }, {
                "SA", "PO"
            }
        },
		{
            {
                "TARTARUGA", "tartaruga.png"
            }, {
                "TAR", "TA", "RU", "GA"
            }
        },
		{
			{
				"TATU", "tatu.png"
			},{
				"TA", "TU"
			}
		},
		{
			{
				"TUBARÃO", "tubarao.png"
			}, {
				"TU", "BA", "RÃO"
			}
		},
		{
			{
				"TUCANO", "tucano.png"
			},
			{
				"TU", "CA", "NO"
			}
		},
		{
            {
                "URSO", "urso.png"
            }, {
                "UR", "SO"
            }
        },
		{
			{
				"VACA", "Vaca.png"
			},
			{
				"VA", "CA"
			}
		},
		{
			{
                "ZEBRA", "zebra.png"
            },
			{
				"ZE", "BRA"
			}
		}

	};

	private int currentAnimal = -1;
	private int lastAnimal = -1;
	private String currentSilabas[];

	public Sounds sounds;

	private int acertos = 0;
	private int health = 3;
	private JButton heart;

	public Vibrator vibrator;

	public GameOver gameOver;

	public QuemSouEu(final Activity ctx, final LinearLayout layout) {
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
					randomAnimal();
					acertos = 0;
					health = 3;
					heart.setText(health + "");
					gameOver.hide();
				}
			});

		vibrator = (Vibrator) ctx.getSystemService("vibrator");

		sounds = new Sounds(ctx);
		sounds.gameScreen();

		data.current_screen = "quemsoueu";
		data.save(appFolderDir + "/current_screen.dat", data.current_screen.getBytes());

		//screen();
		randomAnimal();
	}

	private void screen() {
		try {
			quemSouEuLayout = new Layout(ctx, 0, 0, getScreenWidth(), getScreenHeight());
			quemSouEuLayout.setOrientation(Layout.VERTICAL);
			layout.addView(quemSouEuLayout);

			//top
			Layout topBar = new Layout(ctx, 0, 0, getScreenWidth(), getScreenHeight() / 10);
			topBar.setBackgroundDrawable(loadTexture(ctx, "gui/barra_topo.png"));
			topBar.setGravity(Gravity.CENTER);
			quemSouEuLayout.addView(topBar);

			heart = new JButton(ctx, 0, 0, getScreenWidth() / 13, getScreenHeight() / 18);
			heart.setBackgroundDrawable(loadTexture(ctx, "gui/heart.png"));
			heart.setText(health + "");
			heart.setTextColor(Color.WHITE);
			heart.setPadding(0, 0, 0, 0);
			topBar.addView(heart);

			//animal
			Layout animal = new Layout(ctx, (getScreenWidth() - getScreenWidth() / 3) / 2, getScreenHeight() / 15, getScreenWidth() / 3, getScreenHeight() / 6);
			animal.setBackgroundDrawable(loadTexture(ctx, "animais/" + animais[currentAnimal][0][1]));
			quemSouEuLayout.addView(animal);

			//word box
			Layout wordLayout = new Layout(ctx, 0, getScreenHeight() / 6, getScreenWidth(), getScreenHeight() / 13);
			wordLayout.setGravity(Gravity.CENTER_HORIZONTAL);
			quemSouEuLayout.addView(wordLayout);


			wordBox = new JButton[currentSilabas.length];
			for(int a = 0; a < currentSilabas.length; a++) {
				wordBox[a] = new JButton(ctx, getScreenWidth() / 70, 0, getScreenWidth() / 7, getScreenHeight() / 13);
				wordBox[a].setBackgroundDrawable(loadTexture(ctx, "gui/Espaco_Silaba.png"));
				wordBox[a].setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View p1) {
							if(!wordBox[p1.getId()].getText().equals("")) {
								for(int n = 0; n < silabaBox.length; n++) {
									if(silabaBox[n].getText().equals("")) {
										silabaBox[n].setText(wordBox[p1.getId()].getText());
										wordBox[p1.getId()].setText("");
										break;
									}
								}
							}
						}
					});
				wordBox[a].setPadding(0, 0, 0, 0);
				wordBox[a].setId(a);
				wordLayout.addView(wordBox[a]);
			}

			//silaba
			int numSilabas = currentSilabas.length;
			Layout silabaLayout = new Layout(ctx, (getScreenWidth() - (getScreenWidth() / 7 * numSilabas + getScreenWidth() / 70 * numSilabas)) / 2, getScreenHeight() / 10, getScreenWidth() / 7 * numSilabas + getScreenWidth() / 70 * numSilabas, getScreenHeight() / 13);
			silabaLayout.setBackgroundColor(Color.BLACK);
			silabaLayout.setGravity(Gravity.CENTER);
			quemSouEuLayout.addView(silabaLayout);

			silabaBox = new JButton[currentSilabas.length];
			for(int a = 0; a < currentSilabas.length; a++) {
				silabaBox[a] = new JButton(ctx, getScreenWidth() / 70, 0, getScreenWidth() / 7, getScreenHeight() / 13);
				silabaBox[a].setBackgroundDrawable(loadTexture(ctx, "gui/Silaba.png"));
				silabaBox[a].setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(final View p1) {
							if(!silabaBox[p1.getId()].getText().equals("")) {
								for(int n = 0; n < wordBox.length; n++) {
									if(wordBox[n].getText().equals("")) {
										wordBox[n].setText(silabaBox[p1.getId()].getText());
										silabaBox[p1.getId()].setText("");
										break;
									}
								}
							}

							if(isAllBoxFill() == true) {
								p1.postDelayed(new Runnable() {

										@Override
										public void run() {
											if(isGameOver() == true) {
												randomAnimal();
												acertos++;
											} else {
												health--;
												heart.setText(health + "");
												vibrator.vibrate(100);
												for(int m = 0; m < wordBox.length; m++) {
													wordBox[m].setTextColor(Color.RED);
												}
												p1.postDelayed(new Runnable() {
														@Override
														public void run() {
															for(int m = 0; m < wordBox.length; m++) {
																wordBox[m].setTextColor(Color.BLACK);
															}
														}
												}, 200);

												if(health == 0) {
													//gameover screen
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
				silabaBox[a].setText(currentSilabas[a]);
				silabaBox[a].setId(a);
				silabaLayout.addView(silabaBox[a]);
			}

		} catch(IOException e) {}
	}

	private boolean isGameOver() {
		String s = "";
		for(int g = 0; g < wordBox.length; g++) {
			s += wordBox[g].getText();
		}
		if(s.equals(animais[currentAnimal][0][0])) {
			return true;
		}
		return false;
	}

	private void randomAnimal() {
		currentAnimal = (int) Math.floor(Math.random() * animais.length);
		while(currentAnimal == lastAnimal) {
			currentAnimal = (int) Math.floor(Math.random() * animais.length);
		}
		lastAnimal = currentAnimal;

		currentSilabas = new String[animais[currentAnimal][1].length];
		currentSilabas = animais[currentAnimal][1];

		randomSilabasPosition();
		layout.removeAllViews();
		screen();
	}
	private boolean isAllBoxFill() {
		for(int n = 0; n < wordBox.length; n++) {
			if(wordBox[n].getText().equals("")) {
				return false;
			}
		}
		return true;
	}
	private void randomSilabasPosition() {
		ArrayList<String> list = new ArrayList<>();
		for(int m = 0; m < currentSilabas.length; m++) {
			list.add(currentSilabas[m]);
		}
		Collections.shuffle(list);

		for(int m = 0; m < list.size(); m++) {
			currentSilabas[m] = list.get(m);
		}
	}
}
