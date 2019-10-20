package com.bobo.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.bobo.bonuses.AbstractGameBonus;
import com.bobo.bonuses.CoinsBonus;
import com.bobo.bonuses.GoldCoinBonus;
import com.bobo.game.Assets;
import com.bobo.rewards.AbstractGameRewards;
import com.bobo.rewards.CoinsReward;

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
		
		bonus = new Array<AbstractGameBonus>();
		bonus.add((AbstractGameBonus) new GoldCoinBonus(this),
				(AbstractGameBonus) new CoinsBonus(this, 200),
				
				(AbstractGameBonus) new GoldCoinBonus(this),
				(AbstractGameBonus) new CoinsBonus(this, 200));
		
	
	}

	@Override
	public void grantBonus() {
		for (int i = 0; i < bonus.size; i++) {
			
			AbstractGameBonus bGoldCoin = bonus.get(i);
			
			if (!bGoldCoin.isVisible()) {
				AbstractGameBonus bCoins = bonus.get(i+1);
				
				bGoldCoin.setVisible(true);
				bCoins.setVisible(true);
				
				break;
			}
		}
	}
	
	

}
