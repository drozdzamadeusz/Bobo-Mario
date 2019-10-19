package com.bobo.rewards;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.CpuSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.bobo.game.Assets;
import com.bobo.objects.AbstractGameObject;

public class Coin extends AbstractGameReward{

	public float amout;
	
	private Animation<?> coinCollected;

	public Coin(AbstractGameObject parent) {
		this(parent, 100);
	}
	
	public Coin(AbstractGameObject parent, float amout) {
		this.parent = parent;
		this.amout = amout;
		init();
	}

	
	
	private float COIN_ANIMATION_DURATON = 0.7f;
	private float coinCurrentAnimDuration;
	
	@Override
	public void init() {
		coinCollected = Assets.instance.objectsAssets.goldCoin;
		setAnimation(coinCollected);
		position.set(parent.position);
		dimension.set(1.0f, 1.0f);
		
		terminalVelocity.set(0, 36.0f);
		friction.set(0, 42.0f);
		acceleration.set(0, -80.0f);
		momentumGain = new Vector2(terminalVelocity);
		
		velocity.y = terminalVelocity.y;
		velocity.x = terminalVelocity.x;
		
	}
	
	@Override
	public void setCollected(boolean collected) {
		// TODO Auto-generated method stub
		super.setCollected(collected);
	}

	
	@Override
	public void setVisible(boolean visible) {
		// TODO Auto-generated method stub
		super.setVisible(visible);
		
		coinCurrentAnimDuration = COIN_ANIMATION_DURATON;
	}
	

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		
		if(isVisible()) {
			coinCurrentAnimDuration -= deltaTime;
		}
		
	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg;
		
		if(isVisible() && coinCurrentAnimDuration >= 0.0f) {
			reg = (TextureRegion) animation.getKeyFrame(stateTime, true);
			
			batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,
					scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
					false, false);
			
		}
	}
	
	

}
