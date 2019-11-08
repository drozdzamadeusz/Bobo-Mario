package com.bobo.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.bobo.bonuses.AbstractGameBonus;
import com.bobo.bonuses.CoinsBonus;
import com.bobo.bonuses.GoldCoinAnim;
import com.bobo.game.Assets;

public class BonusBlock extends BlockGeneric {

	public Animation<?> goldCoin;
	
	public BonusBlock() {
		init();
	}

	public void init() {
		super.init();
		
		goldCoin = Assets.instance.objectsAssets.bonusBlock;
		
		bumpFromBottomAnimation = true;
		
		setAnimation(goldCoin);
	}

	@Override
	public void render(SpriteBatch batch) {	
		if(!outOfBonus)
			reg = (TextureRegion) animation.getKeyFrame(stateTime, true);
		else
			reg = Assets.instance.tilesetAssets.blockDisbaled;
		super.render(batch);
	}

	/*@Override
	public void grantBonus() {
		for (int i = 0; i < bonus.size; i++) {
			AbstractGameBonus bGoldCoin = bonus.get(i);
			if (!bGoldCoin.isVisible()) {
				AbstractGameBonus bCoins = bonus.get(i+1);
				
				bGoldCoin.setVisible(true);
				bCoins.setVisible(true);
				
				break;
			}
			outOfBonus = true;
		}
	}*/
	
	
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}
	
	
}
