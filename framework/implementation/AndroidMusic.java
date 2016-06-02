package com.dorf.framework.implementation;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;

import com.dorf.framework.Music;

public class AndroidMusic implements Music, OnCompletionListener,
		OnSeekCompleteListener, OnPreparedListener, OnVideoSizeChangedListener {
	MediaPlayer mediaPlayer;
	boolean isPrepared = false;

	float volume = 1;
	float speed = 0.05f;

	public AndroidMusic(AssetFileDescriptor assetDescriptor) {
		mediaPlayer = new MediaPlayer();
		try {
			mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(),
					assetDescriptor.getStartOffset(),
					assetDescriptor.getLength());
			mediaPlayer.prepare();
			isPrepared = true;
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.setOnSeekCompleteListener(this);
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.setOnVideoSizeChangedListener(this);

		} catch (Exception e) {
			throw new RuntimeException("Couldn't load music");
		}
	}

	@Override
	public void dispose() {

		if (this.mediaPlayer.isPlaying()) {
			this.mediaPlayer.stop();
		}
		this.mediaPlayer.release();
	}

	@Override
	public boolean isLooping() {
		return mediaPlayer.isLooping();
	}

	@Override
	public boolean isPlaying() {
		return this.mediaPlayer.isPlaying();
	}

	@Override
	public boolean isStopped() {
		return !isPrepared;
	}

	@Override
	public void pause() {
		if (this.mediaPlayer.isPlaying())
			mediaPlayer.pause();
	}

	@Override
	public void play() {
		if (this.mediaPlayer.isPlaying())
			return;

		try {
			synchronized (this) {
				if (!isPrepared)
					mediaPlayer.prepare();
				mediaPlayer.start();
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setLooping(boolean isLooping) {
		mediaPlayer.setLooping(isLooping);
	}

	@Override
	public void setVolume(float volume) {
		this.mediaPlayer.setVolume(volume, volume);
		this.volume=volume;
	}

	@Override
	public void stop() {
		// if (mediaPlayer.isPlaying() == true) {
		this.mediaPlayer.stop();

		synchronized (this) {
			isPrepared = false;
		}
		// }
	}

	@Override
	public void onCompletion(MediaPlayer player) {
		synchronized (this) {
			isPrepared = false;
		}
	}

	@Override
	public void seekBegin() {
		mediaPlayer.seekTo(0);

	}

	@Override
	public void onPrepared(MediaPlayer player) {
		// TODO Auto-generated method stub
		synchronized (this) {
			isPrepared = true;
		}

	}

	@Override
	public void onSeekComplete(MediaPlayer player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onVideoSizeChanged(MediaPlayer player, int width, int height) {
		// TODO Auto-generated method stub

	}

	public void fadeOut(float deltaTime) {

		this.volume -= speed * deltaTime;
		this.mediaPlayer.setVolume(volume, volume);
		
		if (volume <= 0f) {
			this.mediaPlayer.pause();
		}

		synchronized (this) {
			isPrepared = false;
		}

	}

	public void fadeIn(float deltaTime) {
		if (this.volume < 1) {
			this.mediaPlayer.setVolume(volume, volume);
			this.volume += speed * deltaTime;
		}
		else
			System.out.println("Fade in finished");
	}

	public float getVolume() {
		return this.volume;
	}
}
