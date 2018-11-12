/**
 * Holds informations about the TrunkWallLeft texture asset
 */

package com.cherryscramble.g1.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cherryscramble.g1.game.Assets;

public class TrunkWallLeft extends AbstractGameObject {
	private TextureRegion regTrunkWallLeft; // Texture Region
	
	/**
	 * Initialize TrunkWallLeft
	 */
	public TrunkWallLeft(){
		init();
	}
	
	/**
	 * Properties for TrunkWallLeft
	 */
	public void init(){
		dimension.set(1.0f, 1.0f);
		regTrunkWallLeft = Assets.instance.trunkWallLeft.trunkWallLeft;
	}

	/**
	 * Rendering properties for the TrunkWallLeft asset
	 */
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		reg = regTrunkWallLeft;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
		rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
	}

}
