package com.bobo.rewards;

import com.bobo.objects.AbstractGameObject;
import com.bobo.objects.AbstractRigidBodyObject;

public abstract class AbstractGameRewards extends AbstractRigidBodyObject {
	public boolean collected;
	public boolean visible;
	
	public AbstractGameObject parent;
	
	public AbstractGameRewards() {
		collected = false;
		visible = false;
	}
}
