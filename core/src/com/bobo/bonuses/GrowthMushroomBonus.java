package com.bobo.bonuses;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.bobo.game.Assets;
import com.bobo.objects.AbstractGameObject;
import com.bobo.objects.AbstractRigidBodyObject.VIEW_DIRECTION;

public class GrowthMushroomBonus extends AbstractGameBonus{

	public TextureRegion regMushroom;
	
	public static float MUSHROOM_SPAWN_TIME  = 0.85f;
	public float currentSpawnTime;
	
	private float MUSHROOM_ANIMATION_DELAY = 0.3f;
	private float currentDelay;
	
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
		
		applyCollisions = true;
	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		currentSpawnTime = MUSHROOM_SPAWN_TIME;	
		currentDelay = MUSHROOM_ANIMATION_DELAY;
	}
	
	boolean sateSliding = false;
	boolean spawnAnimShowed = false;
	
	@Override
	public void update(float deltaTime) {
		if(isVisible()) {
			super.update(deltaTime);
			currentDelay -= deltaTime;
			
			if(currentDelay <= 0) {
				
				if(!spawnAnimShowed) {
					terminalVelocity.set(0.0f, 1.3f);
					acceleration.set(terminalVelocity);
					momentumGain = new Vector2(terminalVelocity);
					velocity.set(terminalVelocity);
					spawnAnimShowed = !spawnAnimShowed;
				}
				
				currentSpawnTime -= deltaTime;
				if(currentSpawnTime <= 0) {
					if(!sateSliding) {
						acceleration.set(0, -60.0f);
						terminalVelocity.set(3.5f, 13.0f);
						momentumGain = new Vector2(terminalVelocity);
						velocity.set(terminalVelocity.x, 0);
						hasBody = true;
						sateSliding = !sateSliding;
					}
				}
				setWalking((viewDirection == VIEW_DIRECTION.RIGHT)?true:false);
			}
		}
	}

	
	@Override
	public void render(SpriteBatch batch) {
		if(isVisible() && currentDelay <= 0) {
			TextureRegion reg;
			reg = regMushroom;
			batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,
					scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
					false, false);
		}
			
	}
	

}
