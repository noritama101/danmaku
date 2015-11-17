package com.example.danmaku.Screen;

import android.view.MotionEvent;

import com.example.danmaku.GameManager;
import com.example.danmaku.Transition.LoadingTransition;
import com.example.opengles20util.core.GLES20Util;
import com.example.opengles20util.graphic.blending_mode.GLES20COMPOSITION_ALPHA;

public class StageSelectScreen implements Screenable {
	@Override
	public void Draw(float offsetX, float offsetY) {
		GLES20Util.DrawString("StageSelectScreen", 1, 255,255,255,1f, offsetX, offsetY,GLES20COMPOSITION_ALPHA.getInstance());
	}

	@Override
	public void Touch(MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_UP:
			GameManager.isTransition = true;
			LoadingTransition.getInstance().setNextScreen(MenuScreen.getInstance());
			LoadingTransition.getInstance().setTransitionTime(10);
			GameManager.transition = LoadingTransition.getInstance();
			break;
		}
	}

}
