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
import com.bobo.objects.characters.Player;

public class Goomba extends AbstractRigidBodyObject implements Enemy{

	public static final String TAG = Player.class.getCanonicalName();
	
	private Animation<?> goombaWalking;
	private TextureRegion regGoombaCrushed;
	
	private float dealingDamage;
	
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
		super.onHitFromBottom(collidedObject);
	}

	@Override
	public void onHitFromSide(AbstractGameObject collidedObject, boolean hitRightEdge) {
		super.onHitFromSide(collidedObject, hitRightEdge);
		viewDirection = (hitRightEdge)?VIEW_DIRECTION.RIGHT:VIEW_DIRECTION.LEFT;
	}

	private float TIME_TO_SHOW_AFTER_DEAD = 0.4f;
	
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if(isAlive) {
			setWalking((viewDirection == VIEW_DIRECTION.RIGHT)?true:false);
		}else {
			if(TIME_TO_SHOW_AFTER_DEAD > 0.0f) {
				TIME_TO_SHOW_AFTER_DEAD -= deltaTime;
			}
		}
	}
	

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		
		
		if(isAlive)
			reg = (TextureRegion) animation.getKeyFrame(stateTime, true);
		else
			reg = regGoombaCrushed;

		if(TIME_TO_SHOW_AFTER_DEAD > 0.0f)
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
	public float getDealingDamage() {
		return dealingDamage;
	}

}
