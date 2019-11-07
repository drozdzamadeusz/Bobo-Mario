package com.bobo.objects.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bobo.game.Assets;
import com.bobo.objects.AbstractGameObject;
import com.bobo.objects.AbstractRigidBodyObject;

public class Goomba extends AbstractRigidBodyObject implements Enemy{

	public static final String TAG = Goomba.class.getCanonicalName();
	
	private Animation<?> goombaWalking;
	private TextureRegion regGoombaCrushed;
	
	private float dealingDamage;
	
	public final float JUMP_TIME_MAX = 0.03f;
	public final float JUMP_TIME_MIN = 0.00f;
	
	public Goomba() {
		init();
	}

	@Override
	public void init() {
		goombaWalking = Assets.instance.enemiesAssets.goombaWalking;
		regGoombaCrushed = Assets.instance.enemiesAssets.goombaCrushed;
		
		setAnimation(goombaWalking);
		
		origin.set(dimension.x / 2, dimension.y / 2);
		bounds.set(0, 0, dimension.x, dimension.y);
		
		terminalVelocity.set(3.0f, 17.0f);
		momentumGain = new Vector2(terminalVelocity);
		
		
		// Goomba init direction
		viewDirection = (0 == (MathUtils.random(0, 1)))?VIEW_DIRECTION.RIGHT:VIEW_DIRECTION.LEFT;
		
		jumpState = JUMP_STATE.FALLING;
		
		isEnemy = true;
		dealingDamage = 100;
	
		isAlive = true;
	}
	
	
	@Override
	public void onHitFromTop(AbstractGameObject collidedObject) {
		super.onHitFromTop(collidedObject);
	}

	@Override
	public void onHitFromBottom(AbstractGameObject collidedObject) {
		if(collidedObject.isEnemy()) return;
		super.onHitFromBottom(collidedObject);
	}

	@Override
	public void onHitFromSide(AbstractGameObject collidedObject, boolean hitRightEdge) {
		if(collidedObject.isEnemy()) return;
		super.onHitFromSide(collidedObject, hitRightEdge);
	}

	private float TIME_TO_SHOW_AFTER_DEAD = 0.4f;
	boolean killedFromSide = false;

	
	public float KILLED_FROM_SIDE_ANIMATION_JUMP_TIME = 0.0001f;
	public boolean killedFromSideSetJump = false;
	
	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		
		if(killedFromSide)
			rotation = 180;
		
		if(isAlive || killedFromSide) {
			if(!killedFromSide)
				reg = (TextureRegion) animation.getKeyFrame(stateTime, true);
			else
				reg = (TextureRegion) animation.getKeyFrame(0, true);
		}else {
			reg = regGoombaCrushed;
		}
			
		if(TIME_TO_SHOW_AFTER_DEAD > 0.0f)
			batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x,
						dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
						reg.getRegionWidth(), reg.getRegionHeight(), false, false);

	}
	
	
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
		
		if(isAlive) {
			setWalking((viewDirection == VIEW_DIRECTION.RIGHT)?true:false);
		}else {
			if(TIME_TO_SHOW_AFTER_DEAD > 0.0f) {
				TIME_TO_SHOW_AFTER_DEAD -= deltaTime;
			}
		}
	}
	
	
	@Override
	public void damageEnemyFromTop() {
		isAlive = false;
		velocity.x = 0.0f;	
	}
	
	
	@Override
	public void damageEnemyFromSide(AbstractGameObject collidedObject, boolean hitRightEdge) {
		isAlive = false;
		killedFromSide = (collidedObject.isEnemy && collidedObject.getClass() == KoopaTroopa.class && ((KoopaTroopa) collidedObject).slidingAfterHit);
	}
	
	@Override
	public void damageEnemyFromBottom() {
		damageEnemyFromTop();
	}
	
	@Override
	public float getDealingDamage() {
		return dealingDamage;
	}

	@Override
	public boolean hasBody() {
		return !killedFromSide && (isAlive() || TIME_TO_SHOW_AFTER_DEAD > 0.0f);
	}

	@Override
	public boolean isAlive() {
		return isAlive;
	}
	
}
