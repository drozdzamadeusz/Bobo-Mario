package com.bobo.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
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
		acceleration.set(0,0);
	}
	
	float r, g, b;
	
	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		reg = regGround;
		

		
		/*r = (r + Gdx.graphics.getDeltaTime()*1.4f*MathUtils.random(0, 1.6f))% 1.0f;
		g = (g + Gdx.graphics.getDeltaTime()*2f*MathUtils.random(0, 1.4f))% 1.0f;
		b= (b + Gdx.graphics.getDeltaTime()*1.3f*MathUtils.random(0, 2f))% 1.0f;
		
		
		Gdx.app.debug("", ""+r);
		
		batch.setColor(r,g,b,1);*/
		
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,
				scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
				false, false);
		
		//batch.setColor(1,1,1,1);
	}
	
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
	}
}
