package com.bobo.bonuses;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bobo.game.Assets;
import com.bobo.game.HelpMethods;
import com.bobo.objects.AbstractGameObject;

public class CoinsBonus extends AbstractGameBonus {

	private int amout;
	
	private float COIN_ANIMATION_DURATON = 0.7f;
	private float currentDuration;

	private float COIN_ANIMATION_DELAY = 0.4f;
	private float currentDelay;
	
	public CoinsBonus() {
		this(null);
	}
	
	public CoinsBonus(AbstractGameObject parent) {
		this(parent, 100);
	}
	
	public CoinsBonus(AbstractGameObject parent, int amout) {
		this.parent = parent;
		this.amout = amout;
		init();
	} 
	
	public BitmapFont font;

	public Array<TextureRegion> glyphTextures = null;
	
	@Override
	public void init() {
		
		if(parent != null) {
			position.x = parent.position.x + parent.dimension.x / 3.0f;
			position.y = parent.position.y + dimension.y + parent.dimension.y;
		}
		dimension.set(0.35f, 0.5f);	
		
		terminalVelocity.set(0.0f, 3.0f);
		friction.set(terminalVelocity);
		momentumGain = new Vector2(terminalVelocity);
		velocity.set(terminalVelocity);
		
		font = Assets.instance.fonts.defaultNormal;
		glyphTextures = HelpMethods.getFontRegions(String.valueOf(amout), font);
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
		if(isVisible() && currentDuration >= 0.0f && currentDelay <= 0) {			
			for (int i = 0; i < glyphTextures.size; i++) {
				TextureRegion reg = glyphTextures.get(i);
				
				batch.draw(reg.getTexture(), position.x + (i*dimension.x), position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,
						scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
						false, false);
			}
		}
		
	}
}
