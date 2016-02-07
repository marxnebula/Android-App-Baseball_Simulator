package com.dorf.skeleton;


 /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	- Class for moving the background.
	- It is used for displaying moving clouds.

	  Code by Jordan Marx (2014)
*/

public class Background {

    // The y position of the background
	private int backgroundY;

	// The x position of the background
	private int backgroundX;

	// The horizontal speed of the background
	private double speedX;

	// The width, height of the background image
	private int width, height;

    // Constructor
	public Background(int x, int y, double initialSpeed, int w, int h) {

	    // Store the variables
		backgroundX = x;
		backgroundY = y;
		speedX = initialSpeed;
		width = w;
		height = h;
	}

	// Update
	public void update() {

	    // Add the speed to the background position
		backgroundX += speedX;

        // Resets the background image to start over
		if(backgroundX <= -width)
		{
			backgroundX = 0;
		}
	}

    // Gets the backgroundX
	public int getBackgroundX() {
		return (int)backgroundX;
	}

    // Gets the backgroundY
	public int getBackgroundY() {
		return backgroundY;
	}

    // Gets the horizontal speed of the background
	public double getSpeedX() {
		return speedX;
	}

	// Sets the horizontal speed of the background
	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

    // Sets the backgroundX
	public void setBackgroundX(int backgroundX) {
		this.backgroundX = backgroundX;
	}

    // Sets the backgroundY
	public void setBackgroundY(int backgroundY) {
		this.backgroundY = backgroundY;
	}

}
