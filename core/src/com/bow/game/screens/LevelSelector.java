package com.bow.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.bow.game.BowGame;
import com.bow.game.control.LevelSelectorController;

public class LevelSelector implements Screen {

    private BowGame game;
    private OrthographicCamera camera;
    private LevelSelectorController levelSelectorController;

    private boolean paused;

    public static final float cameraWidth = 20f;

    public LevelSelector(BowGame game) {
        this.game = game;
        levelSelectorController = new LevelSelectorController(game);
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        paused = true;
    }

    @Override
    public void show() {
        paused = false;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        if (!paused) levelSelectorController.handle(delta);
        levelSelectorController.draw(game.batch);
        game.batch.end();
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
        game.dispose();
        levelSelectorController.dispose();
    }
}
