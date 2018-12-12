/**
 * Allen Crigger
 * 
 * World Controller for Cherry Scramble
 */

package com.cherryscramble.g1.game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.cherryscramble.g1.objects.Cherry;
import com.cherryscramble.g1.objects.GoldenCherry;
import com.cherryscramble.g1.objects.Ground;
import com.cherryscramble.g1.objects.Stump;
import com.cherryscramble.g1.objects.TrunkWallLeft;
import com.cherryscramble.g1.objects.TrunkWallRight;
import com.cherryscramble.g1.objects.WoodPlatform;
import com.cherryscramble.g1.objects.Player.JUMP_STATE;
import com.cherryscramble.g1.screens.GameScreen;
import com.cherryscramble.g1.screens.HighScoreScreen;
import com.cherryscramble.g1.util.AudioManager;
import com.cherryscramble.g1.util.CameraHelper;
import com.cherryscramble.g1.util.Constants;

public class WorldController extends InputAdapter implements Disposable {
	private static final String TAG = WorldController.class.getName();
	
	public Level level;
	
	// Instance of the game
	private Game game;
	
	//GUI Vars
	public int score;
	public float wait;
	public float time;
	public float pUtime;
	int pause = 0;
	int speed = 1;
	private boolean switchScreen;
	
	//Box2D Physics
	public World b2world;
	
	//CameraHelper
	public CameraHelper cameraHelper;
	
	/**
	 * Initialize upon being called
	 */
	public WorldController(Game game) {
		this.game = game;
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
		switchScreen = false;	// switchScreen Position
	}
	
	/**
	 * Initializes the level
	 */
	private void initLevel() {
		score = 0;								// Score Starts at zero
		time = 60;								// Level has a 99 second time limit
		wait = 5;								// 5 second wait time
		pUtime = 5;								// Lazy poweup attempt
		level = new Level(Constants.LEVEL_01); 	// Build the level 1 map
		
		initPhysics(); //Box2D Physics
	}
	
	/**
	 * Update the WorldController as time moves
	 * @param deltaTime
	 */
	public void update(float deltaTime) {
		//handleDebugInput(deltaTime);
		cameraHelper.update(deltaTime);
		
		// TimeLeft game over.
		if (isGameOver()) {
			if(wait < 0)
			{
				AudioManager.instance.stopMusic();
				switchScreen = true;
			}
			else
				wait -= deltaTime;
		} else {
			handleInput(deltaTime);
		}
		level.update(deltaTime); 		// Update level objects
		b2world.step(deltaTime, 8, 3);	// Box2d Physics Update
		
		// Timer Count-Down
		if(time > 0)
		{
			time -= deltaTime;
			if(time < 0)
			{
				time = 0;
			}
		}
		if(level.player.poweredUp)
		{
			speed = 2;	// double speed
			pUtime -= deltaTime;
			if(pUtime < 0)
			{
				level.player.poweredUp = false;
				pUtime = 5;	// reset power up time stays at 0
				speed = 1;	// Revert back to original speed
			}
		}
		
		score += level.player.getScore(); // Dirty method to collect cherry scores from box2d collision
		
		// Possible box2d fix
		if (switchScreen == true)
		{
			saveScore();
			b2world = null;
			game.setScreen(new HighScoreScreen(game));
		}
	}
	
	/**
	 * Game Over Event
	 * @return
	 */
	public boolean isGameOver() {
		return time <= 0; // Game over when time is 0
	}
	
	/**
	 * Method returns the score that world controller contains
	 * @return
	 */
	public int getScore() {
		return score;
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
		float camMoveSpeed = 5 * deltatime;
		
		// Player Left/Right Movement
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			level.player.body.setLinearVelocity(-3*speed, level.player.body.getLinearVelocity().y);
			if(cameraHelper.getPosition().x > 12.6 && level.player.body.getPosition().x < 13)
			{
				moveCamera(-camMoveSpeed, 0);
			}
		} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			level.player.body.setLinearVelocity(3*speed, level.player.body.getLinearVelocity().y);
			if(cameraHelper.getPosition().x < 19.4 && level.player.body.getPosition().x > 19)
			{
				moveCamera(+camMoveSpeed, 0);
			}
		} 
		
		// Player Jumping ability
		if (Gdx.input.isKeyJustPressed(Keys.UP) || Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			level.player.setJumping(true);
		} else {
			level.player.setJumping(false);
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
		} else if (keycode == Keys.ESCAPE) {
			if(pause == 0)
			{
				game.pause();
				pause = 1;
			}
			else
			{
				game.resume();
				pause = 0;
			}
		}
		return false;
	}
	
	public void saveScore() {
		File file = new File("save.dat");	// File that holds the games scores
		String stringScore; 				// Score in string format
		
		try
		{
			if(file.exists() == false)
			{
				file.createNewFile();	// Create the file if it doesn't exist yet
			}
			BufferedWriter output = new BufferedWriter(new FileWriter("save.dat", true));  // Open save for write
			stringScore = Integer.toString(score); 	// Convert score to a sting
			output.write(stringScore);				// Writes the score to the file
			output.newLine();						// Adds a new line in the file (for organization).
			output.close();							// Closes the file
		} catch (IOException e) {
			System.out.println("ERROR! COULD NOT WRITE SCORE TO FILE!!");
		}
	}
	
	/**
	 * =========================================================================
	 *                                BOX2D PHYSICS
	 * =========================================================================
	 */
	private void initPhysics () {
		if (b2world != null) b2world.dispose();
		b2world = new World(new Vector2(0, -9.18f), true); //World Gravity
		
		System.out.println("Box2D Debug!");	// Debug Message
		
		Vector2 origin = new Vector2();
		
		// Ground Physics
		for (Ground ground : level.grounds) {
			BodyDef grounds = new BodyDef();
			grounds.type = BodyType.StaticBody;
			grounds.position.set(ground.position);
			Body body2 = b2world.createBody(grounds);
			ground.body = body2;
			PolygonShape polygonShape2 = new PolygonShape();
			origin.x = ground.bounds.width / 2f; // 1.0f;
			origin.y = ground.bounds.height / 2f; // 1.0f;
			polygonShape2.setAsBox(ground.bounds.width / 2f, ground.bounds.height / 2f, origin, 0);
			FixtureDef fixtureDef2 = new FixtureDef();
			fixtureDef2.shape = polygonShape2;
			body2.createFixture(fixtureDef2);
			body2.setUserData(ground);	// it's user data
			polygonShape2.dispose();
		}
		
		// Platform Physics
		for (WoodPlatform platform : level.platforms) {
			BodyDef plat = new BodyDef();
			plat.type = BodyType.StaticBody;
			plat.position.set(platform.position);
			Body body2 = b2world.createBody(plat);
			platform.body = body2;
			PolygonShape polygonShape2 = new PolygonShape();
			origin.x = (platform.bounds.width / 2.0f) - 1.0f; // 1.0f;
			origin.y = platform.bounds.height / 2.0f; // 1.0f;
			polygonShape2.setAsBox(platform.bounds.width / 2.0f, platform.bounds.height / 2.0f, origin, 0);
			FixtureDef fixtureDef2 = new FixtureDef();
			fixtureDef2.shape = polygonShape2;
			body2.createFixture(fixtureDef2);
			body2.setUserData(platform);	// it's user data
			polygonShape2.dispose();
		}
		
		// Stump Physics
		for (Stump stump : level.stumps) {
			BodyDef stum = new BodyDef();
			stum.type = BodyType.StaticBody;
			stum.position.set(stump.position);
			Body body2 = b2world.createBody(stum);
			stump.body = body2;
			PolygonShape polygonShape2 = new PolygonShape();
			origin.x = (stump.bounds.width / 2.0f) - 1.0f; // 1.0f;
			origin.y = stump.bounds.height / 2.0f; // 1.0f;
			polygonShape2.setAsBox(stump.bounds.width / 2.0f, stump.bounds.height / 2.0f, origin, 0);
			FixtureDef fixtureDef2 = new FixtureDef();
			fixtureDef2.shape = polygonShape2;
			body2.createFixture(fixtureDef2);
			body2.setUserData(stump);	// it's user data
			polygonShape2.dispose();
		}
		
		// LeftWall Physics
		for (TrunkWallLeft lw : level.leftTrunkWalls) {
			BodyDef stum = new BodyDef();
			stum.type = BodyType.StaticBody;
			stum.position.set(lw.position);
			Body body2 = b2world.createBody(stum);
			lw.body = body2;
			PolygonShape polygonShape2 = new PolygonShape();
			origin.x = (lw.bounds.width / 2.0f); // 1.0f;
			origin.y = lw.bounds.height / 2.0f; // 1.0f;
			polygonShape2.setAsBox(lw.bounds.width / 2.0f, lw.bounds.height / 2.0f, origin, 0);
			FixtureDef fixtureDef2 = new FixtureDef();
			fixtureDef2.shape = polygonShape2;
			body2.createFixture(fixtureDef2);
			body2.setUserData(lw);	// it's user data
			polygonShape2.dispose();
		}
		
		// RightWall Physics
		for (TrunkWallRight rw : level.rightTrunkWalls) {
			BodyDef stum = new BodyDef();
			stum.type = BodyType.StaticBody;
			stum.position.set(rw.position);
			Body body2 = b2world.createBody(stum);
			rw.body = body2;
			PolygonShape polygonShape2 = new PolygonShape();
			origin.x = (rw.bounds.width / 2.0f); // 1.0f;
			origin.y = rw.bounds.height / 2.0f; // 1.0f;
			polygonShape2.setAsBox(rw.bounds.width / 2.0f, rw.bounds.height / 2.0f, origin, 0);
			FixtureDef fixtureDef2 = new FixtureDef();
			fixtureDef2.shape = polygonShape2;
			body2.createFixture(fixtureDef2);
			body2.setUserData(rw);	// it's user data
			polygonShape2.dispose();
		}
		
		// Player Physics
		BodyDef player = new BodyDef();
		player.type = BodyType.DynamicBody;
		player.position.set(level.player.position);
		Body body = b2world.createBody(player);
		level.player.body = body;
		PolygonShape polygonShape = new PolygonShape();
		origin.x = level.player.bounds.width / 2f;
		origin.y = level.player.bounds.height / 2f;
		polygonShape.setAsBox(level.player.bounds.width / 2.0f, level.player.bounds.height / 2.0f, origin, 0);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = polygonShape;
		body.createFixture(fixtureDef);
		body.setUserData(player);	// it's user data
		polygonShape.dispose();
		
		//cherry
		for (Cherry cherry : level.cherries) {
			BodyDef stum = new BodyDef();
			stum.type = BodyType.StaticBody;
			stum.position.set(cherry.position);
			Body body2 = b2world.createBody(stum);
			cherry.body = body2;
			PolygonShape polygonShape2 = new PolygonShape();
			origin.x = cherry.bounds.width / 2.0f;
			origin.y = cherry.bounds.height / 2.0f;
			polygonShape2.setAsBox(cherry.bounds.width / 2.0f, cherry.bounds.height / 2.0f, origin, 0);
			FixtureDef fixtureDef2 = new FixtureDef();
			fixtureDef2.shape = polygonShape2;
			fixtureDef2.isSensor = true;
			body2.createFixture(fixtureDef2);
			body2.setUserData(cherry);	// it's user data
			polygonShape2.dispose();
		}
		
		//cherry
		for (GoldenCherry gcherry : level.goldcherries) {
			BodyDef stum = new BodyDef();
			stum.type = BodyType.StaticBody;
			stum.position.set(gcherry.position);
			Body body2 = b2world.createBody(stum);
			gcherry.body = body2;
			PolygonShape polygonShape2 = new PolygonShape();
			origin.x = gcherry.bounds.width / 2.0f;
			origin.y = gcherry.bounds.height / 2.0f;
			polygonShape2.setAsBox(gcherry.bounds.width / 2.0f, gcherry.bounds.height / 2.0f, origin, 0);
			FixtureDef fixtureDef2 = new FixtureDef();
			fixtureDef2.shape = polygonShape2;
			fixtureDef2.isSensor = true;
			body2.createFixture(fixtureDef2);
			body2.setUserData(gcherry);	// it's user data
			polygonShape2.dispose();
		}
	}
	
	/**
	 * Method to remove the world controller.
	 */
	@Override
	public void dispose() {
		if (b2world != null)
			b2world.dispose();	// Disposes of box2D.
	}
}
