package com.example.danmaku.UI;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;

import com.example.danmaku.UI.Text.TextAlign;
import com.example.danmaku.UI.UIAlign.Align;
import com.example.danmaku.UI.Listener.ButtonListener;
import com.example.opengles20util.core.GLES20Util;
import com.example.opengles20util.core.GLES20Util.GLES20UTIL_MODE;
import com.example.opengles20util.graphic.blending_mode.GLES20COMPOSITION_ALPHA;

public class Button {
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getLengthX() {
		return lengthX;
	}

	public void setLengthX(float lengthX) {
		this.lengthX = lengthX;
	}

	public float getLengthY() {
		return lengthY;
	}

	public void setLengthY(float lengthY) {
		this.lengthY = lengthY;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

	private float x,y,lengthX,lengthY;
	private float alpha;
	private Bitmap image;
	private Text text;
	private Align xAlign;
	private Align yAlign;
	private ButtonListener listener;

	public Button(float x,float y,float lengthX,float lengthY,float alpha,int imageId,String text,int r,int g,int b){
		this.x = x;
		this.y = y;
		this.lengthX = lengthX;
		this.lengthY = lengthY;
		this.alpha = alpha;
		image = GLES20Util.createBitmap(255,0,0,255);
		this.text = new Text(text,r,g,b);
		this.text.setHorizontalTextAlign(TextAlign.CENTOR);
		this.text.setVerticalTextAlign(TextAlign.CENTOR);
		xAlign = UIAlign.Align.CENTOR;
		yAlign = UIAlign.Align.CENTOR;
	}

	public void setListener(ButtonListener l){
		listener = l;
	}

	private float xbuffer;
	private float ybuffer;
	public void touch(MotionEvent event){
		xbuffer = GLES20Util.screenToInnerPosition(event.getX(0), GLES20UTIL_MODE.POSX);
		ybuffer = GLES20Util.screenToInnerPosition(event.getY(0), GLES20UTIL_MODE.POSY);
		if(x-UIAlign.convertAlign(lengthX, xAlign) <= xbuffer &&
			xbuffer <= x+UIAlign.convertAlign(lengthX, xAlign) &&
			y-UIAlign.convertAlign(lengthY, yAlign) <= ybuffer &&
			ybuffer <= y+UIAlign.convertAlign(lengthY,yAlign))
		{
			switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				alpha = 0.5f;
				break;
			case MotionEvent.ACTION_MOVE:
				alpha = 0.5f;
				break;
			case MotionEvent.ACTION_UP:
				alpha = 1f;
				execute();
				break;
			}
		}
		else
			alpha = 1f;
	}

	public void execute(){
		Log.d("Button","execute");
		if(listener != null)
			listener.execute(this);
	}

	public void draw(){
		GLES20Util.DrawGraph(x-UIAlign.convertAlign(lengthX, xAlign),
				y-UIAlign.convertAlign(lengthY, yAlign),
				lengthX,
				lengthY,
				image,
				alpha,
				GLES20COMPOSITION_ALPHA.getInstance());
		text.draw(x, y, alpha,GLES20COMPOSITION_ALPHA.getInstance());
	}
	public void draw(float offsetX,float offsetY){
		GLES20Util.DrawGraph(x-UIAlign.convertAlign(lengthX, xAlign)+offsetX,
				y-UIAlign.convertAlign(lengthY, yAlign)+offsetY,
				lengthX,
				lengthY,
				image,
				alpha,
				GLES20COMPOSITION_ALPHA.getInstance());
		text.draw(x+offsetX, y+offsetY, alpha,GLES20COMPOSITION_ALPHA.getInstance());
	}
}
