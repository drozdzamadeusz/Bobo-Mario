package com.bobo.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.bobo.game.Assets;
import com.bobo.transitions.ScreenTransition;
import com.bobo.utils.AudioManager;
import com.bobo.utils.Constants;

public class GameMenu extends AbstractGameScreen{

    public static final String TAG = GameMenu.class.getCanonicalName();

    private Stage stage;

    private Skin skinLibgdx;

    public GameMenu(DirectedGame game) {
        super(game);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(deltaTime);
        stage.draw();
        stage.setDebugAll(false);

        if(Gdx.input.isTouched()){
            onPlayClicked();
        }

    }

    private void rebuildStage(){
        skinLibgdx = new Skin(Gdx.files.internal(Constants.getPath(Constants.SKIN_LIBGDX_UI)),
                new TextureAtlas(Constants.getPath(Constants.TEXTURE_ATLAS_LIBGDX_UI)));

        stage.clear();

        Stack stack = new Stack();
        stack.setSize(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
        stage.addActor(stack);

        Table layerTapToStart = tapToStart();
        stack.add(layerTapToStart);
    }

    private Table tapToStart() {
        Table layer = new Table();
        layer.setSkin(skinLibgdx);
        layer.center().center();
        layer.add("Tap to start");
        return layer;
    }

    private void onPlayClicked() {
        Assets.instance.init(new AssetManager());
        AudioManager.instance.play(Assets.instance.music.groundTheme);
        game.setScreen(new GameScreen(game));
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        stage = new Stage(new StretchViewport(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT));
        rebuildStage();
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public InputProcessor getInputProcessor() {
        return stage;
    }
}
