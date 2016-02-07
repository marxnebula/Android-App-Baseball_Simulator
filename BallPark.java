package com.dorf.skeleton;


 /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	- Class for storing the ballpark name and field dimensions.

	  Code by Jordan Marx (2014)
*/

public class BallPark {

	// Ball park name and team name
	private String ballParkName, teamName;

	// Stored dimensions of the field
	private int leftCorner, leftCenter, center, rightCenter, rightCorner;

	// Stored dimensions of the fence height
	private double fenceHeightCenter, fenceHeightRight, fenceHeightLeft;

	// Creates the ballpark button
	private Button ballParkButton;

	// Current field position
	private int currentFieldPositionX = 0;

	// Constructor
	public BallPark(String team, String ballPark, int leftCorn, int leftCent, int cent,
			int rightCent, int rightCorn, double fenceHeightC, double fenceHeightR,
			double fenceHeightL)
	{
	    // Store variables
		teamName = team;
		ballParkName = ballPark;
		leftCorner = leftCorn;
		leftCenter = leftCent;
		center = cent;
		rightCenter = rightCent;
		rightCorner = rightCorn;
		fenceHeightCenter = fenceHeightC;
		fenceHeightRight = fenceHeightR;
		fenceHeightLeft = fenceHeightL;

		// Create the ball park button
		ballParkButton = new Button(0, 0, Assets.clickedImage, Assets.clickedImage,
				15, ballParkName);
	}

	// Gets the team name
	public String getTeamName()
	{
		return this.teamName;
	}

	// Sets the team name
	public void setTeamName(String newName)
	{
		this.teamName = newName;
	}

	// Gets the ball park name
	public String getBallParkName()
	{
		return this.ballParkName;
	}

	// Sets the ball park name
	public void setBallParkName(String newName)
	{
		this.ballParkName = newName;
	}

	// Gets the left corner dimension
	public int getLeftCorner()
	{
		return this.leftCorner;
	}

	// Gets the left center dimension
	public int getLeftCenter()
	{
		return this.leftCenter;
	}

	// Gets the center field dimension
	public int getCenter()
	{
		return this.center;
	}

	// Gets the right center dimension
	public int getRightCenter()
	{
		return this.rightCenter;
	}

	// Gets the right corner dimension
	public int getRightCorner()
	{
		return this.rightCorner;
	}

	// Gets the center fence height
	public double getFenceHeightCenter()
	{
		return this.fenceHeightCenter;
	}


	// Gets the right fence height
	public double getFenceHeightRight()
	{
		return this.fenceHeightRight;
	}

	// Gets the left fence height
	public double getFenceHeightLeft()
	{
		return this.fenceHeightLeft;
	}

    // Gets the current field position
	public int getCurrentFieldPositionX()
	{
		return this.currentFieldPositionX;
	}
}
