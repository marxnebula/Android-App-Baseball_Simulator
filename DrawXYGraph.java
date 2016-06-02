package com.dorf.skeleton;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.dorf.framework.Graphics;
import com.dorf.framework.Image;

/*
	Draws the the actual graph of the baseball.  Draws red lines and baseballs.
	Converts pixels per feet.
 */

public class DrawXYGraph {

	// List of coordiantes
	private List<Double> xPosList;
	private List<Double> yPosList;
	private List<Double> timeList;
	private List<Double> p1;
	private List<Double> p2;
	
	// Y-axis variables
	private double maxHeight;
	private int yIncrement;
	private int realYPixelIncrement;
	private double pixelsPerFeet;
	private int fence = 0;
	
	// For the wall
	private BallPark currentBallPark;
	private String currentFieldPosition = "no name";
	
	// Position of the graph
	private int xPosition = 61;
	private int yPosition = 398;
	
	// Rects
	private Rect wallRectangle;
	private Rect ballWallCollision;
	
	// Boolean
	private boolean hitsWall = false;

	// Current Planet
	private String currentPlanet;

	// Baseball increment
	private int baseballIncrement = 10;
	
	Paint distancePaint, titlePaint;
	
	
	public DrawXYGraph() {
		xPosList = new ArrayList<Double>();
		yPosList = new ArrayList<Double>();
		timeList = new ArrayList<Double>();
		wallRectangle = new Rect();
		ballWallCollision = new Rect();
		
		p1 = new ArrayList<Double>();
		p1.add(0.0);
		p1.add(1.0);
		p2 = new ArrayList<Double>();
		p2.add(0.0);
		p2.add(1.0);
		
		currentBallPark = new BallPark("Chicago Cubs" ,"Wrigley Field",
				355, 368, 400, 368, 353, 11, 11, 11);
		currentFieldPosition = "Center";
		
		distancePaint = new Paint();
		distancePaint.setTextSize(10);
		distancePaint.setTextAlign(Paint.Align.CENTER);
		distancePaint.setAntiAlias(true);
		distancePaint.setColor(Color.YELLOW);
		
		titlePaint = new Paint();
		titlePaint.setTextSize(12);
		titlePaint.setTextAlign(Paint.Align.CENTER);
		titlePaint.setAntiAlias(true);
		titlePaint.setColor(Color.WHITE);
	}

	// Update
	public void update(List<Double> xPos, List<Double> yPos, List<Double> time, Double maximumHeight,
					   MathCalculator mathCalculator)
	{
		xPosList = xPos;
		yPosList = yPos;
		timeList = time;
		maxHeight = maximumHeight;

		currentPlanet = mathCalculator.getCurrentPlanet();
	}
	
	// Draw
	public void draw(Graphics g)
	{
		
		// Draw the background of score board
		g.drawRect(20, 171, 437, 263, Color.argb(255, 64, 79, 69));
		g.drawString("Height (ft) vs. Distance (ft)", 245, 184, titlePaint);
		// 242, 427
		
		// X-axis
		g.drawLine(xPosition, yPosition, 450, yPosition, Color.BLACK);
		for(int i = 1; i < 16; i++)
		{
			g.drawLine(xPosition + (i * 25), yPosition, xPosition + (i * 25), yPosition - 8, Color.WHITE);
			g.drawString(Integer.toString(i * 30), xPosition + (i * 25), yPosition + 15, distancePaint);
		}
		
		// Determine three variables before drawing Y-axis
		// Determine ">= #" by multiplying the previous yIncrement by 7
		if(maxHeight >= 560)
		{
			realYPixelIncrement = 30;
			yIncrement = 90;
			pixelsPerFeet = 0.3333333333;
		}
		else if(maxHeight >= 420)
		{
			realYPixelIncrement = 30;
			yIncrement = 80;
			pixelsPerFeet = .375;
		}
		else if(maxHeight >= 350)
		{
			realYPixelIncrement = 30;
			yIncrement = 60;
			pixelsPerFeet = .5;
		}
		else if(maxHeight >= 315)
		{
			realYPixelIncrement = 30;
			yIncrement = 50;
			pixelsPerFeet = .6;
		}
		else if(maxHeight >= 210)
		{
			realYPixelIncrement = 30;
			yIncrement = 45;
			pixelsPerFeet = .6666666667;
		}
		else if(maxHeight >= 105)
		{
			realYPixelIncrement = 30;
			yIncrement = 30;
			pixelsPerFeet = 1;
		}
		else if(maxHeight >= 70)
		{
			realYPixelIncrement = 30;
			yIncrement = 15;
			pixelsPerFeet = 2;
		}
		else
		{
			realYPixelIncrement = 30;
			yIncrement = 10;
			pixelsPerFeet = 3;
		}
		
		// Draw the wall (Needs variables ball park and field position)
		if(currentFieldPosition == "Left Corner")
		{
			fence = (int)Math.round(currentBallPark.getLeftCorner() * .833333) + xPosition;
			wallRectangle.set(fence, yPosition - (int)(currentBallPark.getFenceHeightLeft() * pixelsPerFeet), 
					fence + 2, yPosition);
			g.drawRect(wallRectangle, Color.RED);
		}
		if(currentFieldPosition == "Left Center")
		{
			fence = (int)Math.round(currentBallPark.getLeftCenter() * .833333) + xPosition;
			wallRectangle.set(fence, yPosition - (int)(currentBallPark.getFenceHeightLeft() * pixelsPerFeet),
					fence + 2, yPosition);
			g.drawRect(wallRectangle, Color.RED);
		}
		if(currentFieldPosition == "Center")
		{
			fence = (int)Math.round(currentBallPark.getCenter() * .833333) + xPosition;
			wallRectangle.set(fence, yPosition - (int)(currentBallPark.getFenceHeightCenter() * pixelsPerFeet),
					fence + 2, yPosition);
			g.drawRect(wallRectangle, Color.RED);
		}
		if(currentFieldPosition == "Right Corner")
		{
			fence = (int)Math.round(currentBallPark.getRightCorner() * .833333) + xPosition;
			wallRectangle.set(fence, yPosition - (int)(currentBallPark.getFenceHeightRight() * pixelsPerFeet),
					fence + 2, yPosition);
			g.drawRect(wallRectangle, Color.RED);
		}
		if(currentFieldPosition == "Right Center")
		{
			fence = (int)Math.round(currentBallPark.getRightCenter() * .833333) + xPosition;
			wallRectangle.set(fence, yPosition - (int)(currentBallPark.getFenceHeightRight() * pixelsPerFeet),
					fence + 2, yPosition);
			g.drawRect(wallRectangle, Color.RED);
		}
		

		// Y-axis
		g.drawLine(xPosition, yPosition, xPosition, yPosition - 210, Color.BLACK);
		for(int i = 1; i < 8; i++)
		{
			g.drawLine(xPosition, yPosition - (i * realYPixelIncrement), xPosition + 8, yPosition - (i * realYPixelIncrement), Color.WHITE);
			g.drawString(Integer.toString(i * yIncrement), 45, (yPosition + 5) - (i * realYPixelIncrement), distancePaint);
		}
		
		// Draws the trajectory of the baseball with lines
		int k = 0;
		for(int i = 0; i < timeList.size() - 1; i++, k++)
		{
			// Use this so we arent rounding too early 
			p1.set(0, -yPosList.get(i) * pixelsPerFeet);
			p1.set(1, -yPosList.get(i + 1) * pixelsPerFeet);
			p2.set(0, xPosList.get(i) * 0.833);
			p2.set(1, xPosList.get(i + 1) * 0.833);
			
			// So it doesn't draw outside the scoreboard
			if(xPosList.get(i) <= 475 && (yPosList.get(i) <= 630 && yPosList.get(i) >= 2))
			{
				g.drawLine(p2.get(0).intValue() + xPosition, p1.get(0).intValue() + yPosition,
						p2.get(1).intValue() + xPosition, p1.get(1).intValue() + yPosition,
						Color.RED);
			}
			
			// If the line hits the wall then stop drawing
			ballWallCollision.set(p2.get(0).intValue() + xPosition, p1.get(0).intValue() + yPosition, 
					p2.get(1).intValue() + xPosition, p1.get(1).intValue() + yPosition);
			
			
			if(ballWallCollision.intersect(wallRectangle))
			{
				hitsWall = true;
				i = timeList.size() - 1;
				break;
			}
			else
			{
				hitsWall = false;
			}

			
		}

		// Venus acts goofy based on the high density of air
		// Therefore we need to draw less balls
		if(currentPlanet == "Venus")
		{
			baseballIncrement = 25;
		}
		else
		{
			baseballIncrement = 10;
		}


		// Draws the trajectory of the baseball with Balls (LoL)
		for(int i = 2; i < k; i = i + baseballIncrement)
		{
			p1.set(0, -yPosList.get(i) * pixelsPerFeet);
			p2.set(0, xPosList.get(i) * 0.833);
			
			// So it doesn't draw outside the scoreboard
			if(xPosList.get(i) <= 470 && (yPosList.get(i) <= 600 && yPosList.get(i) >= 2))
			{
				if(yPosList.get(i) < maxHeight)
				{
					g.drawImage(Assets.graphBall, p2.get(0).intValue() + xPosition - 5, 
							p1.get(0).intValue() + yPosition);
				}
				else
				{
					g.drawImage(Assets.graphBall, p2.get(0).intValue() + xPosition, 
							p1.get(0).intValue() + yPosition);
				}
			}
		}
		
	}
	
	public void setBallPark(BallPark bp)
	{
		currentBallPark = bp;
	}

	public void setFieldPosition(String fp)
	{
		currentFieldPosition = fp;
	}
	
	public int getWallSize()
	{
		return this.fence;
	}
	
	public boolean getHitsWall()
	{
		return this.hitsWall;
	}
	
	public void setHitsWall(boolean x)
	{
		this.hitsWall = x;
	}
	
	public Rect getWallRectangle()
	{
		return this.wallRectangle;
	}
}
