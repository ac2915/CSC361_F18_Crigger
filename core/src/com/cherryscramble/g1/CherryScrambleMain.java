package com.cherryscramble.g1;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.cherryscramble.g1.game.Assets;
import com.cherryscramble.g1.screens.MenuScreen;
import com.cherryscramble.g1.util.AudioManager;
import com.cherryscramble.g1.util.GamePreferences;

/**
 * Main class that runs the game
 * @author Allen Crigger
 *
 */
public class CherryScrambleMain extends Game {
private static final String TAG = CherryScrambleMain.class.getName();
	
	/**
	 * Creates the game
	 */
	public void create() {
		// Set Libgdx log level to DEBUG
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		// Load Assets
		Assets.instance.init(new AssetManager());
		
		GamePreferences.instance.load();							// Load Game Preferences
		AudioManager.instance.play(Assets.instance.music.titleSong);	// Play Menu Music
		
		// Start the game at the menu screen
		setScreen(new MenuScreen(this));
	}
}