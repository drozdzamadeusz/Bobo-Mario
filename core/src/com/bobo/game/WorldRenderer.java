package com.bobo.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.bobo.utils.Constants;

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
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		
		renderWorld(batch);
		
	}
	
	private void renderWorld(SpriteBatch batch) {
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		batch.draw(Assets.instance.tilesetAssets.blockTop, 0, 0, 1, 1);
		
		batch.end();
	}

	public void render() {
		renderWorld(batch);
	}
	
	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / (float) height) * (float) width;
		camera.update();
	}
	
	
	
	@Override
	public void dispose() {
		batch.dispose();
	}

}
