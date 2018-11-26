/**
 * Class contains all of the information for the Player.
 */

package com.cherryscramble.g1.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.cherryscramble.g1.game.Assets;

public class Player extends AbstractGameObject {
	public static final String TAG = Player.class.getName();
	
	private TextureRegion regPlayer;
	
	/**
	 * Player's Directional States
	 */
	public enum VIEW_DIRECTION {
		LEFT, RIGHT
	}

	/**
	 * Player's Jumping States
	 */
	public enum JUMP_STATE {
		GROUNDED, FALLING, JUMP_RISING, JUMP_FALLING
	}
	
	/**
	 * Initialize the player upon being called
	 */
	public Player() {
		init();
	}
	
	/**
	 * Player Properties
	 */
	public void init() {
		dimension.set(1f, 1f);
		regPlayer = Assets.instance.player.playerTexture;
		
		// Center image on player object
		origin.set(dimension.x / 2, dimension.y / 2);
	}
	
	
	/**
	 * Rendering properties of the player character
	 */
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		
		reg = regPlayer;
		
		// TEMPORARY DRAW BELOW
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
				rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
	}

}
