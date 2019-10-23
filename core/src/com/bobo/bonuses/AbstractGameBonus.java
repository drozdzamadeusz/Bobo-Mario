package com.bobo.bonuses;

import com.bobo.objects.AbstractGameObject;
import com.bobo.objects.AbstractRigidBodyObject;

public abstract class AbstractGameBonus extends AbstractRigidBodyObject{

	public boolean collected;
	public boolean visible;
	public boolean applyCollisions;
	
	public AbstractGameObject parent;
	
	public AbstractGameBonus() {
		collected = false;
		visible = false;
		applyCollisions = false;
		hasBody = false;
		acceleration.set(0.0f, 0.0f);
	}

	public boolean isCollected() {
		return collected;
	}

	public void setCollected(boolean collected) {
		this.collected = collected;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	
	
	@Override
	public void onHitFromSide(AbstractGameObject collidedObject, boolean hitRightEdge) {
		if(applyCollisions && isVisible()) super.onHitFromSide(collidedObject, hitRightEdge);
	}

	@Override
	public void onHitFromTop(AbstractGameObject collidedObject) {
		if(applyCollisions && isVisible()) super.onHitFromTop(collidedObject);
	}

	@Override
	public void movingObjectHitFromBottom(AbstractGameObject collidedObject) {
		if(applyCollisions && isVisible()) super.movingObjectHitFromBottom(collidedObject);
	}

	@Override
	public void update(float deltaTime) {
		if(isVisible())
			super.update(deltaTime);
	}
	

}
