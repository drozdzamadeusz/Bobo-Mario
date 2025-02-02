package com.bobo.objects;

public abstract class AbstractRigidBodyObject extends AbstractGameObject {

	public static final String TAG = AbstractRigidBodyObject.class.getCanonicalName();
	
	public final float JUMP_TIME_MAX = 0.25f;
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
		
		origin.set(dimension.x / 2, dimension.y / 2);
		bounds.set(0, 0, dimension.x, dimension.y);
		
		viewDirection = VIEW_DIRECTION.RIGHT;
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
	public void onHitFromSide(AbstractGameObject collidedObject, boolean hitRightEdge) {
		
		super.onHitFromSide(collidedObject, hitRightEdge);
		
		viewDirection = (hitRightEdge) ? VIEW_DIRECTION.RIGHT : VIEW_DIRECTION.LEFT;
		if(collidedObject.isEnemy() && !collidedObject.isPlayer() && collidedObject.isAlive()) {
			((AbstractRigidBodyObject) collidedObject).viewDirection = (!hitRightEdge) ? VIEW_DIRECTION.RIGHT : VIEW_DIRECTION.LEFT;
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
			position.y = collidedObject.position.y + collidedObject.bounds.height;
			jumpState = JUMP_STATE.GROUNDED;
			break;
		case JUMP_RISING:
			position.y = collidedObject.position.y + collidedObject.bounds.height;

		}
	}
}
