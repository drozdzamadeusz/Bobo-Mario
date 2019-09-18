package com.bobo.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bobo.game.Assets;

public class GameScreen extends AbstractGameScreen {

	SpriteBatch batch;
	
	public GameScreen(DirectedGame game) {
		super(game);
		
		
		batch = new SpriteBatch();
	}

	@Override
	public void render(float delta) {
	
		Gdx.gl.glClearColor(107.0f / 255.0f, 140.0f / 255.0f, 255.0f / 255.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		batch.draw(Assets.instance.tilesetAssets.blockBouns, 1, 1, 300, 300);
		
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public InputProcessor getInputProcessor() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void dispose() {
		Assets.instance.dispose();
		batch.dispose();
	}

}
