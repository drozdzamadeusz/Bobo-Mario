package com.bobo.objects.enemies;

import com.bobo.objects.AbstractGameObject;

public interface Enemy {
	
	public void damageEnemyFromTop();
	
	public void damageEnemyFromSide(AbstractGameObject collidedObjcet, boolean hitRightEdge);
	
	public void damageEnemyFromBottom();
	
	public float getDealingDamage();
}
