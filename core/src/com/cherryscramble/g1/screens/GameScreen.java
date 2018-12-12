package com.cherryscramble.g1.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.cherryscramble.g1.game.Assets;
import com.cherryscramble.g1.game.WorldController;
import com.cherryscramble.g1.game.WorldRenderer;
import com.cherryscramble.g1.util.AudioManager;
import com.cherryscramble.g1.util.GamePreferences;

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
		GamePreferences.instance.load();
		worldController = new WorldController(game);
		worldRenderer = new WorldRenderer(worldController);
		AudioManager.instance.play(Assets.instance.music.level);	// Play Level Music
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
		AudioManager.instance.pause();
		AudioManager.instance.play(Assets.instance.sounds.pause);	// Pause chime
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
	
	/**
	 * If the user resumes then continue to update the game
	 */
	@Override
	public void resume() {
		super.resume();					// call resume
		paused = false;					// pause boolean to false
		AudioManager.instance.play(Assets.instance.sounds.pause);	// Pause chime
		AudioManager.instance.resume();	// Resumes the background music
	}
}
