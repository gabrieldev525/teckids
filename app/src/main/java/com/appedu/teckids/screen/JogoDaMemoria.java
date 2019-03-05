package com.appedu.teckids.screen;

import android.app.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.appedu.teckids.*;
import com.appedu.teckids.gui.*;
import java.io.*;
import java.util.*;

public class JogoDaMemoria extends Gui 
{
	private Activity ctx;
	private LinearLayout layout;
	
	public Sounds sounds;
	private LinearLayout jogoDaMemoriaLayout;
	
	private Label timer;
	private int time = 15000;
	public CountDownTimer cT;
	
	private int numCartas = 6;
	
	private Layout cartasLayoutLinha[] = new Layout[2];
	private Layout cartas[] = new Layout[6];
	
	private int cartasDesviradas[] = {-1, -1};
	private int paresAcertados[] = {
		-1, -1, -1, -1,
		-1, -1, -1, -1
	};
	
	private Flip3dAnimation animCartas;
	
	//todos os animais
	private String animais[][] = {
		//nome do animal, textura
		{"Arara", "animais2/arara.png"},
		{"Baleia", "animais2/Baleia.png"},
		{"Cachorro", "animais2/Cachorro.png"},
		{"Cobra", "animais2/Cobra.png"},
		{"Galinha", "animais2/Galinha.png"},
		{"Gato", "animais2/Gato.png"},
		{"Peixe", "animais2/Peixe.png"},
		{"Polvo", "animais2/Polvo.png"},
		{"Urso", "animais2/Urso.png"},
		{"Vaca", "animais2/Vaca.png"}
	};

	//index da array animais em que estao os animais atual selecionado
	private int currentCartasAnimais[] = {-1, -1, -1, -1};

	//define a posição atual das cartas e cada animal
	private int animaisCartasPosicoes[] = {
		-1, -1, -1, -1,
		-1, -1, -1, -1
	};
	
	private int acertos = 0;
	
	public GameOver gameOver;
	
	public JogoDaMemoria(final Activity ctx, final LinearLayout layout) {
		super(ctx);
		
		
		this.ctx = ctx;
		this.layout = layout;
		
		gameOver = new GameOver(ctx);
		gameOver.againBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1) {
					numCartas = 6;
					time = 15000;
					layout.removeAllViews();
					initVariables();
					sortearAnimais();
					screen();
					cT.cancel();
					createCountDown();
					gameOver.hide();
				}
		});
		gameOver.homeBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View p1) {
					layout.removeAllViews();
					new StartScreen(ctx, layout);
					sounds.player.stop();
					gameOver.hide();
				}
		});
		
		sounds = new Sounds(ctx);
		sounds.gameScreen();
		
		data.current_screen = "jogodamemoria";
		data.save(appFolderDir + "/current_screen.dat", data.current_screen.getBytes());
		
		
		initVariables();
		sortearAnimais();
		screen();
		createCountDown();
		
	}
	
	private void createCountDown() {
		cT = new CountDownTimer(time, 1000) {

			@Override
			public void onTick(long p1) {
				String min = String.format("%02d", p1 / 60000);
				int seg = (int) ((p1 % 60000) / 1000);
				timer.setText(min + ":" + String.format("%02d", seg));
				time -= 1000;
			}

			@Override
			public void onFinish() {
				timer.setText("00:00");
				gameOver.show();
				gameOver.setResult(acertos, -1);
			}
			
		};
		cT.start();
	}
	
	private void initVariables() {
		//cartasDesviradas[]
		cartasDesviradas[0] = -1;
		cartasDesviradas[1] = -1;

		//paresAcertados[]
		paresAcertados = new int[numCartas];
		for(int i = 0; i < paresAcertados.length; i++) {
			paresAcertados[i] = -1;
		}

		//currentCartasAnimais[]
		currentCartasAnimais = new int[numCartas / 2];
		for(int i = 0; i < currentCartasAnimais.length; i++) {
			currentCartasAnimais[i] = -1;
		}

		//animaisCartasPosicoes[]
		animaisCartasPosicoes = new int[numCartas];
		for(int i = 0; i < currentCartasAnimais.length; i++) {
			currentCartasAnimais[i] = -1;
		}
	}
	
	private void screen() {
		try {
			jogoDaMemoriaLayout = new Layout(ctx, 0, 0, getScreenWidth(), getScreenHeight());
			jogoDaMemoriaLayout.setOrientation(Layout.VERTICAL);
			layout.addView(jogoDaMemoriaLayout);
			
			Layout topBar = new Layout(ctx, 0, 0, getScreenWidth(), getScreenHeight() / 10);
			topBar.setBackgroundDrawable(loadTexture(ctx, "gui/barra_topo.png"));
			topBar.setGravity(Gravity.CENTER);
			jogoDaMemoriaLayout.addView(topBar);
			
			timer = new Label(ctx, 0, 0, "");
			timer.setTextColor(Color.WHITE);
			timer.setTextSize(20);
			topBar.addView(timer);
			
			Layout cartasLayout = new Layout(ctx, 0, getScreenHeight() / 8, getScreenWidth(), getScreenHeight() / 5 * 2 + getScreenHeight() / 20);
			cartasLayout.setOrientation(LinearLayout.VERTICAL);
			jogoDaMemoriaLayout.addView(cartasLayout);

			//linha de cartas
			for(int i = 0; i < 2; i++) {
				cartasLayoutLinha[i] = new Layout(ctx, 0, getScreenHeight() / 40, getScreenWidth(), (getScreenWidth() / ((numCartas + 3) / 2)) + getScreenHeight() / 24);
				cartasLayoutLinha[i].setGravity(Gravity.CENTER_HORIZONTAL);
				cartasLayout.addView(cartasLayoutLinha[i]);
			}

			//cartas
			int currentCartasLinha = 0;
			cartas = new Layout[numCartas];
			for(int i = 0; i < numCartas; i++) {
				if(i == Math.floor(numCartas / 2)) {
					currentCartasLinha++;
				}
				cartas[i] = new Layout(ctx, (getScreenWidth() - (getScreenWidth() / (((numCartas + 3) / 2) / 2))) / (numCartas * 4) , 0, getScreenWidth() / ((numCartas + 3) / 2), (getScreenWidth() / ((numCartas + 3) / 2)) + getScreenHeight() / 24);
				cartas[i].setBackgroundDrawable(loadTexture(ctx, "gui/carta_atras.png"));
				cartas[i].setId(i);
				cartas[i].setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View p1) {
							if(carta_desvirada(p1.getId()) == false) {
								setCartasClickable(false);
								applyRotation(p1, carta_desvirada(p1.getId()));
								if(cartasDesviradas[0] == -1)
									cartasDesviradas[0] = p1.getId();
								else 
									cartasDesviradas[1] = p1.getId();
									
								p1.postDelayed(new Runnable() {

										@Override
										public void run() {
											if(cartasDesviradas[0] != -1 && cartasDesviradas[1] != -1) {
												if(isPar(animaisCartasPosicoes[cartasDesviradas[0]], animaisCartasPosicoes[cartasDesviradas[1]]) == true) {
													setCartasClickable(cartasDesviradas[0], false);
													setCartasClickable(cartasDesviradas[1], false);
													//push
													pushValueOnparesAcertados(cartasDesviradas[0]);
													pushValueOnparesAcertados(cartasDesviradas[1]);

													cartasDesviradas[0] = -1;
													cartasDesviradas[1] = -1;
												} else {
													applyRotation(cartas[cartasDesviradas[0]], true);
													applyRotation(cartas[cartasDesviradas[1]], true);

													cartasDesviradas[0] = -1;
													cartasDesviradas[1] = -1;
												}
											}
											
											setCartasClickable(true);
											//all pares are correct
											if(isGameOver() == true) {
												acertos++;
												if(acertos % 8 == 0 && numCartas < 10) {
													numCartas += 2;
												}
												
												if(numCartas == 6)
													time = 15000;
												else if(numCartas == 8)
													time = 25000;
												else if(numCartas == 10)
													time = 30000;
												
												layout.removeAllViews();
												initVariables();
												sortearAnimais();
												screen();
												cT.cancel();
												createCountDown();
											}
										}
									
								}, animCartas.getDuration());
							}
						}
				});
				cartasLayoutLinha[currentCartasLinha].addView(cartas[i]);
			}
			
		} catch(IOException e) {}
	}
	
	//aplica a rotação nas cartas
	private void applyRotation(final View view, boolean virado) {
		animCartas = new Flip3dAnimation(view, virado);
		animCartas.applyPropertiesInRotation(600);
		view.startAnimation(animCartas);
		view.postDelayed(new Runnable() {

				@Override
				public void run() {
					if(carta_desvirada(view.getId())) {
						//vira a carta e pega o animal daquela correpondente carta
						view.setBackgroundDrawable(drawCartaFrente(animais[animaisCartasPosicoes[view.getId()]][1]));
					} else {
						try {
							view.setBackgroundDrawable(loadTexture(ctx, "gui/carta_atras.png"));
						} catch(IOException e) {}
					}
				}


			}, animCartas.getDuration() - 200);
	}
	
	//rotorna se a carta estar desvirada
	public boolean carta_desvirada(int id) {
		for(int i = 0; i < cartasDesviradas.length; i++) {
			if(cartasDesviradas[i] == id) {
				return true;
			}
		}
		return false;
	}
	
	
	//desenha a frente da carta
	private BitmapDrawable drawCartaFrente(String animalTexture) {
		Bitmap blank = Bitmap.createBitmap(172, 226, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(blank);

		try {
			//carta desvirada
			canvas.drawBitmap(BitmapFactory.decodeStream(ctx.getAssets().open("gui/Carta_Frente.png")), 0, 0, null);
			//animal
			canvas.drawBitmap(BitmapFactory.decodeStream(ctx.getAssets().open(animalTexture)), (canvas.getWidth() - 149) / 2, (canvas.getHeight() - 136) / 2, null);
		} catch(IOException e) {}

		return new BitmapDrawable(blank);
	}
	
	private void sortearAnimais() {
		for(int i = 0; i < numCartas / 2; i++) {
			if(currentCartasAnimais[i] == -1) {
				int random = (int) Math.floor((Math.random() * animais.length));
				//verifica se o animal ja foi sorteado alguma vez, caso sim, sorteia outro
				while(isSorteado(random) == true && i > 0) {
					random = (int) Math.floor((Math.random() * animais.length));
				}
				currentCartasAnimais[i] = random;
			}
		}

		//define a posição de cada animal nas cartas (em ordem)
		int n = 0;
		for(int i = 0; i < animaisCartasPosicoes.length; i++) {
			if(i % 2 == 0 && i > 0) {
				n++;
			}
			animaisCartasPosicoes[i] = currentCartasAnimais[n];
		}

		//random as posicoes das cartas
		randomAnimalPosition();
	}
	
	//retorna a posição da carta na array cartas_desviradas
	public int getCartaDesviradaIndex(int id) {
		for(int i = 0; i < cartasDesviradas.length; i++) {
			if(cartasDesviradas[i] == id) {
				return i;
			}
		}
		return -1;
	}
	

	//random as posicoes das cartas
	private void randomAnimalPosition() {
		ArrayList<Integer> list = new ArrayList<>();
		for(int i = 0; i < animaisCartasPosicoes.length; i++) {
			list.add(animaisCartasPosicoes[i]);
		}

		Collections.shuffle(list);

		for(int i = 0; i < list.size(); i++) {
			animaisCartasPosicoes[i] = list.get(i);
		}
	}



	private boolean allPositionRandom() {
		for(int i = 0; i < animaisCartasPosicoes.length; i++) {
			if(animaisCartasPosicoes[i] == -1) {
				return false;
			}
		}
		return true;
	}

	//retorna se um animal ja foi sorteado
	private boolean isSorteado(int index) {
		for(int i = 0; i < currentCartasAnimais.length; i++) {
			if(currentCartasAnimais[i] == index) {
				return true;
			}
		}
		return false;
	}
	
	private void ifgameover() {
		//fim de jogo
		if(isGameOver() == true) {
			//Toast.makeText(ctx, "Fim de jogo", Toast.LENGTH_SHORT).show();
			acertos++;

			layout.removeView(jogoDaMemoriaLayout);
			
			initVariables();
			sortearAnimais();
			screen();
		}
	}
	
	private boolean haveCartasOnPosition(int index) {
		for(int i = 0; i < animaisCartasPosicoes.length; i++) {
			if(animaisCartasPosicoes[i] == index) {
				return true;
			}
		}
		return false;
	}

	//verifica se as cartas sao iguais
	private boolean isPar(int index1, int index2) {
		return (index1 == index2);
	}

	//define as cartas clicaveis
	private void setCartasClickable(boolean clickable) {
		for(int i = 0; i < cartas.length; i++) {
			if(cartaAcertada(i) == false) {
				cartas[i].setClickable(clickable);
			}
		}
	}

	private boolean cartaAcertada(int index) {
		for(int i = 0; i < paresAcertados.length; i++) {
			if(paresAcertados[i] == index) {
				return true;
			}
		}
		return false;
	}

	//define as cartas clicavel
	private void setCartasClickable(int cartaIndex, boolean clickable) {
		cartas[cartaIndex].setClickable(clickable);
	}

	//adiciona um valor na array paresAcertados
	private void pushValueOnparesAcertados(int value) {
		for(int i = 0; i < paresAcertados.length; i++) {
			if(paresAcertados[i] == -1) {
				paresAcertados[i] = value;
				break;
			}
		}
	}

	//verifica se o jogo terminou
	private boolean isGameOver() {
		for(int i = 0; i < paresAcertados.length; i++) {
			if(paresAcertados[i] == -1) {
				return false;
			}
		}
		return true;
	}

	//push a array int
	private int[] push(int[] array, int valor) {
		int array1[] = new int[array.length + 1];
		for(int i = 0; i < array.length; i++) {
			array1[i] = array[i];
		}
		array1[array.length] = valor;
		return array1;
	}

	//push a array String
	private String[] push(String[] array, String valor) {
		String array1[] = new String[array.length + 1];
		for(int i = 0; i < array.length; i++) {
			array1[i] = array[i];
		}
		array1[array.length] = valor;
		return array1;
	}
	
}
