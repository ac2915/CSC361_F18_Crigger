package com.cherryscramble.g1.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.cherryscramble.g1.game.WorldController;
import com.cherryscramble.g1.game.WorldRenderer;

/**
 * The Game Screen for Cherry Scramble. Screen where the game will play!
 * @author Allen Crigger
 *
 */
public class GameScreen extends AbstractGameScreen {
	
	private static final String TAG = GameScreen.class.getName();	// This Class
	private WorldRenderer worldRenderer;							// WorldRenderer Class.
	private WorldController worldController; 						// WorldController Class.
	private boolean paused; 										// Value tells if the game is paused of not.
	
	public GameScreen(Game game) {
		super(game);
	}

	/**
	 * Displays the game screen.
	 */
	@Override
	public void show() {
		//GamePreferences.instance.load();
		worldController = new WorldController(game);
		worldRenderer = new WorldRenderer(worldController);
		Gdx.input.setCatchBackKey(true);
	}

	/**
	 * Renders the game screen.
	 */
	@Override
	public void render(float deltaTime) {
		
		// Do not update game world when paused.
		if (!paused) {
			worldController.update(deltaTime);	// Update the game as time passes since last frame.
		}
		
		// Sets background screen to Light Blue
		Gdx.gl.glClearColor(0x64 / 255.0f, 0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);	// clear the screen.
		worldRenderer.render();	// Renders the world onto the screen.
	}

	/**
	 * Rezise properties (Not really used)
	 */
	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height); // Even resizing if someone ever enables it.
	}

	/**
	 * Method sets paused to true so that the game will stop updating
	 */
	@Override
	public void pause() {
		paused = true;
	}

	/**
	 * Hides the game screen.
	 */
	@Override
	public void hide() {
		worldController.dispose();			// Disposes of the world controller.
		worldRenderer.dispose();			// Disposes of the world renderer.
		Gdx.input.setCatchBackKey(false);	// disables input.
	}
}
