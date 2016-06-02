package com.dorf.skeleton;

import java.util.ArrayList;

import com.dorf.framework.Image;

/*
	This code for animation was learned on a website
*/

public class Animation {
	//for animating separate images together
	private ArrayList<AnimFrame> frames;
	private int currentFrame;
	
	// Long takes up more memory but holds more accurate numbers
	private long animTime;
	private long totalDuration;
	
	
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
		totalDuration += duration;
		frames.add(new AnimFrame(image, totalDuration));
	}

	// Update
	public synchronized void update(long elapsedTime)
	{
		// If it has a frame
		if(frames.size() > 1)
		{
			animTime += elapsedTime;

			// Resets the animation
			if(animTime >= totalDuration)
			{
				animTime = animTime % totalDuration;
				currentFrame = 0;
			}
			
			
			while(animTime > getFrame(currentFrame).endTime)
			{
				currentFrame++;
			}
		}
	}
	
	public synchronized Image getImage()
	{
		if(frames.size() == 0)
		{
			return null;
		}
		else
		{
			return getFrame(currentFrame).image;
		}
	}
	
	public AnimFrame getFrame(int i)
	{
		return (AnimFrame)frames.get(i);
	}
	
	public long getDuration(){
		return totalDuration;
	}
	
	public void setDuration(long newDuration){
		totalDuration = newDuration;
	}
	public int getSize(){
		return frames.size();
	}
	
	public void setCurrentFrameZero()
	{
		this.animTime = totalDuration;
	}
	
	public void setCurrentFrame(int frame) {
		this.currentFrame = frame;
	}
	
	// Class within class!
	private class AnimFrame
	{
		Image image;
		long endTime;
		
		public AnimFrame(Image image, long endTime)
		{
			this.image = image;
			this.endTime = endTime;
		}
	}

}
