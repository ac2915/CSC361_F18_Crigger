package com.cherryscramble.g1.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cherryscramble.g1.game.Assets;
import com.cherryscramble.g1.objects.AbstractGameObject;

public class Ground extends AbstractGameObject {
	private TextureRegion regGround; // Ground Texture Region
	
	/**
	 * Initialize ground.
	 */
	public Ground()
	{
		init();
	}
	
	private void init () {
		dimension.set(1.0f, 1.0f);
		regGround = Assets.instance.ground.ground;
		
		// Set bounding box for collision detection
		//bounds.set(0, 0, dimension.x, dimension.y);
	}
	
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		reg = regGround;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
		rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
	}

}
