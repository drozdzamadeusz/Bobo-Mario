package com.bobo.screens;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.bobo.game.Assets;

public abstract class AbstractGameScreen implements Screen {

	protected DirectedGame game;

	public AbstractGameScreen(DirectedGame game) {
		this.game = game;
	}

	public abstract void render(float deltaTime);

	public abstract void resize(int width, int height);

	public abstract void show();

	public abstract void hide();

	public abstract void pause();

	@Override
	public void resume() {
		Assets.instance.init(new AssetManager());
	}

	@Override
	public void dispose() {
		Assets.instance.dispose();

	}

	public abstract InputProcessor getInputProcessor();
}
