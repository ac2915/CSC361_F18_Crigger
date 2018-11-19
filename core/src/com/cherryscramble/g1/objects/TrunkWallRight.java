/**
 * Holds information about the TrunkWallRight texture asset
 */

package com.cherryscramble.g1.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cherryscramble.g1.game.Assets;

public class TrunkWallRight extends AbstractGameObject {
	private TextureRegion regTrunkWallRight;
	
	/**
	 * Initializes TrunkWallRight
	 */
	public TrunkWallRight()
	{
		init();
	}
	
	/**
	 * Properties for the TrunkWallRight asset
	 */
	public void init(){
		dimension.set(1.0f, 1.0f);
		regTrunkWallRight = Assets.instance.trunkWallRight.trunkWallRight;
	}
	
	/**
	 * Rendering Properties for the TrunkWallRight asset
	 */
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		reg = regTrunkWallRight;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
		rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
	}

}
