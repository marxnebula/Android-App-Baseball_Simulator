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

/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	- This is the main menu screen code.
	- It plays music while the user can select Play or About.
	- Play takes you to the game screen class.
	- About displays information about the game and who made it.

	  Code by Jordan Marx (2014)
  */

public class MainMenuScreen extends Screen {

	// Start button
	private Button startButton = new Button(238, 623, Assets.playBall,
			Assets.playBall, 30, "");

    // About button
	private Button aboutButton = new Button(238, 719, Assets.aboutButton,
			Assets.aboutButton, 30, "");

    // Back button
	private Button backButton = new Button(234, 694, Assets.backImage,
			Assets.backImage, 30, "");

    // Exit buttons
    private Button exitYesButton = new Button(180, 425, Assets.yesExitImage,
			Assets.yesExitImage, 30, "");
	private Button exitNoButton = new Button(296, 425, Assets.noExitImage,
			Assets.noExitImage, 30, "");

	// Sound effects and music
	private Sound clickPlayBall = Assets.clickPlayButton;
	private Music mainMenuMusic = Assets.gameTheme;
	private MediaPlayer mp;

	// Sign animation
	private Animation signAnimation;
	private Image currentSignSprite;

	// Play animation
	private Animation playButtonAnimation;
	private Image currentPlayButtonSprite;

	// About animation
	private Animation aboutButtonAnimation;
	private Image currentAboutButtonSprite;

	// Back animation
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

		// Set start button rest color
		startButton.setRestColor(Color.WHITE);

		// Set about button rest color
		aboutButton.setRestColor(Color.WHITE);

		// Set booleans
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

		// Get all touch events
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);

			// If touch event is up
			if (event.type == TouchEvent.TOUCH_UP)
			{
			    // If you haven't pressed the back button
				if(!isExit) {
                    // If start button pressed and about not pressed
                    if(startButton.touchEvent(event) && !aboutPressed)
                    {
                        // Start game boolean is true
                        startPressed = true;
                    }

                    // If about button pressed
                    if(aboutButton.touchEvent(event))
                    {
                        // Set about pressed to true
                        aboutPressed = true;
                    }

                    // If back button pressed and previous about button pressed is flse
                    if(backButton.touchEvent(event) && previousAboutPressed)
                    {
                        // Set about pressed is false
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

		// Play the music only once
		if(playMusicOnce)
		{
			mainMenuMusic.play();
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

		// Draw the background
		g.drawImage(Assets.menuBackground1, 0, 0);

		// Draw the current sign
		g.drawImage(currentSignSprite, 24, 3);

		// Draw the about button
		aboutButton.draw(g, currentAboutButtonSprite);

		// Draw the start button
		startButton.draw(g, currentPlayButtonSprite);

		// If about is pressed
		if(aboutPressed)
		{
		    // Draw about image
			g.drawImage(Assets.aboutImage, 32, 275);

			// Draw the back button
			backButton.draw(g, currentBackButtonSprite);
		}

		// If back button pressed
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
	public void backButton() {
		//android.os.Process.killProcess(android.os.Process.myPid());
		isExit = true;

	}

    // Call garbage collector to clean up memory.
	private void nullify() {
		System.gc();

	}
}
