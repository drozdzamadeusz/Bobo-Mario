package com.bobo.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.bobo.game.Assets;

public class BlockGeneric extends AbstractRigidBodyObject {

	public static final String TAG = BlockGeneric.class.getCanonicalName();
	
	protected TextureRegion reg;
	protected boolean bumpFromBottomAnimation = true;
	
	public BlockGeneric(){
		init();
	}
	
	public Vector2 originPosition;
	
	
	public void init() {
		reg = Assets.instance.tilesetAssets.block;
		acceleration.set(0,0);
		bumpFromBottomAnimation = true;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		
		super.render(batch);
		
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,
				scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
				false, false);
	}
	
	

	private float BUMP_TIME = 0.001f;
	private float timeBumpingUp;
	private boolean playerBumpFromBottom = false;
	
	
	@Override
	public void update(float deltaTime) {
		
		super.update(deltaTime);
		
		position.y = Math.max(position.y, originPosition.y);
		
		if(playerBumpFromBottom && bumpFromBottomAnimation) {			
			if(timeBumpingUp >= 0.0f) {
				
				terminalVelocity.set(0, 10.0f);
				friction.set(0, 0.0f);
				acceleration.set(0, -100f);
				momentumGain = new Vector2(terminalVelocity);
				
				velocity.y = terminalVelocity.y;
			}
			timeBumpingUp -= deltaTime;
			
		}
	}
	
	
	@Override
	public void movingObjectHitFromTop(AbstractGameObject collidedObject) {
		// TODO Auto-generated method stub
		super.movingObjectHitFromTop(collidedObject);
	}

	@Override
	public void movingObjectHitFromBottom(AbstractGameObject collidedObject) {
		// TODO Auto-generated method stub
		super.movingObjectHitFromBottom(collidedObject);
		timeBumpingUp = BUMP_TIME;
		playerBumpFromBottom = true;
	}

	@Override
	public void movingObjectHitFromSide(AbstractGameObject collidedObject, boolean hitRightEdge) {
		// TODO Auto-generated method stub
		super.movingObjectHitFromSide(collidedObject, hitRightEdge);
	}

}
