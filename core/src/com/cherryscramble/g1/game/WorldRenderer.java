/**
 * Class renders the world onto the screen
 * 
 * @author Allen Crigger
 */
package com.cherryscramble.g1.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.cherryscramble.g1.game.WorldController;
import com.cherryscramble.g1.objects.Finish;
import com.cherryscramble.g1.objects.GuiBackground;
import com.cherryscramble.g1.util.Constants;
import com.cherryscramble.g1.util.GamePreferences;

public class WorldRenderer implements Disposable
{
	// -= Camera and Controls =-
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private WorldController worldController;
	private OrthographicCamera cameraGUI;
	
	//gui background
	public GuiBackground gui;
	
	// Foreground
	public Finish finish;
	
	// Box2D Debug (false when not needed)
	private static final boolean DEBUG_DRAW_BOX2D_WORLD = false;
	private Box2DDebugRenderer b2debugRenderer;
	
	/**
	 * Creates a render of the world
	 * @param worldController: My WorldController
	 */
	public WorldRenderer(WorldController worldController) {
		this.worldController = worldController;
		init();
	}
	
	/**
	 * Initialization of the WorldRenderer
	 */
	private void init() {
		// -= Camera and Sprites =-
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		
		// -= Camera for GUI (Points, and FPS) =-
		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(true); // flip the y-axis
		cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);
		cameraGUI.update();
		
		//Box2D Debug
		b2debugRenderer = new Box2DDebugRenderer();
	}
	
	/**
	 * Used to render the world in the game using a SpriteBatch
	 * 
	 * @param batch
	 */
	private void renderWorld(SpriteBatch batch) {
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		worldController.level.render(batch);
		batch.end();
		
		//Box2D Debug
		if (DEBUG_DRAW_BOX2D_WORLD) {
			 b2debugRenderer.render(worldController.b2world,
			 camera.combined);
		}
	}
	
	/**
	 * Renders the GUI layer that displays the player's level, score, and FPS
	 * @param batch
	 */
	private void renderGui(SpriteBatch batch) {
		batch.setProjectionMatrix(cameraGUI.combined);
		batch.begin();
		// First, draw the gui background image
	    renderGuiBackground(batch);
	    // Draws the score
		renderScore(batch);
		// Draws the time remaining
		renderTimeRemaining(batch);
		// Draws the game's current fps
		if (GamePreferences.instance.showFpsCounter)
			renderGuiFpsCounter(batch);
		else
			renderText(batch);
		
		// draw the game start text
		
		// draw game over text
		renderFinish(batch);
		
		batch.end();
	}
	
	/**
	 * Render the world.
	 */
	public void render() {
		renderWorld(batch);
		renderGui(batch);
	}

	/**
	 * Resize
	 * @param width: Camera Width
	 * @param height: Camera Height
	 */
	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
	}
	
	/**
	 * Disposes of the batches.
	 */
	public void dispose() {
		batch.dispose();
	}
	
	/**
	 * 
	 *  ============ GUI Render Methods Below!! ============
	 * 
	 */
	
	/**
	 * Write the current frames to the user, green is >=45, yellow is >=30, red is
	 * <30
	 * 
	 * @param batch
	 */
	private void renderGuiFpsCounter(SpriteBatch batch) {
		float x = 725;
		float y = 10;
		int fps = Gdx.graphics.getFramesPerSecond();
		BitmapFont fpsFont = Assets.instance.fonts.defaultNormal;
		if (fps >= 45) {
			// 45 or more FPS show up in green
			fpsFont.setColor(0, 1, 0, 1);
		} else if (fps >= 30) {
			// 30 or more FPS show up in yellow
			fpsFont.setColor(1, 1, 0, 1);
		} else {
			// less than 30 FPS show up in red
			fpsFont.setColor(1, 0, 0, 1);
		}
		fpsFont.draw(batch, "FPS: " + fps, x, y);
		fpsFont.setColor(1, 1, 1, 1); // white
	}
	
	private void renderText(SpriteBatch batch) {
		Assets.instance.fonts.defaultNormal.draw(batch, "Cherry Scramble", 650, 10);
	}
	
	/**
	 * Method draws the player's current score in the upper left corner of the gui panel
	 */
	private void renderScore(SpriteBatch batch) {
		Assets.instance.fonts.defaultNormal.draw(batch, "Score: " + worldController.score, 15, 10);
	}
	
	/**
	 * Method draws the player's remaining time in the upper middle part of the gui panel
	 */
	private void renderTimeRemaining(SpriteBatch batch) {
		Assets.instance.fonts.defaultNormal.draw(batch, "Time Left: " + (int)worldController.time, 350, 10);
	}
	
	private void renderFinish(SpriteBatch batch) {
		if (worldController.isGameOver()) {
			finish = new Finish();
			finish.position.set(330,300);
			finish.render(batch);
		}
	}
	
	/**
	 * Method draws the background to the game's level GUI
	 */
	private void renderGuiBackground(SpriteBatch batch) {
		gui = new GuiBackground();
		gui.position.set(0,0);
		gui.render(batch);
	}
}
