package com.dorf.skeleton;

import com.dorf.framework.Game;
import com.dorf.framework.Graphics;
import com.dorf.framework.Screen;
import com.dorf.framework.Graphics.ImageFormat;

/*
	Used to show company logo
 */

public class SplashLoadingScreen extends Screen {
	public SplashLoadingScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		Assets.splash = g.newImage("DorfINC.png", ImageFormat.RGB565);

		game.setScreen(new LoadingScreen(game));
	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
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
