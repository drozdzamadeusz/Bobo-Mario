package com.bobo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.bobo.objects.AbstractGameObject;
import com.bobo.objects.Ground;
import com.bobo.objects.characters.Player;
import com.bobo.objects.enemies.Goomba;

public class Level {
	public static final String TAG = Level.class.getCanonicalName();

	public enum BLOCK_TYPE {
		EMPTY(0, 0, 0), // black
		PLAYER(255, 255, 255), // white - player
		GROUND(155, 74, 0), // brown - ground
		GOOMBA(92, 44, 7); // dark brown - ground
		
		
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
	
	public Level(String filename) {
		init(filename);
	}
	
	public Array<AbstractGameObject> gorundBlocks;
	public Array<AbstractGameObject> goombas;
	
	public AbstractGameObject mario;
	
	private void init(String filename) {
		Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
		
		int lastPixel = -1;
		
		gorundBlocks = new Array<AbstractGameObject>();
		goombas = new Array<AbstractGameObject>();
		
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
						
					offsetHeight = 0.0f;
					obj.position.set(pixelX, baseHeight * obj.dimension.y + offsetHeight);
					
					gorundBlocks.add((Ground) obj);
				// palyer
				}else if (BLOCK_TYPE.PLAYER.sameColor(currentPixel)) {
					obj = new Player();
					
					offsetHeight = 0.0f;
					obj.position.set(pixelX, baseHeight * obj.dimension.y + offsetHeight);
					
					mario = (Player) obj;
				}else if (BLOCK_TYPE.GOOMBA.sameColor(currentPixel)) {
					obj = new Goomba();
					
					offsetHeight = 0.0f;
					obj.position.set(pixelX, baseHeight * obj.dimension.y + offsetHeight);
					
					goombas.add((Goomba) obj);
				} else {
					int r = 0xff & (currentPixel >>> 24);
					int g = 0xff & (currentPixel >>> 16);
					int b = 0xff & (currentPixel >>> 8);
					int a = 0xff & currentPixel;
					Gdx.app.error(TAG, "Unknown object at x<" + pixelX + "> y<" + pixelY + ">: r<" + r + "> g<" + g
							+ "> b<" + b + "> a<" + a + ">");
				}
				lastPixel = currentPixel;
			}
		}


		// free memory
		pixmap.dispose();
	}

	public void update(float deltaTime) {
		mario.update(deltaTime);
		
		for (AbstractGameObject goomba : goombas) {
			goomba.update(deltaTime);
		}
	}

	public void render(SpriteBatch batch) {
		mario.render(batch);
		
		for (AbstractGameObject ground : gorundBlocks) {
			ground.render(batch);
		}
		
		
		for (AbstractGameObject goomba : goombas) {
			goomba.render(batch);
		}
	}
	
}
