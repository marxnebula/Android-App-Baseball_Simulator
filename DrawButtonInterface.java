package com.dorf.skeleton;

import android.graphics.Color;
import android.graphics.Paint;

import com.dorf.framework.Graphics;
import com.dorf.framework.Image;
import com.dorf.framework.Input.TouchEvent;


/*
	Draws all the buttons that can be clicked on in the gameScreen class.
	Includes the stadiums, planets, field position, air resistance, start button,
	initial velocity, and launch angle.
 */

public class DrawButtonInterface {

	// Interface buttons
	private Button ballParkButton;
	private Button fieldPositionButton;
	private Button initialVelocityPlatform;
	private Button launchAnglePlatform;
	private Button airResistanceButton;
	private Button startButton;
	private Button whichPlanetButton;
	
	// Buttons for when clicking on field position
	private Button[] fieldPositionArrayButton = new Button[5];
	
	// Buttons for when clicking on baseball team
	private Button[] nlBallParkArrayButton = new Button[15];
	private Button[] alBallParkArrayButton = new Button[15];
	
	// Buttons for when clicking on which planet
	private int numberOfPlanets = 15;
	private Button[] planetArrayButton = new Button[numberOfPlanets];
	
	// NL teams
	private BallPark[] nlBallParks = new BallPark[15];
	
	// AL teams
	private BallPark[] alBallParks = new BallPark[15];
	private BallPark currentBallPark;
	
	// Booleans
	private boolean ballParkHitBox = false;
	private boolean fieldPositionHitBox = false;
	private boolean planetHitBox = false;
	
	// Planets
	private String[] planets = new String[numberOfPlanets];
	
	
	public DrawButtonInterface() {

		// Planets
		addPlanets();
		
		// Ball Parks
		addBallParks();
		
		
		// Starting ball park
		currentBallPark = new BallPark("Chicago Cubs" ,"Wrigley Field",
				355, 368, 400, 368, 353, 11, 11, 11);
		
		
		// Interface Buttons
		ballParkButton = new Button(240, 493, Assets.teamStatic, Assets.teamStatic, 
				15, "Chicago Cubs");
		ballParkButton.setTextSize(25);
		fieldPositionButton = new Button(110, 560, Assets.planetFieldStatic, Assets.planetFieldStatic, 
				17, "Center");	
		fieldPositionButton.setFontBold();
		whichPlanetButton = new Button(365, 560, Assets.planetFieldStatic, 
				Assets.planetFieldStatic, 17, "Earth");
		whichPlanetButton.setFontBold();
		airResistanceButton = new Button(240, 561, Assets.checkBox,
				Assets.checkBox, 20, "On");
		initialVelocityPlatform = new Button(
				122 + Assets.dragPlatform.getWidth() / 2, 688,
				Assets.dragPlatform, Assets.dragPlatform);
		launchAnglePlatform = new Button(
				122 + Assets.dragPlatform.getWidth() / 2, 721,
				Assets.dragPlatform, Assets.dragPlatform);
		startButton = new Button(240, 768, Assets.menuButtonRest,
				Assets.menuButtonRest, 30, "Start");
		
		
		
		// Buttons for when clicking on field position
		fieldPositionArrayButton[0] = new Button(110, 421, Assets.planetFieldStatic, Assets.planetFieldStatic, 
				15, "Left Corner");
		fieldPositionArrayButton[1] = new Button(110, 457, Assets.planetFieldStatic, Assets.planetFieldStatic, 
				15, "Left Center");
		fieldPositionArrayButton[2] = new Button(110, 493, Assets.planetFieldStatic, Assets.planetFieldStatic, 
				15, "Center");
		fieldPositionArrayButton[3] = new Button(110, 529, Assets.planetFieldStatic, Assets.planetFieldStatic, 
				15, "Right Corner");
		fieldPositionArrayButton[4] = new Button(110, 565, Assets.planetFieldStatic, Assets.planetFieldStatic, 
				15, "Right Center");
		
		
		// Buttons for when clicking on baseball team
		for(int i = 0; i < 15; i++)
		{
			nlBallParkArrayButton[i] = new Button(147, 122 + (36 * i), Assets.clickedImage2, Assets.clickedImage2,
					15, nlBallParks[i].getTeamName());
		}
		for(int j = 0; j < 15; j++)
		{
			alBallParkArrayButton[j] = new Button(147 + 200, 122 + (36 * j), Assets.clickedImage2, Assets.clickedImage2,
					15, alBallParks[j].getTeamName());
		}
		
		// Buttons for when clicking on which planet
		for(int i = 0; i < numberOfPlanets; i++)
		{
			planetArrayButton[i] = new Button(365, 60 + (36 * i), Assets.planetFieldStatic, Assets.planetFieldStatic,
					15, planets[i]);
		}
		
		
		
	}
	
	// Draw
	public void draw(Graphics g, int xVelocity, int xLaunchAngle, int startingDragPos, double dragVConvert,
			double dragLAConvert, Paint debugFont)
	{
		// Ball Park
		ballParkButton.draw(g);
		
		//Field position
		fieldPositionButton.draw(g);
		
		// Initial Velocity
		initialVelocityPlatform.draw(g);
		g.drawImage(Assets.dragButton, xVelocity, 688 - 12);
		double xV = (xVelocity - startingDragPos) * dragVConvert;
		g.drawString(Integer.toString((int)xV) + " mph", initialVelocityPlatform.getCenterX() + 278,
				initialVelocityPlatform.getCenterY() + 17, debugFont);
		
		// Lanuch Angle
		launchAnglePlatform.draw(g);
		g.drawImage(Assets.dragButton, xLaunchAngle, 721 - 12);
		double xLA = (xLaunchAngle - startingDragPos) * dragLAConvert;
		g.drawString(Integer.toString((int)xLA) + " degs", launchAnglePlatform.getCenterX() + 278,
				launchAnglePlatform.getCenterY() + 17, debugFont);
		
		// Air Resistance
		airResistanceButton.draw(g);
		
		// Start Button
		startButton.draw(g);
		
		// Planet Button
		whichPlanetButton.draw(g);
		
		// Which planet
		if(planetHitBox)
		{
			// P for planet
			for(int p = 0; p < numberOfPlanets; p++)
			{
				planetArrayButton[p].draw(g);
			}
		}
		
		
		// If you are selecting the team
		if(ballParkHitBox)
		{
			// Draw NL and AL Team names
			for(int i = 0; i < 15; i++)
			{
				nlBallParkArrayButton[i].draw(g);
				alBallParkArrayButton[i].draw(g);
			}
		}
		
		
		// If you are selecting the field position(center, left corner, etc)
		if(fieldPositionHitBox)
		{
			for(int i = 0; i < 5; i++)
			{
				fieldPositionArrayButton[i].draw(g);
			}
		}
		
	}

	// Create all the planets in string form!
	private void addPlanets()
	{
		planets[0] = "Earth";
		planets[1] = "Moon";
		planets[2] = "Mercury";
		planets[3] = "Venus";
		planets[4] = "Mars";
		planets[5] = "Jupiter";
		planets[6] = "Saturn";
		planets[7] = "Uranus";
		planets[8] = "Neptune";
		planets[9] = "Pluto";
		planets[10] = "Io";
		planets[11] = "Europa";
		planets[12] = "Titan";
		planets[13] = "Sun";
		planets[14] = "Neutron Star";
	}
	
	// All info for each ball park
	// 15 NL and 15 AL
	private void addBallParks()
	{
		// NL
		// Team name, ball park name, left corner, left center, center, right center, right corner
		// fence height center, fence height right, fence height left
		nlBallParks[0] = new BallPark("Arizona Diamonbacks" ,"Chase Field",
				330, 374, 407, 374, 334, 25, 7.5, 7.5);
		nlBallParks[1] = new BallPark("Atlanta Braves" ,"Turner Field",
				335, 380, 401, 390, 330, 8, 8, 8);
		nlBallParks[2] = new BallPark("Chicago Cubs" ,"Wrigley Field",
				355, 368, 400, 368, 353, 11.5, 15, 15);
		nlBallParks[3] = new BallPark("Cincinnati Reds" ,"Great American Ball Park",
				328, 379, 404, 370, 325, 8, 8, 12);
		nlBallParks[4] = new BallPark("Colorado Rockies" ,"Coors Field",
				347, 390, 415, 375, 350, 8, 14, 8);
		nlBallParks[5] = new BallPark("Los Angeles Dodgers" ,"Dodger Stadium",
				330, 375, 400, 375, 330, 10, 8, 8);
		nlBallParks[6] = new BallPark("Miami Marlins" ,"Marlins Park",
				344, 386, 418, 392, 335, 16, 10, 10);
		nlBallParks[7] = new BallPark("Milwaukee Brewers" ,"Wrigley North",
				344, 371, 400, 374, 345, 8, 8, 8);
		nlBallParks[8] = new BallPark("New York Mets" ,"Citi Field",
				335, 358, 408, 375, 330, 8, 8, 8);
		nlBallParks[9] = new BallPark("Philadelphia Phillies" ,"Citizens Bank Park",
				329, 374, 401, 369, 330, 6, 8, 13);
		nlBallParks[10] = new BallPark("Pittsburgh Pirates" ,"PNC Park",
				325, 383, 399, 375, 320, 10, 21, 6);
		nlBallParks[11] = new BallPark("San Diego Padres" ,"Petco Park",
				334, 367, 396, 382, 322, 7.5, 10, 7.5);
		nlBallParks[12] = new BallPark("San Francisco Giants" ,"AT&T Park",
				339, 364, 399, 365, 309, 8, 25, 8);
		nlBallParks[13] = new BallPark("St. Louis Cardinals" ,"Busch Stadium",
				336, 375, 400, 375, 335, 8, 8, 8);
		nlBallParks[14] = new BallPark("Washington Nationals" ,"Nationals Park",
				337, 377, 402, 370, 335, 12, 8, 8);
		
		// AL
		// Team name, ball park name, left corner, left center, center, right center, right corner
		// fence height center, fence height right, fence height left
		alBallParks[0] = new BallPark("Baltimore Orioles" ,"Oriole Park at Camden Yards",
				333, 364, 400, 373, 318, 7, 25, 7);
		alBallParks[1] = new BallPark("Boston Red Sox" ,"Fenway Park", 
				310, 379, 390, 380, 302, 17, 5, 37);
		alBallParks[2] = new BallPark("Chicago White Sox" ,"US Cellular Field",
				330, 375, 400, 375, 335, 8, 8, 8);
		alBallParks[3] = new BallPark("Cleveland Indians" ,"Progressive Field",
				325, 370, 405, 375, 325, 8, 8, 19);
		alBallParks[4] = new BallPark("Detroit Tigers" ,"Comerica Park",
				345, 370, 420, 365, 330, 8.5, 11.5, 7);
		alBallParks[5] = new BallPark("Houston Astros" ,"Minute Maid Park",
				315, 362, 436, 373, 326, 9, 7, 21);
		alBallParks[6] = new BallPark("Kansas City Royals" ,"Kauffman Stadium",
				330, 387, 410, 375, 330, 8, 8, 8);
		alBallParks[7] = new BallPark("Los Angeles Angels" ,"Angel Stadium",
				340, 387, 395, 370, 350, 8, 18, 8);
		alBallParks[8] = new BallPark("Minnesota Twins" ,"Target Field",
				339, 377, 407, 365, 328, 8, 23, 8);
		alBallParks[9] = new BallPark("New York Yankees" ,"Yankee Stadium",
				318, 399, 408, 385, 314, 8.5, 8.5, 8.5);
		alBallParks[10] = new BallPark("Oakland Atheletics" ,"Oakland-Alameda County Coliseum",
				330, 367, 400, 367, 330, 8, 8, 8);
		alBallParks[11] = new BallPark("Seattle Mariners" ,"Safeco Field",
				331, 378, 401, 380, 326, 8, 8, 8);
		alBallParks[12] = new BallPark("Tampa Bay Rays" ,"Tropicana Field",
				315, 370, 404, 370, 322, 9.5, 9.5, 9.5);
		alBallParks[13] = new BallPark("Texas Rangers" ,"Glove Life Park in Arlington",
				332, 390, 400, 377, 325, 8, 8, 14);
		alBallParks[14] = new BallPark("Toronto Blue Jays" ,"Rogers Centre",
				328, 375, 400, 375, 328, 10, 10, 10);
	}
	
	// When field position menu screen pops up this determines which one you clicked
	public void fieldPositionTouchEvents(TouchEvent event)
	{
		for(int i = 0; i < 5; i++)
		{
			if(fieldPositionArrayButton[i].touchEvent(event))
			{
				fieldPositionButton.setText(fieldPositionArrayButton[i].getString());
				setFieldPositionHitBox(false);
				return;
			}
		}
	}
	
	// When ball park menu screen pops up this determines which one you clicked
	public void ballParkTouchEvents(TouchEvent event)
	{
		for(int i = 0; i < 15; i++)
		{
			if(nlBallParkArrayButton[i].touchEvent(event))
			{
				ballParkButton.setText(nlBallParkArrayButton[i].getString());
				setCurrentBallPark(nlBallParks[i]);
				setBallParkHitBox(false);
				return;
			}
			if(alBallParkArrayButton[i].touchEvent(event))
			{
				ballParkButton.setText(alBallParkArrayButton[i].getString());
				setCurrentBallPark(alBallParks[i]);
				setBallParkHitBox(false);
				return;
			}
		}
	}
	
	public void planetTouchEvents(TouchEvent event)
	{
		// P for planet
		for(int p = 0; p < numberOfPlanets; p++)
		{
			if(planetArrayButton[p].touchEvent(event))
			{
				whichPlanetButton.setText(planetArrayButton[p].getString());
				setPlanetHitBox(false);
				return;
			}
		}
	}
	
	// Might make this random team in future
	public void setInitialBallPark(BallPark bp)
	{
		bp = new BallPark("Chicago Cubs" ,"Wrigley Field",
				355, 368, 400, 368, 353, 11, 11, 11);
	}
	
	public Button getBallParkButton()
	{
		return this.ballParkButton;
	}
	
	public Button getFieldPositionButton()
	{
		return this.fieldPositionButton;
	}
	
	public Button getInitialVelocityPlatform()
	{
		return this.initialVelocityPlatform;
	}
	
	public Button getLaunchAnglePlatform()
	{
		return this.launchAnglePlatform;
	}
	
	public Button getAirResistanceButton()
	{
		return this.airResistanceButton;
	}
	
	public Button getStartButton()
	{
		return this.startButton;
	}
	
	public Button getPlanetButton()
	{
		return this.whichPlanetButton;
	}
	
	public void setAirResistanceButtonText(String string)
	{
		airResistanceButton.setText(string);
	}
	
	public boolean getBallParkHitBox()
	{
		return this.ballParkHitBox;
	}
	
	public void setBallParkHitBox(boolean x)
	{
		this.ballParkHitBox = x;
	}
	
	public boolean getFieldPositionHitBox()
	{
		return this.fieldPositionHitBox;
	}
	
	public void setFieldPositionHitBox(boolean x)
	{
		this.fieldPositionHitBox = x;
	}
	
	public void setCurrentBallPark(BallPark bp)
	{
		this.currentBallPark = bp;
	}
	
	public BallPark getCurrentBallPark()
	{
		return this.currentBallPark;
	}
	
	public boolean getPlanetHitBox()
	{
		return this.planetHitBox;
	}
	
	public void setPlanetHitBox(boolean x)
	{
		this.planetHitBox = x;
	}
	
	public String getPlanetStringName(int i)
	{
		return this.planets[i];
	}
	
	

}
