package com.bobo.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;
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
	
	Array<TextureRegion> textures;
	
	public void init() {
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		
		textures = new Array<TextureRegion>();
		textures.add(Assets.instance.tilesetAssets.blockBouns);
		textures.add(Assets.instance.tilesetAssets.ground);
		textures.add(Assets.instance.tilesetAssets.blockDisbaled);
		
		renderWorld(batch);
		

		
	}
	
	private void renderWorld(SpriteBatch batch) {
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		
		/*for (int i = 0; i < 20; i++) {
			batch.draw(textures.random(), i, 0, 1, 1);
			batch.draw(textures.random(), -(i+1), 0, 1, 1);
			batch.draw(textures.random(), 0, i+1, 1, 1);
			batch.draw(textures.random(), 0, -(i+1), 1, 1);
		}*/
		worldController.level.render(batch);
		
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
