package com.bobo.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class HelpMethods {

	public static Array<TextureRegion> getFontRegions(String str){
		return getFontRegions(str, Assets.instance.fonts.defaultNormal);
	}
	
	public static Array<TextureRegion> getFontRegions(String str, BitmapFont font){
		 Array<TextureRegion> glyphTextures = new Array<TextureRegion>();
		
		for (int i = 0; i < str.length(); i++) {
			
			BitmapFont.Glyph glyph = font.getData().getGlyph(str.charAt(i));

			if(glyph == null){
			    // No glyph for character
			}else{
			    TextureRegion page = font.getRegion(glyph.page);
			    glyphTextures.add(new TextureRegion(page.getTexture(), glyph.u, glyph.v, glyph.u2, glyph.v2));
			}
			
		}
		
		return glyphTextures;
	}

}
