package com.dorf.skeleton;

import java.text.DecimalFormat;
import android.graphics.Color;
import android.graphics.Paint;
import com.dorf.framework.Graphics;


 /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	- Draws the stats of max height, max distance, and total time of trajectory
	  to the gameScreen.

	  Code by Jordan Marx (2014)
  */

public class DrawStats {

    // Max height
	private double maxHeight = 0;

	// Max distance
	private double maxDistance = 0;

	// Total time
	private double totalTime = 0;

	// Ball park
	private BallPark ballPark;

	// Field position
	private String fieldPosition;

	// Earths gravity
	private double gravity = 9.80665;

	// Earths air density
	private double densityOfAir = 1.23;

	// Current planet
	private String currentPlanet = "Earth";

	// Font variables
	private Paint infoFontTitle;
	private Paint infoFontData;



	// Constructor
	public DrawStats() {

	    // Starting ball park
		ballPark = new BallPark("Chicago Cubs" ,"Wrigley Field",
				355, 368, 400, 368, 353, 11, 11, 11);

		// Set font specs
		infoFontTitle = new Paint();
		infoFontTitle.setTextSize(12);
		infoFontTitle.setTextAlign(Paint.Align.LEFT);
		infoFontTitle.setAntiAlias(true);
		infoFontTitle.setColor(Color.argb(255,255,187,15));
		infoFontData = new Paint();
		infoFontData.setTextSize(12);
		infoFontData.setTextAlign(Paint.Align.LEFT);
		infoFontData.setAntiAlias(true);
		infoFontData.setColor(Color.WHITE);
	}

	// Update
	public void update(MathCalculator mathCalculator)
	{
		// Update final stats to be displayed
		totalTime = mathCalculator.getTotalTime();
		maxHeight = mathCalculator.getMaxHeight();
		maxDistance = mathCalculator.getMaxDistance();
		gravity = mathCalculator.getGravity();
		densityOfAir = mathCalculator.getDensityOfAir();

		// Current planet
		currentPlanet = mathCalculator.getCurrentPlanet();

		// Just incase at 0 it doesn't display 3.
		// It is 3 because the ball thrown is a strike(3 ft above ground)
		if(maxHeight < 3)
		{
			maxHeight = 3;
		}
	}

	// Draw
	public void draw(Graphics g, Paint debugFont)
	{
		// For displaying 2 decimal places
		DecimalFormat numberFormat = new DecimalFormat("#.00");

		// Stats inside of box
		g.drawString(numberFormat.format(maxDistance) + " ft", 83, 640, debugFont);
		g.drawString(numberFormat.format(maxHeight) + " ft", 216, 640, debugFont);
		g.drawString(numberFormat.format(totalTime) + " secs", 342, 640, debugFont);

		// Neutron star number too big so had to write it out normally
		if(currentPlanet == "Neutron Star")
		{
			// Draw gravity
			g.drawString("Gravity: ", 3, 765, infoFontTitle);
			g.drawString("8.46e12 ft/s^2", 47, 765, infoFontData);
		}
		else
		{
			// Draw gravity
			g.drawString("Gravity: ", 3, 765, infoFontTitle);
			g.drawString(numberFormat.format(gravity * 3.28084) + " ft/s^2", 47, 765, infoFontData);
		}

		// Draw Density of air
		g.drawString("Density of Air: ", 3, 780, infoFontTitle);
		g.drawString((densityOfAir) + " kg/m^3", 80, 780, infoFontData);
	}

	// Set the ball park
	public void setBallPark(BallPark bp)
	{
		this.ballPark = bp;
	}

	// Set the field position
	public void setFieldPosition(String fp)
	{
		this.fieldPosition = fp;
	}

}
