package com.appedu.teckids;

import android.app.*;
import android.content.res.*;
import android.media.*;
import java.io.*;

public class Sounds
{
	public MediaPlayer player;
	private Activity ctx;
	
	public Sounds(Activity ctx) {
		this.ctx = ctx;
		
		player = new MediaPlayer();
	}
	public void startScreen() {
		try {
			AssetFileDescriptor afd = ctx.getAssets().openFd("sounds/tela_inicial.mp3");
			player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			player.prepare();
			player.setLooping(true);
			player.start();
		} catch(IOException e) {}
	}
	public void gameScreen() {
		try {
			AssetFileDescriptor afd = ctx.getAssets().openFd("sounds/games.mp3");
			player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			player.prepare();
			player.setLooping(true);
			player.start();
		} catch(IOException e) {}
	}
}
