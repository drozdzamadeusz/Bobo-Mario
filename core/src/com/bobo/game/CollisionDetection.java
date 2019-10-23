package com.bobo.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.bobo.objects.AbstractGameObject;

public class CollisionDetection {

	public static final String TAG = CollisionDetection.class.getCanonicalName();
	
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
		
		
		/*if(movingObject.isMario == true && block.isEnemy()) {
			Gdx.app.debug(TAG, "heightDifference: "+heightDifference+" widthDifference: "+widthDifference);
		}*/
		
		//hit from bottom
		if (widthDifference < block.bounds.width * 0.6f && heightDifference > block.bounds.height * 1.5f) {
			
			block.movingObjectHitFromBottom(movingObject);
			movingObject.onHitFromBottom(block);
			
			return;	
		}
		
		//hit from right or left side
		if (heightDifference > block.bounds.height * 0.45f) {
			boolean hitRightEdge = movingObject.position.x > (block.position.x + block.bounds.width / 2.0f);
			
			block.movingObjectHitFromSide(movingObject, hitRightEdge);
			movingObject.onHitFromSide(block, hitRightEdge);
			
			return;
			
		}
		//hit from top
		if (widthDifference < block.bounds.width * 0.91f) {
			
			movingObject.onHitFromTop(block);
			block.movingObjectHitFromTop(movingObject);
		}
		

	}
	
	
	private void detectCollisionsObjectForObjects(AbstractGameObject movingObject, Array<AbstractGameObject> collisionObjectsList) {
		
		if(!movingObject.hasBody()) return;
		
		r1.set(movingObject.position.x, movingObject.position.y, movingObject.bounds.width,
				movingObject.bounds.height);
		
		for (AbstractGameObject g : collisionObjectsList) {
			r2.set(g.position.x, g.position.y, g.bounds.width, g.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			
			onCollisionAbstractGameObjectWithBlock(movingObject, g);
			
			// !IMPORTANT: must do all collisions for valid
			// edge testing on rocks.
		}
	}
	
	
	private void detectCollisionsObjectsForObjects(Array<AbstractGameObject> movingObjects, Array<AbstractGameObject> collisionObject) {
		for (AbstractGameObject m : movingObjects) {
			
			if(!m.hasBody()) continue;
			
			r1.set(m.position.x, m.position.y, m.bounds.width,
					m.bounds.height);
	
			
			//for (AbstractGameObject g : block) {
			for (int i = 0; i < collisionObject.size; i++) {
				
				AbstractGameObject g = collisionObject.get(i);
				
				if(m == g) continue;
				
				r2.set(g.position.x, g.position.y, g.bounds.width, g.bounds.height);
				
				if (!r1.overlaps(r2))
					continue;
				
				onCollisionAbstractGameObjectWithBlock(m, g);
				
				// IMPORTANT: must do all collisions for valid
				// edge testing on rocks.
			}
		}
	}
	
	
	
	//TO DO: poprawa jakosci kodu
	public void detectCollisions() {
		if(level.mario.isAlive()) {
			detectCollisionsObjectForObjects(level.mario, level.gorundBlocks); // ground platform
			detectCollisionsObjectForObjects(level.mario, level.goombas);
			detectCollisionsObjectForObjects(level.mario, level.koopaTroopas);			
		}
		
		
		detectCollisionsObjectsForObjects(level.goombas, level.gorundBlocks);
				
		detectCollisionsObjectsForObjects(level.goombas, level.goombas);		
		detectCollisionsObjectsForObjects(level.koopaTroopas, level.koopaTroopas);
		detectCollisionsObjectsForObjects(level.koopaTroopas, level.goombas);
		
		
		detectCollisionsObjectsForObjects(level.koopaTroopas, level.gorundBlocks);
		
		detectCollisionsObjectsForObjects(level.bonuses, level.gorundBlocks);
		
	}


	public Level getLevel() {
		return level;
	}


	public void setLevel(Level level) {
		this.level = level;
	}
	
}
