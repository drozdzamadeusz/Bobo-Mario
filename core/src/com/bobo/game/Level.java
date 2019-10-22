package com.bobo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bobo.bonuses.AbstractGameBonus;
import com.bobo.bonuses.CoinsBonus;
import com.bobo.bonuses.GoldCoinAnim;
import com.bobo.bonuses.GrowthMushroomBonus;
import com.bobo.objects.AbstractGameObject;
import com.bobo.objects.BlockGeneric;
import com.bobo.objects.BonusBlock;
import com.bobo.objects.Ground;
import com.bobo.objects.characters.Player;
import com.bobo.objects.enemies.Goomba;
import com.bobo.objects.enemies.KoopaTroopa;
import com.bobo.utils.CameraHelper;
import com.bobo.utils.Constants;

public class Level {
	public static final String TAG = Level.class.getCanonicalName();

	public enum BLOCK_TYPE {
		EMPTY(0, 0, 0), // black
		PLAYER(255, 255, 255), // white - player
		GROUND(155, 74, 0), // brown - ground
		BLOCK(185, 122, 87), // light brown - block
		GOOMBA(92, 44, 7), // dark brown - goomba
		KOOPA_TROOPA(30, 132, 0), // green - koopa troopa
		BONUS_BLOCK(255, 255, 0), //yellow - bous block
		BONUS_COINS_200(248, 242, 216),
		BONUS_GROWTH_MUSHROOM(255, 174, 174);
		
		private int color;

		private BLOCK_TYPE(int r, int g, int b) {
			color = r << 24 | g << 16 | b << 8 | 0xff;
		}

		public boolean sameColor(int color) {
			return this.color == color;
		}

		public int getColor() {
			return color;
		}
	}
	
	private CameraHelper cameraHelper;

	
	public Level(String filename, CameraHelper cameraHelper) {
		this.cameraHelper = cameraHelper;
		init(filename);
	}
	
	public Level(String filename) {
		init(filename);
	}
	
	public Array<AbstractGameObject> allGameObjects;
	
	public Array<AbstractGameObject> gorundBlocks;
	public Array<AbstractGameObject> goombas;
	public Array<AbstractGameObject> koopaTroopas;
	
	public Array<AbstractGameObject> bonuses;
	
	public AbstractGameObject mario;
	
	private void init(String filename) {
			
		Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
		
		int lastPixel = -1;
		
		allGameObjects = new Array<AbstractGameObject>();
		
		gorundBlocks = new Array<AbstractGameObject>();
		goombas = new Array<AbstractGameObject>();
		koopaTroopas = new Array<AbstractGameObject>();
		bonuses = new Array<AbstractGameObject>();
		
		for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
			
			for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {
				
				AbstractGameObject obj = null;
				float offsetHeight = 0;
				
				float baseHeight = pixmap.getHeight() - pixelY - (pixmap.getHeight()/2.0f);
				int currentPixel = pixmap.getPixel(pixelX, pixelY);

				// empty space
				if (BLOCK_TYPE.EMPTY.sameColor(currentPixel)) {
					// do nothing
				}
				
				// ground
				else if (BLOCK_TYPE.GROUND.sameColor(currentPixel)) {
					obj = new Ground();
						
					obj.setPosition(pixelX, baseHeight + offsetHeight);
					((Ground)obj).originPosition = new Vector2(obj.position);
					
					gorundBlocks.add((Ground) obj);
				// block
				}else if (BLOCK_TYPE.BLOCK.sameColor(currentPixel)) {
					obj = new BlockGeneric();
							
					obj.setPosition(pixelX, baseHeight + offsetHeight);
					((BlockGeneric)obj).originPosition = new Vector2(obj.position);
					
					gorundBlocks.add((BlockGeneric) obj);
					// block
				}else if (BLOCK_TYPE.BONUS_BLOCK.sameColor(currentPixel)) {
					obj = new BonusBlock();
					
					obj.setPosition(pixelX, baseHeight + offsetHeight);
					((BlockGeneric)obj).originPosition = new Vector2(obj.position);
					
					gorundBlocks.add((BlockGeneric) obj);
				// palyer
				}else if (BLOCK_TYPE.PLAYER.sameColor(currentPixel)) {
					obj = new Player();
					
					obj.setPosition(pixelX, baseHeight + offsetHeight);
					
					mario = (Player) obj;
				}else if (BLOCK_TYPE.GOOMBA.sameColor(currentPixel)) {
					obj = new Goomba();
					
					obj.setPosition(pixelX, baseHeight + offsetHeight);
					
					goombas.add((Goomba) obj);
				}else if (BLOCK_TYPE.KOOPA_TROOPA.sameColor(currentPixel)) {
					obj = new KoopaTroopa();					
					
					obj.setPosition(pixelX, baseHeight + offsetHeight);
					
					koopaTroopas.add((KoopaTroopa) obj);
				} else if (BLOCK_TYPE.BONUS_COINS_200.sameColor(currentPixel)) {
					
					obj = addBonusToUpperBlock(new GoldCoinAnim(), pixelX, baseHeight);
					obj = addBonusToUpperBlock(new CoinsBonus(), pixelX, baseHeight);
					
					bonuses.add(obj);
				} else if (BLOCK_TYPE.BONUS_GROWTH_MUSHROOM.sameColor(currentPixel)) {
					obj = addBonusToUpperBlock(new GrowthMushroomBonus(), pixelX, baseHeight);
					bonuses.add(obj);
				} else {
					int r = 0xff & (currentPixel >>> 24);
					int g = 0xff & (currentPixel >>> 16);
					int b = 0xff & (currentPixel >>> 8);
					int a = 0xff & currentPixel;
					Gdx.app.error(TAG, "Unknown object at x<" + pixelX + "> y<" + pixelY + ">: r<" + r + "> g<" + g
							+ "> b<" + b + "> a<" + a + ">");
				}
				if(obj != null) allGameObjects.add(obj);
				lastPixel = currentPixel;
			}
		}


		// free memory
		pixmap.dispose();
	}

	private AbstractGameObject addBonusToUpperBlock(AbstractGameBonus bonus, int pixelX, float baseHeight) {
		AbstractGameObject upperBlock = null;
		for(AbstractGameObject o:allGameObjects) {
			if(o.position.x == pixelX && o.position.y == (baseHeight + 1)) {
				upperBlock = o;
				break;
			}
		}
		Gdx.app.debug(TAG, upperBlock.getClass().getCanonicalName());
		
		if(upperBlock != null) {
			((AbstractGameBonus) bonus).parent = upperBlock;
			bonus.init();
			upperBlock.bonus = new Array<AbstractGameBonus>(1);
			upperBlock.bonus.add((AbstractGameBonus) bonus);
		}
		return bonus;
	}
	
	
	public boolean objectInViewPort(AbstractGameObject obj) {
		//return true;
		return (obj.position.x > (cameraHelper.getPosition().x - Constants.VIEWPORT_WIDTH) && obj.position.x < cameraHelper.getPosition().x + Constants.VIEWPORT_WIDTH/2.0f);
	}

	public void update(float deltaTime) {
		mario.update(deltaTime);
		
		for (AbstractGameObject goomba : goombas) {
			if(objectInViewPort(goomba)) goomba.update(deltaTime);
		}
		
		for (AbstractGameObject koopa : koopaTroopas) {
			if(objectInViewPort(koopa)) koopa.update(deltaTime);
		}
		
		for (AbstractGameObject ground : gorundBlocks) {
			if(objectInViewPort(ground)) ground.update(deltaTime);
		}
		
		for (AbstractGameObject bonus : bonuses) {
			if(objectInViewPort(bonus)) bonus.update(deltaTime);
		}
	}

	public void render(SpriteBatch batch) {
		
		
		for(AbstractGameObject bonus:bonuses) {
			bonus.render(batch);
		}
		
		for (AbstractGameObject ground : gorundBlocks) {
			//if(objectInViewPort(ground))
				ground.render(batch);
		}
		
		
		for (AbstractGameObject koopa : koopaTroopas) {
			//if(objectInViewPort(koopa))
				koopa.render(batch);
		}
		
		
		for (AbstractGameObject goomba : goombas) {
//			/if(objectInViewPort(goomba))
				goomba.render(batch);
		}
		

		mario.render(batch);
	}
	
}
