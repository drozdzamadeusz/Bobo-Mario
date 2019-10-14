package com.bobo.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bobo.game.Assets;
import com.bobo.game.CollisionDetection;

public class Block extends AbstractRigidBodyObject {

	public static final String TAG = Block.class.getCanonicalName();
	
	private TextureRegion regGround;
	
	public Block(){
		init();
	}
	
	public Vector2 originPosition;
	
	
	public void init() {
		regGround = Assets.instance.tilesetAssets.block;
		acceleration.set(0,0);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		reg = regGround;
		
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,
				scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
				false, false);
		
		batch.setColor(1, 1, 1, 1);
	}
	
	

	private float BUMP_TIME = 0.001f;
	private float timeGoingUp;
	private boolean playerBumpFromBottom = false;
	
	
	@Override
	public void update(float deltaTime) {
		
		super.update(deltaTime);
		
		position.y = Math.max(position.y, originPosition.y);
		
		if(playerBumpFromBottom) {


			
			if(timeGoingUp >= 0.0f) {
				
				terminalVelocity.set(0, 10.0f);
				friction.set(0, 0.0f);
				acceleration.set(0, -100f);
				momentumGain = new Vector2(terminalVelocity);
				
				velocity.y = terminalVelocity.y;
			}else {
				/*terminalVelocity.set(0.0f, 0.0f);
				friction.set(0.0f, 0.0f);
				acceleration.set(0f, 0.0f);
				momentumGain = new Vector2(terminalVelocity);*/
				
			}
			timeGoingUp -= deltaTime;
			
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
		timeGoingUp = BUMP_TIME;
		playerBumpFromBottom = true;
	}

	@Override
	public void movingObjectHitFromSide(AbstractGameObject collidedObject, boolean hitRightEdge) {
		// TODO Auto-generated method stub
		super.movingObjectHitFromSide(collidedObject, hitRightEdge);
	}

}
