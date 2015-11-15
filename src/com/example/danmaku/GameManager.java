package com.example.danmaku;

import android.view.MotionEvent;

import com.example.danmaku.Screen.Screenable;
import com.example.danmaku.Transition.Transitionable;

public class GameManager {
	public static Screenable nowScreen;
	public static Screenable nextScreen;
	public static boolean isTransition = false;
	public static Transitionable transition;
	public static void draw(){
		if(isTransition){
			isTransition = transition.Transition();
		}
		else
			nowScreen.Draw(0, 0);
	}
	public static void touch(MotionEvent touch){
		if(!isTransition){
			nowScreen.Touch(touch);
		}
	}

}
