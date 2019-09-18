package com.bobo.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Level {
	public static final String TAG = Level.class.getCanonicalName();

	public enum BLOCK_TYPE {
		GROUND(156, 74, 0); // ground
		
		
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
	
	private void init(String filename) {
		
	}

	public void update(float deltaTime) {
	
	}

	public void render(SpriteBatch batch) {
		
	}
	
}
