package com.bobo.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class AbstractGameObject {

	public Vector2 position;
	public Vector2 dimension;
	public Vector2 origin;
	public Vector2 scale;
	public float rotation;

	public Vector2 velocity;
	public Vector2 terminalVelocity;
	public Vector2 friction;
	public Vector2 acceleration;
	public Vector2 momentumGain;
	
	public Rectangle bounds;
	//public Body body;
	
	public boolean isEnemy;
	public boolean isAlive;
	public boolean isPlayer;
	public boolean hasBody;

	public AbstractGameObject() {
		position = new Vector2();
		dimension = new Vector2(1.0f, 1.0f);
		origin = new Vector2();
		scale = new Vector2(1, 1);
		rotation = 0;
		velocity = new Vector2();
		terminalVelocity = new Vector2(1, 1);
		friction = new Vector2();
		acceleration = new Vector2(0, -60.0f);
		//acceleration = new Vector2(0.0f, 0.0f);
		momentumGain = new Vector2();
		
		isAlive = true;
		isEnemy = false;
		isPlayer = false;
		hasBody = true;
		
		bounds = new Rectangle(0, 0, dimension.x, dimension.y);
	}

	public float stateTime;
	public Animation<?> animation;

	public void setAnimation(Animation<?> animation) {
		this.animation = animation;
		stateTime = 0;
	}

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

	
	public void setWalking(float deltaTime, boolean directionRight) {
		if (directionRight) {
			velocity.x += momentumGain.x * deltaTime;
		}else {
			velocity.x += -momentumGain.x * deltaTime;
		}
	}
	
	
	public void setWalking(boolean directionRight) {
		if (directionRight) {
			velocity.x += momentumGain.x;
		}else {
			velocity.x += -momentumGain.x;
		}
	}
	
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

	public void update(float deltaTime) {
		stateTime += deltaTime;
		updateMotionX(deltaTime);
		updateMotionY(deltaTime);
		// Move to the new position
		position.x += velocity.x * deltaTime;
		position.y += velocity.y * deltaTime;
	}
	
	
	/* THIS OBJECT COLLIDED ON ANOTHER OBJECT */
	
	public void onHitFromBottom(AbstractGameObject collidedObject) {
		if(collidedObject.isEnemy()) return;
		position.y = (collidedObject.position.y - collidedObject.bounds.height);
	}

	public void onHitFromSide(AbstractGameObject collidedObject, boolean hitRightEdge) {
		if(collidedObject.isEnemy()) return;
		if (hitRightEdge) {
			position.x = collidedObject.position.x + collidedObject.bounds.width;
		} else {
			position.x = collidedObject.position.x - bounds.width;
		}
	}
	
	public void onHitFromTop(AbstractGameObject collidedObject) {}
	
	
	/* MOVING OBJECT COLLIDED ON THIS OBJECT */
	
	public void movingObjectHitFromTop(AbstractGameObject collidedObject) {}
	
	public void movingObjectHitFromBottom(AbstractGameObject collidedObject) {}

	public void movingObjectHitFromSide(AbstractGameObject collidedObject, boolean hitRightEdge) {}
	
	
	public boolean isEnemy() {
		return isEnemy;
	}
	
	public boolean isAlive() {
		return isAlive;
	}

	public boolean isPlayer() {
		return isPlayer;
	}

	public boolean hasBody() {
		return hasBody;
	}

	public abstract void init();
	
	// making method abstract forces class that call it to implement it and handle
	// render
	public abstract void render(SpriteBatch batch);
}
