package com.dorf.skeleton;


/*
	For a moving background!
	It is used for moving clouds.
 */

public class Background {

	private int backgroundY;
	private double backgroundX, speedX;
	private int keepTrackX, width, height, backgroundDistance;

	// w and h are the images width and height
	public Background(int x, int y, double initialSpeed, int w, int h) {
		backgroundX = x;
		backgroundY = y;
		speedX = initialSpeed;
		keepTrackX = x;
		width = w;
		height = h;
		backgroundDistance = 0;
	}

	// Update
	public void update() {
		backgroundX += speedX;
		keepTrackX += speedX;
		backgroundDistance -= speedX;

		if(backgroundX <= -width)
		{
			backgroundX = 0;
		}
	}

	public int getBackgroundX() {
		return (int)backgroundX;
	}

	public int getBackgroundY() {
		return backgroundY;
	}

	public int getBackgroundDistance() {
		return backgroundDistance;
	}

	public int clamp() {
		return keepTrackX;
	}

	public double getSpeedX() {
		return speedX;
	}

	public void setBackgroundX(int backgroundX) {
		this.backgroundX = backgroundX;
	}

	public void setBackgroundY(int backgroundY) {
		this.backgroundY = backgroundY;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}
}
