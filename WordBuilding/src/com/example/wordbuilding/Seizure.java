package com.example.wordbuilding;

import java.util.ArrayList;
import java.util.Timer;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.content.Context;
import android.util.Log;

public class Seizure{

	protected static final String TAG = "Seizure";

	Sprite seizure;
	Timersz timer;
	float timerInterval = 100000;
	
	float px=5000,py=5000, initialX=10,initialY= 10;
	
	public Seizure(float pX, float pY, float pWidth, float pHeight,ITextureRegion pTextureRegion,
			VertexBufferObjectManager pSpriteVertexBufferObject, Context cont, int seizureSound) {
		//super(pX, pY, pWidth, pHeight, pTextureRegion, pSpriteVertexBufferObject);
		// TODO Auto-generated constructor stub
		
		seizure = new Sprite(pX, pY, pTextureRegion, pSpriteVertexBufferObject){
			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,	final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				
				switch (pSceneTouchEvent.getAction()) {
					case TouchEvent.ACTION_DOWN: {
						px= pSceneTouchEvent.getX();
						py = pSceneTouchEvent.getY();
						BaseActivity.isSeizure = true;
						
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
						CollutionDetection();
						
						px= pSceneTouchEvent.getX();
						py = pSceneTouchEvent.getY();
						
						timer.setInterval(0.1f);
						break;
					}
					case TouchEvent.ACTION_UP: {
						px = 5000;
						py = 5000;
						Log.d(TAG, " Touched Up " + seizure.getUserData());
						Log.d(TAG, " ");
						BaseActivity.isSeizure = false;
						MoveModifier mf = new MoveModifier(0.5f, seizure.getX(),initialX, seizure.getY(), initialY);
						seizure.registerEntityModifier(mf);
						break;
					}
				}
				return true;
			}
		};
		BaseActivity.mCurrentScene.registerTouchArea(seizure);
		BaseActivity.mCurrentScene.attachChild(seizure);
		seizure.setPosition(initialX, initialY);
		seizure.setZIndex(100);
		seizure.setHeight(pHeight);
		seizure.setWidth(pWidth);
		BaseActivity.mCurrentScene.sortChildren();
		
		 timer = new Timersz(timerInterval, new Timersz.ITimerCallback() {
		    public void onTick() {
		    	MoveModifier mf = new MoveModifier(0.5f, seizure.getX(),initialX, seizure.getY(), initialY);
				seizure.registerEntityModifier(mf);
		    }
		});
		(BaseActivity.eng).registerUpdateHandler(timer);		
	}
	
	ArrayList<Marker> CollutionDetection(){
		ArrayList<Marker> markers = new ArrayList<Marker>();
		for (int i = 0; i < BaseActivity.markers.size(); i++) {
			if(seizure.collidesWith(BaseActivity.markers.get(i).letter)){
				markers.add(BaseActivity.markers.get(i));
			} 
			if(markers.size()== 2){
			//	if(markers.get(0).right.equals(markers.get(1).left) || markers.get(0).left.equals(markers.get(1).right)){
					smoothSplit(markers.get(0),markers.get(1));
					return markers;
			//	}
			}
		}
		return null;
	}
	boolean smoothSplit(Marker m1, Marker m2){
		Marker t1 = null, t2 = null;
		if(m1.letter.getX() < m2.letter.getX() && m1.right != null){
			/*if(!m1.right.equals(m2)) {
				return false;
			}*/
			m1.right = null; 
			m2.left = null; 
			m1.rightValue = 1;
			m2.leftValue = -1;
			
			t1=m1;
			t2=m2;
		}
		else if (m1.letter.getX() > m2.letter.getX() && m1.left != null){
			/*if(!m1.left.equals(m2)) {
				return false;
			}*/
			m1.left = null; 
			m2.right = null; 
			m1.leftValue = -1;
			m2. rightValue= 1;
			
			t1 = m2;
			t2 = m1;
		}
		
		
		
		while(t1 != null){
			MoveXModifier mMfs1 = new MoveXModifier(.5f, t1.letter.getX(),t1.letter.getX() - 20);
			t1.letter.registerEntityModifier(mMfs1);
			t1= t1.left;
		}
		while(t2 != null){
			MoveXModifier mMfs2 = new MoveXModifier(.5f, t2.letter.getX(),t2.letter.getX() + 20);
			t2.letter.registerEntityModifier(mMfs2);
			t2= t2.right;
		}
		return false;
		
		//MainActivity.SpriteContainer.get(i).sprite1.registerEntityModifier(mMfs1);
	}
}
