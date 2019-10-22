package com.bobo.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.bobo.bonuses.AbstractGameBonus;
import com.bobo.bonuses.GrowthMushroomBonus;
import com.bobo.game.Assets;

public class Ground extends BlockGeneric {

	private TextureRegion regGround;

	public Ground() {
		init();
	}

	public void init() {
		super.init();
		
		regGround = Assets.instance.tilesetAssets.ground;
		reg = regGround;
		bumpFromBottomAnimation = false;
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
