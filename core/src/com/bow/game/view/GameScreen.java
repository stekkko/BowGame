package com.bow.game.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bow.game.BowGame;
import com.bow.game.control.EndlessLevelController;
import com.bow.game.control.LevelController;
import com.bow.game.control.SurvivalLevelController;
import com.bow.game.utils.Assets;
import com.bow.game.utils.GUI;

public class GameScreen implements Screen {
    private BowGame game;
    private GUI gui;
    Assets assets;
    private SpriteBatch batch;
    private LevelController levelController;

    private boolean paused;

    private OrthographicCamera camera;

    public static final float cameraWidth = 20f;
    public static float deltaCff;

    public GameScreen(BowGame game, Assets assets) {
        this.game = game;
        this.assets = assets;
        gui = new GUI();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        paused = true;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        levelController = (game.getGamemode() == game.ENDLESS ? new EndlessLevelController(game, assets, gui) : new SurvivalLevelController(game, assets, gui));
        if (game.prefs.getBoolean("musicAllowed", true)) levelController.music.play();
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        paused = false;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.4f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        deltaCff = delta;

        batch.setProjectionMatrix(camera.combined);

        if (!paused) levelController.handle();
        batch.begin();
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

    @Override
    public void pause() {
        paused = true;
        levelController.music.pause();
    }

    @Override
    public void resume() {
        levelController.restartGame();
        paused = false;
        assets.playMusic(levelController.music, 0.15f);
    }

    @Override
    public void hide() {
        pause();
    }

    @Override
    public void dispose() {
        levelController.dispose();
        batch.dispose();
        game.dispose();
    }
}
