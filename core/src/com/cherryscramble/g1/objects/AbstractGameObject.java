/**
 * Allen Crigger
 * AbstractGameObject: Super Class for all of the objects to implement
 */

package com.cherryscramble.g1.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractGameObject {
	// Vectors and Rotation to deal with object's position, size, and orientation
	public Vector2 position;
	public Vector2 dimension;
	public Vector2 origin;
	public Vector2 scale;
	public float rotation;
	
	/**
	 * Constructor for game objects.
	 */
	public AbstractGameObject() {
		position = new Vector2();
		dimension = new Vector2(1, 1);
		origin = new Vector2();
		scale = new Vector2(1, 1);
		rotation = 0;
	}

	/**
	 * Update each object's position as time progresses.
	 * @param deltaTime
	 */
	public void update(float deltaTime) {

	}
	
	/**
	 * Render the object onto the screen
	 * @param batch
	 */
	public abstract void render(SpriteBatch batch);
}
