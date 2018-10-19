package com.bow.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.bow.game.BowGame;
import com.bow.game.model.Ammo;
import com.bow.game.model.Animation;
import com.bow.game.model.Arrow;
import com.bow.game.model.Background;
import com.bow.game.model.Blood;
import com.bow.game.model.Bullet;
import com.bow.game.model.Crosshair;
import com.bow.game.model.mobs.Ally;
import com.bow.game.model.mobs.Enemy;
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
import com.bow.game.utils.Assets;
import com.bow.game.utils.GUI;
import com.bow.game.view.GameScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LevelController {
    private static Random random;
    private BowGame game;
    private GUI gui;
    private Assets assets;

    //Game Objects
    private Background background;
    private ArrayList<Enemy> enemies;
    private ArrayList<Ally> allies;
    private Weapon weapon;
    private ArrayList<Ammo> ammunition;
    private Wall wall;
    private Boss666 boss;
    private ArrayList<Blood> bloodMap;
    private Button pauseButton;
    private Spell spellExplosion;
    private Spell spellKnight;
    private Background wallFloor;

    //Music and Sounds
    private Map<String, Sound> sounds;
    private Sound swordHitSound;
    private Sound swordSound;
    private Sound scream;
    private Sound explosionSound;
    private Sound hitSound;
    private Sound bricksSound;
    private Sound shootBowSound;
    private Sound shootTurretSound;
    private Sound reloadedSound;
    private Sound buttonSound;
    public Music music;

    //Params
    private boolean bossFIGHT;
    private float spawnInterval;
    private float time;
    private float xp = 0;
    private float yp = 0;
    private float jtx = 0;
    private float jty = 0;

    private float width = GameScreen.cameraWidth;
    private float height = width * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

    public LevelController(BowGame game, Assets assets, GUI gui) {
        this.game = game;
        this.gui = gui;
        random = new Random();
        this.assets = assets;
        init();
    }

    private void init() {
        initParams();
        initObjects();
        initSounds();
    }

    public void handle() {
        handleGame();

        for (Ammo ammo : ammunition) ammo.handle();

        if (wall.getPercentHealthPoints() <= 0)
            restartGame();
        else if (wall.getPercentHealthPoints() <= 50)
            wall.brake(assets.getTexture("wall2"));

        gui.addFloat(GameScreen.deltaCff);
        gui.setCooldown(spellExplosion.isOnCD() ? spellExplosion.getCooldownTime() - spellExplosion.getTime() : 0f);
        gui.setCooldown1(spellKnight.isOnCD() ? spellKnight.getCooldownTime() - spellKnight.getTime() : 0f);
        if (gui.getCooldown() == 0) { gui.hideCooldown(); }
        if (gui.getCooldown1() == 0) { gui.hideCooldown1(); }

        weapon.handle();
        wallFloor.handle();
        pauseButton.handle();

        spellExplosion.handle();
        if (!spellExplosion.isOnCD()) spellExplosion.setSprite(assets.getTexture("explosionSpellButtonOn"));
        spellKnight.handle();
        if (!spellKnight.isOnCD()) spellKnight.setSprite(assets.getTexture("knightSpellButtonOn"));


        for (int i = ammunition.size() - 1; i >= 0; i--) {
            Ammo ammo = ammunition.get(i);
            if (ammo.isReadyToDelete() || ammo.getY() > height / 2)
                ammunition.remove(ammo);
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

            if (spellExplosion.isToggled() && !spellExplosion.isOnCD()) {
                spellExplosion.getCrosshair().setPosition(xp - spellExplosion.getCrosshair().getWidth() / 2, yp - spellExplosion.getCrosshair().getHeight() / 2);
                spellExplosion.getCrosshair().setDrawn(true);
            }

            if (spellKnight.isToggled() && !spellKnight.isOnCD()) {
                spellKnight.getCrosshair().setPosition(xp - spellKnight.getCrosshair().getWidth() / 2, yp - spellKnight.getCrosshair().getHeight() / 2);
                spellKnight.getCrosshair().setDrawn(true);
            }

            if (!spellExplosion.getCrosshair().isDrawn() && !spellKnight.getCrosshair().isDrawn()) {
                weapon.setPosition(xp - weapon.getWidth() / 2, weapon.getY());
                if (weapon.isLoaded() && weapon.isReadyToShoot()) {
                    weapon.shoot();
                    ammunition.add(Ammo.copy(weapon.getAmmo()));
                    ammunition.get(ammunition.size() - 1).shoot();

                    if (game.getGamemode() == 1) assets.playSound(shootBowSound, 0.13f);
                    if (game.getGamemode() == 2) assets.playSound(shootTurretSound,0.2f);

                }
            }

        }
        else {
            if (pauseButton.isToggled()) {
                assets.playSound(buttonSound, 1f);
                pauseButton.setToggled(false);
                game.gameScreen.pause();
                game.setScreen(game.pauseScreen);
            }
            if (spellExplosion.isToggled()) {
                assets.playSound(explosionSound, 1f);
                spellExplosion.setToggled(false);
                spellExplosion.setOnCD(true);
                spellExplosion.getCrosshair().setDrawn(false);
                spellExplosion.setSprite(assets.getTexture("explosionSpellButtonOff"));
                allies.add(new Explosion(xp -6f, yp -6f, 12f, 12f,50f));
            }
            if (spellKnight.isToggled()) {
                assets.playSound(swordSound, 1f);
                spellKnight.setToggled(false);
                spellKnight.setOnCD(true);
                spellKnight.getCrosshair().setDrawn(false);
                spellKnight.setSprite(assets.getTexture("knightSpellButtonOff"));
                Knight knight = new Knight(assets.getTexture("knight"),
                        xp -1.5f ,yp - 1.5f * 0.906f, 3f,3f * 0.906f,800, 25f, 2f);
                knight.targetSpawn(xp -1.5f, yp -1.5f * 0.906f, 0f, 1f);
                knight.setPosition(knight.getX(), Math.max(knight.getY(), wall.getY() + wall.getHeight()));
                allies.add(knight);
            }
        }
    }

    private void handleGame() {
        for (Enemy enemy : enemies) enemy.handle();
        for (Ally ally : allies) ally.handle();

        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            for (Ally ally : allies) {
                if (Intersector.overlapConvexPolygons(enemy.getBounds(), ally.getBounds())) {
                    if (ally instanceof Knight) assets.playSound(swordHitSound, 0.2f);
                    if (ally instanceof Wall) assets.playSound(bricksSound, 0.7f);

                    enemy.damaged(ally.getDamage());
                    if (enemy.isRepelable()) enemy.repel(ally.getRepelPower());
                    ally.damaged(enemy.getDamage());
                    if (ally.isRepelable()) ally.repel(enemy.getRepelPower());
                }
            }
            if (enemy.getPercentHealthPoints() <= 0) {
                enemies.remove(enemy);
                bloodMap.add(new Blood(enemy, random));
            }
        }

        for (int i = allies.size() - 1; i >= 0; i--) {
            Ally ally = allies.get(i);
            if (ally instanceof Explosion) ally.setDamage(0f);
            if (ally.getPercentHealthPoints() <= 0 || ally.getY() > height / 2) {
                allies.remove(ally);
            }
        }

        time += GameScreen.deltaCff;
        spawnInterval -= 0.005 * GameScreen.deltaCff;
        if (time > spawnInterval) {
            int param = random.nextInt(6);
            int adjustZombies = 1;
            for (int i = 0; i < param + adjustZombies; i++) {
                enemies.add(new Zombie(-width, height / 2, 3f, 3f * 1.12f, 100f, 50f,-2f));
                enemies.get(enemies.size() - 1).randomSpawn(random, width, 0f, -1.5f);
            }
            for (int i = 0; i < 5 - param; i++) {
                enemies.add(new Dog(-width, height / 2, 2f, 2f * 2.8125f, 50f, 20f,-1f));
                enemies.get(enemies.size() - 1).randomSpawn(random, width, 0f, -4f);
            }
            time = 0;
        }

        for (Ammo ammo : ammunition) {
            for (Enemy enemy : enemies) {
                if (Intersector.overlapConvexPolygons(enemy.getBounds(), ammo.getBounds())) {
                    enemy.damaged(ammo.getDamage(random));
                    enemy.repel(ammo.getRepelDist());
                    assets.playSound(hitSound,0.8f);
                    ammo.delete();
                }
            }
        }

        if (bloodMap.size() > 200) {
            bloodMap.remove(0);
        }

        if (gui.getScore() > 60 && gui.getScore() < 61 && boss == null) bossFIGHT = true;
        if (bossFIGHT) {
            boss = new Boss666(assets.getTexture("boss2"),
                    - 4f, height / 2, 8f, 8f, 600f, (float) Integer.MAX_VALUE);
            boss.targetSpawn(-boss.getWidth() / 2, height / 2, 0f, -1f);
            enemies.add(boss);
            time = 0;
            bossFIGHT = false;
        }
    }

    public void restartGame() {
        music.stop();
        init();
        gui.setScore(0);
        assets.playMusic(music);
    }

    public void draw(SpriteBatch batch) {
        background.draw(batch);
        wallFloor.draw(batch);
        for (Blood blood : bloodMap) blood.draw(batch);

        for (Enemy enemy : enemies) enemy.draw(batch);
        for (Ally ally : allies) ally.draw(batch);

        wall.draw(batch);
        weapon.draw(batch);
        for (Ammo ammo : ammunition) ammo.draw(batch);
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

        this.enemies = new ArrayList<Enemy>();
        this.bloodMap = new ArrayList<Blood>();
        this.ammunition = new ArrayList<Ammo>();
        this.allies = new ArrayList<Ally>();


        if (game.getGamemode() == 1) {
            Ammo ammoInWeapon = new Arrow(assets.getTexture("arrow"),
                    2.25f, -height / 2, 0.5f, 0.5f * 5.6153f, 25f, 0.05f, 100f, 0.3f, 25f);
            weapon = new Bow(assets.getTexture("bow"),
                    0f, -height / 2, 5f, 5f * 0.3255f, ammoInWeapon, 0.3f);
        }
        else if (game.getGamemode() == 2) {
            Ammo ammoInWeapon = new Bullet(assets.getTexture("bullet"),
                    0f, -height / 2, 0.5f, 0.5f * 3.555f,
                    20f, 0.01f, 100f, 0.1f, 40f);
            weapon = new Turret(assets.getTexture("turret"),
                    0f, -height / 2, 3f, 3f * 1.435f, ammoInWeapon, 30, 2.0f, 0.1f);
        }
        wall = new Wall(assets.getTexture("wall1"),
                -width / 2, 4f -height / 2, 2f * 28.96f, 2f, 1500f, width, 3f);
        allies.add(wall);
        wallFloor = new Background(assets.getTexture("wallFloor"),
                -width / 2, - height / 2, 5f * 5.875f, 5f);
        pauseButton = new Button(assets.getTexture("pauseButton"),
                width / 2 - 2f * 1.275f - 0.5f, height / 2 - 2.5f, 2f * 1.275f, 2f);
        Crosshair crosshair = new Crosshair(assets.getTexture("crosshair"),
                0, 0, 9f, 9f);
        Crosshair crosshair1 = new Crosshair(assets.getTexture("crosshair"),
                0, 0, 4f, 4f);
        spellExplosion = new Spell(assets.getTexture("explosionSpellButtonOff"),
                -width / 2, -2f, 4f, 4f, crosshair);
        spellKnight = new Spell(assets.getTexture("knightSpellButtonOff"),
                -width / 2, 2.5f, 4f, 4f, crosshair1);

        background = new Background(assets.getTexture("grass"),
                -width / 2, -height / 2, height * 1f, height);

    }

    private void initSounds() {

        hitSound = Gdx.audio.newSound(Gdx.files.internal("hit.ogg"));
        shootBowSound = Gdx.audio.newSound(Gdx.files.internal("shootBow.ogg"));
        bricksSound = Gdx.audio.newSound(Gdx.files.internal("bricks.ogg"));
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("soundButton.ogg"));
        scream = Gdx.audio.newSound(Gdx.files.internal("scream.ogg"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        swordSound = Gdx.audio.newSound(Gdx.files.internal("knight.ogg"));
        swordHitSound = Gdx.audio.newSound(Gdx.files.internal("swordHit.ogg"));
        shootTurretSound = Gdx.audio.newSound(Gdx.files.internal("shootTurret.ogg"));
        reloadedSound = Gdx.audio.newSound(Gdx.files.internal("reloaded.ogg"));

        music = Gdx.audio.newMusic(Gdx.files.internal("bensound-instinct.mp3"));
        music.setLooping(true);
        music.setVolume(0.15f);
        if (game.isMusicAllowed()) {
            music.play();
        }
    }

    public void dispose() {
        game.dispose();
        gui.dispose();
        assets.dispose();
    }
}

