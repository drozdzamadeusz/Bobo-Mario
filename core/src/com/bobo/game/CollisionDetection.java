package com.bobo.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.bobo.objects.AbstractGameObject;

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

	
	private void onCollisionAbstractGameObjectWithBlock(AbstractGameObject movingObject, AbstractGameObject block) {
		
		float heightDifference = Math.abs(movingObject.position.y - (block.position.y + block.bounds.height));
		float widthDifference = Math.abs(movingObject.position.x - (block.position.x));
		
		
		//hit from bottom
		if (widthDifference < 0.5f && heightDifference > 1.5f) {
			movingObject.onHitFromBottom(block);
			return;	
		}
		
		//hit from right or left side
		if (heightDifference > 0.45f) {
			boolean hitRightEdge = movingObject.position.x > (block.position.x + block.bounds.width / 2.0f);
			movingObject.onHitFromSide(block, hitRightEdge);
			return;
			
		}
		//hit from top
		if (widthDifference < 0.91f)
			movingObject.onHitFromTop(block);
	}
	
	

	
	
	private void detectCollisionsObjectForObjects(AbstractGameObject movingObject, Array<AbstractGameObject> block) {
		
		r1.set(movingObject.position.x, movingObject.position.y, movingObject.bounds.width,
				movingObject.bounds.height);
		
		for (AbstractGameObject g : block) {
			r2.set(g.position.x, g.position.y, g.bounds.width, g.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			
			onCollisionAbstractGameObjectWithBlock(movingObject, g);
			
			// !IMPORTANT: must do all collisions for valid
			// edge testing on rocks.
		}
	}
	
	
	private void detectCollisionsObjectsForObjects(Array<AbstractGameObject> movingObjects, Array<AbstractGameObject> block) {
		for (AbstractGameObject m : movingObjects) {
			
			r1.set(m.position.x, m.position.y, m.bounds.width,
					m.bounds.height);
	
			
			for (AbstractGameObject g : block) {
				r2.set(g.position.x, g.position.y, g.bounds.width, g.bounds.height);
				
				if (!r1.overlaps(r2))
					continue;
				
				onCollisionAbstractGameObjectWithBlock(m, g);
				
				// IMPORTANT: must do all collisions for valid
				// edge testing on rocks.
			}
		}
	}
	
	
	
	
	public void detectCollisions() {
		if(level.mario.isAlive()) detectCollisionsObjectForObjects(level.mario, level.gorundBlocks); // ground platform
		
		detectCollisionsObjectsForObjects(level.goombas, level.gorundBlocks);
		
		if(level.mario.isAlive()) detectCollisionsObjectForObjects(level.mario, level.goombas);
		
	}


	public Level getLevel() {
		return level;
	}


	public void setLevel(Level level) {
		this.level = level;
	}
	
}
