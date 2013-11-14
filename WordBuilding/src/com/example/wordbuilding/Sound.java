package com.example.wordbuilding;

import java.util.HashMap;
import java.util.Map;

import android.media.MediaPlayer;

public class Sound {
	
	static MediaPlayer mediaPlayer = new MediaPlayer();
	public Sound(){
		
	}
	
	public static Map<String, Integer> singleSound = new HashMap<String,Integer>();
	
	public static Integer getSingleSound(String key) {
		return singleSound.get(key);
	}

	public static void setSingleSound(String letterName, Integer value) {
		/*Sound.singleSound = singleSound;*/
		singleSound.put(letterName, value);
	}
	
	public static void playSingleSound(int sound) {
		mediaPlayer.reset();
		mediaPlayer = MediaPlayer.create(BaseActivity.context, sound);
		try {
			mediaPlayer.start();
			mediaPlayer.setLooping(false);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
