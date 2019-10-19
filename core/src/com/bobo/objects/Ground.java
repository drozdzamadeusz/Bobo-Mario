package com.bobo.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bobo.game.Assets;

public class Ground extends BlockGeneric {

	private TextureRegion regGround;
	
	public Ground(){
		init();
	}
	
	public void init() {
		super.init();
		regGround = Assets.instance.tilesetAssets.ground;
		reg = regGround;
		bumpFromBottomAnimation = true;
	}
	
	
}
