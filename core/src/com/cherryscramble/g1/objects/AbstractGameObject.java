/**
 * Allen Crigger
 * AbstractGameObject: Super Class for all of the objects to implement
 */

package com.cherryscramble.g1.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class AbstractGameObject {
	// Vectors and Rotation to deal with object's position, size, and orientation
	public Vector2 position;
	public Vector2 dimension;
	public Vector2 origin;
	public Vector2 scale;
	public float rotation;
	
	//Box2D Properties
	public Vector2 velocity; 			// Current speed
	public Vector2 terminalVelocity; 	// Max speed
	public Vector2 friction; 			// Opposing force that slows down object
	public Vector2 acceleration; 		// Object's constant acceleration
	
	//Box2D Body for physics
	public Body body;
	
	public Rectangle bounds; // bounding box of object for collision
	
	/**
	 * Constructor for game objects.
	 */
	public AbstractGameObject() {
		//Image Property initialization
		position = new Vector2();
		dimension = new Vector2(1, 1);
		origin = new Vector2();
		scale = new Vector2(1, 1);
		rotation = 0;
		
		//Box2D Physics Property initialization
		velocity = new Vector2();
		terminalVelocity = new Vector2(1, 1);
		friction = new Vector2();
		acceleration = new Vector2();
		bounds = new Rectangle();
	}

	/**
	 * Update each object's position as time progresses.
	 * @param deltaTime
	 */
	public void update(float deltaTime) {
		// Chapter 11 if statement
		if (body == null) {
			//updateMotionX(deltaTime);
			//updateMotionY(deltaTime);
			// Move to new position
			//position.x += velocity.x * deltaTime;
			//position.y += velocity.y * deltaTime;
		} else {
			position.set(body.getPosition());
			rotation = body.getAngle() * MathUtils.radiansToDegrees;
		}
	}
	
	/**
	 * Update the velocity in the x direction based on the friction, acceleration
	 * and then clamp it inside of its terminal velocity
	 * 
	 * @param deltaTime
	 */
	protected void updateMotionX(float deltaTime) {
		if (velocity.x != 0) {
			// Apply friction
			if (velocity.x > 0) {
				velocity.x = Math.max(velocity.x - friction.x * deltaTime, 0);
			} else {
				velocity.x = Math.min(velocity.x + friction.x * deltaTime, 0);
			}
		}
		// Apply acceleration
		velocity.x += acceleration.x * deltaTime;
		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		velocity.x = MathUtils.clamp(velocity.x, -terminalVelocity.x, terminalVelocity.x);
	}

	/**
	 * Update the velocity in the y direction based on the friction, acceleration
	 * and then clamp it inside of its terminal velocity
	 * 
	 * @param deltaTime
	 */
	protected void updateMotionY(float deltaTime) {
		if (velocity.y != 0) {
			// Apply friction
			if (velocity.y > 0) {
				velocity.y = Math.max(velocity.y - friction.y * deltaTime, 0);
			} else {
				velocity.y = Math.min(velocity.y + friction.y * deltaTime, 0);
			}
		}
		// Apply acceleration
		velocity.y += acceleration.y * deltaTime;
		// Make sure the object's velocity does not exceed the
		// positive or negative terminal velocity
		velocity.y = MathUtils.clamp(velocity.y, -terminalVelocity.y, terminalVelocity.y);
	}
	
	/**
	 * Render the object onto the screen
	 * @param batch
	 */
	public abstract void render(SpriteBatch batch);
}
