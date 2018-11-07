package com.cherryscramble.g1;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.cherryscramble.g1.game.Assets;
import com.cherryscramble.g1.game.WorldController;
import com.cherryscramble.g1.game.WorldRenderer;

/**
 * Main class that runs the game
 * @author Allen Crigger
 *
 */
public class CherryScrambleMain implements ApplicationListener {
private static final String TAG = CherryScrambleMain.class.getName();
	
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	private boolean paused;
	
	/**
	 * Creates the game
	 */
	public void create() {
		// Set Libgdx log level to DEBUG
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		// Load Assets
		Assets.instance.init(new AssetManager());
		
		// Initialize controller and renderer
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);
		// Game world is active on start
		paused = false;
	}
	
	/**
	 * Renders the game
	 */
	public void render() {
		// Do not update game world when paused
		if(!paused) {
			// Update game world by the time that has passed
			// since last rendered frame.
			worldController.update(Gdx.graphics.getDeltaTime());			
		}
		// Sets the clear green screen color to: Cornflower Blue
		Gdx.gl.glClearColor(0x64/255.0f, 0x95/255.0f, 0xed/255.0f, 0xff/255.0f);
		// Clears the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// Render game world to screen
		worldRenderer.render();
	}
	
	/**
	 * Resizes the window view if the user changes the window screen resolution
	 */
	public void resize(int width, int height) {
		worldRenderer.resize(width, width);
	}
	
	/**
	 * If the user pauses the game, set to true.
	 */
	public void pause() {
		paused = true;
	}
	
	/**
	 * If the player unpauses the game, set to false.
	 */
	public void resume() {
		paused = false;
	}
	
	/**
	 * Disposes of the game when done.
	 */
	public void dispose() {
		worldRenderer.dispose();
		Assets.instance.dispose();
	}
}
