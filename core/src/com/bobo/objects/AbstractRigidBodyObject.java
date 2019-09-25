package com.bobo.objects;

public abstract class AbstractRigidBodyObject extends AbstractGameObject {

	public final float JUMP_TIME_MAX = 0.175f;
	public final float JUMP_TIME_MIN = 0.00f;

	public enum VIEW_DIRECTION {
		LEFT, RIGHT
	}
	
	public enum JUMP_STATE {
		GROUNDED, FALLING, JUMP_RISING, JUMP_FALLING
	}
	
	public JUMP_STATE jumpState;

	public VIEW_DIRECTION viewDirection;

	public float timeJumping;

	public AbstractRigidBodyObject() {
		// View direction
		viewDirection = VIEW_DIRECTION.RIGHT;

		// Jump state
		jumpState = JUMP_STATE.FALLING;

		timeJumping = 0;
	}

	@Override
	protected void updateMotionY(float deltaTime) {
		switch (jumpState) {
		case GROUNDED:
			jumpState = JUMP_STATE.FALLING;
			break;
		case JUMP_RISING:
			// Keep track of jump time
			timeJumping += deltaTime;
			// Jump time left?
			if (timeJumping <= JUMP_TIME_MAX) {
				// Still jumping
				velocity.y = terminalVelocity.y;
			} else {
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

	@Override
	public void onHitFromTop(AbstractGameObject collidedObject) {
		super.onHitFromTop(collidedObject);

		switch (jumpState) {
		case GROUNDED:
			break;
		case FALLING:
		case JUMP_FALLING:
			position.y = collidedObject.position.y + bounds.height;
			jumpState = JUMP_STATE.GROUNDED;
			break;
		case JUMP_RISING:
			position.y = collidedObject.position.y + bounds.height;

		}
	}
}
