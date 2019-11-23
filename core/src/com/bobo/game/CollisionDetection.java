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
	
	
	private void onCollisionAbstractGameObjectWithBlock(AbstractGameObject aObject, AbstractGameObject bObject) {
		float heightDifference = Math.abs(aObject.position.y - (bObject.position.y + bObject.bounds.height));
		float widthDifference = Math.abs(aObject.position.x - (bObject.position.x));
		
		
		/*if(aObject.isMario == true && bObject.isEnemy()) {
			Gdx.app.debug(TAG, "heightDifference: "+heightDifference+" widthDifference: "+widthDifference);
		}*/
		
		//hit from bottom
		if (widthDifference < bObject.bounds.width * 0.6f && heightDifference > bObject.bounds.height * 1.5f) {
			
			bObject.movingObjectHitFromBottom(aObject);
			aObject.onHitFromBottom(bObject);
			
			return;	
		}
		
		//hit from right or left side
		if (heightDifference > bObject.bounds.height * 0.45f) {
			boolean hitRightEdge = aObject.position.x > (bObject.position.x + bObject.bounds.width / 2.0f);
			
			bObject.movingObjectHitFromSide(aObject, hitRightEdge);
			aObject.onHitFromSide(bObject, hitRightEdge);
			
			return;
			
		}
		//hit from top
		if (widthDifference < bObject.bounds.width * 0.91f) {
			
			aObject.onHitFromTop(bObject);
			bObject.movingObjectHitFromTop(aObject);
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
		detectCollisionsObjectsForObjects(level.koopaTroopas, level.gorundBlocks);		
		
		detectCollisionsObjectsForObjects(level.goombas, level.goombas);
		detectCollisionsObjectsForObjects(level.koopaTroopas, level.koopaTroopas);
		
		detectCollisionsObjectsForObjects(level.goombas, level.koopaTroopas);
		detectCollisionsObjectsForObjects(level.koopaTroopas, level.goombas);

		detectCollisionsObjectsForObjects(level.bonuses, level.gorundBlocks);
		
	}


	public Level getLevel() {
		return level;
	}


	public void setLevel(Level level) {
		this.level = level;
	}
	
}
