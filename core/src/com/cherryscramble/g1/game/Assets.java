/**
 * Allen Crigger
 * Assets Class: Holds all of the game's assets
 */

package com.cherryscramble.g1.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;
import com.cherryscramble.g1.util.Constants;

public class Assets implements Disposable, AssetErrorListener {
	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	private AssetManager assetManager;
	public AssetFonts fonts;
	public AssetSounds sounds;
	public AssetMusic music;
	
	// -= Player =-
	public AssetPlayer player;
	
	// -= Intractable Assets =-
	public AssetGround ground;					// Ground Asset
	public AssetTrunkWallLeft trunkWallLeft;	// TrunkWallLeft Asset
	public AssetTrunkWallRight trunkWallRight;	// TrunkWallRight Asset
	public AssetWoodenPlatform woodplatform;    // Wooden Platform Asset
	public AssetStump stump;					// Stump Asset
	
	// -= Decoration Assets =-
	public AssetLevelDecoration decoration;		// Level Decoration Assets (And GUI background)
	
	// Audio Classes placed at the top, because they need to be here.
	/**
	 * Holds the music for the game
	 * 
	 */
	public class AssetMusic {
		public final Music titleSong;
		public final Music level;

		public AssetMusic(AssetManager am) {
			titleSong = am.get("music/TitleScreen.mp3", Music.class);
			level = am.get("music/Level.mp3", Music.class);
		}
	}
	
	/**
	 * Holds the information for all the sounds
	 */
	public class AssetSounds {
		public final Sound jump;
		public final Sound pause;

		/**
		 * Constructor for AssetSounds
		 * 
		 * @param am
		 */
		public AssetSounds(AssetManager am) {
			jump = am.get("sounds/jump.wav", Sound.class);
			pause = am.get("sounds/pause.wav", Sound.class);
		}
	}
	
	
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
		
		// AUDIO LOADING
		// load sounds
		assetManager.load("sounds/jump.wav", Sound.class);
		assetManager.load("sounds/pause.wav", Sound.class);
		// load music
		assetManager.load("music/TitleScreen.mp3", Music.class);
		assetManager.load("music/Level.mp3", Music.class);
		
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
		
		// Player
		player = new AssetPlayer(atlas);
		
		// Texture assets
		ground = new AssetGround(atlas);
		trunkWallLeft = new AssetTrunkWallLeft(atlas);
		trunkWallRight = new AssetTrunkWallRight(atlas);
		woodplatform = new AssetWoodenPlatform(atlas);
		stump = new AssetStump(atlas);
		decoration = new AssetLevelDecoration(atlas);
		
		// Font Assets
		fonts = new AssetFonts();
		
		// Audio Assets
		sounds = new AssetSounds(assetManager);
		music = new AssetMusic(assetManager);
	}

	/**
	 * Error Handler when something goes wrong.
	 */
	public void error(String filename, Class type, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + filename + "'", (Exception) throwable);
	}
	
	/**
	 * Level Decoration class. 
	 *
	 */
	public class AssetLevelDecoration {
		public final AtlasRegion shrub;
		public final AtlasRegion treetop;
		public final AtlasRegion guibackground;
		
		public final AtlasRegion guiFinish;
		
		public AssetLevelDecoration(TextureAtlas atlas) {
			shrub = atlas.findRegion("Shrub");
			treetop = atlas.findRegion("TreeTops");
			guibackground = atlas.findRegion("GuiB");
			guiFinish = atlas.findRegion("Finish");
		}
	}

	/**
	 * Disposes of the asset when the game is done with it.
	 */
	public void dispose() {
		assetManager.dispose();	
		
		fonts.defaultBig.dispose();
		fonts.defaultNormal.dispose();
		fonts.defaultSmall.dispose();
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
	 * Pulls the player texture from the atlas
	 */
	public class AssetPlayer
	{
		public final AtlasRegion playerTexture;
		
		public AssetPlayer(TextureAtlas atlas)
		{
			playerTexture = atlas.findRegion("Player");
		}
	}
	
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
	
	/**
	 * Pulls the Stump texture from the atlas
	 */
	public class AssetStump
	{
		public final AtlasRegion leftEdge;
		public final AtlasRegion rightEdge;
		public final AtlasRegion middle;
		
		public AssetStump(TextureAtlas atlas) {
			leftEdge = atlas.findRegion("StumpLeft");
			rightEdge = atlas.findRegion("StumpRight");
			middle = atlas.findRegion("StumpMiddle");
		}
	}
	
	/**
	 * Assets to hold CherryScrambles fonts.
	 *
	 */
	public class AssetFonts {
		// different size fonts to use
		public final BitmapFont defaultSmall;
		public final BitmapFont defaultNormal;
		public final BitmapFont defaultBig;

		/**
		 * Constructor for fonts, creates the font in three different sizes
		 */
		public AssetFonts() {
			// create three fonts using Libgdx's 15px bitmap font
			defaultSmall = new BitmapFont(Gdx.files.internal("images/Text.fnt"), true);
			defaultNormal = new BitmapFont(Gdx.files.internal("images/Text.fnt"), true);
			defaultBig = new BitmapFont(Gdx.files.internal("images/Text.fnt"), true);

			// set font sizes
			defaultSmall.getData().setScale(0.75f);
			defaultNormal.getData().setScale(1.0f);
			defaultBig.getData().setScale(2.0f);

			// enable linear texture filtering for smooth fonts
			defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}

}
