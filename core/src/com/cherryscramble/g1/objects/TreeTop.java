/**
 * Holds information about the TreeTop decoration
 */

package com.cherryscramble.g1.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cherryscramble.g1.game.Assets;

public class TreeTop extends AbstractGameObject {
	private TextureRegion regTreeTop;
	
	/**
	 * Initialize TreeTop.
	 */
	public TreeTop()
	{
		init();
	}

	/**
	 * TreeTop properties
	 */
	public void init()
	{
		dimension.set(32.0f, 3.0f);
		regTreeTop = Assets.instance.decoration.treetop;
	}
	
	/**
	 * Rendering properties of the treetop foreground.
	 */
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		reg = regTreeTop;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
		rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
	}
	
}
