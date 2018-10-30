package com.bow.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.bow.game.BowGame;
import com.bow.game.control.EndlessLevelController;
import com.bow.game.control.LevelController;
import com.bow.game.control.SurvivalLevelController;

public class GameScreen implements Screen {
    private final static int PAUSED = 0;
    private final static int RENDERING = 1;
    private final static int HIDDEN = 2;


    private BowGame game;
    private LevelController levelController;

    private int state;

    private OrthographicCamera camera;

    public static final float cameraWidth = 20f;

    public GameScreen(BowGame game) {
        this.game = game;
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        state = HIDDEN;
    }

    @Override
    public void show() {
        if (state == HIDDEN) {
            if (game.getGamemode() == game.SURVIVAL && !(levelController instanceof SurvivalLevelController))
                levelController = new SurvivalLevelController(game);
            else if (game.getGamemode() == game.ENDLESS && !(levelController instanceof EndlessLevelController))
                levelController = new EndlessLevelController(game);
            resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            levelController.init();
        }
        game.assets.playMusic("music", 0.15f);
        state = RENDERING;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.4f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        game.batch.setProjectionMatrix(camera.combined);

        if (state == RENDERING) {
            levelController.handle(delta);
            game.batch.begin();
            levelController.draw(game.batch);
            game.batch.end();
        }
    }


    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) height / width;
        camera = new OrthographicCamera(cameraWidth, cameraWidth * aspectRatio);
        camera.update();
    }

    @Override
    public void pause() {
        state = PAUSED;
        game.assets.pauseMusic();
    }

    @Override
    public void resume() {
        state = RENDERING;
        game.assets.playMusic("music", 0.15f);
    }

    @Override
    public void hide() {
        state = HIDDEN;
    }



    @Override
    public void dispose() {
        levelController.dispose();
        game.dispose();
    }
}
