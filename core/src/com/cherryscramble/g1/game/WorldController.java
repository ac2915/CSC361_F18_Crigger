/**
 * Allen Crigger
 * 
 * World Controller for Cherry Scramble
 */

package com.cherryscramble.g1.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.cherryscramble.g1.util.CameraHelper;
import com.cherryscramble.g1.util.Constants;

public class WorldController extends InputAdapter {
	private static final String TAG = WorldController.class.getName();
	
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
	}
	
	/**
	 * Initializes the level
	 */
	private void initLevel() {
		//score = 0;
		//level = new Level(Constants.LEVEL_01);
	}
	
	/**
	 * Update the WorldController as time moves
	 * @param deltaTime
	 */
	public void update(float deltaTime) {
		//handleDebugInput(deltaTime);
		//cameraHelper.update(deltaTime);
	}
}
