package com.dorf.skeleton;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.dorf.framework.Game;
import com.dorf.framework.Graphics;
import com.dorf.framework.Image;
import com.dorf.framework.Input.TouchEvent;
import com.dorf.framework.MathHelper;
import com.dorf.framework.Screen;
import com.dorf.framework.Sound;

 /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	- This is the game screen code.
	- The art displayed is an imitation of wriglet field old school sign.
	- Displayed is the trajectory of the graph and all its buttons.

	  Code by Jordan Marx (2014)
  */

public class GameScreen extends Screen {

    // enum of game state
	enum GameState {
		Ready, Calculating, Paused, GameOver, Options
	}

	// The starting gamestate
	GameState state = GameState.Ready;

	// Text size of all buttons
	float textSize = 30;

	// Boolean for air resitane hit box
	private boolean airResistanceHitBox = true;

	// Previous booleans
	private boolean previousBooleanFieldPosition = false;
	private boolean previousBooleanBallPark = false;
	private boolean previousBooleanPlanet = false;

	// Starting drag button position
	public int startingDragButtonPosition = 125;

	// Store the starting drag button position
	public int dragPosXInitialVelocity = startingDragButtonPosition;
	public int dragPosXLaunchAngle = startingDragButtonPosition;

	// Converts mph per pixels (.844)
	public double dragVelocityConvert = .844;

	// Converts degs per pixels (.379)
	public double dragLaunchAngleConvert = .379;

	// Info button
	public Button infoButton = new Button(440, 770, Assets.gameInfoBox,
			Assets.gameInfoBox, 30, "");

    // Back button
	private Button backButton = new Button(234, 624, Assets.backImage,
			Assets.backImage, 30, "");

	// Music
	public Sound homeRunSound = Assets.homeRunSound;

	// Boolean variables
	private Boolean homerun = false;
	private Boolean playOnce = true;
	private Boolean infoPressed = false;
	private Boolean previousAboutPressed = false;
	private Boolean isStartCanBePressed = true;

	// Info animation stuff
	private Animation infoButtonAnimation;
	private Image currentInfoButtonSprite;
	private Animation backButtonAnimation;
	private Image currentBackButtonSprite;


	// ==============================================================================================
	// MAIN GAME objects
	// ==============================================================================================

	// Images
	private Image main;
	private Image dragButton, dragPlatform;
	private Image xyGraphBox;
	private Image currentSignSprite;

	// Classes!
	private MathCalculator mathCalculator;
	private DrawXYGraph drawXYGraph;
	private DrawStats drawStats;
	private DrawButtonInterface drawButtonInterface;
	private BallPark selectedBallPark;
	private static Background background1, background2;

	// Animations
	private Animation homeRunAnimation;

	// Font
	Paint debugFont;

	// ==============================================================================================
	// MENU OBJECTS
	// ==============================================================================================


	// Rect
	private final Rect screenBox = new Rect(0, 0, 800, 480);


	// Load all the variables first
	public GameScreen(Game game) {
		super(game);

        // Set booleans
		homerun = false;
		playOnce = false;

		// Animation info button
		infoButtonAnimation = new Animation();
		infoButtonAnimation.addFrame(Assets.gameInfoBox, 60);
		infoButtonAnimation.addFrame(Assets.gameInfoBoxRed, 60);
		currentInfoButtonSprite = Assets.gameInfoBox;

		// Animation for back button
		backButtonAnimation = new Animation();
		backButtonAnimation.addFrame(Assets.backImageWhite, 60);
		backButtonAnimation.addFrame(Assets.backImage, 60);
		currentBackButtonSprite = Assets.backImage;

		// Drag Buttons
		dragPlatform = Assets.dragPlatform;
		dragButton = Assets.dragButton;

		// XY Graph Box
		xyGraphBox = Assets.xyGraphBox;

		// Background
		background1 = new Background(0, 0, -3, 2160, 800);
		background2 = new Background(0, 0, -0.5, 2400, 800);

		// Math class
		mathCalculator = new MathCalculator();

		// Graph XY class
		drawXYGraph = new DrawXYGraph();

		// Stats after trajectory
		drawStats = new DrawStats();

		// Draw buttons
		drawButtonInterface = new DrawButtonInterface();

		// Initialize ball park
		drawButtonInterface.setInitialBallPark(selectedBallPark);

		// Animation
		int animTime = 40;
		homeRunAnimation = new Animation();
		homeRunAnimation.addFrame(Assets.homeRunSign2, animTime);
		homeRunAnimation.addFrame(Assets.homeRunSign3, animTime);
		homeRunAnimation.addFrame(Assets.homeRunSign4, animTime);
		currentSignSprite = Assets.homeRunSign;


		// Debug font stuff
		debugFont = new Paint();
		debugFont.setTextSize(15);
		debugFont.setTextAlign(Paint.Align.LEFT);
		debugFont.setAntiAlias(true);
		debugFont.setColor(Color.YELLOW);

	}

	@Override
	public void update(float deltaTime) {

	    // Gets the touch events
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		// Update clouds
		background2.update();

		// Update info button
		currentInfoButtonSprite = infoButtonAnimation.getImage();
		infoButtonAnimation.update((long)deltaTime);

		// Update back button
		currentBackButtonSprite = backButtonAnimation.getImage();
		backButtonAnimation.update((long)deltaTime);


		// If game state is ready
		if (state == GameState.Ready)
		{
			updateReady(touchEvents, deltaTime);
		}
		// If game state is calculating
		if (state == GameState.Calculating)
		{
			updateCalculating(touchEvents, deltaTime);
		}
	}


	// Update when not calculating graph stuff
	private void updateReady(List<TouchEvent> touchEvents, float deltaTime) {

		// Get current image and update animation if home run
		if(!drawXYGraph.getHitsWall()
				&& (mathCalculator.getMaxDistance() >= drawXYGraph.getWallRectangle().left))
		{
			currentSignSprite = homeRunAnimation.getImage();
			homeRunAnimation.update((long) deltaTime);
			homerun = true;
		}
		else
		{
			homerun = false;
			playOnce = true;
		}

		// For playing the home run music only once
		if(homerun && playOnce)
		{
			playOnce = false;
			homeRunSound.play(1);
		}

		// All touch input is handled here
		int len = touchEvents.size();
		if (len > 0) {
			for (int i = 0; i < len; i++) {
				TouchEvent event = touchEvents.get(i);

				// If the touch event is up
				if (event.type == TouchEvent.TOUCH_UP) {

					// Start button
					if(drawButtonInterface.getStartButton().touchEvent(event) && isStartCanBePressed)
					{
						state = GameState.Calculating;
						playOnce = true;
						isStartCanBePressed = false;
					}

					// Info button
					if(infoButton.touchEvent(event))
					{
						infoPressed = !infoPressed;
					}

                    // Back button
					if(backButton.touchEvent(event) && previousAboutPressed)
					{
						infoPressed = !infoPressed;
					}

					// Choose team button
					if(drawButtonInterface.getBallParkButton().touchEvent(event)
							&& !drawButtonInterface.getBallParkHitBox()
							&& !drawButtonInterface.getFieldPositionHitBox()
							&& !drawButtonInterface.getPlanetHitBox())
					{
						drawButtonInterface.setBallParkHitBox(true);
						isStartCanBePressed = true;
					}
					if(drawButtonInterface.getBallParkHitBox()
							&& previousBooleanBallPark)
					{
						drawButtonInterface.ballParkTouchEvents(event);
						isStartCanBePressed = true;
					}


					// Spawns the field position box
					if(drawButtonInterface.getFieldPositionButton().touchEvent(event)
							&& !drawButtonInterface.getFieldPositionHitBox())
					{
						drawButtonInterface.setFieldPositionHitBox(true);
						isStartCanBePressed = true;
					}
					if(drawButtonInterface.getFieldPositionHitBox()
							&& previousBooleanFieldPosition)
					{
						drawButtonInterface.fieldPositionTouchEvents(event);
						isStartCanBePressed = true;
					}

					// Choose planet
					if(drawButtonInterface.getPlanetButton().touchEvent(event)
							&& !drawButtonInterface.getPlanetHitBox())
					{
						drawButtonInterface.setPlanetHitBox(true);
						isStartCanBePressed = true;
					}
					if(drawButtonInterface.getPlanetHitBox()
							&& previousBooleanPlanet)
					{
						drawButtonInterface.planetTouchEvents(event);
						isStartCanBePressed = true;
					}


					// Air resistance button
					if(drawButtonInterface.getAirResistanceButton().touchEvent(event))
					{
						isStartCanBePressed = true;

						if(airResistanceHitBox)
						{
							drawButtonInterface.setAirResistanceButtonText("Off");
							airResistanceHitBox = false;
						}
						else
						{
							drawButtonInterface.setAirResistanceButtonText("On");
							airResistanceHitBox = true;
						}
					}

				}

				// If the touch event is dragged
				if (event.type == TouchEvent.TOUCH_DRAGGED) {

					// Velocity position
					if(drawButtonInterface.getInitialVelocityPlatform().touchEvent(event))
					{
						dragPosXInitialVelocity = event.x;
						isStartCanBePressed = true;

						// Limit how far you can drag the ball for velocity
						if(dragPosXInitialVelocity <= 123)
						{
							dragPosXInitialVelocity = startingDragButtonPosition;
						}

						// Can't go above 360
						if(dragPosXInitialVelocity >= 360)
						{
							dragPosXInitialVelocity = 360;
						}

					}

					// Lanuch Angle position
					if(drawButtonInterface.getLaunchAnglePlatform().touchEvent(event))
					{
						dragPosXLaunchAngle = event.x;
						isStartCanBePressed = true;

						// Limit how far you can drag the ball for launch angle
						if(dragPosXLaunchAngle <= 123)
						{
							dragPosXLaunchAngle = startingDragButtonPosition;
						}

						// Can't go above 360
						if(dragPosXLaunchAngle >= 360)
						{
							dragPosXLaunchAngle = 360;
						}
					}

				}
			}

			// Set ball park for draw xy graph
			drawXYGraph.setBallPark(drawButtonInterface.getCurrentBallPark());

			// Set field position for draw xy graph
			drawXYGraph.setFieldPosition(drawButtonInterface.getFieldPositionButton().getString());

			// Set the ball park for draw stats
			drawStats.setBallPark(drawButtonInterface.getCurrentBallPark());

			// Set the field position for draw stats
			drawStats.setFieldPosition(drawButtonInterface.getFieldPositionButton().getString());

			// Booleans for storing previous
			previousBooleanFieldPosition = drawButtonInterface.getFieldPositionHitBox();
			previousBooleanBallPark = drawButtonInterface.getBallParkHitBox();
			previousBooleanPlanet = drawButtonInterface.getPlanetHitBox();
			previousAboutPressed = infoPressed;

		}



	}


	// Update when calculating the graph to be displayed
	private void updateCalculating(List<TouchEvent> touchEvents, float deltaTime) {

		// Update scoreboard background
		background1.update();

		// Make sure it doesn't move
		background1.setBackgroundX(0);

		// Update Math
		// These need to be sent in as a int
		mathCalculator.update((int) ((dragPosXInitialVelocity - startingDragButtonPosition) * dragVelocityConvert),
				(int) ((dragPosXLaunchAngle - startingDragButtonPosition) * dragLaunchAngleConvert),
				airResistanceHitBox, drawButtonInterface);

		// Updates XY graph
		drawXYGraph.update(mathCalculator.getXPosList(), mathCalculator.getYPosList(),
				mathCalculator.getTimeList(), mathCalculator.getMaxHeight(), mathCalculator);

		// Update final stats to be displayed
		drawStats.update(mathCalculator);

		// Only needs to update once
		state = GameState.Ready;
	}


	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();

		// Clouds background
		g.drawImage(Assets.backgroundImage2, background2.getBackgroundX(),
				background2.getBackgroundY());

		// Draw graph
		drawXYGraph.draw(g);

		// Scoreboard background
		g.drawImage(Assets.backgroundImage1, background1.getBackgroundX(),
				background1.getBackgroundY());

		// Draw if home run
		if(!drawXYGraph.getHitsWall()
				&& (mathCalculator.getMaxDistance() >= drawXYGraph.getWallRectangle().left)) {
			g.drawImage(currentSignSprite, 42, 80);
		}
		else
		{
			g.drawImage(Assets.homeRunSign, 42, 80);
		}


		// Determines the state
		if (state == GameState.Ready)
		{
		    // Draw the ready UI
			drawReadyUI();
		}


		// Draw info box
		infoButton.draw(g, currentInfoButtonSprite);

		// Draw info from info box
		if(infoPressed)
		{
			g.drawImage(Assets.gameInfoDisplayBox, 32, 190);
			backButton.draw(g, currentBackButtonSprite);
		}

	}



	private void drawReadyUI() {
		Graphics g = game.getGraphics();

		// Draw all varibles of graph
		drawStats.draw(g, debugFont);

		// Draw interface buttons
		drawButtonInterface.draw(g, dragPosXInitialVelocity,
				dragPosXLaunchAngle, startingDragButtonPosition, dragVelocityConvert,
				dragLaunchAngleConvert, debugFont);

	}


	@Override
	public void pause() {


	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		nullify();
	}

	@Override
	public void backButton() {

	}

	// Sets off garbage collecter to clean up memory
	private void nullify() {
		System.gc();
	}

    // Gets the background1
	public static Background getBackground1() {
		return background1;
	}

	// Gets the background2
	public static Background getBackground2() {
		return background2;
	}



}
