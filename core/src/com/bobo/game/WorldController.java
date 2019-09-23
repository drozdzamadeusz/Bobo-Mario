package com.bobo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.Disposable;
import com.bobo.objects.AbstractGameObject;
import com.bobo.objects.characters.Player;
import com.bobo.objects.enemies.Goomba;
import com.bobo.screens.DirectedGame;
import com.bobo.utils.AudioManager;
import com.bobo.utils.CameraHelper;
import com.bobo.utils.Constants;
import com.bobo.objects.enemies.Goomba;

public class WorldController extends InputAdapter implements Disposable {

	private static final String TAG = WorldController.class.getCanonicalName();

	public CameraHelper cameraHelper;

	private DirectedGame game;

	public Level level;
	
	public CollisionDetection collisionDetection;
	
	public WorldController(DirectedGame game) {
		this.game = game;
		cameraHelper = new CameraHelper();
		init();
	}

	public void init() {
		Gdx.input.setInputProcessor(this);
		
		cameraHelper = new CameraHelper();
		
		initLevel();
		
		collisionDetection = new CollisionDetection();
		collisionDetection.setLevel(level);
		
		cameraHelper.setTarget(level.mario);
	}

	private void initLevel() {
		level = new Level(Constants.getPath(Constants.WORLD_1_1));
	}
	

	
	public void update(float deltaTime) {
		handleInputGame(deltaTime);
		
		level.update(deltaTime);
		
		collisionDetection.detectCollisions();
		
		cameraHelper.update(deltaTime);
		
		if (isGameOver() || isPlayerInWater()) {
			AudioManager.instance.play(Assets.instance.sounds.lostLife);
			init();
		}
		
		startUpdatingObjects();
	}
	
	public boolean isPlayerInWater() {
		return level.mario.position.y < -10;
	}
	

	private boolean isGameOver() {
		return (((Player) level.mario).health > 0)?false:true;
	}
	
	public void startUpdatingObjects() {
		for (AbstractGameObject goomba : level.goombas) {
			if(goomba.position.x > cameraHelper.getPosition().x && goomba.position.x < cameraHelper.getPosition().x + Constants.VIEWPORT_WIDTH/2.0f) {
				Gdx.app.debug(TAG, ""+cameraHelper.getPosition().x );
				((Goomba)goomba).startUpdating = true;
			}
		}
	}

	private void handleInputGame(float deltaTime) {
		if (cameraHelper.hasTarget(level.mario)) {
			
			// Mario Movement
			if (Gdx.input.isKeyPressed(Keys.LEFT)) {
				((Player) level.mario).setWalking(deltaTime, false);
			} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
				((Player) level.mario).setWalking(deltaTime, true);
			} else {
				
			}
			
			// Mario Jump
			if (Gdx.input.isKeyPressed(Keys.SPACE)) {
				((Player) level.mario).setJumping(deltaTime, true);
			} else {
				((Player) level.mario).setJumping(deltaTime, false);
			}
			
			
			if(((Player) level.mario).makeSmallJump){
				((Player) level.mario).setSmallJump(deltaTime);
			}
		}
	}
	

	/*private void handleDebugInput(float deltaTime) {
		
		// Camera Controls (move)
		float camMoveSpeed = 5 * deltaTime;
		float camMoveSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			camMoveSpeed *= camMoveSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			moveCamera(-camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			moveCamera(camMoveSpeed, 0);
		if (Gdx.input.isKeyPressed(Keys.UP))
			moveCamera(0, camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.DOWN))
			moveCamera(0, -camMoveSpeed);
		if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
			cameraHelper.setPosition(0, 0);
		
		// Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if (Gdx.input.isKeyPressed(Keys.COMMA))
			cameraHelper.addZoom(camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.PERIOD))
			cameraHelper.addZoom(-camZoomSpeed);
		if (Gdx.input.isKeyPressed(Keys.SLASH))
			cameraHelper.setZoom(1);
	}

	private void moveCamera(float x, float y) {
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}*/

	@Override
	public void dispose() {

	}

}
