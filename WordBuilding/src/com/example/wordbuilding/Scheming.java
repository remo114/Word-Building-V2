package com.example.wordbuilding;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;

import android.util.Log;

public class Scheming {

	public static Marker leftMarker, rightMarker;
	public static String TAG = "Scheming class";

	public static void collutionDetection(Marker marker) {
		Marker m;
		if (marker.left == null && marker.right == null) {
			Log.d(TAG, "if Left and Right both are null ");
			m = getColliedObject(marker);
			if (m != null) {
				Log.d(TAG, "If collied object is null ");
				int lr = getLeftRight(marker, m);
				Log.d(TAG, "Get left or right: " + lr);
				if (lr == 1) {
					m.isSingle = false;
					marker.isSingle = false;
					marker.left = m;
					m.right = marker;
				} else if (lr == 2) {
					m.isSingle = false;
					marker.isSingle = false;
					marker.right = m;
					m.left = marker;
				}

			}
		} else {
			Log.d(TAG, "if Left and Right both are not null ");
			if (marker.right == null) {
				Log.d(TAG, "if Right is empty ");
				// leftMarker = getLeftLastObject(marker);
				m = getColliedObject(marker);
				if (m != null) {
					m.isSingle = false;
					m.left = marker;
					marker.right = m;
				}
				leftMarker = getLeftLastObject(marker);
				m = getColliedObject(leftMarker);
				if (m != null) {
					m.isSingle = false;
					m.right = leftMarker;
					leftMarker.left = m;
				}

			}
			if (marker.left == null) {
				Log.d(TAG, "if Left is empty ");
				// rightMarker = getRightLastObject(marker);
				m = getColliedObject(marker);
				if (m != null) {
					m.isSingle = false;
					m.right = marker;
					marker.left = m;
				}

				rightMarker = getRightLastObject(marker);
				m = getColliedObject(rightMarker);
				if (m != null) {
					m.isSingle = false;
					m.left = rightMarker;
					rightMarker.right = m;
				}
			}
			if (marker.left != null && marker.right != null) {
				Log.d(TAG, "if Left Right is not empty ");
				leftMarker = getLeftLastObject(marker);
				m = getColliedObject(leftMarker);
				if (m != null) {
					m.isSingle = false;
					m.right = leftMarker;
					leftMarker.left = m;
				}

				rightMarker = getRightLastObject(marker);
				m = getColliedObject(rightMarker);
				if (m != null) {
					m.isSingle = false;
					m.left = rightMarker;
					rightMarker.right = m;
				}

			}
		}

	}

	public static Marker getColliedObject(Marker marker) {

		Sprite spr1 = new Sprite(marker.letter.getX(), marker.letter.getY(),
				BaseActivity.textureReason.get(0), BaseActivity.vobm);
		Sprite spr2 = new Sprite(marker.letter.getX(), marker.letter.getY(),
				BaseActivity.textureReason.get(0), BaseActivity.vobm);

		spr1.setVisible(false);
		spr1.setHeight(marker.letter.getHeight());
		spr1.setWidth(marker.letter.getWidth() + 20);

		spr2.setVisible(false);
		spr2.setHeight(marker.letter.getHeight());
		spr2.setWidth(marker.letter.getWidth() + 20);

		magnaticJoin(marker);

		for (int i = 0; i < BaseActivity.markers.size(); i++) {
			if (BaseActivity.markers.get(i).isSingle && !BaseActivity.markers.get(i).equals(marker)) {

				spr2.setPosition(BaseActivity.markers.get(i).letter.getX(),	BaseActivity.markers.get(i).letter.getY());

				if (spr2.collidesWith(spr1)) {
					// BaseActivity.markers.get(i).letter.setPosition(marker.letter.getX()
					// + marker.letter.getWidth(), marker.letter.getY());
					return BaseActivity.markers.get(i); 
				}
				
			}
			/*else if(!BaseActivity.markers.get(i).isSingle){
				if(marker.right != null){
					if(!marker.right.equals(BaseActivity.markers.get(i))){
						if (spr2.collidesWith(spr1)) {
							// BaseActivity.markers.get(i).letter.setPosition(marker.letter.getX()
							// + marker.letter.getWidth(), marker.letter.getY());
							return BaseActivity.markers.get(i); 
						}
					}
				}
				else if(marker.left != null){
					if(!marker.left.equals(BaseActivity.markers.get(i))){
						if (spr2.collidesWith(spr1)) {
							// BaseActivity.markers.get(i).letter.setPosition(marker.letter.getX()
							// + marker.letter.getWidth(), marker.letter.getY());
							return BaseActivity.markers.get(i);
						}
					}
				}
			}*/
				
		}
		return null; 
	}

	public static void moveBlock(float px, float py, float ax, float ay,
			Marker marker) {
		Marker leftLast = getLeftLastObject(marker);

		while (leftLast != null) {
			if (!leftLast.equals(marker)) {
				float nx = leftLast.letter.getX() + (ax - px), ny = leftLast.letter
						.getY() + (ay - py);
				leftLast.letter.setPosition(nx, ny);
			}
			leftLast = leftLast.right;
		}

	}

	public static Marker getLeftLastObject(Marker marker) {
		Marker tempMarker = marker;
		if (tempMarker.left == null) {
			return tempMarker;
		}
		while (tempMarker != null) {
			marker = tempMarker;
			tempMarker = marker.left;
		}
		return marker;
	}

	public static Marker getRightLastObject(Marker marker) {
		Marker tempMarker = marker;
		if (tempMarker.right == null) {
			return tempMarker;
		}
		while (tempMarker != null) {
			marker = tempMarker;
			tempMarker = marker.right;
		}
		return marker;
	}

	static int getLeftRight(Marker thisMarker, Marker colliedMarker) {

		if (thisMarker.letter.getX() > colliedMarker.letter.getX()) {
			return 1;
		} else {
			return 2;
		}
	}

	public static boolean allowJoin(Sprite sp1, Sprite sp2) {
		if (sp1.getX() - sp2.getX() > 105 && sp1.getX() - sp2.getX() < 180
				&& sp1.getY() - sp2.getY() < 25
				&& sp1.getY() - sp2.getY() > -25) {
			return true;
		} else if (sp2.getX() - sp1.getX() > 105
				&& sp2.getX() - sp1.getX() < 180
				&& sp2.getY() - sp1.getY() < 25
				&& sp2.getY() - sp1.getY() > -25) {
			return true;
		}
		return false;

	}

	public static void magnaticJoin(Marker marker) {
		marker = getLeftLastObject(marker);
		while (marker != null) {
			if (marker.left != null) {
				marker.letter.setPosition(marker.left.letter.getX()
						+ marker.left.letter.getWidth(),
						marker.left.letter.getY());
			}
			marker = marker.right;
		}
	}

	public static int checkBoundary(Marker marker) {
		Marker leftMarker1 = getLeftLastObject(marker);
		Marker rightMarker1 = getRightLastObject(marker);

		if (leftMarker1 == null) {
			leftMarker1 = marker;
		}
		if (rightMarker1 == null) {
			rightMarker1 = marker;
		}

		if (leftMarker1.letter.getX() < -30) {
			while (leftMarker1 != null) {
				MoveModifier mf = new MoveModifier(0.4f,
						leftMarker1.letter.getX(),
						leftMarker1.letter.getX() + 50,
						leftMarker1.letter.getY(), leftMarker1.letter.getY());
				leftMarker1.letter.registerEntityModifier(mf);
				leftMarker1 = leftMarker1.right;
			}
			return 1;
		}

		else if (leftMarker1.letter.getY() < -20) {
			while (leftMarker1 != null) {
				MoveModifier mf = new MoveModifier(0.4f,
						leftMarker1.letter.getX(), leftMarker1.letter.getX(),
						leftMarker1.letter.getY(),
						leftMarker1.letter.getY() + 50);
				leftMarker1.letter.registerEntityModifier(mf);
				leftMarker1 = leftMarker1.right;
			}
			return 2;
		}

		else if (rightMarker1.letter.getX() + 100 > BaseActivity.CAMERA_WIDTH + 30) {
			while (rightMarker1 != null) {
				MoveModifier mf = new MoveModifier(0.4f,
						rightMarker1.letter.getX(),
						rightMarker1.letter.getX() - 50,
						rightMarker1.letter.getY(), rightMarker1.letter.getY());
				rightMarker1.letter.registerEntityModifier(mf);
				rightMarker1 = rightMarker1.left;
			}
			return 3;
		} else if (leftMarker1.letter.getY() + 100 > BaseActivity.CAMERA_HEIGHT + 20) {
			while (leftMarker1 != null) {
				MoveModifier mf = new MoveModifier(0.4f,
						leftMarker1.letter.getX(), leftMarker1.letter.getX(),
						leftMarker1.letter.getY(),
						leftMarker1.letter.getY() - 50);
				leftMarker1.letter.registerEntityModifier(mf);
				leftMarker1 = leftMarker1.right;
			}
			return 4;
		}

		return 0;
	}
}
