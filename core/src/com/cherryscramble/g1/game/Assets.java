/**
 * Allen Crigger
 * Assets Class: Holds all of the game's assets
 */

package com.cherryscramble.g1.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;
import com.cherryscramble.g1.util.Constants;

public class Assets implements Disposable, AssetErrorListener {
	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	private AssetManager assetManager;
	
	// -= Assets =-
	public AssetGround ground;					// Ground Asset
	public AssetTrunkWallLeft trunkWallLeft;	// TrunkWallLeft Asset
	public AssetTrunkWallRight trunkWallRight;	// TrunkWallRight Asset
	public AssetWoodenPlatform woodplatform;    // Wooden Platform
	
	/**
	 * Initializes the assets
	 * @param assetManager: My assetManager
	 */
	public void init(AssetManager assetManager) {
		this.assetManager = assetManager;
		
		// set asset manager error handler
		assetManager.setErrorListener(this);
				
		// load texture atlas
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		
		// start loading assets and wait until finished
		assetManager.finishLoading();
		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		for (String a : assetManager.getAssetNames())
			Gdx.app.debug(TAG, "asset: " + a);
		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
				
		/*
		// enable texture filtering for pixel smoothing (DOES NOT LOOK GOOD FOR ME, but will keep in here for now)
		for (Texture t : atlas.getTextures())
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		*/
		
		// Texture assets
		ground = new AssetGround(atlas);
		trunkWallLeft = new AssetTrunkWallLeft(atlas);
		trunkWallRight = new AssetTrunkWallRight(atlas);
		woodplatform = new AssetWoodenPlatform(atlas);
		
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
	
	/*
	 * 
	 *  ============= Game Asset Clases Below ==============
	 * 
	 */
	
	/**
	 * Pulls the ground texture from the atlas
	 */
	public class AssetGround
	{
		public final AtlasRegion ground;
		
		public AssetGround(TextureAtlas atlas) {
			ground = atlas.findRegion("Ground");
		}
	}
	
	/**
	 * Pulls the TrunkWallLeft texture from the atlas
	 */
	public class AssetTrunkWallLeft
	{
		public final AtlasRegion trunkWallLeft;
		
		public AssetTrunkWallLeft(TextureAtlas atlas) {
			trunkWallLeft = atlas.findRegion("TrunkWallLeft");
		}
	}
	
	/**
	 * Pulls the TrunkWallRight texture from the atlas
	 */
	public class AssetTrunkWallRight
	{
		public final AtlasRegion trunkWallRight;
		
		public AssetTrunkWallRight(TextureAtlas atlas) {
			trunkWallRight = atlas.findRegion("TrunkWallRight");
		}
	}
	
	/**
	 * Pulls the WoodenPlatform texture from the atlas
	 */
	public class AssetWoodenPlatform
	{
		public final AtlasRegion leftEdge;
		public final AtlasRegion rightEdge;
		public final AtlasRegion middle;
		
		public AssetWoodenPlatform(TextureAtlas atlas) {
			leftEdge = atlas.findRegion("PlatformLeft");
			rightEdge = atlas.findRegion("PlatformRight");
			middle = atlas.findRegion("PlatformMiddle");
		}
	}

}
