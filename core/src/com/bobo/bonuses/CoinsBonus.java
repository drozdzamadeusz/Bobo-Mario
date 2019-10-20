package com.bobo.bonuses;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bobo.game.Assets;
import com.bobo.objects.AbstractGameObject;

public class CoinsBonus extends AbstractGameBonus {

	public float amout;
	
	private float COIN_ANIMATION_DURATON = 0.7f;
	private float currentDuration;
	
	private float COIN_ANIMATION_DELAY = 0.5f;
	private float currentDelay;
	
	
	public CoinsBonus(AbstractGameObject parent) {
		this(parent, 100);
	}
	
	public CoinsBonus(AbstractGameObject parent, float amout) {
		this.parent = parent;
		this.amout = amout;
		init();
	} 
	
	public BitmapFont font;

	public Array<TextureRegion> glyphTextures = null;
	
	@Override
	public void init() {
		
		position.x = parent.position.x + parent.dimension.x / 3.0f;
		position.y = parent.position.y + dimension.y + parent.dimension.y;
		
		dimension.set(0.35f, 0.5f);	
		
		terminalVelocity.set(0.0f, 3.0f);
		friction.set(0, 3.0f);
		momentumGain = new Vector2(terminalVelocity);
		
		velocity.y = terminalVelocity.y;
		velocity.x = terminalVelocity.x;
		
		font = Assets.instance.fonts.defaultNormal;
		font.setColor(1, 1, 1, 1);
		font.getData().setScale(0.1f, -0.1f);
		
		
		String amoutStr = String.valueOf(amout);
		
		glyphTextures = new Array<TextureRegion>();
		
		for (int i = 0; i < amoutStr.length() - 2; i++) {
			
			BitmapFont.Glyph glyph = font.getData().getGlyph(amoutStr.charAt(i));

			if(glyph == null){
			    // No glyph for character
			}else{
			    TextureRegion page = font.getRegion(glyph.page);
			    glyphTextures.add(new TextureRegion(page.getTexture(), glyph.u, glyph.v, glyph.u2, glyph.v2));
			}
			
		}

	}
	
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		currentDuration = COIN_ANIMATION_DURATON;
		currentDelay = COIN_ANIMATION_DELAY;
	}
	
	@Override
	public void update(float deltaTime) {
		
		if(isVisible()) {
			if(currentDelay <= 0) {
				super.update(deltaTime);
				currentDuration -= deltaTime;
			}
			currentDelay -= deltaTime;
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		
		if(isVisible() && currentDuration >= 0.0f && currentDelay <= 0) {			
			for (int i = 0; i < glyphTextures.size; i++) {
				TextureRegion t = glyphTextures.get(i);
				
				batch.draw(t.getTexture(), position.x + (i*dimension.x), position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,
						scale.y, rotation, t.getRegionX(), t.getRegionY(), t.getRegionWidth(), t.getRegionHeight(),
						false, false);
			}
		}
		
	}
	
	

}
