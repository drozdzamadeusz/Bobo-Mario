package com.bobo.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.bobo.game.WorldController;
import com.bobo.game.WorldRenderer;

public class GameScreen extends AbstractGameScreen {

	private WorldController worldController;
	private WorldRenderer worldRenderer;

	
	private boolean paused;
	
	public GameScreen(DirectedGame game) {
		super(game);
	}

	static int TICKS_PER_SECOND = 25;

	@Override
	public void render(float deltaTime) {
		
		if(!paused) {
			worldController.update(deltaTime);
		}
		
		Gdx.gl.glClearColor(107.0f / 255.0f, 140.0f / 255.0f, 255.0f / 255.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		worldRenderer.render();
	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);

	}

	@Override
	public void show() {
		worldController = new WorldController(game);
		worldRenderer = new WorldRenderer(worldController);
	}

	@Override
	public void hide() {
		worldController.dispose();
		worldRenderer.dispose();

	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void resume() {
		super.resume();
		paused = false;
	}
	
	@Override
	public InputProcessor getInputProcessor() {
		return null;
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}

}
