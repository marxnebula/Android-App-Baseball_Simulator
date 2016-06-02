package com.dorf.skeleton;

import java.text.DecimalFormat;

import android.graphics.Color;
import android.graphics.Paint;
import com.dorf.framework.Graphics;

/*
	Draws the stats of max height, max distance, and total time of trajectory.
	Displayed on gameScreen.
 */

public class DrawStats {

	private double maxHeight = 0;
	private double maxDistance = 0;
	private double totalTime = 0;
	private BallPark ballPark;
	private String fieldPosition;
	private double gravity = 9.80665;
	private double densityOfAir = 1.23;
	private String currentPlanet = "Earth";
	private Paint infoFontTitle;
	private Paint infoFontData;


	
	
	public DrawStats() {
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

		// Planet
		currentPlanet = mathCalculator.getCurrentPlanet();

		// Sometimes at 0 degrees it doesn't show that the maxHeight is 3 even though
		// it calculates it at 3
		if(maxHeight < 3)
		{
			maxHeight = 3;
		}
	}

	// Draw
	public void draw(Graphics g, Paint debugFont)
	{
		
		DecimalFormat numberFormat = new DecimalFormat("#.00");
	
		// Stats inside of box
		g.drawString(numberFormat.format(maxDistance) + " ft", 83, 640, debugFont);
		g.drawString(numberFormat.format(maxHeight) + " ft", 216, 640, debugFont);
		g.drawString(numberFormat.format(totalTime) + " secs", 342, 640, debugFont);

		// Neutron star number too big so gonna write it out normally
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
	
	public void setBallPark(BallPark bp)
	{
		this.ballPark = bp;
	}
	
	public void setFieldPosition(String fp)
	{
		this.fieldPosition = fp;
	}

}
