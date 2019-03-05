package com.appedu.teckids;

import android.os.*;
import android.util.*;
import java.io.*;

public class Utils
{
	public String appFolderDir = "/Android/data/com.appedu.teckids";
	
	public class Data
	{
		//current screen on app
		public String current_screen = "";
		
		
		public void save(String filename, byte[] data) {
			try {
				File file = new File(System.getenv("EXTERNAL_STORAGE"), filename);
				FileOutputStream outputStream;
				outputStream = new FileOutputStream(file);
				outputStream.write(data);
			} catch(IOException e) {}
		}
		public String load(String filename) {
			StringBuilder text = new StringBuilder();

			try {
				File file = new File(System.getenv("EXTERNAL_STORAGE") + "/" + filename);
				if(!file.exists()) {
					//Log.i("File", "O Arquivo " + file.getAbsolutePath() + "/" + file.getName() + " não existe");
					return null;
				}

				FileInputStream fIn;
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line;
				while((line = reader.readLine()) != null) {
					text.append(line);
					//text.append("\n");
				}
				reader.close();
			} catch(IOException e) {}
			return text.toString();
		}
	}
}
