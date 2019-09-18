package com.bobo.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class WorldRenderer implements Disposable {

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private WorldController worldController;

	private OrthographicCamera cameraGUI;


	public WorldRenderer() {
		// TODO Auto-generated constructor stub
	}

	public WorldRenderer(WorldController worldController) {
		this.worldController = worldController;
		init();
	}
	
	public void init() {
		
	}
	
	public void render() {
	
		
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
