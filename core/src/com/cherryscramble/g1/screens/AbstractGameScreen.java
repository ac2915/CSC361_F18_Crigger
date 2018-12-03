/**
 * Abstract game screen class that will hold all of the values and methods that each
 * game screen in the program must have.
 */

package com.cherryscramble.g1.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.cherryscramble.g1.game.Assets;

public abstract class AbstractGameScreen implements Screen {
	protected Game game;

	/**
	 * Sets the game to the one that we wish to use
	 * @param game
	 */
	public AbstractGameScreen(Game game) {
		this.game = game;
	}
		
	@Override
	public abstract void show();

	@Override
	public abstract void render(float delta);

	@Override
	public abstract void resize(int width, int height);

	@Override
	public abstract void pause();
	
	@Override
	public abstract void hide();

	/**
	 * Calls a new asset manager
	 */
	@Override
	public void resume() {
		Assets.instance.init(new AssetManager());
	}

	/**
	 * Asset disposal
	 */
	@Override
	public void dispose() {
		Assets.instance.dispose();
	}

}
