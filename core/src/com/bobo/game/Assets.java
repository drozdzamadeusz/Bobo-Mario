package com.bobo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.bobo.utils.Constants;

public class Assets implements Disposable, AssetErrorListener {

	public static final String TAG = Assets.class.getCanonicalName();
	
	public static final Assets instance  = new Assets();
	public AssetManager assetManager;
	
	
	public TilesetAssets tilesetAssets;
	public CharactersAssets charactersAssets;
	
	public AssetFonts fonts;
	
	public void init(AssetManager assetManager) {
		
		this.assetManager = assetManager;
		assetManager.setErrorListener(this);

		assetManager.load(Constants.getPath(Constants.TILESET_TEXTURE_ATLAS_OBJECTS), TextureAtlas.class);
		assetManager.load(Constants.getPath(Constants.CHARACTERS_TEXTURE_ATLAS_OBJECTS), TextureAtlas.class);
		
		assetManager.finishLoading();
		
		Gdx.app.debug(TAG, assetManager.getAssetNames().size+" of assets loaded");

		for (String a : assetManager.getAssetNames()) {
			Gdx.app.debug(TAG, "asset: " + a);
		}

		TextureAtlas tilesetAtlas = assetManager.get(Constants.getPath(Constants.TILESET_TEXTURE_ATLAS_OBJECTS));
		TextureAtlas charactersAtlas = assetManager.get(Constants.getPath(Constants.CHARACTERS_TEXTURE_ATLAS_OBJECTS));

		
		for (Texture t : tilesetAtlas.getTextures())
			t.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
	
		for (Texture t : charactersAtlas.getTextures())
			t.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		
		tilesetAssets = new TilesetAssets(tilesetAtlas);
		charactersAssets = new CharactersAssets(charactersAtlas);
		
		fonts = new AssetFonts();
		
	}

	public class AssetFonts {
		public final BitmapFont defaultNormal;

		public AssetFonts() {			
			defaultNormal = new BitmapFont(Gdx.files.internal(Constants.getPath(Constants.FONT_DEFAULT_NORMAL)), true);			
			defaultNormal.getData().setScale(1.0f);			
			defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		}
	}
	
	
	public class TilesetAssets{
		public final AtlasRegion block;
		public final AtlasRegion blockTop;
		
		public final AtlasRegion blockBouns;
		public final AtlasRegion blockDisbaled;
		public final AtlasRegion blockStairs;

		
		public final AtlasRegion bushRight;
		public final AtlasRegion bushMiddle;
		public final AtlasRegion bushLeft;
		
		public final AtlasRegion cloudRight;
		public final AtlasRegion cloudMiddle;
		public final AtlasRegion cloudLeft;
		
		public final AtlasRegion ground;
		
		public final AtlasRegion tube;
		public final AtlasRegion tubeTop;
		
		public TilesetAssets(TextureAtlas atlas) {
			block = atlas.findRegion("block");
			blockTop = atlas.findRegion("block_top");
			
			blockBouns = atlas.findRegion("block_bonus");
			blockDisbaled = atlas.findRegion("block_disabled");
			blockStairs = atlas.findRegion("block_stairs");

			
			bushRight = atlas.findRegion("bush_right");
			bushMiddle = atlas.findRegion("bush_middle");
			bushLeft = atlas.findRegion("bush_left");
			
			cloudRight = atlas.findRegion("cloud_right");
			cloudMiddle = atlas.findRegion("cloud_middle");
			cloudLeft  = atlas.findRegion("cloud_left");
			
			ground = atlas.findRegion("ground");
			
			tube = atlas.findRegion("tube");
			tubeTop = atlas.findRegion("tube_top");
			
		}
		
	}
	
	public class CharactersAssets{
		public final AtlasRegion marioStanding;
		public final AtlasRegion marioJumping;
		
		public final Animation<?> marioWalking;
		
		public CharactersAssets(TextureAtlas atlas) {
			marioStanding = atlas.findRegion("mario_standing");
			marioJumping = atlas.findRegion("mario_jumping");
			
			Array<AtlasRegion> regions = null;
			// Animation: Mario walking
			regions = atlas.findRegions("mario_walking");
			marioWalking = new Animation<Object>(1.0f / 15.0f, regions, Animation.PlayMode.LOOP_PINGPONG);
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception) throwable);
	}

	@Override
	public void dispose() {
		assetManager.dispose();
		fonts.defaultNormal.dispose();
	}

}
