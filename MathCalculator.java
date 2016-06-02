package com.dorf.skeleton;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

import com.dorf.framework.Graphics;

/*
	Takes math parameters from main and calcualtes everything for the graph to display.
	Graphing is in DrawXYGraph
 */

public class MathCalculator {

	// List of coordiantes
	private List<Double> xPosList;
	private List<Double> yPosList;
	private List<Double> timeList;
	
	// Variables
	private double timeBallHitsGround;
	private double gravity = 9.8; // 32.174 fps^2
	private double convertedGravity; // Used for no air resistance calculation
	private double maxHeight;
	private double maxHeightTime;
	private double terminalVelocity = 42.52; // 139.50 fps, 42.52 m/s
	private double mass = 0.145; // kg
	private double b = .00078597; // (F_d / v^2) (Earths)
	private double dragCoef = 0.3;
	private double crossSectionArea = 0.00426; // m^2
	private double densityOfAir = 1.23; // kg/m^3 (Earths)
	private double top = 0;
	private double bottom = 0;
	private double timeIncrement = .025;
	private int strikeHeight = 3;
	
	// Temp position varaiables
	private double xPos;
	private double yPos;
	
	// Booleans
	private boolean noAtmosphere = false;
	
	// Planet name
	private String planet;
	
	public MathCalculator() {
		xPosList = new ArrayList<Double>();
		yPosList = new ArrayList<Double>();
		timeList = new ArrayList<Double>();
		
		timeBallHitsGround = 0;
		xPos = 0;
		yPos = 1;
	}

	// Update
	public void update(double velocity, double launchAngle, boolean airResistanceHitBox, 
			DrawButtonInterface drawButtonInterface)
	{
		// Must make a new one every time you draw graph
		xPosList = new ArrayList<Double>();
		yPosList = new ArrayList<Double>();
		timeList = new ArrayList<Double>();
		
		// Determines planet, b, and terminalVelocity
		determinePlanet(drawButtonInterface, airResistanceHitBox);
		
		// Calculate trajectory
		if(airResistanceHitBox && !noAtmosphere)
		{
			AirResistanceCalculator(velocity, launchAngle);
		}
		else
		{
			NoAirResistanceCalculator(velocity, launchAngle);
		}


		// Print end results
		//printStats();
	}
	

	private void NoAirResistanceCalculator(double velocity, double launchAngle)
	{
		/*
		 * Convert launch angle from degrees into radians
		 * Convert velocity from mph to fps
		 * Convert gravity to fps
		 */
		launchAngle = (launchAngle * (Math.PI)) / 180;
		velocity = velocity * 1.46667;
		convertedGravity = gravity * 3.28084;
		
		// Calculate when the ball hits the ground
		timeBallHitsGround = (2 * velocity * Math.sin(launchAngle)) / convertedGravity;
		
		// Calculate max height (velocity_f = 0)
		maxHeightTime = (velocity * Math.sin(launchAngle)) / convertedGravity;
		maxHeight = strikeHeight + (velocity * Math.sin(launchAngle) * maxHeightTime) - ((.5 * convertedGravity) * (maxHeightTime * maxHeightTime));
		
		// Y Position: y = y_0 + (v_y)t + .5(-9.8)(t^2), v_y = (v_0)sin(theta)
		for(double t = 0; t <= timeBallHitsGround + .1; t = t + timeIncrement)
		{
			// Math for y(t) position
			yPos = (velocity * Math.sin(launchAngle) * t) - ((.5 * convertedGravity) * (t * t));
			
			// Add y position and time
			yPosList.add(yPos + strikeHeight);
			timeList.add(t);
			
		}
		
		// X Position: x = x_0 + (v_x)t, v_x = (v_0)cos(theta)
		for(int i = 0; i < timeList.size(); i++)
		{
			// Math for x(t) position
			xPos = (velocity * Math.cos(launchAngle) * timeList.get(i));
			
			// Add x position
			xPosList.add(xPos);
		}
	}
	
	
	private void AirResistanceCalculator(double velocity, double launchAngle)
	{
		/*
		 * Convert launch angle from degrees into radians
		 * Convert velocity from mph to mps
		 * Do not convert gravity to fps
		 * This one is different than noAirResis because it converts units at the end
		 */
		launchAngle = (launchAngle * (Math.PI)) / 180;
		velocity = velocity * 0.44704;
		
		// Max height and the time it takes to get to max height
		maxHeightTime = (terminalVelocity / gravity) *
				(Math.atan((velocity * Math.sin(launchAngle)) / terminalVelocity));
		top = Math.cos(Math.atan(((velocity * Math.sin(launchAngle)) / terminalVelocity)) - 
				((gravity * maxHeightTime) / terminalVelocity));
		bottom = Math.cos(Math.atan((velocity * Math.sin(launchAngle)) / terminalVelocity));
		maxHeight = ((terminalVelocity * terminalVelocity) / gravity) *
				Math.log(top / bottom);
		
		// Time it takes to hit ground
		// coh^-1(x) = ln(x + sqrt((x^2) - 1) x >= 1
		double poo = Math.exp((maxHeight * gravity) / (terminalVelocity * terminalVelocity));
		timeBallHitsGround = (terminalVelocity / gravity) * 
				Math.log(poo + Math.sqrt((poo * poo) - 1)) + maxHeightTime;
		System.out.println("TimeBallHitsGround: " + timeBallHitsGround);
		System.out.println("MaxHeightTime " + maxHeightTime);

		
		// Y Position, t2 is the new time that starts when the ball reaches max height
		for(double t = 0, t2 = 0; t < timeBallHitsGround + .1; t = t + timeIncrement)
		{
			if(t <= maxHeightTime)
			{
				// 1 meter = 3.28084 ft
				top = Math.cos(Math.atan(((velocity * Math.sin(launchAngle)) / terminalVelocity)) - 
						((gravity * t) / terminalVelocity));
				bottom = Math.cos(Math.atan((velocity * Math.sin(launchAngle)) / terminalVelocity));
				
				
				yPos = (((terminalVelocity * terminalVelocity) / gravity) *
						Math.log(top / bottom)) * 3.28084;
			}
			// Drag changes directions as the ball falls down
			else
			{
				// 1 meter = 3.28084 ft
				yPos = (-((terminalVelocity * terminalVelocity) / gravity) * 
						(Math.log((Math.cosh((gravity * t2) / terminalVelocity)))) + maxHeight) * 3.28084;
				System.out.println("YPos: " + yPos + " t: " + t + " t2: " + t2);
				t2 = t2 + timeIncrement;
			}

			
			// Add y position and time
			yPosList.add(yPos + strikeHeight);
			timeList.add(t);
		}
		
		// X Position
		for(int i = 0; i < timeList.size(); i++)
		{
			// Math for x(t) position
			// 1 meter = 3.28084 ft
			xPos = ((mass / b) * (Math.log(((b * velocity * Math.cos(launchAngle) * timeList.get(i)) + mass)
					/ mass))) * 3.28084;
			
			// Add x position
			// 1 meter = 3.28084 ft
			xPosList.add(xPos);
		}
		
		// Convert the final stats to meters
		maxHeight = maxHeight * 3.28084;
	}
	
	// Determine what planet it is so you can change variables!
	// Thanks nssdc.gsfc.nasa.gov/planetary/factsheet
	private void determinePlanet(DrawButtonInterface db, boolean airResistanceHitBox)
	{
		// Reset boolean
		noAtmosphere = false;

		// Strings of the planets are located in DrawButtonInterface under addPlanets()
		if(db.getPlanetButton().getString() == "Earth")
		{
			gravity = 9.80665;
			densityOfAir = 1.23;
			planet = "Earth";
			System.out.println("Earth: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Moon")
		{
			gravity = 1.622;
			densityOfAir = 0;
			planet = "Moon";
			
			// Super Thin Atmosphere
			noAtmosphere = true;
			System.out.println("Moon: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Mercury")
		{
			gravity = 3.7;
			densityOfAir = 0;
			planet = "Mercury";
			
			// Super Thin Atmosphere
			noAtmosphere = true;
			System.out.println("Mercury: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Venus")
		{
			gravity = 8.87;
			densityOfAir = 67;
			planet = "Venus";
			System.out.println("Venus: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Mars")
		{
			gravity = 3.711;
			densityOfAir = 0.020;
			planet = "Mars";
			System.out.println("Mars: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Jupiter")
		{
			gravity = 24.79;
			densityOfAir = 0.16;
			planet = "Jupiter";
			System.out.println("Jupiter: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Saturn")
		{
			gravity = 10.44;
			densityOfAir = 0.19;
			planet = "Saturn";
			System.out.println("Saturn: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Uranus")
		{
			gravity = 8.69;
			densityOfAir = 0.42;
			planet = "Uranus";
			System.out.println("Uranus: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Neptune")
		{
			gravity = 11.15;
			densityOfAir = 0.45;
			planet = "Neptune";
			System.out.println("Neptune: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Pluto")
		{
			gravity = 0.658;
			densityOfAir = 0;
			planet = "Pluto";
			
			// Super Thin Atmosphere
			noAtmosphere = true;
			System.out.println("Pluto: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Io")
		{
			gravity = 1.796;
			densityOfAir = 0;
			planet = "Io";
			
			// Super Thin Atmosphere
			noAtmosphere = true;
			System.out.println("Io: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Europa")
		{
			gravity = 1.314;
			densityOfAir = 0;
			planet = "Europa";
			
			// Super Thin Atmosphere
			noAtmosphere = true;
			System.out.println("Europa: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Titan")
		{
			gravity = 1.352;
			densityOfAir = 0;
			planet = "Titan";
			
			// Super Thin Atmosphere
			noAtmosphere = true;
			System.out.println("Titan: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Sun")
		{
			gravity = 274;
			densityOfAir = 0;
			planet = "Sun";
			
			// Super Thin Atmosphere
			noAtmosphere = true;
			System.out.println("Sun: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Neutron Star")
		{
			gravity = 25800 * 100000000;
			densityOfAir = 1.23; // Doesn't matter because gravity so great
			planet = "Neutron Star";
			System.out.println("Neutron Star: Gravity = " + gravity);
		}
		
		// Final calculation
		b = .5 * dragCoef * densityOfAir * crossSectionArea;
		terminalVelocity = Math.sqrt(((mass * gravity) / b));
		System.out.println("b: " + b + "TV: " + terminalVelocity);
	}
	
	
	// Just for making sure the numbers are correct
	private void printStats()
	{
		// X
		System.out.println("X:");
		for(int i = 0; i < xPosList.size(); i++)
		{
			System.out.println(xPosList.get(i));
		}
		
		// Y
		System.out.println("Y:");
		for(int i = 0; i < yPosList.size(); i++)
		{
			System.out.println(yPosList.get(i));
		}
		
		// Time
		System.out.println("Time:");
		for(int i = 0; i < timeList.size(); i++)
		{
			System.out.println(timeList.get(i));
		}
	}
	
	public List<Double> getXPosList()
	{
		return this.xPosList;
	}
	
	public List<Double> getYPosList()
	{
		return this.yPosList;
	}
	
	public List<Double> getTimeList()
	{
		return this.timeList;
	}
	
	public double getMaxHeight()
	{
		return this.maxHeight;
	}
	
	public double getMaxDistance()
	{
		if(xPosList.size() > 0)
		{
			return this.xPosList.get(xPosList.size() - 1);
		}
		
		return 0;
	}
	
	public double getTotalTime()
	{
		return this.timeBallHitsGround;
	}
	
	// P for planet
	public String getCurrentPlanet()
	{
		return this.planet;
	}

	public double getGravity()
	{
		return this.gravity;
	}

	public double getDensityOfAir()
	{
		return this.densityOfAir;
	}
	

}
