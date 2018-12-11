/**
 * Contains the finish image properties
 */

package com.cherryscramble.g1.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cherryscramble.g1.game.Assets;

public class Finish extends AbstractGameObject{

	private TextureRegion regFinish;
	
	/**
	 * Initialize the Finish image.
	 */
	public Finish()
	{
		init();
	}
	
	/**
	 * Finish properties
	 */
	public void init()
	{
		dimension.set(162f, 36.0f);
		regFinish = Assets.instance.decoration.guiFinish;
	}
	
	
	/**
	 * Rendering properties
	 */
	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		reg = regFinish;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
				rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, true);
	}

}
