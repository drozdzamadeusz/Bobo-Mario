package com.bobo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.bobo.objects.AbstractGameObject;
import com.bobo.objects.Ground;

public class Level {
	public static final String TAG = Level.class.getCanonicalName();

	public enum BLOCK_TYPE {
		EMPTY(0, 0, 0), // black
		GROUND(155, 74, 0); // ground
		
		
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
	
	public Array<Ground> gorundBlocks;
	
	private void init(String filename) {
		Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
		
		int lastPixel = -1;
		
		gorundBlocks = new Array<Ground>();
		
		for (int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++) {
			
			for (int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++) {
				
				AbstractGameObject obj = null;
				float offsetHeight = 0;
				
				float baseHeight = pixmap.getHeight() - pixelY;
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
	
	}

	public void render(SpriteBatch batch) {
		for (Ground ground : gorundBlocks) {
			ground.render(batch);
		}
	}
	
}
