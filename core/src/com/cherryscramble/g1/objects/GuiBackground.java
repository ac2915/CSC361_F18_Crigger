/**
 * Class contains the properties to the gui background object
 */

package com.cherryscramble.g1.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cherryscramble.g1.game.Assets;

public class GuiBackground extends AbstractGameObject {
	private TextureRegion regBackground;
	
	/**
	 * Initialize the GUI Background image.
	 */
	public GuiBackground()
	{
		init();
	}
	
	/**
	 * GUI background properties
	 */
	public void init()
	{
		dimension.set(800.0f, 32.0f);
		regBackground = Assets.instance.decoration.guibackground;
	}

	/**
	 * GUI background Rendering Properties
	 */
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		reg = regBackground;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
		rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
	}

}
