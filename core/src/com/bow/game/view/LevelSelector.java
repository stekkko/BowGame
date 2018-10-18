package com.bow.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.bow.game.BowGame;
import com.bow.game.control.LevelSelectorController;
import com.bow.game.control.MainMenuController;
import com.bow.game.control.PauseMenuController;
import com.bow.game.utils.Assets;

public class LevelSelector implements Screen {

    private BowGame game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private LevelSelectorController levelSelectorController;

    private boolean paused;

    public static final float cameraWidth = 20f;
    public static float deltaCff;

    public LevelSelector(BowGame game, Assets assets) {
        this.game = game;
        levelSelectorController = new LevelSelectorController(game, assets);
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        paused = false;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        paused = false;
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        deltaCff = delta;

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if (!paused) levelSelectorController.handle();
        levelSelectorController.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) height / width;
        camera = new OrthographicCamera(cameraWidth, cameraWidth * aspectRatio);
        camera.update();
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void hide() {
        pause();
    }

    @Override
    public void dispose() {
        batch.dispose();
        game.dispose();
        levelSelectorController.dispose();
    }
}
