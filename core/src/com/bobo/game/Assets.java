package com.bobo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
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
	public ObjectsAssets objectsAssets;
	
	public AssetFonts fonts;

	public void init(AssetManager assetManager) {

		this.assetManager = assetManager;
		assetManager.setErrorListener(this);

		assetManager.load(Constants.getPath(Constants.TILESET_TEXTURE_ATLAS_OBJECTS), TextureAtlas.class);
		assetManager.load(Constants.getPath(Constants.CHARACTERS_TEXTURE_ATLAS_OBJECTS), TextureAtlas.class);
		assetManager.load(Constants.getPath(Constants.ENEMIES_TEXTURE_ATLAS_OBJECTS), TextureAtlas.class);
		assetManager.load(Constants.getPath(Constants.OBJECTS_TEXTURE_ATLAS_OBJECTS), TextureAtlas.class);
		
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
		TextureAtlas objectsAtlas = assetManager.get(Constants.getPath(Constants.OBJECTS_TEXTURE_ATLAS_OBJECTS));
		
		tilesetAssets = new TilesetAssets(tilesetAtlas);
		charactersAssets = new CharactersAssets(charactersAtlas);
		enemiesAssets = new EnemiesAssets(enemiesAtlas);
		objectsAssets = new ObjectsAssets(objectsAtlas);
		
		fonts = new AssetFonts();

		sounds = new AssetSounds(assetManager);
		music = new AssetMusic(assetManager);
	}

	
	//FONTS
	public class AssetFonts {
		public final BitmapFont defaultNormal;

		public AssetFonts() {
			defaultNormal = new BitmapFont(Gdx.files.internal(Constants.getPath(Constants.FONT_DEFAULT_NORMAL)), true);
			defaultNormal.getData().setScale(2.0f);
			//defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
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
		public final AtlasRegion marioDied;
		
		
		public final Animation<?> marioWalking;

		public CharactersAssets(TextureAtlas atlas) {
			marioStanding = atlas.findRegion("mario_standing");
			marioJumping = atlas.findRegion("mario_jumping");
			marioDied = atlas.findRegion("mario_died");
			
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
		
		public final Animation<?> koopaTroopaWalking;
		public final Animation<?> koopaTroopaCrushedReborn;
		//public final AtlasRegion koopaTroopaCrushed;
		
		public EnemiesAssets(TextureAtlas atlas) {
			
			Array<AtlasRegion> regions = null;
			// Animation: Goomba walking
			regions = atlas.findRegions("goomba");
			goombaWalking = new Animation<Object>(1.0f / 15.0f, regions, Animation.PlayMode.LOOP_PINGPONG);
			
			goombaCrushed = atlas.findRegion("goomba_crushed");
			
			// Animation: Koopa Troopa walking
			regions = atlas.findRegions("koopa_troopa");
			koopaTroopaWalking = new Animation<Object>(1.0f / 15.0f, regions, Animation.PlayMode.LOOP_PINGPONG);
			
			// Animation: Koopa Troopa crushed reborn
			regions = atlas.findRegions("koopa_troopa_crushed");
			koopaTroopaCrushedReborn = new Animation<Object>(1.0f / 15.0f, regions, Animation.PlayMode.LOOP_PINGPONG);
			
			//koopaTroopaCrushed = atlas.findRegion("koopa_troopa_crushed_01");
		}
	}
	
	//Items, Objects and NPCs
	public class ObjectsAssets {
		
		public final Animation<?> goldCoin;
		public final Animation<?> bonusBlock;
		public final AtlasRegion growthMushroom;
		
		public ObjectsAssets(TextureAtlas atlas) {
			
			Array<AtlasRegion> regions = null;
			
			// Animation: Coin turns
			regions = atlas.findRegions("coin");
			goldCoin = new Animation<Object>(1.0f / 16.0f, regions, Animation.PlayMode.LOOP);
			
			Array<AtlasRegion> bonusBlockRegions = atlas.findRegions("bonus_block");
			
			Gdx.app.debug(TAG, bonusBlockRegions.size+"");
			
			// Animation: Coin turns
			regions = new Array<AtlasRegion>(4);
			regions.add(bonusBlockRegions.get(0));
			regions.add(bonusBlockRegions.get(0), bonusBlockRegions.get(1), bonusBlockRegions.get(2), bonusBlockRegions.get(1));
			bonusBlock = new Animation<Object>(1.0f / 7.5f, regions, Animation.PlayMode.LOOP);
			
			// Growth Mushroom
			growthMushroom = atlas.findRegion("growth_mushroom");
			
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
