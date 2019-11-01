package com.bobo.objects.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bobo.game.Assets;
import com.bobo.objects.AbstractGameObject;
import com.bobo.objects.AbstractRigidBodyObject;

public class KoopaTroopa extends AbstractRigidBodyObject implements Enemy {

	public static final String TAG = KoopaTroopa.class.getCanonicalName();
	
	private Animation<?> koopaTroopaWalking;
	private Animation<?> koopaTroopaCrushedReborn;
	private AtlasRegion koopaTroopaCrushed;
	
	private float TIME_TO_REBORN;
	private float TIME_TO_START_REBORN_ANIMATION;
	
	public boolean slidingAfterHit;
	
	public KoopaTroopa() {
		init();
	}

	@Override
	public void init() {
		koopaTroopaWalking = Assets.instance.enemiesAssets.koopaTroopaWalking;
		koopaTroopaCrushedReborn = Assets.instance.enemiesAssets.koopaTroopaCrushedReborn;
		koopaTroopaCrushed =  (AtlasRegion) Assets.instance.enemiesAssets.koopaTroopaCrushedReborn.getKeyFrame(0);
		
		setAnimation(koopaTroopaWalking);
		 
		
		dimension = new Vector2(1.0f, 1.5f);	
		origin.set(dimension.x / 2, dimension.y / 2);
		bounds.set(0, 0, dimension.x, dimension.y);
		
		terminalVelocity.set(3.0f, 17.0f);
		momentumGain = new Vector2(terminalVelocity);
		
		
		// Goomba init direction
		viewDirection = (0 == (MathUtils.random(0, 1)))?VIEW_DIRECTION.RIGHT:VIEW_DIRECTION.LEFT;
		
		jumpState = JUMP_STATE.FALLING;
		
		isEnemy = true;
	
		isAlive = true;
		
		slidingAfterHit = false;

		
	}

	@Override
	public float getDealingDamage() {
		// TODO Auto-generated method stub
		return 100;
	}	
	
	@Override
	public boolean isAlive() {
		return true;
	}

	@Override
	public void onHitFromTop(AbstractGameObject collidedObject) {
		if(collidedObject.isEnemy()) return;
		super.onHitFromTop(collidedObject);
	}

	@Override
	public void onHitFromBottom(AbstractGameObject collidedObject) {
		if(collidedObject.isEnemy()) return;
		super.onHitFromBottom(collidedObject);
	}

	@Override
	public void onHitFromSide(AbstractGameObject collidedObject, boolean hitRightEdge) {
		
		if(!slidingAfterHit  || (slidingAfterHit && !collidedObject.isEnemy())) {
			if (hitRightEdge) {
				position.x = collidedObject.position.x + collidedObject.bounds.width;
			} else {
				position.x = collidedObject.position.x - bounds.width;
			}
			
			viewDirection = (hitRightEdge) ? VIEW_DIRECTION.RIGHT : VIEW_DIRECTION.LEFT;	
		}else if(slidingAfterHit){
			
		}
		
		if(collidedObject.isEnemy() && collidedObject.isAlive()) {			
			if(slidingAfterHit) {
				((Enemy) collidedObject).damageEnemyFromSide(this, hitRightEdge);
			}else{
				((AbstractRigidBodyObject) collidedObject).viewDirection = (!hitRightEdge)?VIEW_DIRECTION.RIGHT:VIEW_DIRECTION.LEFT;
			}
		}
		
	}
	
	boolean killedFromSide = false;
	public float KILLED_FROM_SIDE_ANIMATION_JUMP_TIME = 0.0001f;
	
	
	boolean setKilledFromSideJumpVelocity = true;
	
	@Override
	public void update(float deltaTime) {
		if(killedFromSide) {
			if(KILLED_FROM_SIDE_ANIMATION_JUMP_TIME >= 0.0f) {
				KILLED_FROM_SIDE_ANIMATION_JUMP_TIME -= deltaTime;
				if(setKilledFromSideJumpVelocity) {
					terminalVelocity.set(7.0f, 14.0f);
					friction.set(10.0f, 0.0f);
					acceleration.set(0f, -100.0f);
					momentumGain = new Vector2();
					velocity.set(terminalVelocity);
					setKilledFromSideJumpVelocity = !setKilledFromSideJumpVelocity;
				}
			}
		}
		
		super.update(deltaTime);
		
		if(killedFromSide) return;
		
		if(isAlive || slidingAfterHit) {
			setWalking(viewDirection == VIEW_DIRECTION.RIGHT);
		}else{
			if(TIME_TO_REBORN > 0.0f) {
				TIME_TO_REBORN -= deltaTime;
			}
			if(TIME_TO_START_REBORN_ANIMATION > 0.0f) {
				TIME_TO_START_REBORN_ANIMATION -= deltaTime;
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		
		if(isAlive) {
			reg = (TextureRegion) animation.getKeyFrame(stateTime, true);
		}else {
			
			dimension = new Vector2(1.0f, 1.0f);
			origin.set(dimension.x / 2, dimension.y / 2);
			bounds.set(0, 0, dimension.x, dimension.y);
			
			reg = koopaTroopaCrushed;
			
			if(TIME_TO_START_REBORN_ANIMATION <= 0.0f) {
				if(animation != koopaTroopaCrushedReborn)
					setAnimation(koopaTroopaCrushedReborn);
				reg = (TextureRegion) animation.getKeyFrame(stateTime, true);
			}
			
			if(TIME_TO_REBORN <= 0.0f) {
				viewDirection = (0 == (MathUtils.random(0, 1)))?VIEW_DIRECTION.RIGHT:VIEW_DIRECTION.LEFT;
				
				setAnimation(koopaTroopaWalking);
				
				dimension = new Vector2(1.0f, 1.5f);
				origin.set(dimension.x / 2, dimension.y / 2);
				bounds.set(0, 0, dimension.x, dimension.y);
				
				isAlive = true;
				slidingAfterHit = false;
			}
		}
		if(killedFromSide) {
			rotation = 180;
			if(isAlive()) {
				reg = (TextureRegion) animation.getKeyFrame(0, true);
				dimension.set(1.0f, 1.5f);
			}else {
				reg = koopaTroopaCrushed;
				dimension.set(1.0f, 1.0f);
			}
			viewDirection= VIEW_DIRECTION.LEFT;
		}
			
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x,
					dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
					reg.getRegionWidth(), reg.getRegionHeight(), viewDirection == VIEW_DIRECTION.RIGHT, false);

	}
	
	@Override
	public void damageEnemyFromTop() {
		if(!(isAlive)) {
			terminalVelocity.set(12.0f, 17.0f);
			momentumGain.set(terminalVelocity);
			slidingAfterHit = true;
		}
		isAlive = false;
		velocity.x = 0.0f;
		TIME_TO_REBORN = 2.0f;
		TIME_TO_START_REBORN_ANIMATION = 1f;
	}


	@Override
	public void damageEnemyFromBottom() {
		
	}

	@Override
	public void damageEnemyFromSide(AbstractGameObject collidedObjcet, boolean hitRightEdge) {
		isAlive = false;
		killedFromSide = (collidedObjcet.isEnemy && collidedObjcet.getClass() == KoopaTroopa.class && ((KoopaTroopa)collidedObjcet).slidingAfterHit);
	}

	@Override
	public boolean hasBody() {
		// TODO Auto-generated method stub
		return !killedFromSide && (isAlive());
	}
	
	
	
	
	

}
