package com.bobo.game;

import com.badlogic.gdx.Gdx;
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

	
	/*
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
	*/
	
	
	private void onCollisionAbstractGameObjectWithBlock(AbstractGameObject movingObject, AbstractGameObject block) {
		
		float heightDifference = Math.abs(movingObject.position.y - (block.position.y + block.bounds.height));
		float widthDifference = Math.abs(movingObject.position.x - (block.position.x));
		
		
		/*if(movingObject.isMario == true && block.isEnemy()) {
			Gdx.app.debug(TAG, "heightDifference: "+heightDifference+" widthDifference: "+widthDifference);
		}*/
		
		//hit from bottom
		if (widthDifference < block.bounds.width * 0.5f && heightDifference > block.bounds.height * 1.5f) {
			movingObject.onHitFromBottom(block);
			return;	
		}
		
		//hit from right or left side
		if (heightDifference > block.bounds.height * 0.45f) {
			boolean hitRightEdge = movingObject.position.x > (block.position.x + block.bounds.width / 2.0f);
			movingObject.onHitFromSide(block, hitRightEdge);
			return;
			
		}
		//hit from top
		if (widthDifference < block.bounds.width * 0.91f) {
			movingObject.onHitFromTop(block);
		}
		

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
	
			
			//for (AbstractGameObject g : block) {
			
			for (int i = 0; i < block.size; i++) {
				
				AbstractGameObject g = block.get(i);
				
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
	
	
	
	
	public void detectCollisions() {
		if(level.mario.isAlive()) {
			detectCollisionsObjectForObjects(level.mario, level.gorundBlocks); // ground platform
			detectCollisionsObjectForObjects(level.mario, level.goombas);
			detectCollisionsObjectForObjects(level.mario, level.koopaTroopas);
			
		}
		
		
		detectCollisionsObjectsForObjects(level.goombas, level.gorundBlocks);
		
		
		
		detectCollisionsObjectsForObjects(level.goombas, level.goombas);		
		detectCollisionsObjectsForObjects(level.koopaTroopas, level.koopaTroopas);
		
		
		detectCollisionsObjectsForObjects(level.koopaTroopas, level.gorundBlocks);
		
		
	}


	public Level getLevel() {
		return level;
	}


	public void setLevel(Level level) {
		this.level = level;
	}
	
}
