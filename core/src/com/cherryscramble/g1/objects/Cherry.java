package com.cherryscramble.g1.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cherryscramble.g1.game.Assets;

public class Cherry extends AbstractGameObject {
	private TextureRegion regCherry;
	public boolean collected;
	
	/**
	 * Initialize Cherry
	 */
	public Cherry () {
		init();
	}
		
	/**
	 * Cherry Initialization
	 */
	private void init () {
		dimension.set(1.0f, 1.0f);
		regCherry = Assets.instance.cherry.cherry;
		
		// Set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		collected = false;
	}
	
	/**
	 * Rendering properties of the cherry
	 */
	@Override
	public void render(SpriteBatch batch) {
		if (collected) return;
		TextureRegion reg = null;
		reg = regCherry;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
		rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
	}
	
	/**
	 * Returns a score of 100
	 * @return
	 */
	public int getScore() {
		return 100;
	}

}
