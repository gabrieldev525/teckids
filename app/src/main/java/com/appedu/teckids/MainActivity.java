package com.appedu.teckids;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import com.appedu.teckids.gui.*;
import com.appedu.teckids.screen.*;
import java.io.*;
import android.support.v4.content.*;
import android.*;
import android.content.pm.*;
import android.support.v4.app.*;
import android.content.*;

public class MainActivity extends Activity 
{
	private LinearLayout mainLayout;
	private Activity ctx = MainActivity.this;
	private Gui gui = new Gui(ctx);
	
	public StartScreen startScreen;
	
	private static final int REQUEST_CODE = 1;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		
		if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			//explain the ppermission
			if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
				AlertDialog.Builder dlg = new AlertDialog.Builder(ctx);
				dlg.setTitle("permissões");
				dlg.setMessage("A permission solicitada é necessaria para armazenar no seu dispositivo alguns dados do app. Deseja permitir?");
				dlg.setPositiveButton("sim", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface p1, int p2)
						{
							ActivityCompat.requestPermissions(ctx, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
						}
				});
				dlg.setNegativeButton("não", null);
				dlg.setCancelable(false);
				dlg.show();
			} else {
				ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
			}
		} else {
		
			File file = new File(System.getenv("EXTERNAL_STORAGE") + "/Android/data/com.appedu.teckids");
			if(!file.exists())
				file.mkdirs();
        
			mainLayout = new LinearLayout(ctx);
			setContentView(mainLayout);
		
			startScreen = new StartScreen(ctx, mainLayout);
		}
    }

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		if(requestCode == REQUEST_CODE) {
			for(int i = 0; i < permissions.length; i++) {
				if(permissions[i].equalsIgnoreCase(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
					File file = new File(System.getenv("EXTERNAL_STORAGE") + "/Android/data/com.appedu.teckids");
					if(!file.exists())
						file.mkdirs();

					mainLayout = new LinearLayout(ctx);
					setContentView(mainLayout);

					startScreen = new StartScreen(ctx, mainLayout);
				}
			}
		}
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
	}
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == event.KEYCODE_BACK) {
			if(ContextCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
			String currentScreen = gui.data.load("/Android/data/com.appedu.teckids/current_screen.dat");
			
			if(currentScreen.equals("home_screen")) {
				finish();
			} else if(currentScreen.equals("select_game")) {
				mainLayout.removeAllViews();
				startScreen = new StartScreen(ctx, mainLayout);
			} else if(currentScreen.equals("quemsoueu")) {
				if(startScreen.selectGame.quemSouEu.gameOver.showed == true) {
					startScreen.selectGame.quemSouEu.gameOver.hide();
					//Toast.makeText(ctx, "ok", Toast.LENGTH_LONG).show();
				}
				startScreen.selectGame.quemSouEu.sounds.player.stop();
				mainLayout.removeAllViews();
				startScreen = new StartScreen(ctx, mainLayout);
			} else if(currentScreen.equals("advinheacor")) {
				if(startScreen.selectGame.advinheACor.gameOver.showed == true) {
					startScreen.selectGame.advinheACor.gameOver.hide();
				}
				startScreen.selectGame.advinheACor.sounds.player.stop();
				mainLayout.removeAllViews();
				startScreen = new StartScreen(ctx, mainLayout);
			} else if(currentScreen.equals("thenumbers")) {
				if(startScreen.selectGame.theNumbers.gameOver.showed == true) {
					startScreen.selectGame.theNumbers.gameOver.hide();
				}
				startScreen.selectGame.theNumbers.sounds.player.stop();
				mainLayout.removeAllViews();
				startScreen = new StartScreen(ctx, mainLayout);
			} else if(currentScreen.equals("formasgeometricas")) {
				if(startScreen.selectGame.formasGeometricas.gameOver.showed == true) {
					startScreen.selectGame.formasGeometricas.gameOver.hide();
				}
				startScreen.selectGame.formasGeometricas.sounds.player.stop();
				mainLayout.removeAllViews();
				startScreen = new StartScreen(ctx, mainLayout);
			} else if(currentScreen.equals("separandoassilabas")) {
				if(startScreen.selectGame.separandoAsSilabas.gameOver.showed == true) {
					startScreen.selectGame.separandoAsSilabas.gameOver.hide();
				}
				startScreen.selectGame.separandoAsSilabas.sounds.player.stop();
				mainLayout.removeAllViews();
				startScreen = new StartScreen(ctx, mainLayout);
			} else if(currentScreen.equals("desenhando")) {
				startScreen.selectGame.Desenhando.sounds.player.stop();
				mainLayout.removeAllViews();
				startScreen = new StartScreen(ctx, mainLayout);
			} else if(currentScreen.equals("jogodamemoria")) {
				if(startScreen.selectGame.jogoDaMemoria.gameOver.showed == true) {
					startScreen.selectGame.jogoDaMemoria.gameOver.hide();
				}
				startScreen.selectGame.jogoDaMemoria.cT.cancel();
				startScreen.selectGame.jogoDaMemoria.sounds.player.stop();
				mainLayout.removeAllViews();
				startScreen = new StartScreen(ctx, mainLayout);
			}
		}
		} else {
			finish();
		}
		return false;
	}

	@Override
	protected void onPause() {
		if(ContextCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
			
		String currentScreen = gui.data.load("/Android/data/com.appedu.teckids/current_screen.dat");
		
		if(currentScreen.equals("quemsoueu")) {
			startScreen.selectGame.quemSouEu.sounds.player.pause();
		} else if(currentScreen.equals("advinheacor")) {
			startScreen.selectGame.advinheACor.sounds.player.pause();
		} else if(currentScreen.equals("thenumbers")) {
			startScreen.selectGame.theNumbers.sounds.player.pause();
		} else if(currentScreen.equals("formasgeometricas")) {
			startScreen.selectGame.formasGeometricas.sounds.player.pause();
		} else if(currentScreen.equals("separandoassilabas")) {
			startScreen.selectGame.separandoAsSilabas.sounds.player.pause();
		} else if(currentScreen.equals("desenhando")) {
			startScreen.selectGame.Desenhando.sounds.player.pause();
		} else if(currentScreen.equals("home_screen")) {
			startScreen.sounds.player.pause();
		} else if(currentScreen.equals("jogodamemoria")) {
			startScreen.selectGame.jogoDaMemoria.sounds.player.pause();
		}
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		if(ContextCompat.checkSelfPermission(ctx, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
			
		String currentScreen = gui.data.load("/Android/data/com.appedu.teckids/current_screen.dat");
		
		if(currentScreen.equals("quemsoueu")) {
			startScreen.selectGame.quemSouEu.sounds.player.start();
		} else if(currentScreen.equals("advinheacor")) {
			startScreen.selectGame.advinheACor.sounds.player.start();
		} else if(currentScreen.equals("thenumbers")) {
			startScreen.selectGame.theNumbers.sounds.player.start();
		} else if(currentScreen.equals("formasgeometricas")) {
			startScreen.selectGame.formasGeometricas.sounds.player.start();
		} else if(currentScreen.equals("separandoassilabas")) {
			startScreen.selectGame.separandoAsSilabas.sounds.player.start();
		} else if(currentScreen.equals("desenhando")) {
			startScreen.selectGame.Desenhando.sounds.player.start();
		} else if(currentScreen.equals("home_screen")) {
			startScreen.sounds.player.start();
		} else if(currentScreen.equals("jogodamemoria")) {
			startScreen.selectGame.jogoDaMemoria.sounds.player.start();
		}
		}
		super.onResume();
	}
	
	
}
