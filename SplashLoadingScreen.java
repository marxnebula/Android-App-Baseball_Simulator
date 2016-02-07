package com.dorf.skeleton;

import com.dorf.framework.Game;
import com.dorf.framework.Graphics;
import com.dorf.framework.Screen;
import com.dorf.framework.Graphics.ImageFormat;


 /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	- Class used to display the splash screen asset.
	- Loads the loading screen.

	  Code by Jordan Marx (2014)
  */

public class SplashLoadingScreen extends Screen {
	public SplashLoadingScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();

		// Loads the splash image
		Assets.splash = g.newImage("DorfINC.png", ImageFormat.RGB565);

        // Sets the screen to loading screen
		game.setScreen(new LoadingScreen(game));
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();

		// Draw the splash asset
		g.drawImage(Assets.splash, 0, 0);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {

	}
}
