package com.bobo.bonuses;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.bobo.game.Assets;
import com.bobo.objects.AbstractGameObject;
import com.bobo.objects.AbstractRigidBodyObject.VIEW_DIRECTION;

public class GrowthMushroomBonus extends AbstractGameBonus{

	public TextureRegion regMushroom;
	
	public static float MUSHROOM_SPAWN_TIME  = 0.8f;
	public float currentSpawnTime;
	
	
	public GrowthMushroomBonus() {
		this(null);
	}
	
	public GrowthMushroomBonus(AbstractGameObject parent) {
		this.parent = parent;
		init();
	}

	@Override
	public void init() {
		regMushroom = Assets.instance.objectsAssets.growthMushroom;
		
		if(parent != null) position.set(parent.position);
		
		origin.set(dimension.x / 2, dimension.y / 2);
		bounds.set(0, 0, dimension.x, dimension.y);
		
		viewDirection = VIEW_DIRECTION.RIGHT;
		
		jumpState = JUMP_STATE.FALLING;
		
		applyCollisions = true;

	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		currentSpawnTime = MUSHROOM_SPAWN_TIME;
		
		terminalVelocity.set(0.0f, 1.3f);
		acceleration.set(terminalVelocity);
		momentumGain = new Vector2(terminalVelocity);
		velocity.set(terminalVelocity);
	}
	
	boolean sateSliding = false;
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if(isVisible()) {
			currentSpawnTime -= deltaTime;
			if(currentSpawnTime <= 0) {
				if(!sateSliding) {
					acceleration.set(0, -60.0f);
					terminalVelocity.set(5.0f, 17.0f);
					momentumGain = new Vector2(terminalVelocity);
					velocity.set(terminalVelocity.x, 0);
					hasBody = true;
					sateSliding = true;
				}
			}
			setWalking((viewDirection == VIEW_DIRECTION.RIGHT)?true:false);
		}
	}

	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		
		if(isVisible()) {
			TextureRegion reg;
			reg = regMushroom;
			batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,
					scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
					false, false);
		}
			
	}
	

}
