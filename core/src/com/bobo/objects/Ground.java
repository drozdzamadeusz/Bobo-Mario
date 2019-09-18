package com.bobo.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bobo.game.Assets;

public class Ground extends AbstractGameObject {

	private TextureRegion regGround;
	
	public Ground(){
		init();
	}
	
	public void init() {
		dimension.set(1.0f, 1.0f);		
		regGround = Assets.instance.tilesetAssets.ground;
		bounds.set(0, 0, dimension.x, dimension.y);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		reg = regGround;
		
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,
				scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
				false, false);
	}
	
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}
}
