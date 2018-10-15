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
import com.bow.game.model.Bullet;
import com.bow.game.model.Crosshair;
import com.bow.game.model.Explosion;
import com.bow.game.model.Spell;
import com.bow.game.model.Turret;
import com.bow.game.model.Weapon;
import com.bow.game.model.mobs.Boss666;
import com.bow.game.model.Bow;
import com.bow.game.model.Button;
import com.bow.game.model.mobs.Dog;
import com.bow.game.model.Wall;
import com.bow.game.model.mobs.Knight;
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
    private ArrayList<Knight> knights;
    private Boss666 boss666;
    private Weapon weapon;
    private ArrayList<Ammo> ammunition;
    private Wall wall;
    private ArrayList<Blood> bloodMap;
    private Button pauseButton;
    private Spell spellExplosion;
    private Spell spellKnight;
    private Background wallFloor;
    private ArrayList<Explosion> explosions;

    private Sound scream;

    private Sound explosionSound;
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
    private float time;
    private float xp = 0;
    private float yp = 0;
    private float jtx = 0;
    private float jty = 0;

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

        for (Ammo ammo : ammunition) ammo.handle();
        for (Explosion ex : explosions) {
            ex.setDamage(0);
            ex.handle();
        }
        gui.addFloat(GameScreen.deltaCff);
        gui.setCooldown(spellExplosion.isOnCD() ? spellExplosion.getCooldownTime() - spellExplosion.getTime() : 0f);
        gui.setCooldown1(spellKnight.isOnCD() ? spellKnight.getCooldownTime() - spellKnight.getTime() : 0f);
        if (gui.getCooldown()==0){gui.hideCooldown();}
        if (gui.getCooldown1()==0){gui.hideCooldown1();}
        weapon.handle();
        wall.handle();
        wall.update(HPtextureAtlas);
        boss666.handle();
        boss666.update(HPtextureAtlas);
        wallFloor.handle();
        pauseButton.handle();
        spellExplosion.handle();
        spellKnight.handle();

        for (Ammo ammo : ammunition) {
            if (ammo.isReadyToDelete() || ammo.getY() > height / 2) {
                ammunition.remove(ammo);
                break;
            }
        }
        for (Explosion ex : explosions) {
            if (ex.isReadyToDelete()) {
                explosions.remove(ex);
                break;
            }
        }

        if (Gdx.input.justTouched()) {
            jtx = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            jty = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;
            pauseButton.setToggled(pauseButton.getBounds().contains(jtx, jty));
            spellExplosion.setToggled(spellExplosion.getBounds().contains(jtx, jty));
            spellKnight.setToggled(spellKnight.getBounds().contains(jtx, jty));
        }


        if (Gdx.input.isTouched()) {
            xp = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            yp = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;

            pauseButton.setToggled(pauseButton.getBounds().contains(xp, yp));
            if (spellExplosion.getBounds().contains(xp, yp)) spellExplosion.setToggled(true);
            if (spellExplosion.isToggled() && spellExplosion.getBounds().contains(jtx, jty) && !spellExplosion.isOnCD()) {
                spellExplosion.getCrosshair().setPosition(xp - spellExplosion.getCrosshair().getWidth() / 2, yp - spellExplosion.getCrosshair().getHeight() / 2);
                spellExplosion.getCrosshair().setDrawn(true);
            }


            if (!spellExplosion.getCrosshair().isDrawn()) {
                weapon.setPosition(xp - weapon.getWidth() / 2, weapon.getY());
                if (weapon.isLoaded() && weapon.isReadyToShoot()) {
                    weapon.shoot();
                    ammunition.add(weapon.getAmmo().copy());
                    ammunition.get(ammunition.size() - 1).shoot();
                    if (game.isSoundsAllowed()) shootSound.play(0.09f);
                }
            }
            if (spellKnight.getBounds().contains(xp, yp)) spellKnight.setToggled(true);
            if (spellKnight.isToggled() && spellKnight.getBounds().contains(jtx, jty) && !spellKnight.isOnCD()) {
                spellKnight.getCrosshair().setPosition(xp - spellKnight.getCrosshair().getWidth() / 2, yp - spellKnight.getCrosshair().getHeight() / 2);
                spellKnight.getCrosshair().setDrawn(true);
            }
            if (!spellKnight.getCrosshair().isDrawn()) {
                weapon.setPosition(xp - weapon.getWidth() / 2, weapon.getY());
                if (weapon.isLoaded() && weapon.isReadyToShoot()) {
                    weapon.shoot();
                    ammunition.add(weapon.getAmmo().copy());
                    ammunition.get(ammunition.size() - 1).shoot();
                    if (game.isSoundsAllowed()) shootSound.play(0.09f);
                }
            }
        }
        else {
            if (pauseButton.isToggled() && pauseButton.getBounds().contains(jtx, jty)) {
                if (game.isSoundsAllowed()) buttonSound.play();
                pauseButton.setToggled(false);
                game.gameScreen.pause();
                game.setScreen(game.pauseScreen);
            }
            if (spellExplosion.isToggled() && spellExplosion.getBounds().contains(jtx, jty)) {
                if (game.isSoundsAllowed()) explosionSound.play();
                spellExplosion.setToggled(false);
                spellExplosion.setOnCD(true);
                explosions.add(new Explosion(textureAtlas.findRegion("explosion"), xp -4.5f, yp -4.5f, 9f, 9f, 100f));
            }

            if (spellKnight.isToggled() && spellKnight.getBounds().contains(jtx, jty)) {
                spellKnight.setToggled(false);
                spellKnight.setOnCD(true);
                knights.add(new Knight(textureAtlas.findRegion("dog"),HPtextureAtlas, xp - 5f, yp - 1.5f, 10f,3f,100500,100));
            }
            spellExplosion.getCrosshair().setDrawn(false);
            spellKnight.getCrosshair().setDrawn(false);
        }
    }

    private void handleBossFight() {
        boss666.handle();
        boss666.update(HPtextureAtlas);
        for (Explosion ex : explosions) {
            if (Intersector.overlapConvexPolygons(boss666.getBounds(), ex.getBounds())) {
                boss666.damaged(ex.getDamage());
            }
        }
        if (Intersector.overlapConvexPolygons(boss666.getBounds(), wall.getBounds())) {
            restartGame();
            return;
        }
        for (Ammo ammo : ammunition) {
            if (Intersector.overlapConvexPolygons(ammo.getBounds(), boss666.getBounds())) {
                boss666.damaged(ammo.getDamage());
                if (game.isSoundsAllowed()) hitSound.play(0.8f);
                ammo.delete();
            }
        }

        bossFIGHT = boss666.getPercentHealthPoints() > 0;
        if (!bossFIGHT) {
            gui.addScore(2);
            boss666.setPosition(random.nextFloat() * (12f) + (-10f), boss666.getY());
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
        for (Knight knight : knights) {
            knight.handle();
            knight.update(HPtextureAtlas);
        }

        for (Zombie zombie : zombies) {
            for (Explosion explosion : explosions) {
                if (Intersector.overlapConvexPolygons(zombie.getBounds(), explosion.getBounds())) {
                    zombie.damaged(explosion.getDamage());
                }
            }
            for (Knight knight : knights) {
                if (Intersector.overlapConvexPolygons(zombie.getBounds(), knight.getBounds())) {
                    zombie.damaged(0);
                    zombie.repel(2f);
                    knight.damaged(zombie.getDamage());
                }
            }
            if (Intersector.overlapConvexPolygons(zombie.getBounds(), wall.getBounds())) {
                zombies.remove(zombie);
                if (game.isSoundsAllowed()) bricksSound.play(0.6f);
                wall.damaged(zombie.getDamage());
                wall.handle();
                wall.update(HPtextureAtlas);
                if (wall.getPercentHealthPoints() <= 0) {
                    restartGame();
                }
                else if (wall.getPercentHealthPoints() <= 50){
                    wall.brake(textureAtlas.findRegion("wall2"));
                }
                break;
            }
            if (zombie.getPercentHealthPoints() <= 0) {
                zombies.remove(zombie);
                gui.addScore(0);
                break;
            }
        }
        for (Dog dog : dogs) {
            for (Explosion explosion : explosions) {
                if (Intersector.overlapConvexPolygons(dog.getBounds(), explosion.getBounds())) {
                    dog.damaged(explosion.getDamage());
                }
            }
            for (Knight knight : knights) {
                if (Intersector.overlapConvexPolygons(dog.getBounds(), knight.getBounds())) {
                    dog.damaged(knight.getDamage());
                    knight.damaged(dog.getDamage());
                }
            }
            if (Intersector.overlapConvexPolygons(dog.getBounds(), wall.getBounds())) {
                dogs.remove(dog);
                if (game.isSoundsAllowed()) bricksSound.play(0.6f);
                wall.damaged(dog.getDamage());
                wall.handle();
                if (wall.getPercentHealthPoints() <= 0) {
                    restartGame();
                } else if (wall.getPercentHealthPoints() <= 50) {
                    wall.brake(textureAtlas.findRegion("wall2"));
                }
                break;
            }
            if (dog.getPercentHealthPoints() <= 0) {
                dogs.remove(dog);
                gui.addScore(0);
                break;
            }
        }
        for (Knight knight : knights){
            if (knight.getPercentHealthPoints() <=0) {
                knights.remove(0);
                break;
            }
        }
        time += GameScreen.deltaCff;
        spawnInterval -= 0.001 * GameScreen.deltaCff;
        if (time > spawnInterval) {
            int param = random.nextInt(6);
            int adjustZombies = 1;
            for (int i = 0; i < param + adjustZombies; i++) {
                zombies.add(new Zombie(textureAtlas.findRegion("zombie"), HPtextureAtlas,
                        -width, height / 2, 3f, 3f * 1.12f, 100f, 200f));
                zombies.get(zombies.size() - 1).randomSpawn(random, width, 0f, -1.5f);
            }
            for (int i = 0; i < 5 - param; i++) {
                dogs.add(new Dog(textureAtlas.findRegion("dog"), HPtextureAtlas,
                        -width, height / 2, 2f, 2f * 2.8125f, 50f, 50f));
                dogs.get(dogs.size() - 1).randomSpawn(random, width, 0f, -3f);
            }
            time = 0;
        }

        for (Ammo ammo : ammunition) {
            for (Zombie zombie : zombies) {
                if (Intersector.overlapConvexPolygons(zombie.getBounds(), ammo.getBounds())) {
                    float damage = random.nextFloat() < ammo.getCriticalChance() ? ammo.getCriticalDamage() : ammo.getDamage();
                    if (zombie.getPercentHealthPoints() > 66 && zombie.getPercentHealthPoints() <= 66 + damage) {
                        zombie.setSprite(textureAtlas.findRegion("zombie66"));
                    }
                    else if (zombie.getPercentHealthPoints() > 33 && zombie.getPercentHealthPoints() <= 33 + damage) {
                        zombie.setSprite(textureAtlas.findRegion("zombie33"));
                    }
                    zombie.repel(ammo.getRepelDist());
                    zombie.damaged(damage);

                    bloodMap.add(new Blood(textureAtlas.findRegion("blood"),
                            zombie.getX() - 0.5f + random.nextFloat(), zombie.getY(), zombie.getWidth(), zombie.getWidth()));

                    if (game.isSoundsAllowed()) hitSound.play(0.8f);
                    ammo.delete();
                }
            }
            for (Dog dog : dogs) {
                if (Intersector.overlapConvexPolygons(dog.getBounds(), ammo.getBounds())) {
                    float damage = random.nextFloat() < ammo.getCriticalChance() ? ammo.getCriticalDamage() : ammo.getDamage();
                    dog.damaged(damage);
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
        bossFIGHT = gui.getScore() > 60 && gui.getScore() < 61;
        if (bossFIGHT) {
            boss666.targetSpawn(-boss666.getWidth() / 2, height / 2, 0f, -1f);
            time = 0;

        }
    }

    public void restartGame() {
        music.stop();
        init();
        gui.setScore(0);
        if (game.isMusicAllowed()) music.play();
    }

    private void bossFightDraw(SpriteBatch batch) {
        boss666.draw(batch);
    }

    private void gameDraw(SpriteBatch batch) {
        for (Dog dog : dogs) dog.draw(batch);
        for (Zombie zombie : zombies) zombie.draw(batch);
        for (Knight knight : knights) {
            knight.draw(batch);
        }
    }

    public void draw(SpriteBatch batch) {
        background.draw(batch);
        wallFloor.draw(batch);
        for (Blood blood : bloodMap) blood.draw(batch);

        if (bossFIGHT) bossFightDraw(batch);
        else gameDraw(batch);

        wall.draw(batch);
        weapon.draw(batch);
        for (Ammo ammo : ammunition) ammo.draw(batch);
        for (Explosion ex : explosions) ex.draw(batch);
        pauseButton.draw(batch);
        spellExplosion.draw(batch);
        spellKnight.draw(batch);
    }

    private void initParams() {
        spawnInterval = 5f;
        time = 5f;
        bossFIGHT = false;
        xp = 0;
        yp = 0;
        jtx = 0;
        jty = 0;
    }

    private void initObjects() {

        this.zombies = new ArrayList<Zombie>();
        this.dogs = new ArrayList<Dog>();
        this.bloodMap = new ArrayList<Blood>();
        this.ammunition = new ArrayList<Ammo>();
        this.explosions = new ArrayList<Explosion>();
        this.knights = new ArrayList<Knight>();


        boss666 = new Boss666(textureAtlas.findRegion("boss1"), HPtextureAtlas,
                - 4f, height / 2, 8f, 8f, 800f, (float) Integer.MAX_VALUE);
        if (game.getGamemode() == 1) {
            Ammo ammoInWeapon = new Arrow(textureAtlas.findRegion("arrow"),
                    2.25f, -height / 2, 0.5f, 0.5f * 5.6153f, 25f, 0.05f, 100f, 0.3f, 25f);
            weapon = new Bow(textureAtlas.findRegion("bow"),
                    0f, -height / 2, 5f, 5f * 0.3255f, ammoInWeapon, 0.3f);
        }
        else if (game.getGamemode() == 2) {
            Ammo ammoInWeapon = new Bullet(textureAtlas.findRegion("bullet"),
                    0f, -height / 2, 0.2f, 0.2f * 3.555f, 20f, 0.01f, 100f, 0.1f, 40f);
            weapon = new Turret(textureAtlas.findRegion("turret"),
                    0f, -height / 2, 3f, 3f * 1.435f, ammoInWeapon, 30, 2.0f, 0.1f);
        }
        wall = new Wall(textureAtlas.findRegion("wall1"), HPtextureAtlas,
                -width / 2, 4f -height / 2, 2f * 28.96f, 2f, 1500f, width);


        knights.add(new Knight(textureAtlas.findRegion("dog"),HPtextureAtlas,
                0 ,7f -height /2, 3f,3f,500,50f));
        wallFloor = new Background(textureAtlas.findRegion("wallFloor"),
                -width / 2, - height / 2, 5f * 5.875f, 5f);
        pauseButton = new Button(textureAtlas.findRegion("pauseButton"),
                width / 2 - 2f * 1.275f - 0.5f, height / 2 - 2.5f, 2f * 1.275f, 2f);
        Crosshair crosshair = new Crosshair(textureAtlas.findRegion("crosshair"),
                0, 0, 9f, 9f);
        spellExplosion = new Spell(textureAtlas.findRegion("exSpellButton"),
                -width / 2, -2f, 4f, 4f, crosshair);
        spellKnight = new Spell(textureAtlas.findRegion("exSpellButton"),
                -width / 2, 3f, 4f, 4f, crosshair);
        background = new Background(textureAtlas.findRegion("grass"),
                -width / 2, -height / 2, height * 1f, height);

    }

    private void initSounds() {
        hitSound = Gdx.audio.newSound(Gdx.files.internal("hit.ogg"));
        shootSound = Gdx.audio.newSound(Gdx.files.internal("shoot.ogg"));
        bricksSound = Gdx.audio.newSound(Gdx.files.internal("bricks.ogg"));
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("soundButton.ogg"));
        scream = Gdx.audio.newSound(Gdx.files.internal("scream.ogg"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));


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

