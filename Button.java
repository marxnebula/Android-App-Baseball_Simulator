package com.dorf.skeleton;

import java.util.List;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.dorf.framework.Graphics;
import com.dorf.framework.Image;
import com.dorf.framework.Input.TouchEvent;
import com.dorf.framework.MathHelper;

/*
	Class for creating a button.  It can have text or no text.  After the button has been clicked,
	it changes image.
 */

public class Button {

	private enum ButtonType {
		text, noText
	}

	// Images
	private Image restImage;
	private Image clickedImage;
	private Image currentImage;

	// Strings
	private String words = "Set Text Pls";

	// Fonts
	private Paint font = new Paint();

	// Ints
	private int restColor = Color.WHITE;
	private int clickedColor = Color.BLUE;
	private int height;
	private int width;
	private int x;
	private int y;
	private int textX;
	private int textY;
	private int spaceBorder = 2;
	private float size = 30;

	// Rects
	private Rect box;

	// Booleans
	private boolean isTouched = false;

	// Buttons
	private ButtonType type = ButtonType.noText;

	// Button without text
	public Button(int centerX, int centerY, Image restImage, Image clickedImage) {
		this.x = centerX - restImage.getWidth() / 2;
		this.y = centerY - restImage.getHeight() / 2;
		this.restImage = restImage;
		this.clickedImage = clickedImage;
		this.font.setTextAlign(Paint.Align.CENTER);
		this.font.setColor(restColor); // default 30
		font.setTextSize(size);
		createRectangle();

		// autoTextSize();
		this.currentImage = restImage;
		isTouched = false;
	}

	// Button with text
	public Button(int centerX, int centerY, Image restImage,
			Image clickedImage, float textSize, String text) {
		this.type = ButtonType.text;
		this.x = centerX - restImage.getWidth() / 2;
		this.y = centerY - restImage.getHeight() / 2;
		this.restImage = restImage;
		this.clickedImage = clickedImage;
		this.font.setTextAlign(Paint.Align.CENTER);
		this.font.setColor(restColor);
		this.size = textSize; // default 30
		this.words = text;
		font.setTextSize(size);
		createRectangle();
		
		// autoTextSize();
		this.currentImage = restImage;
		isTouched = false;
	}

	// used to check if the event this button corresponds to should be
	// triggered
	public boolean performEvent() {

		boolean ans = false;
		if (this.isTouched) {
			ans = true;
			this.isTouched = false;
		}
		return ans;
	}

	// Should be updated every time a down event is triggered
	public boolean down(TouchEvent event) {

		boolean x = false;
		if (MathHelper.inBounds(event, box)) {
			this.currentImage = clickedImage;
			this.font.setColor(clickedColor);
			
			this.isTouched = true;
			x = true;
			
		}
		return x;

	}

	// Should be updated every time an up event is triggered
	public boolean up(TouchEvent event) {

		this.currentImage = restImage;
		this.font.setColor(restColor);
		
		if (MathHelper.inBounds(event, box)) {
			this.isTouched = true;
			return true;
		}
		
		return false;

	}

	// Check if button is touched
	public boolean touchEvent(TouchEvent event){
		if(MathHelper.inBounds(event, box))
		{
			this.isTouched = true;
			return true;
		}
		
		return false;
	}
	
	// Update
	public void update(int xPos)
	{
		box.set(xPos, y, xPos + this.clickedImage.getHeight(), y + this.clickedImage.getWidth());
	}


	public void draw(Graphics g) {
		g.drawImage(currentImage, x, y);
		if (type == ButtonType.text) {
			font.setLinearText(true);
			g.drawString(words, textX, textY, font);
		}
	}
	
	public void draw(Graphics g, int xPos) {
		g.drawImage(currentImage, xPos, y);
		if (type == ButtonType.text) {
			font.setLinearText(true);
			g.drawString(words, textX, textY, font);
		}
	}

	public void draw(Graphics g, Image image) {
		g.drawImage(image, x, y);
		if (type == ButtonType.text) {
			font.setLinearText(true);
			g.drawString(words, textX, textY, font);
		}
	}

	// Creates a rectangle with already input positions
	private void createRectangle() {
		// creates the bounding box based on button images
		this.height = this.clickedImage.getHeight();
		this.width = this.clickedImage.getWidth();
		this.box = new Rect(x, y, x + width, y + height);
		this.textX = box.centerX();
		this.textY = box.centerY() + (int) font.getTextSize() / 2;
	}

	// Change the rectangles position
	public void setRectangle(int xPos, int yPos)
	{
		box.set(xPos, yPos, xPos + this.clickedImage.getHeight(), y + this.clickedImage.getWidth());
	}

	// Get rect info
	public Rect getRectangle() {
		return this.box;
	}


	public void autoTextSize() {
		// chooses the text size based on the size of the rectangle
		// not working yet... not sure if worth it but also not entirely sure
		// how to do the auto fitting to a rectangle
		Rect textBox = new Rect();
		this.font.getTextBounds(this.words, 0, this.words.length(), textBox);
		boolean cont = true;
		int count = 0;
		while (cont || count > 1000) {
			count++;
			if (textBox.height() > this.height) {
				setTextSize(this.size - 1);
			} else if (textBox.height() < this.height) {
				setTextSize(this.size + 1);
			} else {
				System.out.println("Purrfect fit");
				cont = false;
			}
		}
	}


	public void setText(String text) {
		this.type = ButtonType.text;
		this.words = text;
	}

	public void setRestColor(int newColor) {
		this.type = ButtonType.text;
		this.font.setColor(newColor);
	}

	public void setClickedColor(int newColor) {
		this.type = ButtonType.text;
		this.clickedColor = newColor;
	}

	public void setTextSize(float textSize) {
		this.type = ButtonType.text;
		font.setTextSize(textSize);
	}

	public void setFontType(Typeface tf) {
		// example of Typeface:
		// "Typeface tf = Typeface.create("Helvetica",Typeface.BOLD);"
		this.type = ButtonType.text;
		font.setTypeface(tf);
	}
	
	public void setFontBold()
	{
		this.font.setFakeBoldText(true);
	}

	public void setTextAlign(Paint.Align align) {
		this.type = ButtonType.text;
		font.setTextAlign(align);
	}

	public void setAntiAlias(boolean t) {
		this.type = ButtonType.text;
		font.setAntiAlias(t);
	}
	
	public void setCenterX(int xPos)
	{
		this.x = xPos;
	}
	
	public void setCenterY(int yPos)
	{
		this.y = yPos;
	}
	
	public int getCenterX()
	{
		return this.x;
	}
	
	public int getCenterY()
	{
		return this.y;
	}
	
	public String getString()
	{
		return this.words;
	}
	
}
