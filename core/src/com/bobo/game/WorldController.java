package com.bobo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.bobo.objects.Ground;
import com.bobo.objects.Mario;
import com.bobo.objects.Mario.JUMP_STATE;
import com.bobo.screens.DirectedGame;
import com.bobo.utils.CameraHelper;
import com.bobo.utils.Constants;

public class WorldController extends InputAdapter implements Disposable {

	private static final String TAG = WorldController.class.getCanonicalName();

	public CameraHelper cameraHelper;

	private DirectedGame game;

	public Level level;
	
	public WorldController(DirectedGame game) {
		this.game = game;
		cameraHelper = new CameraHelper();
		init();
	}

	public void init() {
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		initLevel();
		cameraHelper.setTarget(level.mario);
	}

	private void initLevel() {
		level = new Level(Constants.getPath(Constants.WORLD_1_1));
	}
	
	public void update(float deltaTime) {
		//handleDebugInput(deltaTime);
		
		handleInputGame(deltaTime);
		
		level.update(deltaTime);
		
		testCollisions();
		
		cameraHelper.update(deltaTime);
	}
	
	
	// Rectangles for collision detection
	private Rectangle r1 = new Rectangle();
	private Rectangle r2 = new Rectangle();
	
	public void onCollisionMarioWithBlock(Ground ground) {
		Mario mario = level.mario;
		
		float heightDifference = Math.abs(mario.position.y - (ground.position.y + ground.bounds.height));
		float widthDifference = Math.abs(mario.position.x - (ground.position.x));
		
		
		//hit from below
		if (widthDifference < 0.5f && heightDifference > 1.5f) {
			mario.position.y = (ground.position.y - ground.bounds.height);
			
			mario.timeJumping = mario.JUMP_TIME_MAX+1;
			mario.velocity.y = -mario.terminalVelocity.y;
			
			return;	
		}
		
		//hit from right or left side
		if (heightDifference > 0.45f) {
			boolean hitRightEdge = mario.position.x > (ground.position.x + ground.bounds.width / 2.0f);	
		 
			if (hitRightEdge) {
				mario.position.x = ground.position.x + ground.bounds.width;
			} else {
				mario.position.x = ground.position.x - mario.bounds.width;
			}
			
			return;
			
		}
		
		if (widthDifference < 0.91f) 
		
			switch (mario.jumpState) {
				case GROUNDED:
					break;
				case FALLING:
				case JUMP_FALLING:
					mario.position.y = ground.position.y + mario.bounds.height;
					mario.jumpState = JUMP_STATE.GROUNDED;
					break;
				case JUMP_RISING:
					mario.position.y = ground.position.y + mario.bounds.height;
					
			}
	}
	
	private void testCollisions() {
		r1.set(level.mario.position.x, level.mario.position.y, level.mario.bounds.width,
				level.mario.bounds.height);
		
		// Test collision: Mario <-> Blocks
		for (Ground g : level.gorundBlocks) {
			r2.set(g.position.x, g.position.y, g.bounds.width, g.bounds.height);
			
			if (!r1.overlaps(r2))
				continue;
			
			onCollisionMarioWithBlock(g);
			
			// IMPORTANT: must do all collisions for valid
			// edge testing on rocks.
		}
		
	}

	private void handleInputGame(float deltaTime) {
		if (cameraHelper.hasTarget(level.mario)) {
			
			// Mario Movement
			if (Gdx.input.isKeyPressed(Keys.LEFT)) {
				level.mario.setWalking(deltaTime, false);
			} else if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
				level.mario.setWalking(deltaTime, true);
			} else {
				
			}
			
			// Mario Jump
			if (Gdx.input.isKeyPressed(Keys.SPACE)) {
				level.mario.setJumping(deltaTime, true);
			} else {
				level.mario.setJumping(deltaTime, false);
			}
		}
	}
	

	private void handleDebugInput(float deltaTime) {
		
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
	}

	@Override
	public void dispose() {

	}

}
