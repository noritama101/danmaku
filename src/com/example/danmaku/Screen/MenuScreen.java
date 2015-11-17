package com.example.danmaku.Screen;

import android.util.Log;
import android.view.MotionEvent;

import com.example.danmaku.GameManager;
import com.example.danmaku.R;
import com.example.danmaku.Transition.ScrollTransition;
import com.example.danmaku.UI.Button;
import com.example.danmaku.UI.Text;
import com.example.danmaku.UI.Listener.ButtonListener;
import com.example.opengles20util.core.GLES20Util;
import com.example.opengles20util.core.GLES20Util.GLES20UTIL_MODE;
import com.example.opengles20util.graphic.blending_mode.GLES20COMPOSITION_ADD;

public class MenuScreen implements Screenable {
	private static MenuScreen instance;
	private final static String[] content = {"START","OPTION","CREDIT"};
	private static Text[] b_content;
	private static Button button;
	private long time = 0;

	private MenuScreen(){
		b_content = new Text[content.length];
		for(int n=0;n<content.length;n++){
			b_content[n] = new Text(content[n],255,255,255);
			b_content[n].setHorizontalTextAlign(Text.TextAlign.CENTOR);
			b_content[n].setVerticalTextAlign(Text.TextAlign.CENTOR);
		}
		Log.d("MenuScreen",String.valueOf(GLES20Util.getAspect()));
		button = new Button(GLES20Util.getAspect(), 0.5f, 0.7f, 0.2f, 1f,R.drawable.plane , "Start", 255, 255, 255);
		button.setListener(new StartButtonListener());
	}

	public static MenuScreen getInstance(){
		if(instance == null)
			instance = new MenuScreen();
		return instance;
	}
	@Override
	public void Draw(float offsetX, float offsetY) {
		button.setX((float)(0.01*Math.sin(Math.toRadians(time))) + GLES20Util.getAspect());
		button.setY((float)(0.01*Math.sin(Math.toRadians(time))) + 0.5f);

		for(int n=0;n<content.length;n++){
			if(b_content[n] != null){
				b_content[n].draw(offsetX+GLES20Util.getAspect()+(float)(0.01*Math.sin(Math.toRadians(time))*(content.length-n)),
						offsetY-0.3f*n+1.5f+(float)(0.01*Math.sin(Math.toRadians(time)*n)),
						1f,
						GLES20COMPOSITION_ADD.getInstance());
			}
		}
		button.draw(offsetX,offsetY);
		time++;
	}
	@Override
	public void Touch(MotionEvent event) {
		float tempX = GLES20Util.screenToInnerPosition(event.getX(0), GLES20UTIL_MODE.POSX);
	    float tempY = GLES20Util.screenToInnerPosition(event.getY(0), GLES20UTIL_MODE.POSY);
		button.touch(event);
		/*switch(event.getAction()){
		case MotionEvent.ACTION_UP:
			GameManager.isTransition = true;
			ScrollTransition.getInstance().setScrollTime(10);
			ScrollTransition.getInstance().setDirect(GLES20Util.getAspect()*2f, 0);
			GameManager.nextScreen = new StageSelectScreen();
			GameManager.transition = ScrollTransition.getInstance();
			break;
		}*/
	}

	public class StartButtonListener implements ButtonListener{
		@Override
		public void execute(Button button) {
			GameManager.isTransition = true;
			ScrollTransition.getInstance().setScrollTime(10);
			ScrollTransition.getInstance().setDirect(GLES20Util.getAspect()*2f, 0);
			GameManager.nextScreen = new StageSelectScreen();
			GameManager.transition = ScrollTransition.getInstance();
		}

	}

}
