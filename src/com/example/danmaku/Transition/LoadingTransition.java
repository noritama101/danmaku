package com.example.danmaku.Transition;

import android.graphics.Bitmap;

import com.example.danmaku.GameManager;
import com.example.danmaku.Screen.Screenable;
import com.example.opengles20util.core.GLES20Util;

public class LoadingTransition implements Transitionable {
	private static LoadingTransition instance;

	public static LoadingTransition getInstance(){
		if(instance == null)
			instance = new LoadingTransition();
		return instance;
	}
	private Class<?> nextScreen;
	public void setNextScreen(Class<?> nextScreen) {
		this.nextScreen = nextScreen;
	}
	private float alpha = 0;
	private float deltaAlpha =0;
	private int mode = 0;
	private Bitmap bitmap = GLES20Util.createBitmap(122, 122, 122, 255);
	private int transitionTime = 60;

	@Override
	public boolean Transition() {
		if(mode == 0){
			deltaAlpha = 1f/(float)transitionTime;
			mode = 1;
		}
		if(mode == 1){
			GameManager.nowScreen.Draw(0,0);
			alpha += deltaAlpha;
			GLES20Util.DrawGraph(0, 0, GLES20Util.getAspect()*2f, 2f, bitmap,alpha);
			GLES20Util.DrawString("NowLoading...", 1, 255,255,255,alpha, 0.5f, 0);
			transitionTime--;
			if(transitionTime <= 0)
				mode = 2;
			return true;
		}
		else{
			try {
				GameManager.nowScreen  = (Screenable)(nextScreen.newInstance());
			} catch (InstantiationException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			mode = 0;
			alpha = 0;
			deltaAlpha = 0;
			return false;
		}
	}

	public void setTransitionTime(int transitionTime) {
		this.transitionTime = transitionTime;
	}

}
