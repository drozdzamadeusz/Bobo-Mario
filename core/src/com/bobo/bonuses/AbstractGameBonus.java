package com.bobo.bonuses;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bobo.objects.AbstractGameObject;
import com.bobo.objects.AbstractRigidBodyObject;

public abstract class AbstractGameBonus extends AbstractRigidBodyObject{

	public boolean collected;
	public boolean visible;
	
	public AbstractGameObject parent;
	
	public AbstractGameBonus() {
		collected = false;
		visible = false;
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
	public void update(float deltaTime) {
		if(isVisible())
			super.update(deltaTime);
	}

	@Override
	public void render(SpriteBatch batch) {
		if(isVisible())
			super.render(batch);
	}
	
	

}
