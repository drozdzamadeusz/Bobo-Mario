package com.bobo.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.bobo.objects.AbstractGameObject;
import com.bobo.objects.Mario;
import com.bobo.objects.Mario.JUMP_STATE;

public class CollisionDetection {

	private Level level;
	
	public CollisionDetection() {
		init();
	}
	
	
	private void init() {
	}


	// Rectangles for collision detection
	private Rectangle r1 = new Rectangle();
	private Rectangle r2 = new Rectangle();

	
	private void onCollisionMarioWithBlock(AbstractGameObject ground) {
		Mario mario = (Mario) level.mario;
		
		float heightDifference = Math.abs(mario.position.y - (ground.position.y + ground.bounds.height));
		float widthDifference = Math.abs(mario.position.x - (ground.position.x));
		
		
		//hit from below
		if (widthDifference < 0.5f && heightDifference > 1.5f) {
			mario.position.y = (ground.position.y - ground.bounds.height);
			
			mario.timeJumping = mario.JUMP_TIME_MAX+1.0f;
			mario.velocity.y = -mario.terminalVelocity.y / 1.5f;
			
			return;	
		}
		
		//hit from right or left side
		if (heightDifference > 0.45f) {
			boolean hitRightEdge = mario.position.x > (ground.position.x + ground.bounds.width / 2.0f);	
		 
			if (hitRightEdge) {
				mario.position.x = ground.position.x + ground.bounds.width;
			} else {
				mario.position.x = ground.position.x - mario.bounds.width;
			}
			
			return;
			
		}
		
		//mario on block
		if (widthDifference < 0.91f)
			switch (mario.jumpState) {
				case GROUNDED:
					break;
				case FALLING:
				case JUMP_FALLING:
					mario.position.y = ground.position.y + mario.bounds.height;
					mario.jumpState = JUMP_STATE.GROUNDED;
					break;
				case JUMP_RISING:
					mario.position.y = ground.position.y + mario.bounds.height;
					
			}
	}
	
	private void detectCollisionsForObjects(Array<AbstractGameObject> gorundBlocks) {
		for (AbstractGameObject g : gorundBlocks) {
			r2.set(g.position.x, g.position.y, g.bounds.width, g.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			
			onCollisionMarioWithBlock(g);
			
			// !IMPORTANT: must do all collisions for valid
			// edge testing on rocks.
		}
	}
	
	public void detectCollisions() {
		r1.set(level.mario.position.x, level.mario.position.y, level.mario.bounds.width,
				level.mario.bounds.height);
	
		detectCollisionsForObjects(level.gorundBlocks); // ground platform
		
	}


	public Level getLevel() {
		return level;
	}


	public void setLevel(Level level) {
		this.level = level;
	}
	
}
