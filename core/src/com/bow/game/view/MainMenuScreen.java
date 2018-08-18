package com.bow.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bow.game.utils.UI;

public class MainMenuScreen implements Screen {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private UI ui;

    public static float deltaCff;

    @Override
    public void show() {
        batch = new SpriteBatch();
        ui = new UI();

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
        batch.end();
        ui.draw();
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) height  / width;
        camera = new OrthographicCamera(20f, 20f * aspectRatio);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        ui.dispose();
        batch.dispose();
    }
}
