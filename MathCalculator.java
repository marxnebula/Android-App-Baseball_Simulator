package com.dorf.skeleton;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;

import com.dorf.framework.Graphics;


 /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	- Takes math parameters from game screen class and calcualtes everything
	  for the graph to display.
    - Graphing is in the DrawXYGraph class.  This class is just for solving for
      the kinematics and diffie q equations.
    - If no air resistance then use kinematics.
    - If yes air resistance then use diffie q equation.

	  Code by Jordan Marx (2014)
  */

public class MathCalculator {

	// List of coordiantes
	private List<Double> xPosList;
	private List<Double> yPosList;
	private List<Double> timeList;

	// Time it takes balll to hit the ground
	private double timeBallHitsGround;

	// Earths gravity
	private double gravity = 9.8; // 32.174 fps^2

	// Max height
	private double maxHeight;

	// Time it took to get max height
	private double maxHeightTime;

	// Terminal velocity(calculated from standard variables online)
	private double terminalVelocity = 42.52; // 139.50 fps, 42.52 m/s

	// Mass of baseball
	private double mass = 0.145; // kg

	// Calculated from standard variables online
	private double b = .00078597; // (F_d / v^2) (Earths)

	// Draw coef(online said around 0.3)
	private double dragCoef = 0.3;

	// Cross sectional area
	private double crossSectionArea = 0.00426; // m^2

	// Earths denstiy of air
	private double densityOfAir = 1.23; // kg/m^3 (Earths)

	// Diffie q equation top portion
	private double top = 0;

	// Diffie q equation bottom portion
	private double bottom = 0;

	// Time increments
	private double timeIncrement = .025;

	// Height of baseball when reached batter
	private int strikeHeight = 3;

	// Temp position varaiables
	private double xPos;
	private double yPos;

	// Booleans
	private boolean noAtmosphere = false;

	// Planet name
	private String planet;


	// Constructor
	public MathCalculator() {

	    // For storing position
		xPosList = new ArrayList<Double>();
		yPosList = new ArrayList<Double>();

		// For storing time
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

		// If air resistance and there is an atmosphere
		if(airResistanceHitBox && !noAtmosphere)
		{
		    // Air resistance calculator
			AirResistanceCalculator(velocity, launchAngle);
		}
		// No air resistance
		else
		{
		    // No air restance calculator
			NoAirResistanceCalculator(velocity, launchAngle);
		}

	}

    // Function for calculating when no air resistance.
    // This is just basic kinematics.
	private void NoAirResistanceCalculator(double velocity, double launchAngle)
	{
		/*
		 * Convert launch angle from degrees into radians
		 * Convert velocity from mph to fps
		 * Convert gravity to fps
		 */
		launchAngle = (launchAngle * (Math.PI)) / 180;
		velocity = velocity * 1.46667;
		gravity = gravity * 3.28084;

		// Calculate when the ball hits the ground
		timeBallHitsGround = (2 * velocity * Math.sin(launchAngle)) / gravity;

		// Calculate max height (velocity_f = 0)
		maxHeightTime = (velocity * Math.sin(launchAngle)) / gravity;
		maxHeight = strikeHeight + (velocity * Math.sin(launchAngle) * maxHeightTime) - ((.5 * gravity) * (maxHeightTime * maxHeightTime));

		// Y Position: y = y_0 + (v_y)t + .5(-9.8)(t^2), v_y = (v_0)sin(theta)
		for(double t = 0; t <= timeBallHitsGround + .1; t = t + timeIncrement)
		{
			// Math for y(t) position
			yPos = (velocity * Math.sin(launchAngle) * t) - ((.5 * gravity) * (t * t));

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

	// Calculates air resistance.
	// Diffie q equation where the force of drag is proportional to velocity squared
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
		    // Set gravity
			gravity = 9.80665;

			// Set density
			densityOfAir = 1.23;

			// Set planet name
			planet = "Earth";
			System.out.println("Earth: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Moon")
		{
		    // Set gravity
			gravity = 1.622;

			// Set density
			densityOfAir = 0;

			// Set planet name
			planet = "Moon";

			// Super Thin Atmosphere
			noAtmosphere = true;
			System.out.println("Moon: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Mercury")
		{
		    // Set gravity
			gravity = 3.7;

			// Set density
			densityOfAir = 0;

			// Set planet name
			planet = "Mercury";

			// Super Thin Atmosphere
			noAtmosphere = true;
			System.out.println("Mercury: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Venus")
		{
		    // Set gravity
			gravity = 8.87;

			// Set density(yes that high)
			densityOfAir = 67;

			// Set planet name
			planet = "Venus";
			System.out.println("Venus: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Mars")
		{
		    // Set gravity
			gravity = 3.711;

			// Set density
			densityOfAir = 0.020;

			// Set planet name
			planet = "Mars";
			System.out.println("Mars: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Jupiter")
		{
		    // Set gravity
			gravity = 24.79;

			// Set density
			densityOfAir = 0.16;

			// Set planet name
			planet = "Jupiter";
			System.out.println("Jupiter: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Saturn")
		{
		    // Set gravity
			gravity = 10.44;

			// Set density
			densityOfAir = 0.19;

			// Set planet name
			planet = "Saturn";
			System.out.println("Saturn: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Uranus")
		{
		    // Set gravity
			gravity = 8.69;

			// Set density name
			densityOfAir = 0.42;

			// Set planet name
			planet = "Uranus";
			System.out.println("Uranus: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Neptune")
		{
		    // Set gravity
			gravity = 11.15;

			// Set density
			densityOfAir = 0.45;

			// Set planet name
			planet = "Neptune";
			System.out.println("Neptune: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Pluto")
		{
		    // Set gravity
			gravity = 0.658;

			// Set density
			densityOfAir = 0;

			// Set planet name
			planet = "Pluto";

			// Super Thin Atmosphere
			noAtmosphere = true;
			System.out.println("Pluto: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Io")
		{
		    // Set gravity
			gravity = 1.796;

			// Set density
			densityOfAir = 0;

			// Set planet name
			planet = "Io";

			// Super Thin Atmosphere
			noAtmosphere = true;
			System.out.println("Io: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Europa")
		{
		    // Set gravity
			gravity = 1.314;

			// Set density
			densityOfAir = 0;

			// Set planet name
			planet = "Europa";

			// Super Thin Atmosphere
			noAtmosphere = true;
			System.out.println("Europa: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Titan")
		{
		    // Set gravity
			gravity = 1.352;

			// Set density
			densityOfAir = 0;

			// Set planet name
			planet = "Titan";

			// Super Thin Atmosphere
			noAtmosphere = true;
			System.out.println("Titan: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Sun")
		{
		    // Set gravity
			gravity = 274;

			// Set density
			densityOfAir = 0;

			// Set planet name
			planet = "Sun";

			// Super Thin Atmosphere
			noAtmosphere = true;
			System.out.println("Sun: Gravity = " + gravity);
		}
		else if(db.getPlanetButton().getString() == "Neutron Star")
		{
		    // Set gravity
			gravity = 25800 * 100000000;

			// Set density
			densityOfAir = 1.23; // Doesn't matter because gravity so great

			// Set planet name
			planet = "Neutron Star";
			System.out.println("Neutron Star: Gravity = " + gravity);
		}

		// Final calculation for b and terminal velocity
		b = .5 * dragCoef * densityOfAir * crossSectionArea;
		terminalVelocity = Math.sqrt(((mass * gravity) / b));
		System.out.println("b: " + b + "TV: " + terminalVelocity);
	}


	// Debug function for making sure the numbers are correct
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

	// Get the list of x position
	public List<Double> getXPosList()
	{
		return this.xPosList;
	}

	// Get the list of y position
	public List<Double> getYPosList()
	{
		return this.yPosList;
	}

	// Get list of time
	public List<Double> getTimeList()
	{
		return this.timeList;
	}

	// Get the max height
	public double getMaxHeight()
	{
		return this.maxHeight;
	}

	// Get the max horizontal distance
	public double getMaxDistance()
	{
	    // Make sure it went some distance
		if(xPosList.size() > 0)
		{
			return this.xPosList.get(xPosList.size() - 1);
		}

		return 0;
	}

	// Get the total time
	public double getTotalTime()
	{
		return this.timeBallHitsGround;
	}

	// Get the current planet
	public String getCurrentPlanet()
	{
		return this.planet;
	}

    // Get the gravity
	public double getGravity()
	{
		return this.gravity;
	}

    // Get the density of air
	public double getDensityOfAir()
	{
		return this.densityOfAir;
	}


}
