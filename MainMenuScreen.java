package com.dorf.skeleton;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import android.R;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.widget.EditText;

import com.dorf.framework.Game;
import com.dorf.framework.Graphics;
import com.dorf.framework.Image;
import com.dorf.framework.Music;
import com.dorf.framework.Screen;
import com.dorf.framework.Input.TouchEvent;
import com.dorf.framework.Sound;
import com.dorf.framework.implementation.AndroidMusic;

public class MainMenuScreen extends Screen {

	// Buttons
	private Button startButton = new Button(238, 623, Assets.playBall,
			Assets.playBall, 30, "");
	private Button aboutButton = new Button(238, 719, Assets.aboutButton,
			Assets.aboutButton, 30, "");
	private Button backButton = new Button(234, 694, Assets.backImage,
			Assets.backImage, 30, "");
	private Button exitYesButton = new Button(180, 425, Assets.yesExitImage,
			Assets.yesExitImage, 30, "");
	private Button exitNoButton = new Button(296, 425, Assets.noExitImage,
			Assets.noExitImage, 30, "");

	// Sound effect
	private Sound clickPlayBall = Assets.clickPlayButton;
	private Music mainMenuMusic = Assets.gameTheme;
	private MediaPlayer mp;

	// Animation and current images
	private Animation signAnimation;
	private Image currentSignSprite;
	private Animation playButtonAnimation;
	private Image currentPlayButtonSprite;
	private Animation aboutButtonAnimation;
	private Image currentAboutButtonSprite;
	private Animation backButtonAnimation;
	private Image currentBackButtonSprite;

	// Booleans
	private boolean startPressed = false;
	private boolean aboutPressed = false;
	private boolean previousAboutPressed = false;
	private boolean playMusicOnce = true;
	private boolean isExit = false;

	public MainMenuScreen(Game game) {
		super(game);

	//	mp = MediaPlayer.create(this, R.raw.main);

		// Button color
		startButton.setRestColor(Color.WHITE);
		aboutButton.setRestColor(Color.WHITE);

		// Boolean set
		startPressed = false;
		playMusicOnce = true;

		// Animation for title sign
		int animTime = 5;
		signAnimation = new Animation();
		signAnimation.addFrame(Assets.sign2, animTime);
		signAnimation.addFrame(Assets.sign3, animTime);
		signAnimation.addFrame(Assets.sign4, animTime);
		signAnimation.addFrame(Assets.sign5, animTime);
		signAnimation.addFrame(Assets.sign6, animTime);
		signAnimation.addFrame(Assets.sign7, animTime);
		signAnimation.addFrame(Assets.sign6, animTime);
		signAnimation.addFrame(Assets.sign5, animTime);
		signAnimation.addFrame(Assets.sign4, animTime);
		signAnimation.addFrame(Assets.sign3, animTime);
		signAnimation.addFrame(Assets.sign2, animTime);

		// Animation for play button
		int buttonAnimationTime = 60;
		playButtonAnimation = new Animation();
		playButtonAnimation.addFrame(Assets.playBallWhite, buttonAnimationTime);
		playButtonAnimation.addFrame(Assets.playBall, buttonAnimationTime);

		// Animation for about button
		aboutButtonAnimation = new Animation();
		aboutButtonAnimation.addFrame(Assets.aboutButtonWhite, buttonAnimationTime);
		aboutButtonAnimation.addFrame(Assets.aboutButton, buttonAnimationTime);

		// Animation for back button
		backButtonAnimation = new Animation();
		backButtonAnimation.addFrame(Assets.backImageWhite, buttonAnimationTime);
		backButtonAnimation.addFrame(Assets.backImage, buttonAnimationTime);


		// Assign current sprites
		currentSignSprite = Assets.playBall;
		currentPlayButtonSprite = Assets.playBall;
		currentAboutButtonSprite = Assets.aboutButton;
		currentBackButtonSprite = Assets.backImage;

	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) 
			{
				// If you haven't pressed the back button
				if(!isExit) {

					// Play button
					if (startButton.touchEvent(event) && !aboutPressed) {
						// Start game boolean
						startPressed = true;
					}

					// About button
					if (aboutButton.touchEvent(event)) {
						aboutPressed = true;
					}

					// Back button
					if (backButton.touchEvent(event) && previousAboutPressed) {
						aboutPressed = false;
					}
				}

				// Yes button
				if(exitYesButton.touchEvent(event) && isExit)
				{
					// Exits app
					android.os.Process.killProcess(android.os.Process.myPid());
				}

				// No button
				if(exitNoButton.touchEvent(event) && isExit)
				{
					// Turn off exit option
					isExit = false;
				}
			}
		}
		
		// Get current sign image and update animation
		currentSignSprite = signAnimation.getImage();
		signAnimation.update((long)deltaTime);

		// Get current play image and update animation
		currentPlayButtonSprite = playButtonAnimation.getImage();
		playButtonAnimation.update((long)deltaTime);

		// Get current about image and update animation
		currentAboutButtonSprite = aboutButtonAnimation.getImage();
		aboutButtonAnimation.update((long)deltaTime);

		// Get current back image and update animation
		currentBackButtonSprite = backButtonAnimation.getImage();
		backButtonAnimation.update((long)deltaTime);

		
		// Previous booleans
		previousAboutPressed = aboutPressed;

		// This was not working in the initialize step so I put it in update
		// If you set loop to true it plays music twice... why?
		if(playMusicOnce)
		{
			this.mainMenuMusic.play();
			playMusicOnce = false;
		}

		// If you start game then stop main music and play sound clip
		if (startPressed) {
			mainMenuMusic.setLooping(false);
			mainMenuMusic.stop();
			mainMenuMusic.setVolume(0);
			clickPlayBall.play(1);

			// Make the screen wait for a bit
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				System.out.println("Main Screen Sleep Unsuccessful");
			}

			// Set screen to GameScreen
			game.setScreen(new GameScreen(game));
		}



	}

	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawImage(Assets.menuBackground1, 0, 0);
		g.drawImage(currentSignSprite, 24, 3);
		aboutButton.draw(g, currentAboutButtonSprite);
		startButton.draw(g, currentPlayButtonSprite);
		
		if(aboutPressed)
		{
			g.drawImage(Assets.aboutImage, 32, 275);
			backButton.draw(g, currentBackButtonSprite);
		}

		if(isExit)
		{
			g.drawImage(Assets.exitImage, 115, 250);
			exitYesButton.draw(g, Assets.yesExitImage);
			exitNoButton.draw(g, Assets.noExitImage);
		}

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
		// Are you sure you want to quit?

		//android.os.Process.killProcess(android.os.Process.myPid());
		isExit = true;

	}

	private void nullify() {

		// Call garbage collector to clean up memory.
		System.gc();

	}
}
