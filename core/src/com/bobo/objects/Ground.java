package com.bobo.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.bobo.game.Assets;
import com.bobo.rewards.AbstractGameReward;
import com.bobo.rewards.Coin;

public class Ground extends BlockGeneric {

	private TextureRegion regGround;

	public Ground() {
		init();
	}

	public void init() {
		super.init();
		regGround = Assets.instance.tilesetAssets.ground;
		reg = regGround;
		bumpFromBottomAnimation = true;
		//reward = Array.with((AbstractGameReward) new Coin(this, 100), (AbstractGameReward) new Coin(this, 100),
		//		(AbstractGameReward) new Coin(this, 100));
		reward = new Array<AbstractGameReward>();
		reward.add((AbstractGameReward) new Coin(this, 100), (AbstractGameReward) new Coin(this, 100));
	}

	@Override
	public void movingObjectHitFromBottom(AbstractGameObject collidedObject) {
		if (collidedObject.isPlayer()) {
			for (AbstractGameReward r : reward) {
				if (!r.isVisible()) {
					r.setVisible(true);
					break;
				}
			}
		}
		super.movingObjectHitFromBottom(collidedObject);
	}

}
