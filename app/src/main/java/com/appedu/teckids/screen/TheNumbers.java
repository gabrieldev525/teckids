package com.appedu.teckids.screen;
import android.app.*;
import android.graphics.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.appedu.teckids.*;
import com.appedu.teckids.gui.*;
import java.io.*;
import java.util.*;
import android.os.*;

public class TheNumbers extends Gui
{
	private Activity ctx;
	private LinearLayout layout;
	private LinearLayout theNumbersLayout;
	private JButton resultBox[];
	Typeface fonteBromo;
	
	int num1,num2, result;
	int currentOptions[] = { -1, -1, -1, -1 };
	
	public Sounds sounds;
	
	private int acertos = 0;
	private int health = 3;
	private JButton heart;
	
	private Vibrator vibrator;
	
	public GameOver gameOver;
	
	public TheNumbers(final Activity ctx, final LinearLayout layout) {
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
					randomNumber();
					acertos = 0;
					health = 3;
					heart.setText(health + "");
					gameOver.hide();
				}
			});
		
		vibrator = (Vibrator) ctx.getSystemService("vibrator");
		
		sounds = new Sounds(ctx);
		sounds.gameScreen();
		
		fonteBromo = Typeface.createFromAsset(ctx.getAssets(), "fonts/BROMO.otf");

		data.current_screen = "thenumbers";
		data.save(appFolderDir + "/current_screen.dat", data.current_screen.getBytes());
		
		//screen();
		randomNumber();
	}

	private void screen() {
		try {
			theNumbersLayout = new Layout(ctx, 0, 0, getScreenWidth(), getScreenHeight());
			theNumbersLayout.setOrientation(Layout.VERTICAL);
			layout.addView(theNumbersLayout);

			//top
			Layout topBar = new Layout(ctx, 0, 0, getScreenWidth(), getScreenHeight() / 10);
			topBar.setBackgroundDrawable(loadTexture(ctx, "gui/barra_topo.png"));
			topBar.setGravity(Gravity.CENTER);
			theNumbersLayout.addView(topBar);
			
			heart = new JButton(ctx, 0, 0, getScreenWidth() / 13, getScreenHeight() / 18);
			heart.setBackgroundDrawable(loadTexture(ctx, "gui/heart.png"));
			heart.setText(health + "");
			heart.setTextColor(Color.WHITE);
			heart.setPadding(0, 0, 0, 0);
			topBar.addView(heart);
			
			//operacao
			Layout contaLayout = new Layout(ctx, 0, getScreenHeight() / 4, getScreenWidth(), getScreenHeight() / 6);
			contaLayout.setGravity(Gravity.CENTER);
			theNumbersLayout.addView(contaLayout);
			
			//num1
			Layout num1Layout = new Layout(ctx, 0, 0, getScreenWidth() / 3, getScreenHeight() / 7);
            num1Layout.setBackgroundDrawable(loadTexture(ctx, "numbers/" + num1 + ".png"));
            contaLayout.addView(num1Layout);
			
			//operacao
			Label opeTxt = new Label(ctx, getScreenWidth() / 70, 0, "+");
			opeTxt.setTextSize(getScreenWidth() / 6);
			opeTxt.setTypeface(fonteBromo);
			contaLayout.addView(opeTxt);
			//num2
			Layout num2Layout = new Layout(ctx, getScreenWidth() / 70, 0, getScreenWidth() / 3, getScreenHeight() / 7);
            num2Layout.setBackgroundDrawable(loadTexture(ctx, "numbers/" + num2 + ".png"));
            contaLayout.addView(num2Layout);
			
			//silaba
			int numResults = 4;
			Layout resultLayout = new Layout(ctx, (getScreenWidth() - (getScreenWidth() / 7 * numResults + getScreenWidth() / 70 * numResults)) / 2, getScreenHeight() / 10, getScreenWidth() / 7 * numResults + getScreenWidth() / 70 * numResults, getScreenHeight() / 13);
			resultLayout.setBackgroundColor(Color.parseColor("#217FC4"));
			resultLayout.setGravity(Gravity.CENTER);
			theNumbersLayout.addView(resultLayout);

			resultBox = new JButton[4];
			for(int a = 0; a < 4; a++) {
				resultBox[a] = new JButton(ctx, getScreenWidth() / 70, 0, getScreenWidth() / 7, getScreenHeight() / 13);
				resultBox[a].setBackgroundDrawable(loadTexture(ctx, "gui/Silaba.png"));
				resultBox[a].setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(final View p1) {
							if(Integer.parseInt(resultBox[p1.getId()].getText().toString()) == result) {
								randomNumber();
								acertos++;
							} else {
								health--;
								heart.setText(health + "");
								vibrator.vibrate(100);
								
								resultBox[p1.getId()].setTextColor(Color.RED);
								p1.postDelayed(new Runnable() {
										@Override
										public void run() {
											resultBox[p1.getId()].setTextColor(Color.BLACK);
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
				resultBox[a].setText(currentOptions[a] + "");
				resultBox[a].setPadding(0, 0, 0, 0);
				resultBox[a].setId(a);
				resultLayout.addView(resultBox[a]);
			}
		} catch(IOException e) {
			Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	
	private void randomNumber() {
		num1 = (int) Math.floor(Math.random() * 8);
		while(num1 == 0)
			num1 = (int) Math.floor(Math.random() * 8);
		num2 = (int) Math.floor(Math.random() * 8);
		while(num2 == 0)
			num2 = (int) Math.floor(Math.random() * 8);
		result = num1 + num2;
		currentOptions[0] = result;
		
		int options1 = (int) Math.floor(Math.random() * 19);
		while(isNumChosed(options1) == true)
			options1 = (int) Math.floor(Math.random() * 19);
		currentOptions[1] = options1;
		
		int options2 = (int) Math.floor(Math.random() * 19);
		while(isNumChosed(options2) == true)
			options2 = (int) Math.floor(Math.random() * 19);
		currentOptions[2] = options2;
		
		int options3= (int) Math.floor(Math.random() * 19);
		while(isNumChosed(options3) == true)
			options3 = (int) Math.floor(Math.random() * 19);
		currentOptions[3] = options3;
		
		randomNumPosition();
		layout.removeAllViews();
		screen();
	}
	
	private void randomNumPosition() {
		ArrayList<Integer> list = new ArrayList<>();
		for(int m = 0; m < currentOptions.length; m++) {
			list.add(currentOptions[m]);
		}
		Collections.shuffle(list);

		for(int m = 0; m < list.size(); m++) {
			currentOptions[m] = list.get(m);
		}
	}
	
	private boolean isNumChosed(int value) {
		for(int t = 0; t  < currentOptions.length; t++) {
			if(currentOptions[t] == value)
				return true;
		}
		return false;
	}
}
