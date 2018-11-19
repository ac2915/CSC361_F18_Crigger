/**
 * Class contains the properties for the stump asset and how it renders onto the screen.
 */

package com.cherryscramble.g1.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cherryscramble.g1.game.Assets;

public class Stump extends AbstractGameObject {
	private TextureRegion regLeftEdge;	// Left Edge Texture
	private TextureRegion regRightEdge; // Right Edge Texture
	private TextureRegion regMiddle;    // Middle Texture
	private int length;                 // Length

	/**
	 * Initializes the Stump
	 */
	public Stump() {
		init();
	}
	
	/**
	 * Stump Properties
	 */
	public void init() {
		dimension.set(1f, 1f);
		regLeftEdge = Assets.instance.stump.leftEdge;
		regRightEdge = Assets.instance.stump.rightEdge;
		regMiddle = Assets.instance.stump.middle;
		//Start length of this stump
		setLength(1);
	}
	
	/**
	 * Sets the length of a Wooden Platform
	 * @param length = Initial Length of the Wooden Platform
	 */
	public void setLength (int length) {
		this.length = length;
	}
		
	/**
	 * Increases the length of the Wooden Platform
	 * @param amount = Amound to increase the platform by
	 */
	public void increaseLength (int amount) {
		setLength(length + amount);
	}
	
	/**
	 * Rendering properties of the Stump.
	 */
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		float relX = 0;
		float relY = 0;
		
		//Stump's LeftEdge
		reg = regLeftEdge;
		relX -= 1f;
		batch.draw(reg.getTexture(), position.x + relX, position.y + relY, origin.x, origin.y, dimension.x, dimension.y,
				scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		
		//Stumps's Middle
		relX = 0;
		reg = regMiddle;
		for (int i = 0; i < length; i++) {
			batch.draw(reg.getTexture(), position.x + relX, position.y + relY, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
			relX += dimension.x;
		}

		//Stump's RightEdge
		reg = regRightEdge;
		batch.draw(reg.getTexture(), position.x + relX, position.y + relY, origin.x + dimension.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
				rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
	}
}
