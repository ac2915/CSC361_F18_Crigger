package com.cherryscramble.g1.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.cherryscramble.g1.util.AudioManager;
import com.cherryscramble.g1.util.Constants;
import com.cherryscramble.g1.util.GamePreferences;

public class MenuScreen extends AbstractGameScreen{

	private Stage stage;				// Scene Stage
	private Skin skinCherryScramble; 	// Menu Skin
	private Skin skinLibgdx;			// Option Skin
	
	// Menu Screen Objects
	private Image imgBackground;	// Background Image
	private Button btnPlay;			// Play Button
	private Button btnOptions;		// Options Button
	
	// Options objects
	private Window winOptions;		// Options Window
	private Button winSave;			// Save Button
	private Button winCancel;		// Cancel Button
	private Button winHighScore;	// See HighScore Button
	
	private CheckBox chkSound;			// Disable Sound Checkbox
	private Slider sldSound;			// Sound Effect Slider
	private CheckBox chkMusic;			// Disable Music Checkbox
	private Slider sldMusic;			// Music Slider.
	private CheckBox chkShowFPSCounter;	// Disable FPS counter
	
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
		// Build Skins
		skinCherryScramble = new Skin(Gdx.files.internal(Constants.SKIN_UI),
				new TextureAtlas(Constants.TEXTURE_ATLAS_UI));
		
		skinLibgdx = new Skin(Gdx.files.internal(Constants.SKIN_LIBGDX_UI),
				new TextureAtlas(Constants.TEXTURE_ATLAS_LIBGDX_UI));
		
		Table layerBackground = buildBackgroundLayer(); // Background Layer
		Table layerButtons = buildButtonLayer();		// Button Layer
		
		Table layerOptions = buildOptionsWindowLayer();	// Options Layer
		
		// Assemble Menu Screen Stage
		stage.clear();
		Stack stack = new Stack();
		stage.addActor(stack);
		stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		stack.add(layerBackground);

		stack.add(layerButtons);
		stage.addActor(layerOptions);
	}

	/**
	 * Background layer is just the Cherry Scramble Background Menu image
	 * @return the layer
	 */
	private Table buildBackgroundLayer() {
		Table layer = new Table();
		// Background
		imgBackground = new Image(skinCherryScramble, "Background");
		layer.add(imgBackground);
		return layer;
	}
	
	/**
	 * Build the button area on the main menu screen
	 * @return
	 */
	private Table buildButtonLayer() {
		Table layer = new Table();
		
		// Button Positioning
		layer.bottom().left();
		layer.padLeft(115);
		layer.padBottom(65);
		
		// Play button
		btnPlay = new Button(skinCherryScramble, "play");
		btnPlay.setPosition(150, 30);
		layer.add(btnPlay);
		btnPlay.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onPlayClicked();
			}
		});
		
		layer.row();
		
		// Options button
		btnOptions = new Button(skinCherryScramble, "options");
		btnOptions.setPosition(150, 20);
		layer.add(btnOptions).pad(20);
		btnOptions.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onOptionsClicked();
			}
		});
		
		return layer;
	}
	
	// Event for when the play button is clicked
	private void onPlayClicked() {
		AudioManager.instance.stopMusic();
		game.setScreen(new GameScreen(game));
	}
	
	// Event for when the options button is clicked
	private void onOptionsClicked() {
		loadSettings();
		winOptions.setVisible(true);
	}
	
	/**
	 * Method loads in the users personal preferences
	 */
	private void loadSettings() {
		GamePreferences prefs = GamePreferences.instance;
		prefs.load();
		chkSound.setChecked(prefs.sound);
		sldSound.setValue(prefs.volSound);
		chkMusic.setChecked(prefs.music);
		sldMusic.setValue(prefs.volMusic);
		chkShowFPSCounter.setChecked(prefs.showFpsCounter);
	}
	
	/**
	 * Method Saves the user personal preferences
	 */
	private void saveSettings() {
		GamePreferences prefs = GamePreferences.instance;
		prefs.sound = chkSound.isChecked();
		prefs.volSound = sldSound.getValue();
		prefs.music = chkMusic.isChecked();
		prefs.volMusic = sldMusic.getValue();
		prefs.showFpsCounter = chkShowFPSCounter.isChecked();
		prefs.save();
	}
	
	/**
	 * 
	 *  ========== Options Window Part ==========
	 * 
	 */
	
	/*
	 * Method builds the options window layer of the menu screen
	 */
	private Table buildOptionsWindowLayer() {
		winOptions = new Window("Game Settings", skinLibgdx);
		
		// Audio Settings
		winOptions.add(buildAudioOptions()).row();
		
		// Option Buttions (Save and Cancel)
		winOptions.add(buildOptionsBtns()).row();
		
		// Hide the options window by default
		winOptions.setVisible(false);
		// Size
		winOptions.setSize(500, 200);
		// Locate the window to the bottom left corner
		winOptions.setPosition(25, 55);
		return winOptions;
	}
	
	/**
	 * Method builds the audio options table
	 * @return
	 */
	private Table buildAudioOptions() {
		Table tbl = new Table();
		
		tbl.add(new Label("Sounds", skinLibgdx, "default-font", Color.RED)).colspan(3);
		tbl.row();
		
		// Enable Checkbox, "Sound" label, sound volume slider
		chkSound = new CheckBox("", skinLibgdx);
		tbl.add(chkSound);
		tbl.add(new Label("Sound Effects", skinLibgdx));
		sldSound = new Slider(0.0f, 1.0f, 0.1f, false, skinLibgdx);
		tbl.add(sldSound);
		tbl.row();

		// Checkbox, "Music" label, music volume slider
		chkMusic = new CheckBox("", skinLibgdx);
		tbl.add(chkMusic);
		tbl.add(new Label("Background Music", skinLibgdx));
		sldMusic = new Slider(0.0f, 1.0f, 0.1f, false, skinLibgdx);
		tbl.add(sldMusic);
		tbl.row();
		
		// Checkbox, "Show FPS Counter" label
		chkShowFPSCounter = new CheckBox("", skinLibgdx);
		tbl.add(new Label("Show FPS Counter", skinLibgdx));
		tbl.add(chkShowFPSCounter);
		tbl.row();
				
		return tbl;
	}
	
	/**
	 * Method builds the option buttons table
	 * @return the table
	 */
	public Table buildOptionsBtns() {
		Table tbl = new Table();
		
		// Save Button with event handler
		winSave = new TextButton("Save", skinLibgdx);
		tbl.add(winSave).padRight(30);
		winSave.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onSaveClicked();
			}
		});

		// Cancel Button with event handler
		winCancel = new TextButton("Cancel", skinLibgdx);
		tbl.add(winCancel);
		winCancel.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onCancelClicked();
			}
		});
		
		// High Score Button
		winHighScore = new TextButton("See High Scores", skinLibgdx);
		tbl.add(winHighScore);
		winHighScore.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				onScoreClicked();
			}
		});
		
		return tbl;
	}
	
	/**
	 * Event handler for when save button is clicked
	 */
	public void onSaveClicked() {
		saveSettings();
		onCancelClicked();
		AudioManager.instance.onSettingsUpdated();
	}
	
	/**
	 * Event handler for when cancel button is clicked
	 */
	public void onCancelClicked() {
		winOptions.setVisible(false);
		AudioManager.instance.onSettingsUpdated();
	}
	
	/**
	 * Event handler for when the High Score button is clicked
	 */
	public void onScoreClicked() {
		game.setScreen(new HighScoreScreen(game));
	}

	
	
	/**
	 * Method renders the MenuScreen objects onto the screen.
	 */
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
	 * What happens when the game is paused
	 */
	@Override
	public void pause() {

	}

	/**
	 * Hides the screen
	 */
	@Override
	public void hide() {
		stage.dispose();
		skinCherryScramble.dispose();
		skinLibgdx.dispose();
	}

}
