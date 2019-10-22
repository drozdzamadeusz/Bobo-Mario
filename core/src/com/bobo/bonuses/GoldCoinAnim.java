package com.bobo.bonuses;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.bobo.game.Assets;
import com.bobo.objects.AbstractGameObject;

public class GoldCoinAnim extends AbstractGameBonus{

	private Animation<?> coinCollectedAnimation;
	
	private float COIN_ANIMATION_DURATON = 0.7f;
	private float coinCurrentAnimDuration;
	
	public GoldCoinAnim() {
		this(null);
	}
	
	
	public GoldCoinAnim(AbstractGameObject parent) {
		this.parent = parent;
		init();
	}
	
	@Override
	public void init() {
		coinCollectedAnimation = Assets.instance.objectsAssets.goldCoin;
		setAnimation(coinCollectedAnimation);
		
		if(parent != null) position.set(parent.position);
		
		dimension.set(1.0f, 1.0f);	
		terminalVelocity.set(0, 34.0f);
		friction.set(0, 42.0f);
		acceleration.set(0, -75.0f);
		momentumGain = new Vector2(terminalVelocity);
		
		velocity.y = terminalVelocity.y;
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		coinCurrentAnimDuration = COIN_ANIMATION_DURATON;
	}
	

	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if(isVisible())
			coinCurrentAnimDuration -= deltaTime;	
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
