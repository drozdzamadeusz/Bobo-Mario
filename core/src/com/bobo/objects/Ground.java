package com.bobo.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.bobo.bonuses.AbstractGameBonus;
import com.bobo.game.Assets;
import com.bobo.utils.Constants;

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
	public void render(SpriteBatch batch) {
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,
				scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
				false, false);
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
