/**
 * Holds information about the shrub decoration
 */

package com.cherryscramble.g1.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cherryscramble.g1.game.Assets;

public class Shrub extends AbstractGameObject{
	private TextureRegion regShrub;
	
	/**
	 * Initialize Shrub.
	 */
	public Shrub()
	{
		init();
	}
	
	/**
	 * Shrub properties
	 */
	public void init()
	{
		dimension.set(30.0f, 2.0f);
		regShrub = Assets.instance.decoration.shrub;
	}

	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		reg = regShrub;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
		rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
	}
}
