/**
 * Class renders the world onto the screen
 * 
 * @author Allen Crigger
 */
package com.cherryscramble.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.cherryscramble.game.WorldController;
import com.cherryscramble.util.Constants;

public class WorldRenderer implements Disposable
{
	// -= Camera and Controls =-
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private WorldController worldController;
	private OrthographicCamera cameraGUI;
	
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
	}
	
	/**
	 * Used to render the world in the game using a SpriteBatch
	 * 
	 * @param batch
	 */
	private void renderWorld(SpriteBatch batch) {
		//worldController.cameraHelper.applyTo(camera);
		//batch.setProjectionMatrix(camera.combined);
		//batch.begin();
		//worldController.level.render(batch);
		//batch.end();
	}
	
	/**
	 * Render the world.
	 */
	public void render() {
		//renderWorld(batch);
		//renderGui(batch);
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
}
