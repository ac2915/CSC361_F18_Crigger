/**
 * Class contains all of the information for the Player.
 */

package com.cherryscramble.g1.objects;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.cherryscramble.g1.game.Assets;

public class Player extends AbstractGameObject implements ContactListener {
	public static final String TAG = Player.class.getName();
	//public ParticleEffect dustParticles = new ParticleEffect();  Particle Effects for another milestone
	private final float JUMP_TIME_MAX = 0.3f;	// The longest amount of time the player can jump
	private final float JUMP_TIME_MIN = 0.1f;   // The shortest amount of time the player can jump
	
	// Player Properties
	private TextureRegion regPlayer; 		// Player Texture
	public VIEW_DIRECTION viewDirection; 	// Directional state the player is facing
	public JUMP_STATE jumpState;		 	// Player's jumping state
	public float timeJumping;				// maximum jump time
	
	
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
		
		bounds.set(0, 0, dimension.x, dimension.y);
		
		// Player initially looks right at the start
		viewDirection = VIEW_DIRECTION.RIGHT;
		
		// Player Jump State
		jumpState = JUMP_STATE.FALLING;
		timeJumping = 0;
	}
	
	public void setJumping(boolean jumpKeyPressed) {
		//Player Jump States
		switch (jumpState) {
		
		case GROUNDED: 				// Character is standing on the ground or platform
			// If the jump key is pressed
			if (jumpKeyPressed) {
				//AudioManager.instance.play(Assets.instance.sounds.jump);  Audio for next milestone
				
				// Start Counting the AirTime.
				timeJumping = 0;					// Time Starts at 0
				jumpState = JUMP_STATE.JUMP_RISING;	// Player Jump State becomes rising
			}
			break;
			
		case JUMP_RISING: 			// Player is rising into the air
			// If the jump key is not pressed
			if (!jumpKeyPressed)
				jumpState = JUMP_STATE.JUMP_FALLING;
			break;
			
		case FALLING:	// Falling down
		case JUMP_FALLING: 	// Falling down after jump
			break;
		}
	}
	
	/**
	 * Update the player's location/state
	 */
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (velocity.x != 0) {
			viewDirection = velocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
		}
	}
	
	/**
	 * Override the update Motion in the Y direction, because the player has the
	 * ability to jump which could change the motion of it compared to another game
	 * object
	 */
	@Override
	protected void updateMotionY(float deltaTime) {
		switch (jumpState) {
		case GROUNDED:
			jumpState = JUMP_STATE.FALLING;
			// Only draw the dust if the player is on the ground
			//if (velocity.x != 0) {
				//dustParticles.setPosition(position.x + dimension.x / 2, position.y);
				//dustParticles.start();
			//}
			break;
		case JUMP_RISING:
			// Keep track of jump time
			timeJumping += deltaTime;
			// Jump time left
			if (timeJumping <= JUMP_TIME_MAX) {
				// Still jumping
				velocity.y = terminalVelocity.y;
			}
			break;
		case FALLING:
			break;
		case JUMP_FALLING:
			// Add delta times to track jump time
			timeJumping += deltaTime;
			// Jump to minimal height if jump key was pressed too short
			if (timeJumping > 0 && timeJumping <= JUMP_TIME_MIN) {
				// Still jumping
				velocity.y = terminalVelocity.y;
			}
		}
		if (jumpState != JUMP_STATE.GROUNDED)
			//dustParticles.allowCompletion();
			super.updateMotionY(deltaTime);
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

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
