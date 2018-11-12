/**
 * Allen Crigger
 * 
 * CameraHelper
 */

package com.cherryscramble.g1.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.cherryscramble.g1.objects.AbstractGameObject;

public class CameraHelper {
	private static final String TAG = CameraHelper.class.getName();

	private final float MAX_ZOOM_IN = 0.25f;
	private final float MAX_ZOOM_OUT = 10.0f;

	private Vector2 position;
	private float zoom;
	// removed sprite in place for our game objects
	private AbstractGameObject target;

	public CameraHelper() {
		position = new Vector2();
		zoom = 1.0f;
	}

	/**
	 * Instead of using a Sprite now, use the game objects and set their instance
	 * variables
	 * 
	 * @param deltaTime
	 */
	public void update(float deltaTime) {
		if (!hasTarget())
			return;
		position.x = target.position.x + target.origin.x;
		position.y = target.position.y + target.origin.y;
	}

	public void setPosition(float x, float y) {
		this.position.set(x, y);
	}

	public Vector2 getPosition() {
		return position;
	}

	public void addZoom(float amount) {
		setZoom(zoom + amount);
	}

	public void setZoom(float zoom) {
		this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
	}

	public float getZoom() {
		return zoom;
	}

	/**
	 * set the target of the camera helper
	 * 
	 * @param target
	 *            changed to AbstractGameObject from Sprite
	 */
	public void setTarget(AbstractGameObject target) {
		this.target = target;
	}

	/**
	 * Return the current object that CameraHelper is targeting
	 * 
	 * @return The game object, no longer a sprite
	 */
	public AbstractGameObject getTarget() {
		return target;
	}

	public boolean hasTarget() {
		return target != null;
	}

	public boolean hasTarget(AbstractGameObject target) {
		return hasTarget() && this.target.equals(target);
	}

	public void applyTo(OrthographicCamera camera) {
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}
}
