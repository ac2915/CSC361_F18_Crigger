/**
 * Holds informations about the TrunkWallLeft texture asset
 */

package com.cherryscramble.g1.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cherryscramble.g1.game.Assets;

public class TrunkWallLeft extends AbstractGameObject {
	private TextureRegion regTrunkWallLeft; // Texture Region
	private int length;						// Length of the object
	
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
		
		//Start length of this ground
		setLength(1);
				
		// Set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		origin.set(dimension.x / 2, dimension.y / 2);
	}
	
	
	/**
	 * Sets the length of a wall
	 * @param length = Initial Length of the wall
	 */
	public void setLength (int length) {
		this.length = length;
		
		bounds.set(-1.0f, 0, dimension.x * length, dimension.y);
	}
		
	/**
	 * Increases the length of the wall
	 * @param amount = Amount to increase the wall by
	 */
	public void increaseLength (int amount) {
		setLength(length + amount);
	}

	/**
	 * Rendering properties for the TrunkWallLeft asset
	 */
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		reg = regTrunkWallLeft;
		float relY = 0;
		for (int i = 0; i < length; i++) {
			batch.draw(reg.getTexture(), position.x, position.y + relY, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
			relY += dimension.y;
		}
	}

}
