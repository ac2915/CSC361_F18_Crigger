package com.cherryscramble.g1.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.cherryscramble.g1.game.Assets;

public class GoldenCherry extends AbstractGameObject {
	private Animation<TextureRegion> goldCherry;
	public boolean collected;
	float stateTime;
	
	/**
	 * Initialize Cherry
	 */
	public GoldenCherry () {
		init();
	}
	
	/**
	 * Cherry Initialization
	 */
	@SuppressWarnings("unchecked")
	private void init () {
		dimension.set(1.0f, 1.0f);
		//goldCherry = Assets.instance.cherry.cherry;
		
		goldCherry = Assets.instance.goldcherry.Cherryani;
		//stateTime = MathUtils.random(0.0f, 1.0f);
		
		// Set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		collected = false;
	}
	
	/**
	 * Rendering properties of the cherry
	 */
	@Override
	public void render(SpriteBatch batch) {
		if (collected) return;
		TextureRegion reg = null;
		stateTime += Gdx.graphics.getDeltaTime();
		reg = (TextureRegion) goldCherry.getKeyFrame(stateTime, true);
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,
		rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
	}
	
	/**
	 * Returns a score of 100
	 * @return
	 */
	public int getScore() {
		return 500;
	}
}
