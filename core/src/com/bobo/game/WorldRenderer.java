package com.bobo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
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
	
    Texture colorTable;

    private ShaderProgram shader;
    private String shaderVertIndexPalette, shaderFragIndexPalette;
    
	private float paletteIndex;
	private int currentPalette;
	
	
    public void createTestShader() {
        colorTable =  new Texture(Constants.getPath("worlds/colortables/world_1_1.png"));

        currentPalette = 1;
        paletteIndex = (currentPalette + 0.5f) / colorTable.getHeight();
        
        shaderVertIndexPalette = Gdx.files.internal(Constants.getPath("shaders/colorpalette.vert")).readString();
        shaderFragIndexPalette = Gdx.files.internal(Constants.getPath("shaders/colorpalette.frag")).readString();

        ShaderProgram.pedantic = false;

        shader = new ShaderProgram(shaderVertIndexPalette, shaderFragIndexPalette);
        if(!shader.isCompiled()) {
            System.out.println("Shader nie dziala");
        }
        else{
        	batch.setShader(shader);
            System.out.println("Shader dziala");
        }
    }
	
	
	public void init() {
		
		batch = new SpriteBatch();
		createTestShader();
		
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		
		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		cameraGUI.setToOrtho(true);
		// flip y-axis
		cameraGUI.update();
		
		
	}
	
	
	public void render() {
		renderWorld(batch);
		renderGui(batch);
	}
	
	private void renderWorld(SpriteBatch batch) {
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);


        colorTable.bind(1);

        //Must return active texture unit to default of 0 before batch.end 
        //because SpriteBatch does not automatically do this. It will bind the
        //sprite's texture to whatever the current active unit is, and assumes
        //the active unit is 0
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0); 
		
		batch.begin();
		
        shader.setUniformi("colorTable", 1);
        shader.setUniformf("paletteIndex", paletteIndex);
        
		worldController.level.render(batch);
		
		batch.end();
	}


	private void renderGui(SpriteBatch batch) {
		batch.setProjectionMatrix(cameraGUI.combined);
		ShaderProgram s = batch.getShader();
		batch.setShader(null);
		
		batch.begin();
		
		renderGuiFpsCounter(batch);
		
		batch.end();
		batch.setShader(s);
		
	}
	
	
	private void renderGuiFpsCounter(SpriteBatch batch) {
		float x = cameraGUI.viewportWidth - 185f;
		float y = 10f;
		int fps = Gdx.graphics.getFramesPerSecond();
		BitmapFont fpsFont = Assets.instance.fonts.defaultNormal;
		fpsFont.setColor(1, 1, 1, 1); // white
		fpsFont.draw(batch, "FPS: " + fps, x, y);		
	}
	
	
	public void resize(int width, int height) {
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / (float) height) * (float) width;
		camera.update();
		
		cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
		cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float) height) * (float) width;
		cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);
		cameraGUI.update();
	}
	
	
	
	@Override
	public void dispose() {
		batch.dispose();
	}

}
