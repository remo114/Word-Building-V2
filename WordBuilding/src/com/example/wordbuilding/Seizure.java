package com.example.wordbuilding;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.content.Context;
import android.util.Log;

public class Seizure extends Sprite{

	protected static final String TAG = "Seizure";

	Sprite seizure;
	
	float px=5000,py=5000, initialX=10,initialY= 10;
	
	public Seizure(float pX, float pY, float pWidth, float pHeight,ITextureRegion pTextureRegion,
			VertexBufferObjectManager pSpriteVertexBufferObject, Context cont, int seizureSound) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pSpriteVertexBufferObject);
		// TODO Auto-generated constructor stub
		
		seizure = new Sprite(pX, pY, pTextureRegion, pSpriteVertexBufferObject){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,	final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				switch (pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN: {
						px= pSceneTouchEvent.getX();
						py = pSceneTouchEvent.getY();
						
						//play audio
						Log.d(TAG, " Touched down " + seizure.getUserData());
						break;
					}
					case TouchEvent.ACTION_MOVE: {
						if(px ==5000 && py == 5000 ){
							px= pSceneTouchEvent.getX();
							py = pSceneTouchEvent.getY();
						} 
						seizure.setPosition((this.getX()+(pSceneTouchEvent.getX()-px)),(this.getY() + (pSceneTouchEvent.getY()-py)));
						/*Scheming.collutionDetection(markerSelf);
						
						if(markerSelf.isSingle){
							seizure.setPosition((this.getX()+(pSceneTouchEvent.getX()-px)),(this.getY() + (pSceneTouchEvent.getY()-py)));
						}
						else{
							float previousX = seizure.getX();
							float previousY = seizure.getY();
							seizure.setPosition((seizure.getX()+(pSceneTouchEvent.getX()-px)),(seizure.getY() + (pSceneTouchEvent.getY()-py)));
							float afterX = seizure.getX();
							float afterY = seizure.getY();
							
							Scheming.moveBlock(previousX, previousY, afterX, afterY, markerSelf);
						}*/
						
						px= pSceneTouchEvent.getX();
						py = pSceneTouchEvent.getY();
						break;
					}
					case TouchEvent.ACTION_UP: {
						px = 5000;
						py = 5000;
						Log.d(TAG, " Touched Up " + seizure.getUserData());
						Log.d(TAG, " ");
						
						MoveModifier mf = new MoveModifier(0.5f, seizure.getX(),initialX, seizure.getY(), initialY);
						seizure.registerEntityModifier(mf);
						break;
					}
				}
				return false;
			}
		};
		BaseActivity.mCurrentScene.registerTouchArea(seizure);
		BaseActivity.mCurrentScene.attachChild(seizure);
		seizure.setPosition(initialX, initialY);
		//con = cont;
		seizure.setHeight(pHeight);
		seizure.setWidth(pWidth);
	}
}
