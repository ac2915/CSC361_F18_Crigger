package com.cherryscramble.g1.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.cherryscramble.g1.util.Constants;

public class HighScoreScreen extends AbstractGameScreen{

	private Stage stage;				// Scene Stage
	private Skin skinCherryScramble; 	// Menu Skin
	private Skin skinLibgdx;			// Text Skin
	
	// Menu Screen Objects
	private Image imgBackground;	// Background Image
	private Button btnReturn;		// Return Button
	
	public HighScoreScreen(Game game) {
		super(game);
	}

	/**
	 * Show the high score screen
	 */
	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		rebuildStage();
	}

	/**
	 * Rebuilds the stage
	 */
	private void rebuildStage() {
		// Build Skins
		skinCherryScramble = new Skin(Gdx.files.internal(Constants.SKIN_UI),
			new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
		
		skinLibgdx = new Skin(Gdx.files.internal(Constants.SKIN_LIBGDX_UI),
				new TextureAtlas(Constants.TEXTURE_ATLAS_LIBGDX_UI));
		
		Table layerBackground = buildBackgroundLayer(); 	// Background Layer
		Table layerScore = buildScoreLayer();				// Score Layer
		Table layerButtons = buildReturnButtonLayer();		// Button Layer
		
		// Assemble High Score Screen Stage
		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		stack.add(layerBackground);
		stack.add(layerScore);
		stack.add(layerButtons);
	}
	
	/**
	 * Builds the background image layer of the high score screen. 
	 * @return
	 */
	private Table buildBackgroundLayer() {
		Table layer = new Table();
		// Background
		imgBackground = new Image(skinCherryScramble, "HS_Background");
		layer.add(imgBackground);
		return layer;
	}
	
	private Table buildScoreLayer() {
		Table layer = new Table();
		for(int i = 0; i < 10; i++) {
			layer.add(new Label("Player " + i, skinLibgdx));			// Player Name
			layer.add(new Label("                      ", skinLibgdx)); // Buffer because pad isn't working
			layer.add(new Label("50,000", skinLibgdx));					// Their Score
			layer.row();
		}
		return layer;
	}
	
	/**
	 * Builds the return button layer
	 * @return
	 */
	public Table buildReturnButtonLayer() {
		Table layer = new Table();
		
		// positioning
		layer.center().bottom();
		layer.padBottom(25);
		
		btnReturn = new Button(skinCherryScramble, "options");
		layer.add(btnReturn);
		btnReturn.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onReturnClicked();
			}
		});
		
		return layer;
	}
	
	public void onReturnClicked() {
		game.setScreen(new MenuScreen(game));	// Switch to the menu screen
	}

	/**
	 * Rendering properties
	 */
	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(deltaTime);
		stage.draw();
		
		stage.setDebugAll(false);
	}

	/**
	 * Resizing properties
	 */
	@Override
	public void resize(int width, int height) {
		stage.getViewport();
	}

	/**
	 * Pausing properties
	 */
	@Override
	public void pause() {
		
	}

	/**
	 * Hides the screen when it is no longer needed
	 */
	@Override
	public void hide() {
		stage.dispose();
		skinCherryScramble.dispose();
	}

}
