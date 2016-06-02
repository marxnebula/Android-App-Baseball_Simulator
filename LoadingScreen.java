package com.dorf.skeleton;

import com.dorf.framework.Game;
import com.dorf.framework.Graphics;
import com.dorf.framework.Graphics.ImageFormat;
import com.dorf.framework.Screen;

public class LoadingScreen extends Screen {
	public LoadingScreen(Game game) {

		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();

		// Background
		Assets.backgroundImage1 = g.newImage("game/background.png",
				ImageFormat.RGB565);
		Assets.backgroundImage2 = g.newImage("game/background2.png",
				ImageFormat.RGB565);

		// Drag buttons
		Assets.dragPlatform = g.newImage("game/DragPlatform.png",
				ImageFormat.RGB565);
		Assets.dragButton = g.newImage("game/DragonButton.png",
				ImageFormat.RGB565);
		
		// Check box
		Assets.checkBox = g.newImage("game/CheckBox.png",
				ImageFormat.RGB565);

		// Info box
		Assets.gameInfoBox = g.newImage("game/GameInfoBox.png",
				ImageFormat.RGB565);
		Assets.gameInfoBoxYellow = g.newImage("game/GameInfoBoxYellow.png",
				ImageFormat.RGB565);
		Assets.gameInfoBoxRed = g.newImage("game/GameInfoBoxRed.png",
				ImageFormat.RGB565);
		Assets.gameInfoDisplayBox = g.newImage("game/GameInfoDisplayBox.png",
				ImageFormat.RGB565);

		// Exit box
		Assets.exitImage = g.newImage("menu/ExitButton.png",
				ImageFormat.RGB565);
		Assets.yesExitImage = g.newImage("menu/YesButton.png",
				ImageFormat.RGB565);
		Assets.noExitImage = g.newImage("menu/NoButton.png",
				ImageFormat.RGB565);
		
		// XY Graph Box
		Assets.xyGraphBox = g.newImage("game/GraphBackground.png",
				ImageFormat.RGB565);
		
		// Ball Park Teams
		Assets.teamStatic = g.newImage("game/TeamStatic.png",
				ImageFormat.RGB565);
		
		// Selecting a park or field position
		Assets.clickedImage = g.newImage("game/ClickedImage.png",
				ImageFormat.RGB565);
		Assets.clickedImage2 = g.newImage("game/ClickedImage2.png",
				ImageFormat.RGB565);
		Assets.planetFieldStatic = g.newImage("game/PlanetFieldStatic.png",
				ImageFormat.RGB565);
		
		// Menu buttons
		Assets.playBall = g.newImage("menu/PlayBall.png",
				ImageFormat.RGB565);
		Assets.playBallWhite = g.newImage("menu/PlayBallWhite.png",
				ImageFormat.RGB565);
		Assets.aboutButton = g.newImage("menu/AboutButton.png",
				ImageFormat.RGB565);
		Assets.aboutButtonWhite = g.newImage("menu/AboutButtonWhite.png",
				ImageFormat.RGB565);
		Assets.aboutImage = g.newImage("menu/AboutBox.png",
				ImageFormat.RGB565);
		Assets.backImage = g.newImage("menu/BackButton.png",
				ImageFormat.RGB565);
		Assets.backImageWhite = g.newImage("menu/BackButtonWhite.png",
				ImageFormat.RGB565);
		
		// Sign animation
		Assets.sign1 = g.newImage("menu/sign/Sign1.png",
				ImageFormat.RGB565);
		Assets.sign2 = g.newImage("menu/sign/Sign2.png",
				ImageFormat.RGB565);
		Assets.sign3 = g.newImage("menu/sign/Sign3.png",
				ImageFormat.RGB565);
		Assets.sign4 = g.newImage("menu/sign/Sign4.png",
				ImageFormat.RGB565);
		Assets.sign5 = g.newImage("menu/sign/Sign5.png",
				ImageFormat.RGB565);
		Assets.sign6 = g.newImage("menu/sign/Sign6.png",
				ImageFormat.RGB565);
		Assets.sign7 = g.newImage("menu/sign/Sign7.png",
				ImageFormat.RGB565);
		Assets.sign8 = g.newImage("menu/sign/Sign8.png",
				ImageFormat.RGB565);
		
		// Home run sign
		Assets.homeRunSign = g.newImage("game/homeRunSign/HomeRunSign.png",
				ImageFormat.RGB565);
		Assets.homeRunSign1 = g.newImage("game/homeRunSign/HomeRunSign1.png",
				ImageFormat.RGB565);
		Assets.homeRunSign2 = g.newImage("game/homeRunSign/HomeRunSign2.png",
				ImageFormat.RGB565);
		Assets.homeRunSign3 = g.newImage("game/homeRunSign/HomeRunSign3.png",
				ImageFormat.RGB565);
		Assets.homeRunSign4 = g.newImage("game/homeRunSign/HomeRunSign4.png",
				ImageFormat.RGB565);
		
		// Graph Ball
		Assets.graphBall = g.newImage("game/GraphBall.png",
				ImageFormat.RGB565);


		// Menu images
		Assets.menuBackground1 = g.newImage("menu/TempTitleScreen4.png",
				ImageFormat.RGB565);
		Assets.menuButtonRest = g.newImage("menu/MenuButtonRest.png",
				ImageFormat.RGB565);
		Assets.menuButtonClicked = g.newImage("menu/MenuButtonClick.png",
				ImageFormat.RGB565);
		Assets.rightArrowRest = g.newImage("menu/RightArrow.png",
				ImageFormat.RGB565);
		Assets.rightArrowClicked = g.newImage("menu/RightArrow.png",
				ImageFormat.RGB565);
		Assets.leftArrowRest = g.newImage("menu/LeftArrow.png",
				ImageFormat.RGB565);
		Assets.leftArrowClicked = g.newImage("menu/LeftArrow.png",
				ImageFormat.RGB565);

		//Sounds
		Assets.gameTheme = game.getAudio().createMusic("mainMenuSong.mp3");
		Assets.clickPlayButton = game.getAudio().createSound("menu/PlayBall.mp3");
		Assets.homeRunSound = game.getAudio().createSound("game/homerun.mp3");



		// Make the screen wait for a bit
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("Splash Screen Sleep Unsuccessful");
		}

		game.setScreen(new MainMenuScreen(game));
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
