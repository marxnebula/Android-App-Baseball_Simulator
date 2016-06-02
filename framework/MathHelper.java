package com.dorf.framework;

import android.graphics.Rect;

import com.dorf.framework.Input.TouchEvent;

public class MathHelper {
	// any functions that need to be used frequently, aka clamp should be
	// written as static methods here.

	public static float clamp(float val, float min, float max) {
		return Math.max(min, Math.min(max, val));
	}
	public static boolean inBounds(TouchEvent event, Rect rect) {
		if (event.x > rect.left && event.x < rect.right && event.y > rect.top
				&& event.y < rect.bottom) {
			return true;
		} else
			return false;
	}
	public static boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}

}
