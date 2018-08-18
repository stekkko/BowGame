package com.bow.game.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.bow.game.control.ArrowController;
import com.bow.game.control.BowController;
import com.bow.game.control.ZombieController;
import com.bow.game.model.Arrow;
import com.bow.game.model.Background;
import com.bow.game.model.Blood;
import com.bow.game.model.Bow;
import com.bow.game.model.Zombie;
import com.bow.game.utils.GUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreen implements Screen {
    private TextureAtlas textureAtlas;
    private SpriteBatch batch;
    private Bow bow;
    private Arrow arrow;
    private Zombie zombie;
    private Zombie zombie1;
    private Zombie zombie2;
    private List<Blood> bloodMap;
    private GUI gui;

    private Background background;

    private ZombieController zombieController;
    private ZombieController zombieController1;
    private ZombieController zombieController2;
    private ArrowController arrowController;
    private BowController bowController;

    private OrthographicCamera camera;

    public final float PIX_TO_METERS = 20f;
    public static float deltaCff;
    public static Random random;

    @Override
    public void show() {
        batch = new SpriteBatch();
        gui = new GUI();
        random = new Random();
        float aspect = (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
        bow = new Bow(textureAtlas.findRegion("bow"),
                0f, -PIX_TO_METERS * aspect / 2, 5f, 5f * 0.3255f);
        bow.setSpeedX(6f);
        arrow = new Arrow(textureAtlas.findRegion("arrow"),
                2.25f, -PIX_TO_METERS * aspect / 2, 0.5f, 0.5f * 5.6153f);

        zombie = new Zombie(textureAtlas.findRegion("zombie"),
                0f, PIX_TO_METERS * aspect / 2, 4f, 4f * 1.12f, 100f);
        zombie1 = new Zombie(textureAtlas.findRegion("zombie"),
                2f, PIX_TO_METERS * aspect / 2, 4f, 4f * 1.12f, 100f);
        zombie2 = new Zombie(textureAtlas.findRegion("zombie"),
                -2f, PIX_TO_METERS * aspect / 2, 4f, 4f * 1.12f, 100f);
        zombie.setSpeedY(-5f + random.nextFloat() * 2);
        zombie1.setSpeedY(-5f + random.nextFloat() * 2);
        zombie2.setSpeedY(-5f + random.nextFloat() * 2);

        bloodMap = new ArrayList<Blood>();
        background = new Background(textureAtlas.findRegion("grass"),
                -PIX_TO_METERS / 2, -PIX_TO_METERS * aspect / 2, PIX_TO_METERS * aspect, PIX_TO_METERS * aspect);

        zombieController = new ZombieController(PIX_TO_METERS, zombie);
        zombieController1 = new ZombieController(PIX_TO_METERS, zombie1);
        zombieController2 = new ZombieController(PIX_TO_METERS, zombie2);
        arrowController = new ArrowController(PIX_TO_METERS, arrow);
        bowController = new BowController(PIX_TO_METERS, bow, arrow);
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void handle() {
        if (arrow.isShooted && Intersector.overlapConvexPolygons(zombie.getBounds(), arrow.getBounds())) {
            bloodMap.add(new Blood(textureAtlas.findRegion("blood"), zombie.getX(), zombie.getY(), zombie.getWidth(), zombie.getWidth()));
            zombie.respawn((float) Gdx.graphics.getWidth() / PIX_TO_METERS, random);
            zombie.setSpeedY(-5f + random.nextFloat() * 2);
            gui.addScore(1);
        }
        if (arrow.isShooted && Intersector.overlapConvexPolygons(zombie1.getBounds(), arrow.getBounds())) {
            bloodMap.add(new Blood(textureAtlas.findRegion("blood"), zombie1.getX(), zombie1.getY(), zombie1.getWidth(), zombie1.getWidth()));
            zombie1.respawn((float) Gdx.graphics.getWidth() / PIX_TO_METERS, random);
            zombie1.setSpeedY(-5f + random.nextFloat() * 2);
            gui.addScore(1);
        }
        if (arrow.isShooted && Intersector.overlapConvexPolygons(zombie2.getBounds(), arrow.getBounds())) {
            bloodMap.add(new Blood(textureAtlas.findRegion("blood"), zombie2.getX(), zombie2.getY(), zombie2.getWidth(), zombie2.getWidth()));
            zombie2.respawn((float) Gdx.graphics.getWidth() / PIX_TO_METERS, random);
            zombie2.setSpeedY(-5f + random.nextFloat() * 2);

            gui.addScore(1);
        }

        zombieController.handle();
        zombieController1.handle();
        zombieController2.handle();
        arrowController.handle();
        bowController.handle();
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
        background.draw(batch);
        for (Blood blood : bloodMap) {
            blood.draw(batch);
        }
        zombie.draw(batch);
        zombie1.draw(batch);
        zombie2.draw(batch);
        bow.draw(batch);
        arrow.draw(batch);

        batch.end();
        gui.draw();
    }

    @Override
    public void resize(int width, int height) {
        float aspectRatio = (float) height / width;
        camera = new OrthographicCamera(PIX_TO_METERS, PIX_TO_METERS * aspectRatio);
        camera.update();
    }

    public void setTextureAtlas(TextureAtlas textureAtlas) {
        this.textureAtlas = textureAtlas;
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
        batch.dispose();
        textureAtlas.dispose();
    }
}
