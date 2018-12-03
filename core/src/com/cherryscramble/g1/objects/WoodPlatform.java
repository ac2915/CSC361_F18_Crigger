/**
 * Class contains the properties for the WoodenPlatform asset and how it renders onto the screen.
 */

package com.cherryscramble.g1.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cherryscramble.g1.game.Assets;

public class WoodPlatform extends AbstractGameObject {
	private TextureRegion regLeftEdge;	// Left Edge Texture
	private TextureRegion regRightEdge; // Right Edge Texture
	private TextureRegion regMiddle;    // Middle Texture
	private int length;                 // Length

	/**
	 * Initializes the Wood Platform
	 */
	public WoodPlatform() {
		init();
	}
	
	/**
	 * Wooden Platform properties.
	 */
	private void init() {
		dimension.set(1f, 1f);
		regLeftEdge = Assets.instance.woodplatform.leftEdge;
		regRightEdge = Assets.instance.woodplatform.rightEdge;
		regMiddle = Assets.instance.woodplatform.middle;
		//Start length of this platform
		setLength(1);
		
		// Set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		origin.set(dimension.x / 2, dimension.y / 2);
	}
	
	/**
	 * Sets the length of a Wooden Platform
	 * @param length = Initial Length of the Wooden Platform
	 */
	public void setLength (int length) {
		this.length = length;
		
		bounds.set(-1.0f, 0, dimension.x * (length+2), dimension.y);
	}
		
	/**
	 * Increases the length of the Wooden Platform
	 * @param amount = Amound to increase the platform by
	 */
	public void increaseLength (int amount) {
		setLength(length + amount);
	}
	
	/**
	 * Rendering properties for the Wood Platform
	 */
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		float relX = 0;
		float relY = 0;
		
		//Platform's LeftEdge
		reg = regLeftEdge;
		relX -= 1f;
		batch.draw(reg.getTexture(), position.x + relX, position.y + relY, origin.x, origin.y, dimension.x, dimension.y,
				scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		
		//Platform's Middle
		relX = 0;
		reg = regMiddle;
		for (int i = 0; i < length; i++) {
			batch.draw(reg.getTexture(), position.x + relX, position.y + relY, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
			relX += dimension.x;
		}

		//Platform's RightEdge
		reg = regRightEdge;
		batch.draw(reg.getTexture(), position.x + relX, position.y + relY, origin.x + dimension.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
				rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
	}

}
