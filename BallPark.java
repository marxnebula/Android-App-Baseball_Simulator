package com.dorf.skeleton;

/*
	Creates the ballpark name and field dimensions
 */

public class BallPark {

	
	private String ballParkName, teamName;
	private int leftCorner, leftCenter, center, rightCenter, rightCorner;
	private double fenceHeightCenter, fenceHeightRight, fenceHeightLeft;
	private Button ballParkButton;
	private int currentFieldPositionX = 0;
	
	public BallPark(String team, String ballPark, int leftCorn, int leftCent, int cent,
			int rightCent, int rightCorn, double fenceHeightC, double fenceHeightR,
			double fenceHeightL) 
	{
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
		
		ballParkButton = new Button(0, 0, Assets.clickedImage, Assets.clickedImage, 
				15, ballParkName);
	}
	
	public String getTeamName()
	{
		return this.teamName;
	}
	
	public void setTeamName(String newName)
	{
		this.teamName = newName;
	}
	
	public String getBallParkName()
	{
		return this.ballParkName;
	}
	
	public void setBallParkName(String newName)
	{
		this.ballParkName = newName;
	}
	
	public int getLeftCorner()
	{
		return this.leftCorner;
	}
	
	public int getLeftCenter()
	{
		return this.leftCenter;
	}
	
	public int getCenter()
	{
		return this.center;
	}
	
	public int getRightCenter()
	{
		return this.rightCenter;
	}
	
	public int getRightCorner()
	{
		return this.rightCorner;
	}
	
	public double getFenceHeightCenter()
	{
		return this.fenceHeightCenter;
	}
	
	public double getFenceHeightRight()
	{
		return this.fenceHeightRight;
	}
	
	public double getFenceHeightLeft()
	{
		return this.fenceHeightLeft;
	}

	public int getCurrentFieldPositionX()
	{
		return this.currentFieldPositionX;
	}
}
