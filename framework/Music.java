package com.dorf.framework;

public interface Music {
    public void play();

    public void stop();

    public void pause();

    public void setLooping(boolean looping);

    public void setVolume(float volume);

    public boolean isPlaying();

    public boolean isStopped();

    public boolean isLooping();

    public void dispose();

	void seekBegin();
	
	public void fadeOut(float deltaTime);
	
	public void fadeIn(float deltaTime);
	
	public float getVolume();
}
