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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.cherryscramble.g1.util.CameraHelper;
import com.cherryscramble.g1.util.Constants;

public class WorldController extends InputAdapter {
	private static final String TAG = WorldController.class.getName();
	
	public Level level;
	
	//GUI Vars
	public int score;
	public int time;
	
	//Box2D Physics
	public World b2world;
	
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
		b2world.setContactListener(level.player); //Box 2d contact listener
	}
	
	/**
	 * Initializes the level
	 */
	private void initLevel() {
		score = 0;								// Score Starts at zero
		time = 99;								// Level has a 99 second time limit
		level = new Level(Constants.LEVEL_01); 	// Build the level 1 map
		
		initPhysics(); //Box2D Physics
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
		
		level.update(deltaTime); 		// Update level objects
		b2world.step(deltaTime, 8, 3);	// Box2d Physics Update
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
			level.player.body.setLinearVelocity(-1, level.player.body.getLinearVelocity().y);
			//level.player.velocity.x = -level.player.terminalVelocity.x;
		} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			level.player.body.setLinearVelocity(1, level.player.body.getLinearVelocity().y);
			//level.player.velocity.x = level.player.terminalVelocity.x;
		}
	}
	
	/**
	 * When the key has been released
	 */
	public boolean keyUp(int keycode) {
		// Stop Movement
		if (keycode == Keys.LEFT) {
			level.player.body.setLinearVelocity(0, level.player.body.getLinearVelocity().y);
		}
		else if (keycode == Keys.RIGHT) {
			level.player.body.setLinearVelocity(0, level.player.body.getLinearVelocity().y);
		}
		return false;
	}
	
	/**
	 * =========================================================================
	 *                                BOX2D PHYSICS
	 * =========================================================================
	 */
	private void initPhysics () {
		if (b2world != null) b2world.dispose();
		b2world = new World(new Vector2(0, -1f), true); //World Gravity
		
		System.out.println("Box2D Debug!");
		
		Vector2 origin = new Vector2();
		
		// Player Physics
		BodyDef player = new BodyDef();
		player.type = BodyType.DynamicBody;
		player.position.set(level.player.position);
		Body body = b2world.createBody(player);
		level.player.body = body;
		PolygonShape polygonShape = new PolygonShape();
		origin.x = level.player.bounds.width;
		origin.y = level.player.bounds.height;
		polygonShape.setAsBox(level.player.bounds.width / 1.0f, level.player.bounds.height / 1.0f, origin, 0);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		body.createFixture(fixtureDef);
		polygonShape.dispose();
		
		System.out.println("Player added to box 2d!");
		
		//cherry
		//fixtureDef.isSensor = true;
	}
}
