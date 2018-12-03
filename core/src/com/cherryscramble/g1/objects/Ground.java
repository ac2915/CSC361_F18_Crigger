/**
 * Holds information about the Ground Texture Asset
 */
package com.cherryscramble.g1.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cherryscramble.g1.game.Assets;
import com.cherryscramble.g1.objects.AbstractGameObject;

public class Ground extends AbstractGameObject {
	private TextureRegion regGround; // Ground Texture Region
	private int length;              // Length
	
	/**
	 * Initialize ground.
	 */
	public Ground()
	{
		init();
	}
	
	/**
	 * Ground Properties
	 */
	private void init () {
		dimension.set(1.0f, 2.0f);
		regGround = Assets.instance.ground.ground;
		
		//Start length of this ground
		setLength(1);
		
		// Set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		origin.set(dimension.x / 2, dimension.y / 2);
	}
	
	/**
	 * Sets the length of a ground
	 * @param length = Initial Length of the ground
	 */
	public void setLength (int length) {
		this.length = length;
		
		bounds.set(-1.0f, 0, dimension.x * length, dimension.y);
	}
		
	/**
	 * Increases the length of the ground
	 * @param amount = Amount to increase the ground by
	 */
	public void increaseLength (int amount) {
		setLength(length + amount);
	}
	
	/**
	 * Rendering properties of the ground asset
	 */
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		float relX = 0;
		reg = regGround;
		for (int i = 0; i < length; i++) {
			batch.draw(reg.getTexture(), position.x + relX, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
			relX += dimension.x;
		}
	}

}
