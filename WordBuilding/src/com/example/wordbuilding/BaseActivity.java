package com.example.wordbuilding;

import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.modifier.IModifier;

import android.content.Context;
import android.view.Display;

public class BaseActivity extends SimpleBaseGameActivity {

	public static int CAMERA_WIDTH;
	public static int CAMERA_HEIGHT;
	public static Scene mCurrentScene;
	public Camera mCamera;
	
	
	public Sprite BgSprite; 
	public Seizure seizure;
	
	public static VertexBufferObjectManager vobm;
	public static Context context;
	public static EngineOptions engOps;
	public static Engine eng;
	
	public static BuildableBitmapTextureAtlas BgBuildableBitmapTextureAtlas;
	public static BuildableBitmapTextureAtlas LettersBuildableBitmapTextureAtlas;
	public BitmapTextureAtlas ParrotBitmapTextureAtlus;
	public static ITextureRegion BgTextureReason;
	public static ITextureRegion SeizureTextureReason;
	
	public static ITextureRegion Letter1TextureReason;
	public static ArrayList<ITextureRegion> textureReason;
	public static ArrayList<Marker> markers;
	
	public static ArrayList<String> letterName;
	
	public static boolean isSeizure;
	
	
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		Display display = getWindowManager().getDefaultDisplay();
		CAMERA_HEIGHT = display.getHeight();
		CAMERA_WIDTH = display.getWidth();
		context = getApplicationContext();
		//instance = this;
		
		textureReason = new ArrayList<ITextureRegion>();
		markers = new ArrayList<Marker>();
		letterName = new ArrayList<String>();
		
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
	    engOps = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
		engOps.getTouchOptions().setNeedsMultiTouch(true);
		eng = new Engine(engOps);
	    return engOps; 
	}

	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		BgBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 2900, 2532,TextureOptions.DEFAULT);
		LettersBuildableBitmapTextureAtlas = new BuildableBitmapTextureAtlas(this.getTextureManager(), 1400, 1400,TextureOptions.DEFAULT);
		
		BgTextureReason = BitmapTextureAtlasTextureRegionFactory.createFromAsset(BgBuildableBitmapTextureAtlas, this, "jungle16.png");
		SeizureTextureReason = BitmapTextureAtlasTextureRegionFactory.createFromAsset(LettersBuildableBitmapTextureAtlas, this, "kacchi.png");
		
		textureReason.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(LettersBuildableBitmapTextureAtlas, this, "Letters/akar.png"));
		letterName.add("akar");
		textureReason.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(LettersBuildableBitmapTextureAtlas, this, "Letters/mo.png"));
		letterName.add("moo");
		textureReason.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(LettersBuildableBitmapTextureAtlas, this, "Letters/letter_lo.png"));
		letterName.add("loo");
		textureReason.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(LettersBuildableBitmapTextureAtlas, this, "Letters/letter_to.png"));
		letterName.add("too");
		textureReason.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(LettersBuildableBitmapTextureAtlas, this, "Letters/shoroa.png"));
		letterName.add("shoroa");
		/*textureReason.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(LettersBuildableBitmapTextureAtlas, this, "Letters/shoroa.png"));
		letterName.add("shoroa");*/
		//textureReason.add(BitmapTextureAtlasTextureRegionFactory.createFromAsset(LettersBuildableBitmapTextureAtlas, this, "Letters/letter_mo.png"));
		
		try {
			BgBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			LettersBuildableBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
			BgBuildableBitmapTextureAtlas.load();
			LettersBuildableBitmapTextureAtlas.load();
			//ParrotBitmapTextureAtlus.load();

		} catch (TextureAtlasBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected Scene onCreateScene() {
		// TODO Auto-generated method stub
		
		mCurrentScene = new Scene();
		mCurrentScene.setTouchAreaBindingOnActionMoveEnabled(true);
	   // mCurrentScene.setBackground(new Background(0.09804f, 0.7274f, 0.8f));
	    
		BgSprite = new Sprite(0, 0, BgTextureReason, getVertexBufferObjectManager());
				
	    BgSprite.setHeight(CAMERA_HEIGHT);
		BgSprite.setWidth(CAMERA_WIDTH);
		mCurrentScene.attachChild(BgSprite);
		mCurrentScene.setUserData("MainScene");		
		
		setSingleSound();
		for(int i = 0; i< textureReason.size(); i++){
			markers.add(new Marker(1+((121)*(i+1) ), CAMERA_HEIGHT - 120, 100f, 100f, textureReason.get(i), getVertexBufferObjectManager(),getApplicationContext(),Sound.getSingleSound(letterName.get(i))));
			markers.get(i).markerSelf = markers.get(i);
		}
		markers.get(0).letter.setUserData("aakar");
		markers.get(1).letter.setUserData("moo");
		markers.get(2).letter.setUserData("loo");
		markers.get(3).letter.setUserData("too");
		markers.get(4).letter.setUserData("shoroa");
		
		
		seizure = new Seizure(10, 10,110,110, SeizureTextureReason,getVertexBufferObjectManager(),getApplicationContext(),0);
		mCurrentScene.sortChildren();
		return mCurrentScene;
	}
	
	void setLetterName(){
		
	}
	
	void setSingleSound(){
		Sound.setSingleSound("akar", R.raw.aakar);
		Sound.setSingleSound("moo", R.raw.mo);
		Sound.setSingleSound("loo", R.raw.mo);
		Sound.setSingleSound("too", R.raw.mo);		
		Sound.setSingleSound("shoroa", R.raw.shoroa);	
		
	}
	
	

}
