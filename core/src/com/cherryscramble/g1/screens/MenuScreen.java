package com.cherryscramble.g1.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.cherryscramble.g1.util.Constants;

public class MenuScreen extends AbstractGameScreen{

	private Stage stage;				// Scene Stage
	private Skin skinCherryScramble; 	// Skin
	
	// Menu Screen Objects
	private Image imgBackground;	// Background Image
	private Button btnPlay;			// Play Button
	private Button btnOptions;		// Options Button
	
	public MenuScreen(Game game) {
		super(game);
	}

	/**
	 * Shows the MenuScreen
	 */
	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		rebuildStage();
	}
	
	private void rebuildStage() {
		skinCherryScramble = new Skin(Gdx.files.internal(Constants.SKIN_UI),
				new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
		
		Table layerBackground = buildBackgroundLayer(); // Background Layer
		
		// Assemble Menu Screen Stage
		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		stack.add(layerBackground);
	}

	private Table buildBackgroundLayer() {
		Table layer = new Table();
		// Background
		imgBackground = new Image(skinCherryScramble, "Background");
		layer.add(imgBackground);
		return layer;
	}

	@Override
	public void render(float deltaTime) {
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//if (debugEnabled) {
		//	debugRebuildStage -= deltaTime;
		//	if (debugRebuildStage <= 0) {
		//		debugRebuildStage = DEBUG_REBUILD_INTERVAL;
		//		rebuildStage();
		//	}
		//}
		stage.act(deltaTime);
		stage.draw();
		
		stage.setDebugAll(true);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

}
