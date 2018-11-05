/**
 * Allen Crigger
 * Assets Class: Holds all of the game's assets
 */

package com.cherryscramble.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {
	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	private AssetManager assetManager;
	
	/**
	 * Initializes the assets
	 * @param assetManager: My assetManager
	 */
	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
	}

	/**
	 * Error Handler when something goes wrong.
	 */
	public void error(String filename, Class type, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + filename + "'", (Exception) throwable);
	}
	
	/**
	 * Level Decoration class (hidden inside the Assets class)
	 *
	 */
	public class AssetLevelDecoration {
		
	}

	/**
	 * Disposes of the asset when the game is done with it.
	 */
	public void dispose() {
		assetManager.dispose();	
	}


	/**
	 * New libGDX error handler
	 */
	public void error(AssetDescriptor asset, Throwable throwable) {
		// TODO Auto-generated method stub
		
	}

}
