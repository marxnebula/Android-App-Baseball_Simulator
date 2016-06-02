package com.dorf.skeleton;

import java.util.ArrayList;
import com.dorf.framework.Image;

/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	- Class for animating sprites.
	- For each image added, you add a duration time also.  After the duration time
	  is completed, then it switches the frame.  This repeats after it has gone
	  through all the images.

	  Code by Jordan Marx (2014)
*/



public class Animation {

	// Stores the frames
	private ArrayList<AnimFrame> frames;

	// The current frame
	private int currentFrame;

	// The animation time
	// Long takes up more memory but holds more accurate numbers
	private long animTime;

	// Total duration of the animation
	private long totalDuration;


	// Constructor
	public Animation()
	{
		frames = new ArrayList<AnimFrame>();
		totalDuration = 0;

		// Calls these two variables together
		synchronized(this)
		{
			animTime = 0;
			currentFrame = 0;
		}
	}


	// Adds a frame
	public synchronized void addFrame(Image image, long duration)
	{
	    // Adds to the total duration
		totalDuration += duration;

		// Add the fram to the array list
		frames.add(new AnimFrame(image, totalDuration));
	}

	// Update
	public synchronized void update(long elapsedTime)
	{
		// If it has a frame
		if(frames.size() > 1)
		{
			animTime += elapsedTime;

			// If animTime is greater than total duration then reset the animation
			if(animTime >= totalDuration)
			{
				animTime = animTime % totalDuration;
				currentFrame = 0;
			}

            // While animTime is greater than the current fram end time
			while(animTime > getFrame(currentFrame).endTime)
			{
			    // Add 1 to the current frame
				currentFrame++;
			}
		}
	}

    // Gets the current image
	public synchronized Image getImage()
	{
	    // If there is no image then return null
		if(frames.size() == 0)
		{
			return null;
		}
		else
		{
			return getFrame(currentFrame).image;
		}
	}

    // Gets the current frame
	public AnimFrame getFrame(int i)
	{
		return (AnimFrame)frames.get(i);
	}

    // Gets the total duration
	public long getDuration(){
		return totalDuration;
	}

    // Set the duration
	public void setDuration(long newDuration){
		totalDuration = newDuration;
	}

	// Gets the number of frames
	public int getSize(){
		return frames.size();
	}

    // Set the current fram to the parameter frame
	public void setCurrentFrame(int frame) {
		this.currentFrame = frame;
	}

	// Class within class
	// Stores an image and end time
	private class AnimFrame
	{
		Image image;
		long endTime;

        // Constructor
		public AnimFrame(Image image, long endTime)
		{
			this.image = image;
			this.endTime = endTime;
		}
	}

}
