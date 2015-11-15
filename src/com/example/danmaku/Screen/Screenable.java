package com.example.danmaku.Screen;

import android.view.MotionEvent;

public interface Screenable {
	public void Draw(float offsetX,float offsetY);
	public void Touch(MotionEvent event);
}
