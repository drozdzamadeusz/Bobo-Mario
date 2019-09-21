package com.bobo.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.bobo.screens.DirectedGame;
import com.bobo.screens.GameScreen;
import com.bobo.utils.AudioManager;

public class BoboMain extends DirectedGame {

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Assets.instance.init(new AssetManager());
		
		AudioManager.instance.play(Assets.instance.music.groundTheme);
		
		setScreen(new GameScreen(this));
	}
	
}
