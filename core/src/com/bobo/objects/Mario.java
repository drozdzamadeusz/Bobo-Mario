package com.bobo.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bobo.game.Assets;

public class Mario extends AbstractGameObject {

	public static final String TAG = Mario.class.getCanonicalName();

	public final float JUMP_TIME_MAX = 0.175f;
	public final float JUMP_TIME_MIN = 0.00f;

	public enum VIEW_DIRECTION {
		LEFT, RIGHT
	}

	public enum JUMP_STATE {
		GROUNDED, FALLING, JUMP_RISING, JUMP_FALLING
	}

	public TextureRegion regMario;
	public VIEW_DIRECTION viewDirection;
	public JUMP_STATE jumpState;
	public float timeJumping;

	public Vector2 momentumGain;
	
	public Mario() {
		init();
	}

	public void init() {
		dimension.set(1, 1);

		regMario = Assets.instance.charactersAssets.marioStanding;

		// Center image on game object
		origin.set(dimension.x / 2, dimension.y / 2);

		// Bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);

		// Set physics values
		terminalVelocity.set(6.0f, 17.0f);
		friction.set(27.0f, 10.0f);
		acceleration.set(0, -70.0f);
		momentumGain = new Vector2(40,1);
		
		// View direction
		viewDirection = VIEW_DIRECTION.RIGHT;

		// Jump state
		jumpState = JUMP_STATE.FALLING;

		timeJumping = 0;
	}

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if (velocity.x != 0) {
			viewDirection = velocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
		}
	}

	public void setJumping(float deltaTime, boolean jumpKeyPressed) {
		switch (jumpState) {
		case GROUNDED: // Character is standing on a platform
			if (jumpKeyPressed) {
				// Start counting jump time from the beginning
				timeJumping = 0;
				jumpState = JUMP_STATE.JUMP_RISING;
			}
			break;
		case JUMP_RISING: // Rising in the air
			if (!jumpKeyPressed)
				jumpState = JUMP_STATE.JUMP_FALLING;
			break;
		case FALLING:// Falling down
		case JUMP_FALLING: // Falling down after jump
			break;
		}
	}

	@Override
	protected void updateMotionY(float deltaTime) {
		switch (jumpState) {
		case GROUNDED:
			jumpState = JUMP_STATE.FALLING;

			if (velocity.x != 0) {
			}

			break;
		case JUMP_RISING:
			// Keep track of jump time
			timeJumping += deltaTime;
			// Jump time left?
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
		if (jumpState != JUMP_STATE.GROUNDED) {
			super.updateMotionY(deltaTime);
		}
	}
	

	public void setWalking(float deltaTime, boolean directionRight) {
		if (directionRight) {
			
			velocity.x += momentumGain.x * deltaTime;
		}else {
			velocity.x += -momentumGain.x * deltaTime;
		}
	}
	

	@Override
	protected void updateMotionX(float deltaTime) {
		super.updateMotionX(deltaTime);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;

		// Draw image
		reg = regMario;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,
				scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
				viewDirection == VIEW_DIRECTION.LEFT, false);

	}

}
