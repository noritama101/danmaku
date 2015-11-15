package com.example.danmaku;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;

import com.example.danmaku.Screen.MenuScreen;
import com.example.opengles20util.Util.FileManager;
import com.example.opengles20util.Util.FpsController;
import com.example.opengles20util.core.GLES20Util;

public class Main extends Activity implements GLSurfaceView.Renderer {
  // メンバー変数
  private boolean touch = false;
  private ScaleGestureDetector gesDetect = null;
  private Bitmap[] fpsImage = new Bitmap[10];
  private Player player;
  private String vertexShader;
  private String fragmentShader;
  private FpsController fpsControll = new FpsController((short)60);

  public boolean onTouchEvent(MotionEvent event){
	  if(event.getPointerCount()>1){
		  //マルチタッチを検出
		  gesDetect.onTouchEvent(event);
		  touch = true;
		  return true;
	  }
	  switch(event.getAction()){
	  case MotionEvent.ACTION_DOWN:
		  //player.setTouchDown(event.getX(),event.getY());
		  break;
	  case MotionEvent.ACTION_MOVE:
		  //player.move(event.getX(),event.getY());
		  break;
	  case MotionEvent.ACTION_UP:
		  GameManager.touch(event);
		  break;
	  }
	return true;
  }

//スケールジェスチャーイベントを取得
private final SimpleOnScaleGestureListener onScaleGestureListener = new SimpleOnScaleGestureListener(){
	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		// TODO Auto-generated method stub
		Log.v("ScaleGesture", "onScaleBegin");
		return super.onScaleBegin(detector);
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {
		// TODO Auto-generated method stub
		//moveZ = positionZ;
		//positionZ = 0;
		Log.v("ScaleGesture", "onScaleEnd");
		super.onScaleEnd(detector);
	}
};

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);

     // OpenGL ES 2.0が使用できるように初期化する
     GLSurfaceView glSurfaceView = GLES20Util.initGLES20(this, this);

     // GLSurfaceViewをこのアプリケーションの画面として使用する
     setContentView(glSurfaceView);

     // ScaleGestureDetecotorクラスのインスタンス生成
     gesDetect = new ScaleGestureDetector(this, onScaleGestureListener);

     GameManager.nowScreen = new MenuScreen();

     Log.d("onCreate","onCreate finished");
  }

  @Override
  public void onSurfaceCreated(GL10 gl, EGLConfig config) {
	Log.d("onSurfaceCreated","initShader");
	vertexShader = new String(FileManager.readShaderFile(this,"VSHADER.txt"));
	fragmentShader = new String(FileManager.readShaderFile(this,"FSHADER.txt"));
	GLES20Util.initGLES20Util(vertexShader,fragmentShader);
    GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // 画面をクリアする色を設定する

    Log.d("onSurfaceCreate","ScreenSize width : " + String.valueOf(GLES20Util.getWidth())
   		 + " | height : " + String.valueOf(GLES20Util.getHight())
   		 );

  }

  @Override
  public void onSurfaceChanged(GL10 gl, int width, int height) {
    // 表示領域を設定する
    GLES20Util.initDrawErea(width, height,false);
    //テクスチャの再読み込み
    GLES20Util.initTextures();
    GLES20Util.initFpsBitmap(fpsImage,true,R.drawable.degital2);

  }

  @Override
  public void onDrawFrame(GL10 gl) {
	  process();
	  draw();
  }
  private void process(){
	fpsControll.updateFps();
  }
  private void draw(){
	// 描画領域をクリアする
	GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    //FPSの表示
    GLES20Util.DrawFPS(0,1.8f,fpsControll.getFps(),fpsImage,1f);
    //
    GameManager.draw();
  }
}