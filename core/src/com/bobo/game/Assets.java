package com.bobo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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

	public static final Assets instance = new Assets();
	public AssetManager assetManager;

	public AssetSounds sounds;
	public AssetMusic music;
	
	public TilesetAssets tilesetAssets;
	public CharactersAssets charactersAssets;
	public EnemiesAssets enemiesAssets;

	public AssetFonts fonts;

	public void init(AssetManager assetManager) {

		this.assetManager = assetManager;
		assetManager.setErrorListener(this);

		assetManager.load(Constants.getPath(Constants.TILESET_TEXTURE_ATLAS_OBJECTS), TextureAtlas.class);
		assetManager.load(Constants.getPath(Constants.CHARACTERS_TEXTURE_ATLAS_OBJECTS), TextureAtlas.class);
		assetManager.load(Constants.getPath(Constants.ENEMIES_TEXTURE_ATLAS_OBJECTS), TextureAtlas.class);
		
		assetManager.load(Constants.getPath("music/ground_theme.mp3"), Music.class);
		
		assetManager.load(Constants.getPath("sounds/jump.wav"), Sound.class);
		assetManager.load(Constants.getPath("sounds/bump.wav"), Sound.class);
		assetManager.load(Constants.getPath("sounds/stomp.wav"), Sound.class);
		assetManager.load(Constants.getPath("sounds/coin.wav"), Sound.class);
		assetManager.load(Constants.getPath("sounds/lost_life.wav"), Sound.class);
		
		assetManager.finishLoading();

		Gdx.app.debug(TAG, assetManager.getAssetNames().size + " of assets loaded");

		for (String a : assetManager.getAssetNames()) {
			Gdx.app.debug(TAG, "asset: " + a);
		}

		TextureAtlas tilesetAtlas = assetManager.get(Constants.getPath(Constants.TILESET_TEXTURE_ATLAS_OBJECTS));
		TextureAtlas charactersAtlas = assetManager.get(Constants.getPath(Constants.CHARACTERS_TEXTURE_ATLAS_OBJECTS));
		TextureAtlas enemiesAtlas = assetManager.get(Constants.getPath(Constants.ENEMIES_TEXTURE_ATLAS_OBJECTS));
		
		for (Texture t : tilesetAtlas.getTextures())
			t.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		for (Texture t : charactersAtlas.getTextures())
			t.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		for (Texture t : enemiesAtlas.getTextures())
			t.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		tilesetAssets = new TilesetAssets(tilesetAtlas);
		charactersAssets = new CharactersAssets(charactersAtlas);
		enemiesAssets = new EnemiesAssets(enemiesAtlas);
		
		fonts = new AssetFonts();

		sounds = new AssetSounds(assetManager);
		music = new AssetMusic(assetManager);
	}

	
	//FONTS
	public class AssetFonts {
		public final BitmapFont defaultNormal;

		public AssetFonts() {
			defaultNormal = new BitmapFont(Gdx.files.internal(Constants.getPath(Constants.FONT_DEFAULT_NORMAL)), true);
			defaultNormal.getData().setScale(1.0f);
			defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		}
	}
	
	
	//SOUNDS
	public class AssetSounds {
		public final Sound jump;
		public final Sound bump;
		public final Sound stomp;
		public final Sound coin;
		public final Sound lostLife;
		
		public AssetSounds(AssetManager am) {
			jump = am.get(Constants.getPath("sounds/jump.wav"), Sound.class);
			bump = am.get(Constants.getPath("sounds/bump.wav"), Sound.class);
			stomp = am.get(Constants.getPath("sounds/stomp.wav"), Sound.class);
			coin = am.get(Constants.getPath("sounds/coin.wav"), Sound.class);
			lostLife = am.get(Constants.getPath("sounds/lost_life.wav"), Sound.class);
		}
	}
	
	
	//MUSIC
	public class AssetMusic {
		public final Music groundTheme;

		public AssetMusic(AssetManager am) {
			groundTheme = am.get(Constants.getPath("music/ground_theme.mp3"), Music.class);
		}
	}

	
	// IMAGES
	// Tileset Assets
	public class TilesetAssets {
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
			cloudLeft = atlas.findRegion("cloud_left");

			ground = atlas.findRegion("ground");

			tube = atlas.findRegion("tube");
			tubeTop = atlas.findRegion("tube_top");

		}

	}

	//Characters Assets
	public class CharactersAssets {
		public final AtlasRegion marioStanding;
		public final AtlasRegion marioJumping;

		public final Animation<?> marioWalking;

		public CharactersAssets(TextureAtlas atlas) {
			marioStanding = atlas.findRegion("mario_standing");
			marioJumping = atlas.findRegion("mario_jumping");

			Array<AtlasRegion> regions = null;
			// Animation: Mario walking
			regions = atlas.findRegions("mario_walking");
			marioWalking = new Animation<Object>(1.0f / 15.0f, regions, Animation.PlayMode.LOOP);
		}
	}

	
	//Enemies Assets
	public class EnemiesAssets {
		
		public final Animation<?> goombaWalking;
		public final AtlasRegion goombaCrushed;
		
		public EnemiesAssets(TextureAtlas atlas) {
			
			Array<AtlasRegion> regions = null;
			// Animation: Goomba walking
			regions = atlas.findRegions("goomba");
			goombaWalking = new Animation<Object>(1.0f / 15.0f, regions, Animation.PlayMode.LOOP_PINGPONG);
			
			goombaCrushed = atlas.findRegion("goomba_crushed");
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
