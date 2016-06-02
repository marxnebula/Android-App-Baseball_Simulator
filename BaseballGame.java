package com.dorf.skeleton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.Log;
import com.dorf.framework.Screen;
import com.dorf.framework.implementation.AndroidGame;

/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	- First code that is run.
	- It sets the initial screen to be the splash screen.

	  Code by Jordan Marx (2014)
  */

public class BaseballGame extends AndroidGame {

	public static String map;
	boolean firstTimeCreate = true;

	@Override
	public Screen getInitScreen() {

        // If first time running game
		if (firstTimeCreate) {

            // Load the assets
			Assets.load(this);
			firstTimeCreate = false;
		}

        // Returns the splash loading screen
		return new SplashLoadingScreen(this);

	}

	@Override
	public void onBackPressed() {
		getCurrentScreen().backButton();
	}

	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append((line + "\n"));
			}
		} catch (IOException e) {
			Log.w("LOG", e.getMessage());
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				Log.w("LOG", e.getMessage());
			}
		}
		return sb.toString();
	}

	@Override
	public void onResume() {
		super.onResume();


	}

	@Override
	public void onPause() {
		super.onPause();

	}

}
