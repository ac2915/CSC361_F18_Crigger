/**
 * Allen Crigger
 * 
 * World Controller for Cherry Scramble
 */

package com.cherryscramble.g1.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.cherryscramble.g1.util.CameraHelper;
import com.cherryscramble.g1.util.Constants;

public class WorldController extends InputAdapter {
	private static final String TAG = WorldController.class.getName();
	
	public Level level;
	public int score;
	public int time;
	
	//GUI Vars
	
	
	//CameraHelper
	public CameraHelper cameraHelper;
	
	/**
	 * Initialize upon being called
	 */
	public WorldController() {
		init();
	}
	
	/**
	 * Initializes the controller
	 */
	private void init() {
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();

		initLevel(); // Initializes the level
		//b2World.setContactListener(level.player); //Box 2d contact listener
	}
	
	/**
	 * Initializes the level
	 */
	private void initLevel() {
		score = 0;
		time = 99;
		level = new Level(Constants.LEVEL_01);
	}
	
	/**
	 * Update the WorldController as time moves
	 * @param deltaTime
	 */
	public void update(float deltaTime) {
		handleDebugInput(deltaTime);
		cameraHelper.update(deltaTime);
		
		// TimeLeft game over.
		if (isGameOver()) {

		} else {
			handleInput(deltaTime);
		}
		
		level.update(deltaTime); 	// Update level objects
	}
	
	/**
	 * Game Over Event
	 * @return
	 */
	public boolean isGameOver() {
		return 1 < 0; // True value to keep the game going for now.
	}
	
	/***
	 *       ========= CAMERA DEBUG CONTROLS BELOW =========
	 *	      Comment out when they are not needed anymore
	 *       ===============================================
	 */
	private void handleDebugInput(float deltaTime) {
		if (Gdx.app.getType() != ApplicationType.Desktop)
			return;

		// Camera Controls (move)
		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			camMoveSpeed *= camMoveSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.A))
			moveCamera(-camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.D))
			moveCamera(camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.W))
			moveCamera(0, camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.S))
			moveCamera(0, -camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
			cameraHelper.setPosition(0, 0);

		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.COMMA))
			cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.PERIOD))
			cameraHelper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.SLASH))
			cameraHelper.setZoom(1);
	}

	private void moveCamera(float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}
	
	/**
	 *  ============== Player Controls Below ============== 
	 */
	private void handleInput(float deltatime)
	{
		// Player Left/Right Movement
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			level.player.velocity.x = -level.player.terminalVelocity.x;
		} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			level.player.velocity.x = level.player.terminalVelocity.x;
		}
	}
}
