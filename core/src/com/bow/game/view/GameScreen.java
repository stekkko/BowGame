package com.bow.game.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.bow.game.BowGame;
import com.bow.game.control.EndlessLevelController;
import com.bow.game.control.LevelController;
import com.bow.game.control.SurvivalLevelController;

public class GameScreen implements Screen {
    private BowGame game;
    private LevelController levelController;

    private boolean paused;

    private OrthographicCamera camera;

    public static final float cameraWidth = 20f;
    public static float deltaCff;

    public GameScreen(BowGame game) {
        this.game = game;
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        paused = true;
    }

    @Override
    public void show() {
        if (game.getGamemode() == game.SURVIVAL && !(levelController instanceof SurvivalLevelController))
            levelController = new SurvivalLevelController(game);
        else if (game.getGamemode() == game.ENDLESS && !(levelController instanceof  EndlessLevelController))
            levelController = new EndlessLevelController(game);
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        if (game.prefs.getBoolean("musicAllowed", true)) game.assets.playMusic("music", 0.15f);
        paused = false;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.4f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        deltaCff = delta;

        game.batch.setProjectionMatrix(camera.combined);

        if (!paused) levelController.handle();
        game.batch.begin();
        levelController.draw(game.batch);
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
        game.assets.pauseMusic("music");
    }

    @Override
    public void resume() {
        paused = false;
        game.assets.playMusic("music", 0.15f);
    }

    @Override
    public void hide() {
        paused = true;
        game.assets.pauseMusic("music");
    }

    @Override
    public void dispose() {
        levelController.dispose();
        game.dispose();
    }
}
