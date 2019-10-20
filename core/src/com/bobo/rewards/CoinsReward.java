package com.bobo.rewards;

import com.bobo.objects.AbstractGameObject;

public class CoinsReward extends AbstractGameRewards{

	public float amout;
	
	public CoinsReward(AbstractGameObject parent) {
		this(parent, 100);
	}
	
	public CoinsReward(AbstractGameObject parent, float amout) {
		this.parent = parent;
		this.amout = amout;
		init();
	}

	@Override
	public void init() {
		
	}

}
