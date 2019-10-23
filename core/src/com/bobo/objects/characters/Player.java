package com.bobo.objects.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bobo.game.Assets;
import com.bobo.game.Level;
import com.bobo.objects.AbstractGameObject;
import com.bobo.objects.AbstractRigidBodyObject;
import com.bobo.objects.enemies.Enemy;
import com.bobo.objects.enemies.KoopaTroopa;
import com.bobo.utils.AudioManager;

public class Player extends AbstractRigidBodyObject {

	public static final String TAG = Player.class.getCanonicalName();
	
	public TextureRegion regMarioStanding;
	public TextureRegion regMarioDied;
	public Animation<?> marioWalking;
	
	public boolean makeSmallJump = false;
	
	public float health = 100;
	
	public Player() {
		init();
	}

	
	public final float JUMP_TIME_MAX = 0.20f;
	public final float JUMP_TIME_MIN = 0.01f;
	
	public void init() {
		dimension.set(1.0f, 1.0f);

		marioWalking = Assets.instance.charactersAssets.marioWalking;
		regMarioStanding = Assets.instance.charactersAssets.marioStanding;
		regMarioDied = Assets.instance.charactersAssets.marioDied;
		
		// Center image on game object
		origin.set(dimension.x / 2, dimension.y / 2);

		// Bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);

		
		terminalVelocity.set(8.0f, 15.0f);
		friction.set(20.0f, 10.0f);
		acceleration.set(0, -60.0f);
		momentumGain = new Vector2(30,1);
		
		
		// View direction
		viewDirection = VIEW_DIRECTION.RIGHT;

		// Jump state
		jumpState = JUMP_STATE.FALLING;

		timeJumping = 0;
		
		isPlayer = true;
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
				AudioManager.instance.play(Assets.instance.sounds.jump);
				
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

	public void makeSmallJump(float deltaTime) {
		timeJumping = 0.15f;
		jumpState = JUMP_STATE.JUMP_RISING;
		makeSmallJump = false;
	} 
	
	@Override
	protected void updateMotionY(float deltaTime) {
		deltaTime = Math.min(deltaTime, 1.0f / 60.0f);
		
		switch (jumpState) {
		case GROUNDED:
			jumpState = JUMP_STATE.FALLING;

			//Mario standing on platform
			if (velocity.x != 0) {
				if(animation == null) setAnimation(marioWalking);
			}else{
				setAnimation(null);
			}

			break;
		case JUMP_RISING:
			// Keep track of jump time
			timeJumping += deltaTime;
			
			// Jump time left?
			if (timeJumping <= JUMP_TIME_MAX) {
				// Still jumping
				velocity.y = terminalVelocity.y;
			}else {
				//Gdx.app.debug(TAG, "");
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
		
		if (jumpState == JUMP_STATE.JUMP_RISING || jumpState == JUMP_STATE.JUMP_FALLING) {
			setAnimation(null);
			regMarioStanding = Assets.instance.charactersAssets.marioJumping;
		}else {
			regMarioStanding = Assets.instance.charactersAssets.marioStanding;
		}
		
		if (jumpState != JUMP_STATE.GROUNDED) {
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
	}
	

	@Override
	protected void updateMotionX(float deltaTime) {
		super.updateMotionX(deltaTime);
	}

	
	
	@Override
	public void onHitFromBottom(AbstractGameObject collidedObject) {
		
		if(enemyKillerPlayer(collidedObject)) return;
		
		if(!(collidedObject.isEnemy() && !(collidedObject).hasBody())) {
			super.onHitFromBottom(collidedObject);
		}
		
		AudioManager.instance.play(Assets.instance.sounds.bump);
		
		timeJumping = JUMP_TIME_MAX+1.0f;
		velocity.y = -terminalVelocity.y / 2f;
		
	}



	@Override
	public void onHitFromSide(AbstractGameObject collidedObject, boolean hitRightEdge) {
		
		if(enemyKillerPlayer(collidedObject)) return;
		
		if(!(collidedObject.isEnemy() && !(collidedObject).hasBody())) {
			if (hitRightEdge) {
				position.x = collidedObject.position.x + collidedObject.bounds.width;
			} else {
				position.x = collidedObject.position.x - bounds.width;
			}
		}
	}

	
	/* TO DO: POPRAWA JAKOSCI KODU */
	@Override
	public void onHitFromTop(AbstractGameObject collidedObject) {
		//collidedObject.movingObjectHitFromTop(this);
		
		if(collidedObject.isEnemy() && collidedObject.dimension.y == 1.5f) {
			float heightDifference = Math.abs(position.y - (collidedObject.position.y + collidedObject.bounds.height));
			float widthDifference = Math.abs(position.x - (collidedObject.position.x));
			
			if(heightDifference > 0.20f && widthDifference > 0.8f) {
				enemyKillerPlayer(collidedObject);
			}
		}
		
		if(collidedObject.isEnemy() && (collidedObject).hasBody()) {
			((Enemy) collidedObject).damageEnemyFromTop();
			
			if(collidedObject.getClass() == KoopaTroopa.class && ((KoopaTroopa) collidedObject).slidingAfterHit == true) {
				float widthDifference = position.x - (collidedObject.position.x);
				if(widthDifference < 0)
					((KoopaTroopa) collidedObject).viewDirection = VIEW_DIRECTION.RIGHT;
				else
					((KoopaTroopa) collidedObject).viewDirection = VIEW_DIRECTION.LEFT;
			}
			
			this.jumpState = JUMP_STATE.GROUNDED;
			this.timeJumping = 0.0f;
			AudioManager.instance.play(Assets.instance.sounds.stomp);
			makeSmallJump = true;
		}
		
		if(!(collidedObject.isEnemy() && !(collidedObject).hasBody())) {
			super.onHitFromTop(collidedObject);
		}
	}

	private boolean enemyKillerPlayer(AbstractGameObject collidedObject) {
		if(collidedObject.isEnemy() && (collidedObject).isAlive()) {
			health -= ((Enemy) collidedObject).getDealingDamage();
			return true;
		}
		return false;
	}

	@Override
	public boolean isAlive() {
		return health > 0;
	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;

		float dimCorrectionX = 0;
		float dimCorrectionY = 0;
		
		if(isAlive())
			if(animation == null)
				reg = regMarioStanding;
			else
				reg = (TextureRegion) animation.getKeyFrame(stateTime, true);
		else
			reg = regMarioDied;
		
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x + dimCorrectionX,
				dimension.y + dimCorrectionY, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
				reg.getRegionWidth(), reg.getRegionHeight(), viewDirection == VIEW_DIRECTION.LEFT, false);

	}

}