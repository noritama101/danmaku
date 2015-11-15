package com.example.danmaku.Screen;
import android.view.MotionEvent;

import com.example.danmaku.GameManager;
import com.example.danmaku.Transition.ScrollTransition;
import com.example.danmaku.UI.Text;
import com.example.opengles20util.core.GLES20Util;

public class MenuScreen implements Screenable {
	private final static String[] content = {"START","OPTION","CREDIT"};
	private static Text[] b_content;

	static {
		b_content = new Text[content.length];
		for(int n=0;n<content.length;n++){
			b_content[n] = new Text(content[n],255,255,255);
			b_content[n].setHorizontalTextAlign(Text.TextAlign.CENTOR);
			b_content[n].setVerticalTextAlign(Text.TextAlign.BOTTOM);
		}
	}

	@Override
	public void Draw(float offsetX, float offsetY) {
		for(int n=0;n<content.length;n++){
			if(b_content[n] != null){
				b_content[n].draw(offsetX+GLES20Util.getAspect(), offsetY-0.3f*n+1.5f, 1, 1f);
			}
		}
	}
	@Override
	public void Touch(MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_UP:
			GameManager.isTransition = true;
			ScrollTransition.getInstance().setScrollTime(10);
			ScrollTransition.getInstance().setDirect(GLES20Util.getAspect()*2f, 0);
			GameManager.nextScreen = new StageSelectScreen();
			GameManager.transition = ScrollTransition.getInstance();
			break;
		}
	}

}
