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


 /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	- Class for creating a button to be clicked on.
	- It can have text or no text.
	- After the button has been clicked it can change image.

	  Code by Jordan Marx (2014)
*/

public class Button {

    // Enum for button type
	private enum ButtonType {
		text, noText
	}

	// Rest image
	private Image restImage;

	// Clicked image
	private Image clickedImage;

	// Current image
	private Image currentImage;

	// Text
	private String words = "Set Text Pls";

	// Font
	private Paint font = new Paint();

	// Font color
	private int restColor = Color.WHITE;
	private int clickedColor = Color.BLUE;

	// Height and width of button
	private int height;
	private int width;

	// Positions of button
	private int x;
	private int y;

	// Text position
	private int textX;
	private int textY;

	// Border
	private int spaceBorder = 2;

	// Default sie of text
	private float size = 30;

	// Rect
	private Rect box;

	// Boolean for if button touched
	private boolean isTouched = false;

	// Button type
	private ButtonType type = ButtonType.noText;


    // Constructor
	// Button without text
	public Button(int centerX, int centerY, Image restImage, Image clickedImage) {
	    // Store positions
		this.x = centerX - restImage.getWidth() / 2;
		this.y = centerY - restImage.getHeight() / 2;

		// Store rest image
		this.restImage = restImage;

		// Store clicked image
		this.clickedImage = clickedImage;

		// Set font variables
		this.font.setTextAlign(Paint.Align.CENTER);
		this.font.setColor(restColor); // default 30
		font.setTextSize(size);

		// Create the rect
		createRectangle();

        // Set current image
		this.currentImage = restImage;

		// Set boolean to false
		isTouched = false;
	}

    // Constructor
	// Button with text
	public Button(int centerX, int centerY, Image restImage,
			Image clickedImage, float textSize, String text) {

        // Set enum to text
		this.type = ButtonType.text;

		// Store positions
		this.x = centerX - restImage.getWidth() / 2;
		this.y = centerY - restImage.getHeight() / 2;

		// Store rest image
		this.restImage = restImage;

		// Store clicked image
		this.clickedImage = clickedImage;

		// Set font variables
		this.font.setTextAlign(Paint.Align.CENTER);
		this.font.setColor(restColor); // default 30
		font.setTextSize(size);

		// Create the rect
		createRectangle();

        // Set current image
		this.currentImage = restImage;

		// Set boolean to false
		isTouched = false;
	}

	// Used to check if the event this button corresponds to should be triggered
	public boolean performEvent() {

		boolean ans = false;

		// If is touched
		if (this.isTouched) {
			ans = true;
			this.isTouched = false;
		}
		return ans;
	}

	// Should be updated every time a down event is triggered
	public boolean down(TouchEvent event) {

		boolean x = false;

		// If in the bounds of box
		if (MathHelper.inBounds(event, box)) {
            // Change to clicked image
			this.currentImage = clickedImage;

            // Set the font color// Store positions
		this.x = centerX - restImage.getWidth() / 2;
		this.y = centerY - restImage.getHeight() / 2;

		// Store rest image
		this.restImage = restImage;

		// Store clicked image
		this.clickedImage = clickedImage;

		// Set font variables
		this.font.setTextAlign(Paint.Align.CENTER);
		this.font.setColor(restColor); // default 30
		font.setTextSize(size);

		// Create the rect
		createRectangle();

        // Set current image
		this.currentImage = restImage;

		// Set boolean to false
		isTouched = false;
			this.font.setColor(clickedColor);

            // Set is touched to true
			this.isTouched = true;

		}
		return x;

	}

	// Should be updated every time an up event is triggered
	public boolean up(TouchEvent event) {

        // Set the current image to rest image
		this.currentImage = restImage;

		// Set the rest color
		this.font.setColor(restColor);

		if (MathHelper.inBounds(event, box)) {
			this.isTouched = true;
			return true;
		}

		return false;

	}

	// Check if button is touched
	public boolean touchEvent(TouchEvent event){

	    // If event is in bounds of box
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
	    // Set the position of the box
		box.set(xPos, y, xPos + this.clickedImage.getHeight(), y + this.clickedImage.getWidth());
	}

    // Draw the button if not using current image
	public void draw(Graphics g) {
	    // Draw image
		g.drawImage(currentImage, x, y);

		// If it is of type text
		if (type == ButtonType.text) {

			font.setLinearText(true);

            // Draw the string
			g.drawString(words, textX, textY, font);
		}
	}


    // Draw button if using different image
	public void draw(Graphics g, Image image) {

	    // Draw the image
		g.drawImage(image, x, y);

		// If button is type text
		if (type == ButtonType.text) {
			font.setLinearText(true);

			// Draw the string
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

    // Sets the text
	public void setText(String text) {
		this.type = ButtonType.text;
		this.words = text;
	}

    // Sets the rest color
	public void setRestColor(int newColor) {
		this.type = ButtonType.text;
		this.font.setColor(newColor);
	}

    // Sets the clicked color
	public void setClickedColor(int newColor) {
		this.type = ButtonType.text;
		this.clickedColor = newColor;
	}

    // Sets the text size
	public void setTextSize(float textSize) {
		this.type = ButtonType.text;
		font.setTextSize(textSize);
	}

    // Sets the font type
	public void setFontType(Typeface tf) {

	    // Make it of text type
		this.type = ButtonType.text;
		font.setTypeface(tf);
	}

    // Set the font to bold
	public void setFontBold()
	{
		this.font.setFakeBoldText(true);
	}

    // Set text align
	public void setTextAlign(Paint.Align align) {

	    // Make it of text type
		this.type = ButtonType.text;
		font.setTextAlign(align);
	}

    // Set anti alias
	public void setAntiAlias(boolean t) {

	    // Make it of text type
		this.type = ButtonType.text;
		font.setAntiAlias(t);
	}

    // Set the center x position
	public void setCenterX(int xPos)
	{
		this.x = xPos;
	}

    // Set the center y position
	public void setCenterY(int yPos)
	{
		this.y = yPos;
	}

    // Get the center x posiiton
	public int getCenterX()
	{
		return this.x;
	}

    // Get the center y position
	public int getCenterY()
	{
		return this.y;
	}

    // Get the text
	public String getString()
	{
		return this.words;
	}

}
