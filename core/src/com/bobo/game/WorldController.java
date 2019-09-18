package com.bobo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.Disposable;
import com.bobo.screens.DirectedGame;
import com.bobo.utils.CameraHelper;

public class WorldController extends InputAdapter implements Disposable {

	private static final String TAG = WorldController.class.getCanonicalName();

	public CameraHelper cameraHelper;
	
	private DirectedGame game;
	
	public WorldController(DirectedGame game) {
		this.game = game;
		init();
	}

	
	public void init() {
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
	}
	
	public void update(float deltaTime) {
		
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
