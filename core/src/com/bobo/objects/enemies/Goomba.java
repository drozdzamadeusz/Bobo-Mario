package com.bobo.objects.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.bobo.game.Assets;
import com.bobo.objects.AbstractGameObject;
import com.bobo.objects.AbstractRigidBodyObject;
import com.bobo.objects.characters.Player;

public class Goomba extends AbstractRigidBodyObject implements Enemy{

	public static final String TAG = Player.class.getCanonicalName();
	
	private Animation<?> goombaWalking;
	private TextureRegion regGoombaCrushed;
	
	private float dealingDamage;
	
	private boolean isAlive;
	
	public boolean startUpdating = false;
	
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
		//friction.set(20.0f, 10.0f);
		momentumGain = new Vector2(terminalVelocity);
		
		
		// View direction
		viewDirection = VIEW_DIRECTION.RIGHT;
		
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
		super.onHitFromBottom(collidedObject);
	}

	@Override
	public void onHitFromSide(AbstractGameObject collidedObject, boolean hitRightEdge) {
		super.onHitFromSide(collidedObject, hitRightEdge);
		viewDirection = (hitRightEdge)?VIEW_DIRECTION.RIGHT : VIEW_DIRECTION.LEFT;
	}

	@Override
	public void update(float deltaTime) {
		if(startUpdating) {
			super.update(deltaTime);
			if(isAlive) {
				setWalking((viewDirection == VIEW_DIRECTION.RIGHT)?true:false);
			}else {
				if(TIME_AFTER_DEAD > 0.0f) {
					TIME_AFTER_DEAD -= deltaTime;
				}
			}
		}
		
	}
	
	private float TIME_AFTER_DEAD = 0.4f;
	
	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		
		
		if(isAlive)
			reg = (TextureRegion) animation.getKeyFrame(stateTime, true);
		else
				reg = regGoombaCrushed;

		if(TIME_AFTER_DEAD > 0.0f)
			batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x,
						dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
						reg.getRegionWidth(), reg.getRegionHeight(), false, false);

	}

	@Override
	public void killEnemy() {
		isAlive = false;
		velocity.x = 0.0f;
		
	}

	@Override
	public boolean isEnemyAlive() {
		return isAlive;
	}

	@Override
	public float getDealingDamage() {
		return dealingDamage;
	}

}
