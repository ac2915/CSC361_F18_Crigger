/**
 * Level class properly creates the game's level based off of the image template
 */

package com.cherryscramble.g1.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class Level {
	public static final String TAG = Level.class.getName();
	
	/**
	 * Color coordination. Sets colors to specific kind of object
	 */
	public enum BLOCK_TYPE {
		//EMPTY(0, 0, 0), // Empty spaces are Black.
	}
	
	/**
	 * Initializes the level. Places objects where they should be.
	 */
	private void init (String filename) {
		
	}
	
	/**
	 * Renders the objects into the level.
	 */
	public void render (SpriteBatch batch) {
		
	}
}
