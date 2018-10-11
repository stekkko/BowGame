package com.bow.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.bow.game.BowGame;
import com.bow.game.model.Ammo;
import com.bow.game.model.Arrow;
import com.bow.game.model.Background;
import com.bow.game.model.Blood;
import com.bow.game.model.Weapon;
import com.bow.game.model.mobs.Boss;
import com.bow.game.model.Bow;
import com.bow.game.model.Button;
import com.bow.game.model.mobs.Dog;
import com.bow.game.model.Wall;
import com.bow.game.model.mobs.Zombie;
import com.bow.game.utils.GUI;
import com.bow.game.view.GameScreen;

import java.util.ArrayList;
import java.util.Random;

public class LevelController {

    private static Random random;
    private BowGame game;

    private Background background;
    private ArrayList<Zombie> zombies;
    private ArrayList<Dog> dogs;
    //TODO
    private ArrayList<Zombie> zombiesInStash;
    private ArrayList<Dog> dogsInStash;
    //??
    private Boss boss;
    private Weapon weapon;
    private ArrayList<Ammo> ammos;
    private Wall wall;
    private ArrayList<Blood> bloodMap;
    private Button pauseButton;
    private Background wallFloor;

    private Sound scream;
    private float bossRate;

    private Sound hitSound;
    private Sound bricksSound;
    private Sound shootSound;
    private Sound buttonSound;
    public Music music;

    private TextureAtlas textureAtlas;
    private TextureAtlas HPtextureAtlas;
    private GUI gui;

    private boolean bossFIGHT;

    private float spawnInterval;
    private float spawnRate;
    private float time;

    private float width = GameScreen.cameraWidth;
    private float height = width * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

    public LevelController(BowGame game, TextureAtlas textureAtlas, TextureAtlas HPtextueAtlas, GUI gui) {
        this.game = game;

        random = new Random();
        this.gui = gui;
        this.HPtextureAtlas = HPtextueAtlas;
        this.textureAtlas = textureAtlas;

        init();
    }

    private void init() {
        initParams();
        initObjects();
        initSounds();
    }

    public void handle() {
        if (bossFIGHT)
            handleBossFight();
        else
            handleGame();

        for (Ammo ammo : ammos) ammo.handle();
        weapon.handle();
        wall.handle();
        wall.update(HPtextureAtlas);
        boss.handle();
        boss.update(HPtextureAtlas);
        wallFloor.handle();
        pauseButton.handle();

        for (Ammo ammo : ammos) {
            if (ammo.isReadyToDelete() || ammo.getY() > height / 2) {
                ammos.remove(ammo);
                break;
            }
        }
        if (Gdx.input.isTouched()) {
            float x = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            float y = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;

            if (pauseButton.getBounds().contains(x, y)) {
                pauseButton.setToggled(true);
            }
            else {
                weapon.setPosition(-width / 2 - weapon.getWidth() / 2 + width * Gdx.input.getX() / Gdx.graphics.getWidth(), weapon.getY());
                if (weapon.isLoaded()) {
                    ammos.add(new Arrow(weapon.getAmmo().getSprite(),
                            weapon.getAmmo().getX(), weapon.getAmmo().getY(), weapon.getAmmo().getWidth(), weapon.getAmmo().getHeight(),
                            34f, 0.05f, 100f, 0.3f, 25f));
                    ammos.get(ammos.size() - 1).shoot();
                    weapon.shoot();
                    if (game.isSoundsAllowed()) shootSound.play(0.09f);
                }
            }
        }
        else {
            if (pauseButton.isToggled()) {
                if (game.isSoundsAllowed()) buttonSound.play();
                pauseButton.setToggled(false);
                game.gameScreen.pause();
                game.setScreen(game.pauseScreen);
            }
        }


    }

    private void handleBossFight() {
        boss.handle();
        boss.update(HPtextureAtlas);
        if (Intersector.overlapConvexPolygons(boss.getBounds(), wall.getBounds())) {
            restartGame();
            return;
        }
        for (Ammo ammo : ammos) {
            if (Intersector.overlapConvexPolygons(ammo.getBounds(), boss.getBounds())) {
                boss.damage(ammo.getDamage());
                if (game.isSoundsAllowed()) hitSound.play(0.8f);
                ammo.delete();
            }
        }

        time += GameScreen.deltaCff * spawnRate * 10f;
        if (time > spawnInterval) {
            bossFIGHT = boss.getPerHealthPoints() > 0;
            time = 0;
        }
        if (!bossFIGHT) {
            gui.addScore(2);
            boss.setPosition(random.nextFloat() * (12f) + (-10f), boss.getY());
            scream.play(1f);
        }
    }

    private void handleGame() {
        for (Dog dog : dogs) {
            dog.handle();
            dog.update(HPtextureAtlas);
        }
        for (Zombie zombie : zombies) {
            zombie.handle();
            zombie.update(HPtextureAtlas);
        }

        for (Zombie zombie : zombies) {
            if (Intersector.overlapConvexPolygons(zombie.getBounds(), wall.getBounds())) {
                zombies.remove(zombie);
                if (game.isSoundsAllowed()) bricksSound.play(0.6f);
                wall.damage(200f);
                wall.handle();
                if (wall.getPerHealthPoints() <= 0) {
                    restartGame();
                }
                else if (wall.getPerHealthPoints() <= 50){
                    wall.brake(textureAtlas.findRegion("wall2"));
                }
                break;
            }
            if (zombie.getPerHealthPoints() <= 0) {
                zombies.remove(zombie);
                gui.addScore(1);
                break;
            }
        }
        for (Dog dog : dogs) {
            if (Intersector.overlapConvexPolygons(dog.getBounds(), wall.getBounds())) {
                dogs.remove(dog);
                if (game.isSoundsAllowed()) bricksSound.play(0.6f);
                wall.damage(50f);
                wall.handle();
                if (wall.getPerHealthPoints() <= 0) {
                    restartGame();
                }
                else if (wall.getPerHealthPoints() <= 50){
                    wall.brake(textureAtlas.findRegion("wall2"));
                }
                break;
            }
            if (dog.getPerHealthPoints() <= 0) {
                dogs.remove(dog);
                gui.addScore(1);
                break;
            }
        }
        time += GameScreen.deltaCff * spawnRate;
        spawnRate += GameScreen.deltaCff * 0.02f;
        if (time > spawnInterval) {
            int param = random.nextInt(6);
            for (int i = 0; i < param; i++) {
                zombies.add(zombiesInStash.get(zombiesInStash.size() - 1));
                zombiesInStash.remove(zombiesInStash.size() - 1);
                zombies.get(zombies.size() - 1).spawn(width, random);
                Zombie zzz = zombiesInStash.get(0);
                zombiesInStash.add(new Zombie(zzz.getSprite(), HPtextureAtlas,
                        -width, height / 2, zzz.getWidth(), zzz.getHeight(), zzz.getMaxHealthPoints()));
            }
            for (int i = 0; i < 5 - param; i++) {
                dogs.add(dogsInStash.get(dogsInStash.size() - 1));
                dogsInStash.remove(dogsInStash.size() - 1);
                dogs.get(dogs.size() - 1).spawn(width, random);
                Dog ddd = dogsInStash.get(0);
                dogsInStash.add(new Dog(ddd.getSprite(), HPtextureAtlas,
                        -width, height / 2, ddd.getWidth(), ddd.getHeight(), ddd.getMaxHealthPoints()));
            }
            time = 0;
        }

        for (Ammo ammo : ammos) {
            for (Zombie zombie : zombies) {
                if (Intersector.overlapConvexPolygons(zombie.getBounds(), ammo.getBounds())) {
                    float damage = random.nextFloat() < ammo.getCritChance() ? ammo.getCritDamage() : ammo.getDamage();
                    if (zombie.healthBar.getHealth() > 66 && zombie.healthBar.getHealth() <= 66 + damage) {
                        zombie.setSprite(textureAtlas.findRegion("zombie66"));
                    }
                    else if (zombie.healthBar.getHealth() > 33 && zombie.healthBar.getHealth() <= 33 + damage) {
                        zombie.setSprite(textureAtlas.findRegion("zombie33"));
                    }
                    zombie.repel(ammo.getRepelDist());
                    zombie.damage(damage);

                    bloodMap.add(new Blood(textureAtlas.findRegion("blood"),
                            zombie.getX() - 0.5f + random.nextFloat(), zombie.getY(), zombie.getWidth(), zombie.getWidth()));

                    if (game.isSoundsAllowed()) hitSound.play(0.8f);
                    ammo.delete();
                }
            }
            for (Dog dog : dogs) {
                if (Intersector.overlapConvexPolygons(dog.getBounds(), ammo.getBounds())) {
                    float damage = random.nextFloat() < ammo.getCritChance() ? ammo.getCritDamage() : ammo.getDamage();
                    dog.damage(damage);
                    bloodMap.add(new Blood(textureAtlas.findRegion("blood"),
                            dog.getX() - 0.5f + random.nextFloat(), dog.getY(), dog.getWidth(), dog.getWidth()));
                    if (game.isSoundsAllowed()) hitSound.play(0.8f);
                    ammo.delete();
                }
            }

        }
        if (bloodMap.size() > 200) {
            bloodMap.remove(0);
        }
        bossFIGHT = (gui.getScore() >0) && (gui.getScore()) % Math.max((int) (bossRate), 1) == 0 && gui.getScore() < 6000;
        if (bossFIGHT) {
            boss.spawn();
            time = 0;
            if (bossRate <= 2f) {
                gui.setScore(666);
                boss.setSpeedY(-3f);
            }
            bossRate *= 0.8f;
        }
    }

    public void restartGame() {
        init();
        gui.setScore(0);
    }


    private void bossFightDraw(SpriteBatch batch) {
        boss.draw(batch);
    }

    private void gameDraw(SpriteBatch batch) {
        for (Dog dog : dogs) dog.draw(batch);
        for (Zombie zombie : zombies) zombie.draw(batch);
    }

    public void draw(SpriteBatch batch) {
        background.draw(batch);
        wallFloor.draw(batch);
        for (Blood blood : bloodMap) blood.draw(batch);

        if (bossFIGHT) bossFightDraw(batch);
        else gameDraw(batch);

        wall.draw(batch);
        weapon.draw(batch);
        for (Ammo ammo : ammos) ammo.draw(batch);
        pauseButton.draw(batch);


    }


    private void initParams() {
        spawnInterval = 100f;
        spawnRate = 25f;
        time = 99f;
        bossRate = 666f;
        bossFIGHT = false;
    }

    private void initObjects() {

        this.zombies = new ArrayList<Zombie>();
        this.zombiesInStash = new ArrayList<Zombie>();
        this.dogs = new ArrayList<Dog>();
        this.dogsInStash = new ArrayList<Dog>();
        this.bloodMap = new ArrayList<Blood>();
        this.ammos = new ArrayList<Ammo>();
        this.boss = new Boss(textureAtlas.findRegion("boss1"), HPtextureAtlas,
                - 4f, height / 2, 8f, 8f, 800f);
        Ammo ammoInWeapon = new Arrow(textureAtlas.findRegion("arrow"),
                2.25f, -height / 2, 0.5f, 0.5f * 5.6153f, 34f, 0.05f, 100f, 0.3f, 25f);
        weapon = new Bow(textureAtlas.findRegion("bow"),
                0f, -height / 2, 5f, 5f * 0.3255f, ammoInWeapon, 100f, 1);
        for (int i = 0; i < 10f; i++) {
            zombiesInStash.add(new Zombie(textureAtlas.findRegion("zombie"), HPtextureAtlas,
                    -width, height / 2, 3f, 3f * 1.12f, 100f));
            dogsInStash.add(new Dog(textureAtlas.findRegion("dog"), HPtextureAtlas,
                    -width, height / 2, 2f, 2f * 2.8125f, 50f));
        }
        wall = new Wall(textureAtlas.findRegion("wall1"), HPtextureAtlas,
                -width / 2, 4f -height / 2, 2f * 28.96f, 2f, 1499.9f, width);
        wallFloor = new Background(textureAtlas.findRegion("wallFloor"),
                -width / 2, - height / 2, 5f * 5.875f, 5f);
        pauseButton = new Button(textureAtlas.findRegion("pauseButton"),
                width / 2 - 2f * 1.275f - 0.5f, height / 2 - 2.5f, 2f * 1.275f, 2f);
        background = new Background(textureAtlas.findRegion("grass"),
                -width / 2, -height / 2, height * 1f, height);
    }

    private void initSounds() {
        hitSound = Gdx.audio.newSound(Gdx.files.internal("hit.ogg"));
        shootSound = Gdx.audio.newSound(Gdx.files.internal("shoot.ogg"));
        bricksSound = Gdx.audio.newSound(Gdx.files.internal("bricks.ogg"));
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("soundButton.ogg"));
        scream = Gdx.audio.newSound(Gdx.files.internal("scream.ogg"));

        music = Gdx.audio.newMusic(Gdx.files.internal("bensound-instinct.mp3"));
        music.setLooping(true);
        music.setVolume(0.18f);
        if (game.isMusicAllowed()) {
            music.play();
        }
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
