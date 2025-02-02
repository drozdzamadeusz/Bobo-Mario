package com.bobo.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bobo.objects.AbstractGameObject;

public class CameraHelper {

	private static final String TAG = CameraHelper.class.getName();
	
	private final float MAX_ZOOM_IN = 0.25f;
	private final float MAX_ZOOM_OUT = 10.0f;
	
	private final float FOLLOW_SPEED = 8.0f;
	
	private Vector2 position;
	private float zoom;
	private AbstractGameObject target;
	
	public CameraHelper() {
		position = new Vector2();
		zoom = 1.0f;
	}
	
	public void update(float deltaTime) {
		if(!hasTarget()) return;
		
		position.x =  Math.max(target.position.x + target.origin.x, position.x);
		//position.y = target.position.y + target.origin.y;
		
		//position.lerp(target.position, FOLLOW_SPEED * deltaTime);
		
		// Prevent camera from moving down too far
		position.y = Math.max(-1f, position.y);
	}
	
	public void setPosition(float x, float y) {
		this.position.x = x;
		this.position.y = y;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public float getZoom() {
		return zoom;
	}

	public void setZoom(float zoom) {
		this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
	}

	public void addZoom(float amout) {
		setZoom(zoom + amout);
	}
	
	public AbstractGameObject getTarget() {
		return target;
	}

	public void setTarget(AbstractGameObject target) {
		this.target = target;
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
