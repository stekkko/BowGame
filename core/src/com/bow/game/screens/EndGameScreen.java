package com.bow.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.bow.game.BowGame;
import com.bow.game.control.EndLevelController;

public class EndGameScreen implements Screen {
    private BowGame game;
    private EndLevelController endLevelController;

    private boolean paused;

    private OrthographicCamera camera;

    public static final float cameraWidth = 20f;

    public EndGameScreen(BowGame game) {
        this.game = game;
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        paused = true;
    }

    @Override
    public void show() {
        endLevelController = new EndLevelController(game);
        paused = false;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.4f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        game.batch.setProjectionMatrix(camera.combined);

        if (!paused) endLevelController.handle(delta);
        game.batch.begin();
        endLevelController.draw(game.batch);
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
        paused = true;
    }

    @Override
    public void dispose() {
        endLevelController.dispose();
        game.dispose();
    }
}
