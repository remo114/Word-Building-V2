package com.example.wordbuilding;

import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.content.Context;
import android.util.Log;

public class Marker extends Sprite{
	
	Sprite letter;
	Marker left,right,markerSelf;
	int sound;
	boolean isSingle = true;
	public static String TAG = "Marker ";
	
	
	float px=5000,py=5000;

	public Marker(float pX, float pY, float pWidth, float pHeight,ITextureRegion pTextureRegion,
			VertexBufferObjectManager pSpriteVertexBufferObject, Context cont, int letterSound) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pSpriteVertexBufferObject);
		// TODO Auto-generated constructor stub
		sound = letterSound;
		left = null;
		right = null;
		
		letter = new Sprite(pX, pY, pTextureRegion, pSpriteVertexBufferObject){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,	final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				switch (pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN: {
						px= pSceneTouchEvent.getX();
						py = pSceneTouchEvent.getY();
						
						//play audio
						Sound.playSingleSound(sound);
						Log.d(TAG, " Touched down " + letter.getUserData());
						break;
					}
					case TouchEvent.ACTION_MOVE: {
						if(px ==5000 && py == 5000 ){
							px= pSceneTouchEvent.getX();
							py = pSceneTouchEvent.getY();
						} 
						//if(letter.getX() > 0 && letter.getX() < BaseActivity.CAMERA_WIDTH-100 && letter.getY() >0 && letter.getY()<BaseActivity.CAMERA_HEIGHT-100 ){
							Scheming.collutionDetection(markerSelf);
							Scheming.checkBoundary(markerSelf);
							
							if(markerSelf.isSingle){
								/*if(letter.getX() + letter.getWidth()  > BaseActivity.CAMERA_WIDTH || letter.getX()< 0){
									letter.setPosition(letter.getX() - 0.1f,letter.getY() - 0.1f);
								}
								else{*/
									letter.setPosition((this.getX()+(pSceneTouchEvent.getX()-px)),(this.getY() + (pSceneTouchEvent.getY()-py)));
							//	}
							} 
							else{
								float previousX = letter.getX();
								float previousY = letter.getY();
								letter.setPosition((letter.getX()+(pSceneTouchEvent.getX()-px)),(letter.getY() + (pSceneTouchEvent.getY()-py)));
								float afterX = letter.getX();
								float afterY = letter.getY();
								
								Scheming.moveBlock(previousX, previousY, afterX, afterY, markerSelf);
							}
							
							
						/*}
						else{
							
						}*/
						
						px= pSceneTouchEvent.getX();
						py = pSceneTouchEvent.getY();
						break;
					}
					case TouchEvent.ACTION_UP: {
						px = 5000;
						py = 5000;
						Log.d(TAG, " Touched Up " + letter.getUserData());
						Log.d(TAG, " ");
						break;
					}
				}
				return false;
			}
		};
		BaseActivity.mCurrentScene.registerTouchArea(letter);
		BaseActivity.mCurrentScene.attachChild(letter);
		//con = cont;
		letter.setHeight(pHeight);
		letter.setWidth(pWidth);
	}

}
