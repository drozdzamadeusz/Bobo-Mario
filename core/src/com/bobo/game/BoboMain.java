package com.bobo.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.bobo.screens.DirectedGame;
import com.bobo.screens.GameMenu;
import com.bobo.screens.GameScreen;
import com.bobo.utils.AudioManager;

public class BoboMain extends DirectedGame {

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		setScreen(new GameMenu(this));
	}
	
}
