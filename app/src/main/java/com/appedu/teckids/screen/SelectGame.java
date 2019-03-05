package com.appedu.teckids.screen;

import android.app.*;
import android.widget.*;
import com.appedu.teckids.gui.*;
import java.io.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;

public class SelectGame extends Gui {
	private Activity ctx;
	private Layout selectGameLayout;
	private LinearLayout layout;
	
	public QuemSouEu quemSouEu;
	public AdvinheACor advinheACor;
	public TheNumbers theNumbers;
	public FormasGeometricas formasGeometricas;
	public SeparandoAsSilabas separandoAsSilabas;
	public Desenhando Desenhando;
	public JogoDaMemoria jogoDaMemoria;
	
	private Animation anim;

	public SelectGame(Activity ctx, LinearLayout layout) {
		super(ctx);

		this.ctx = ctx;
		this.layout = layout;
		
		anim = new TranslateAnimation(-getScreenWidth(), 0, 0, 0);
		anim.setDuration(600);
		
		screen();
		
		data.current_screen = "select_game";
		data.save(appFolderDir + "/current_screen.dat", data.current_screen.getBytes());
	}

	private void screen() {
		try {
			selectGameLayout = new Layout(ctx, 0, 0, getScreenWidth(), getScreenHeight());
			selectGameLayout.setOrientation(LinearLayout.VERTICAL);
			selectGameLayout.setBackgroundDrawable(loadTexture(ctx, "gui/background_black_opacity.png"));
			layout.addView(selectGameLayout);

			//quem sou eu
			JButton quemSouEuBtn = new JButton(ctx, 0, getScreenHeight() / 30, getScreenWidth(), getScreenHeight() / 9);
			quemSouEuBtn.setBackgroundDrawable(loadTexture(ctx, "gui/quemsoueu.png"));
			quemSouEuBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View p1) {
						layout.removeAllViews();
						quemSouEu = new QuemSouEu(ctx, layout);
					}
				
			});
			quemSouEuBtn.startAnimation(anim);
			selectGameLayout.addView(quemSouEuBtn);
			
			//advinhe a cor
			JButton advinheACorBtn = new JButton(ctx, 0, getScreenHeight() / 30, getScreenWidth(), getScreenHeight() / 9);
			advinheACorBtn.setBackgroundDrawable(loadTexture(ctx, "gui/advinheacor.png"));
			advinheACorBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View p1) {
						layout.removeAllViews();
						advinheACor = new AdvinheACor(ctx, layout);
					}

			});
			advinheACorBtn.startAnimation(anim);
			selectGameLayout.addView(advinheACorBtn);
			
			//the numbers
			JButton theNumbersBtn = new JButton(ctx, 0, getScreenHeight() / 30, getScreenWidth(), getScreenHeight() / 9);
			theNumbersBtn.setBackgroundDrawable(loadTexture(ctx, "gui/thenumbers.png"));
			theNumbersBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View p1) {
						layout.removeAllViews();
						theNumbers = new TheNumbers(ctx, layout);
					}

				});
			theNumbersBtn.startAnimation(anim);
			selectGameLayout.addView(theNumbersBtn);
			
			//jogo da memoria
			JButton jogoDaMemoriaBtn = new JButton(ctx, 0, getScreenHeight() / 30, getScreenWidth(), getScreenHeight() / 9);
			jogoDaMemoriaBtn.setBackgroundDrawable(loadTexture(ctx, "gui/jogodamemoria.png"));
			jogoDaMemoriaBtn.startAnimation(anim);
			jogoDaMemoriaBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View p1) {
						layout.removeAllViews();
						jogoDaMemoria = new JogoDaMemoria(ctx, layout);
					}

				});
			selectGameLayout.addView(jogoDaMemoriaBtn);
			
			//quem sou eu
			JButton formasGeometricasBtn = new JButton(ctx, 0, getScreenHeight() / 30, getScreenWidth(), getScreenHeight() / 9);
			formasGeometricasBtn.setBackgroundDrawable(loadTexture(ctx, "gui/formasGeometricas.png"));
			formasGeometricasBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View p1) {
						layout.removeAllViews();
						formasGeometricas = new FormasGeometricas(ctx, layout);
					}

				});
			formasGeometricasBtn.startAnimation(anim);
			selectGameLayout.addView(formasGeometricasBtn);
			
			//separando as silabas
			JButton separandoAsSilabasBtn = new JButton(ctx, 0, getScreenHeight() / 30, getScreenWidth(), getScreenHeight() / 9);
			separandoAsSilabasBtn.setBackgroundDrawable(loadTexture(ctx, "gui/separandoAsSilabas.png"));
			separandoAsSilabasBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View p1) {
						layout.removeAllViews();
						separandoAsSilabas = new SeparandoAsSilabas(ctx, layout);
					}

				});
			separandoAsSilabasBtn.startAnimation(anim);
			selectGameLayout.addView(separandoAsSilabasBtn);
			
			//desenhando
			JButton desenhando = new JButton(ctx, 0, getScreenHeight() / 30, getScreenWidth(), getScreenHeight() / 9);
			desenhando.setBackgroundDrawable(loadTexture(ctx, "gui/desenhando.png"));
			desenhando.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View p1) {
						layout.removeAllViews();
						Desenhando = new Desenhando(ctx, layout);
					}

				});
			desenhando.startAnimation(anim);
			selectGameLayout.addView(desenhando);
		} catch(IOException e) {}
	}
}
