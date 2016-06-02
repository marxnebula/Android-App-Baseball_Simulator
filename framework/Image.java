package com.dorf.framework;

import com.dorf.framework.Graphics.ImageFormat;

public interface Image {
    public int getWidth();
    public int getHeight();
    public ImageFormat getFormat();
	void dispose();
	
}

