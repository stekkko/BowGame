package com.bow.game.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.control.LevelController;
import com.bow.game.utils.GUI;

public class GameScreen implements Screen {
    public TextureAtlas textureAtlas;
    private TextureAtlas HPtextureAtlas;
    private SpriteBatch batch;
    public GUI gui;
    private LevelController levelController;

    private OrthographicCamera camera;

    public static final float cameraWidth = 20f;
    public static float deltaCff;

    @Override
    public void show() {
        batch = new SpriteBatch();
        gui = new GUI();
        levelController = new LevelController(textureAtlas, HPtextureAtlas, gui, 500, 100f);
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void handle() {
        levelController.handle();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.4f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        deltaCff = delta;

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        handle();
        levelController.draw(batch);
        batch.end();
        gui.draw();
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) height / width;
        camera = new OrthographicCamera(cameraWidth, cameraWidth * aspectRatio);
        camera.update();
    }

    public void setTextureAtlas(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
    }

    public void setHPTextureAtlas(TextureAtlas textureAtlas) {
        this.HPtextureAtlas = textureAtlas;
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
        levelController.dispose();
        batch.dispose();
        gui.dispose();
        HPtextureAtlas.dispose();
        textureAtlas.dispose();
    }
}
