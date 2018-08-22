package com.bow.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.bow.game.BowGame;
import com.bow.game.model.Arrow;
import com.bow.game.model.Background;
import com.bow.game.model.Blood;
import com.bow.game.model.Bow;
import com.bow.game.model.Button;
import com.bow.game.model.Wall;
import com.bow.game.model.Zombie;
import com.bow.game.utils.GUI;
import com.bow.game.view.GameScreen;

import java.util.ArrayList;
import java.util.Random;

public class LevelController {

    public static Random random;
    private BowGame game;

    private Background background;
    private ArrayList<Zombie> zombies;
    private ArrayList<Zombie> zombiesInStash;
    private Bow bow;
    private ArrayList<Arrow> arrows;
    private Wall wall;
    private ArrayList<Blood> bloodMap;
    private Button pauseButton;

    private Sound hitSound;
    private Sound bricksSound;
    private Sound shootSound;
    public Music music;

    private TextureAtlas textureAtlas;
    private TextureAtlas HPtextureAtlas;
    private GUI gui;

    private float spawnInterval;
    private float spawnRate;
    private float time;

    private float width = GameScreen.cameraWidth;
    private float height = width * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

    public LevelController(BowGame game, TextureAtlas textureAtlas, TextureAtlas HPtextueAtlas, GUI gui, int zombieCount, float zombieHp) {

        this.game = game;

        random = new Random();
        this.gui = gui;
        this.HPtextureAtlas = HPtextueAtlas;
        this.textureAtlas = textureAtlas;

        spawnInterval = 100f;
        spawnRate = 25f;
        time = 99f;

        this.zombies = new ArrayList<Zombie>();
        this.zombiesInStash = new ArrayList<Zombie>();
        this.bloodMap = new ArrayList<Blood>();
        this.arrows = new ArrayList<Arrow>();
        Arrow arrowInBow = new Arrow(textureAtlas.findRegion("arrow"),
                2.25f, -height / 2, 0.5f, 0.5f * 5.6153f);
        bow = new Bow(textureAtlas.findRegion("bow"), arrowInBow,
                0f, -height / 2, 5f, 5f * 0.3255f);
        for (int i = 0; i < zombieCount; i++)
            zombiesInStash.add(new Zombie(textureAtlas.findRegion("zombie"), HPtextueAtlas,
                -width, height / 2, 3f, 3f * 1.12f, zombieHp));
        wall = new Wall(textureAtlas.findRegion("wall1"),
                -width / 2, 4f -height / 2, 2f * 28.96f, 2f);
        pauseButton = new Button(textureAtlas.findRegion("pauseButton"),
                width / 2 - 2f * 1.275f - 0.5f, height / 2 - 2.5f, 2f * 1.275f, 2f);


        background = new Background(textureAtlas.findRegion("grass"),
                -width / 2, -height / 2, height * 1f, height);

        hitSound = Gdx.audio.newSound(Gdx.files.internal("hit.ogg"));
        shootSound = Gdx.audio.newSound(Gdx.files.internal("shoot.ogg"));
        bricksSound = Gdx.audio.newSound(Gdx.files.internal("bricks.ogg"));
        music = Gdx.audio.newMusic(Gdx.files.internal("bensound-instinct.mp3"));
        music.setLooping(true);
        music.setVolume(0.18f);
        if (game.isMusicAllowed()) {
            music.play();
        }
    }


    public void handle() {
        for (Arrow arrow : arrows) arrow.handle();
        for (Zombie zombie : zombies) {
            zombie.handle();
            zombie.update(HPtextureAtlas);
        }
        bow.handle();
        wall.handle();
        pauseButton.handle();

        for (Arrow arrow : arrows) {
            if (arrow.isReadyToDelete() || arrow.getY() > height / 2) {
                arrows.remove(arrow);
                break;
            }
        }

        for (Zombie zombie : zombies) {
            if (Intersector.overlapConvexPolygons(zombie.getBounds(), wall.getBounds())) {
                zombies.remove(zombie);
                if (game.isSoundsAllowed()) bricksSound.play(0.6f);
                if (wall.isBroken()) {
                    gui.setScore(0);
                    wall.repair(textureAtlas.findRegion("wall1"));
                    spawnRate = 25f;
                    zombies.clear();
                    bloodMap.clear();
                }
                else {
                    wall.brake(textureAtlas.findRegion("wall2"));
                }
                break;
            }
            if (zombie.getHealthPoints() <= 0) {
                zombies.remove(zombie);
                gui.addScore(1);
                break;
            }
        }
        time += GameScreen.deltaCff * spawnRate;
        spawnRate += GameScreen.deltaCff * 0.02f;
        if (time > spawnInterval) {
            for (int i = 0; i < 2; i++) {
                zombies.add(zombiesInStash.get(zombiesInStash.size() - 1));
                zombiesInStash.remove(zombiesInStash.size() - 1);
                zombies.get(zombies.size() - 1).spawn(width, random);
            }
            time = 0;
        }

        if (Gdx.input.isTouched()) {
            float x = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            float y = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;

            if (pauseButton.getBounds().contains(x, y)) {
                pauseButton.setToggled(true);
            }
            else {
                bow.setPosition(-width / 2 - bow.getWidth() / 2 + width * Gdx.input.getX() / Gdx.graphics.getWidth(), bow.getY());
                if (bow.isLoaded()) {
                    arrows.add(new Arrow(bow.getArrow().getSprite(),
                            bow.getArrow().getX(), bow.getArrow().getY(), bow.getArrow().getWidth(), bow.getArrow().getHeight()));
                    arrows.get(arrows.size() - 1).shoot();
                    bow.shoot();
                    if (game.isSoundsAllowed()) shootSound.play(0.09f);
                }
            }
        }
        else {
            if (pauseButton.isToggled()) {
                pauseButton.setToggled(false);
                game.gameScreen.pause();
                game.setScreen(game.menuScreen);
            }
        }
        for (Arrow arrow : arrows) {
            for (Zombie zombie : zombies) {
                if (Intersector.overlapConvexPolygons(zombie.getBounds(), arrow.getBounds())) {
                    bloodMap.add(new Blood(textureAtlas.findRegion("blood"),
                            zombie.getX() - 0.5f + random.nextFloat(), zombie.getY(), zombie.getWidth(), zombie.getWidth()));
                    if (zombie.healthBar.getHealth() > 66 && zombie.healthBar.getHealth() <= 66 + arrow.getDamage()) {
                        zombie.setSprite(textureAtlas.findRegion("zombie66"));
                    }
                    else if (zombie.healthBar.getHealth() > 33 && zombie.healthBar.getHealth() <= 33 + arrow.getDamage()) {
                        zombie.setSprite(textureAtlas.findRegion("zombie33"));
                    }
                    zombie.repel(arrow.getRepelDist());
                    zombie.damage(arrow.getDamage());
                    if (game.isSoundsAllowed()) hitSound.play(0.8f);
                    arrow.delete();
                }
            }
        }
        if (bloodMap.size() > 200) {
            bloodMap.remove(0);
        }
    }

    public void draw(SpriteBatch batch) {
        background.draw(batch);
        for (Blood blood : bloodMap) blood.draw(batch);
        for (Zombie zombie : zombies) zombie.draw(batch);
        wall.draw(batch);
        bow.draw(batch);
        for (Arrow arrow : arrows) arrow.draw(batch);
        pauseButton.draw(batch);
    }

    public void dispose() {
        game.dispose();
        gui.dispose();
        HPtextureAtlas.dispose();
        textureAtlas.dispose();

        hitSound.dispose();
        shootSound.dispose();
        bricksSound.dispose();
        music.dispose();
    }
}
