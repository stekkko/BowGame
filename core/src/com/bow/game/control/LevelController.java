package com.bow.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Align;
import com.bow.game.BowGame;
import com.bow.game.model.ammo.Ammo;
import com.bow.game.model.ammo.Arrow;
import com.bow.game.model.Blood;
import com.bow.game.model.weapons.Bow;
import com.bow.game.model.ammo.Bullet;
import com.bow.game.model.ui.Button;
import com.bow.game.model.ui.Crosshair;
import com.bow.game.model.mobs.Explosion;
import com.bow.game.model.GameObject;
import com.bow.game.model.ui.Spell;
import com.bow.game.model.weapons.Turret;
import com.bow.game.model.mobs.Wall;
import com.bow.game.model.weapons.Weapon;
import com.bow.game.model.mobs.Ally;
import com.bow.game.model.mobs.Boss666;
import com.bow.game.model.mobs.Enemy;
import com.bow.game.model.mobs.GolemStone;
import com.bow.game.model.mobs.Knight;
import com.bow.game.screens.GameScreen;

import java.util.ArrayList;
import java.util.Random;

public abstract class LevelController implements Controller {
    static Random random;
    protected BowGame game;

    //Game Objects
    private GameObject background;
    private GameObject wallFloor;
    ArrayList<Enemy> enemies;
    private ArrayList<Ally> allies;
    private ArrayList<Ammo> ammunition;
    private ArrayList<Blood> bloodMap;
    private ArrayList<Spell> spells;
    private Weapon weapon;
    Wall wall;
    Boss666 boss;
    private Button pauseButton;

    //Sounds
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

    //Params
    float spawnInterval;
    float time;
    float xp = 0;
    float yp = 0;
    float jtx = 0;
    float jty = 0;

    float width;
    float height;

    LevelController(BowGame game) {
        this.game = game;
        width = GameScreen.cameraWidth;
        height = width * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
        random = new Random();
        init();
    }

    public void init() {
        initParams();
        initObjects();
        initSounds();
    }

    protected abstract void initParams();

    protected void initObjects() {
        enemies = new ArrayList<>();
        bloodMap = new ArrayList<>();
        ammunition = new ArrayList<>();
        allies = new ArrayList<>();
        spells = new ArrayList<>();

        spells.add(initSpell(game.prefs.getString("spell1", "null"), 0));
        spells.add(initSpell(game.prefs.getString("spell2", "null"), 1));
        spells.add(initSpell(game.prefs.getString("spell3", "null"), 2));

        weapon = initWeapon(initAmmo(game.prefs.getString("ammoEquip", "arrow")), game.prefs.getString("weaponEquip", "bow"));

        wall = new Wall(game.assets.getTexture("wall1"),
                -width / 2, 4f -height / 2, 2f * 28.96f, 2f, game.prefs.getFloat("WallHP", 300f), width, 3f);
        allies.add(wall);
        wallFloor = new GameObject(game.assets.getTexture("wallFloor"),
                -width / 2, - height / 2, 5f * 5.875f, 5f);
        background = new GameObject(game.assets.getTexture("grass"),
                -width / 2, -height / 2, height * 1f, height);
        pauseButton = new Button(game.assets.getTexture("pauseButton"),
                width / 2 - 2f * 1.275f - 0.5f, height / 2 - 2f - 0.5f, 2f * 1.275f, 2f);
    }

    private Spell initSpell(String spell, int index) {
        switch (spell) {
            case ("spellKnight"): {
                return new Spell(game.assets.getTexture("knightSpellButtonOff"),
                        -width / 2, index * -4f, 3f, 3f, Spell.KNIGHT, 10f,
                        new Crosshair(game.assets.getTexture("crosshair"), 4f, 4f));
            }
            case ("spellExplosion"): {
                Crosshair crosshair = new Crosshair(game.assets.getTexture("crosshair"), 9f, 9f);
                return new Spell(game.assets.getTexture("explosionSpellButtonOff"),
                        -width / 2, index * -4f, 3f, 3f,
                        Spell.EXPLOSION, game.prefs.getFloat("explosionCD", 20f), crosshair);
            }
            default: return new Spell(game.assets.getTexture("nullSpellButton"),
                    -width / 2, index * -4f, 3f, 3f, Spell.NULLSPELL, Float.MAX_VALUE);
        }
    }

    private Ammo initAmmo(String ammoEquip) {
        switch (ammoEquip) {
            case ("arrow"): {
                return new Arrow(2.25f, -height / 2, 0.5f, 0.5f * 5.6153f,
                        game.prefs.getFloat("bowPower", 25f), 0.0f, 0f, game.prefs.getFloat("bowRepel", 0f), 25f, random);
            }
            case ("bullet"): {
                return new Bullet(game.assets.getTexture("bullet"),
                        0f, -height / 2, 0.5f, 0.5f * 3.555f,
                        game.prefs.getFloat("turretPower", 10f), 0.0f, 0f, game.prefs.getFloat("turretRepel", 0f), 40f);
            }
            default: {
                return new Arrow(2.25f, -height / 2, 0.5f, 0.5f * 5.6153f,
                        game.prefs.getFloat("bowPower", 25f), 0.0f, 0f, game.prefs.getFloat("bowRepel", 0f), 25f, random);
            }
        }
    }

    private Weapon initWeapon(Ammo ammoInWeapon, String weaponEquip) {
        switch (weaponEquip) {
            case ("bow"): {
                return new Bow(game.assets.getTexture("bow"),
                        0f, -height / 2, 5f, 5f * 0.3255f, ammoInWeapon,
                        1f / game.prefs.getFloat("bowSpeed", 1f));
            }
            case ("turret"): {
                return new Turret(game.assets.getTexture("turret"),
                        0f, -height / 2, 3f, 3f * 1.435f, ammoInWeapon,
                        30, 2.0f, 1f / game.prefs.getFloat("turretSpeed", 10f));
            }
            default: {
                return new Bow(game.assets.getTexture("bow"),
                        0f, -height / 2, 5f, 5f * 0.3255f, ammoInWeapon,
                        1f / game.prefs.getFloat("bowSpeed", 1f));
            }
        }
    }

    private void initSounds() {
        hitSound = Gdx.audio.newSound(Gdx.files.internal("audio/hit.ogg"));
        shootBowSound = Gdx.audio.newSound(Gdx.files.internal("audio/shootBow.ogg"));
        bricksSound = Gdx.audio.newSound(Gdx.files.internal("audio/bricks.ogg"));
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("audio/soundButton.ogg"));
        scream = Gdx.audio.newSound(Gdx.files.internal("audio/scream.ogg"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("audio/explosion.ogg"));
        swordSound = Gdx.audio.newSound(Gdx.files.internal("audio/knight.ogg"));
        swordHitSound = Gdx.audio.newSound(Gdx.files.internal("audio/swordHit.ogg"));
        shootTurretSound = Gdx.audio.newSound(Gdx.files.internal("audio/shootTurret.ogg"));
        reloadedSound = Gdx.audio.newSound(Gdx.files.internal("audio/reloaded.ogg"));
    }

    @Override
    public void handle(float dt) {
        weapon.handle(dt);
        enemiesHandle(dt);
        alliesHandle(dt);
        ammoHandle(dt);
        ammoCollisionHandle(dt);
        spellsHandle(dt);
        handleInput(dt);
        if (bloodMap.size() > 200) bloodMap.remove(0);

    }

    private void handleInput(float dt) {
        if (Gdx.input.justTouched()) {
            jtx = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            jty = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;

            pauseButton.setToggled(pauseButton.getBounds().contains(jtx, jty));
            for (Spell spell : spells) spell.setToggled(spell.getBounds().contains(jtx, jty));
        }

        if (Gdx.input.isTouched()) {
            xp = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            yp = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;

            boolean nothingToggled = true;
            for (Spell spell : spells) {
                nothingToggled = nothingToggled && !spell.isToggled();
                if (spell.isToggled() && !spell.isOnCD() && !spell.getBounds().contains(xp, yp)) {
                    spell.moveCrosshair(xp, yp);
                    spell.setDrawnCrosshair(true);
                }
                else spell.setDrawnCrosshair(false);
            }

            if (nothingToggled) {
                weapon.setPosition(xp - weapon.getWidth() / 2, weapon.getY());
                if (weapon.isLoaded() && weapon.isReadyToShoot()) {
                    weapon.shoot();
                    ammunition.add(Ammo.copy(weapon.getAmmo()));
                    ammunition.get(ammunition.size() - 1).shoot();

                    if (weapon instanceof Turret) game.assets.playSound(shootTurretSound, 0.2f);
                    if (weapon instanceof Bow) game.assets.playSound(shootBowSound, 0.1f);

                }
            }

        }
        else {
            if (pauseButton.isToggled() && pauseButton.getBounds().contains(xp, yp)) {
                game.assets.playSound(buttonSound, 1f);
                pauseButton.setToggled(false);
                game.setScreen(game.pauseScreen);
                game.gameScreen.pause();

            }
            for (Spell spell : spells) {
                if (spell.isToggled() && !spell.getBounds().contains(xp, yp)) {
                    if (spell.getId() == Spell.KNIGHT) {
                        game.assets.playSound(swordSound, 1f);
                        spell.setSprite(game.assets.getTexture("knightSpellButtonOff"));
                        Knight knight = new Knight(game.assets.getTexture("knight"),
                                xp -1.5f ,yp - 1.5f * 0.906f, 3f,3f * 0.906f,800, 25f, 2f);
                        knight.targetSpawn(xp -1.5f, yp -1.5f * 0.906f, 0f, 1f);
                        knight.setPosition(knight.getX(), Math.max(knight.getY(), wall.getY() + wall.getHeight()));
                        allies.add(knight);
                    }
                    else if (spell.getId() == Spell.EXPLOSION) {
                        game.assets.playSound(explosionSound, 1f);
                        spell.setSprite(game.assets.getTexture("explosionSpellButtonOff"));
                        allies.add(new Explosion(xp -6f, yp -6f, 12f, 12f, game.prefs.getFloat("explosionPower", 140f)));
                    }
                    spell.off();
                }
            }
        }
    }

    private void spellsHandle(float dt) {
        for (Spell spell : spells) {
            spell.handle(dt);
            if (!spell.isOnCD()) {
                if (spell.getId() == Spell.EXPLOSION) spell.setSprite(game.assets.getTexture("explosionSpellButtonOn"));
                if (spell.getId() == Spell.KNIGHT) spell.setSprite(game.assets.getTexture("knightSpellButtonOn"));
            }
        }
    }

    private void ammoHandle(float dt) {
        for (int i = ammunition.size() - 1; i >= 0; i--) {
            Ammo ammo = ammunition.get(i);
            ammo.handle(dt);
            if (ammo.isReadyToDelete() || ammo.getY() > height / 2)
                ammunition.remove(ammo);
        }
    }

    private void enemiesHandle(float dt) {
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            enemy.handle(dt);
            for (Ally ally : allies) {
                if (Intersector.overlapConvexPolygons(enemy.getBounds(), ally.getBounds())) {
                    if (ally instanceof Knight) game.assets.playSound(swordHitSound, 0.2f);
                    else if (ally instanceof Wall) game.assets.playSound(bricksSound, 0.7f);
                    else if (ally instanceof Explosion && enemy instanceof Boss666) enemy.setPosition(enemy.getX(), enemy.getY() + 5f);
                    else if (enemy instanceof GolemStone) enemy.damaged(100f);

                    enemy.damaged(ally.getDamage());
                    if (enemy.isRepelable()) enemy.repel(ally.getRepelPower());
                    ally.damaged(enemy.getDamage());
                    if (ally.isRepelable()) ally.repel(enemy.getRepelPower());
                }
            }
            if (enemy.getPercentHealthPoints() <= 0) {
                if (enemy instanceof Boss666) game.assets.playSound(scream, 0.5f);
                enemies.remove(enemy);
                game.prefs.putInteger("cash", game.prefs.getInteger("cash", 0) + enemy.getValue()).flush();
                bloodMap.add(new Blood(enemy, random));
            }
        }
    }

    private void alliesHandle(float dt) {
        for (int i = allies.size() - 1; i >= 0; i--) {
            Ally ally = allies.get(i);
            ally.handle(dt);
            if (ally instanceof Explosion) ally.setDamage(0f);
            if (ally.getPercentHealthPoints() <= 0 || ally.getY() > height / 2) {
                allies.remove(ally);
            }
        }
    }

    void spawnHandle(float dt) {

    }

    private void ammoCollisionHandle(float dt) {
        for (Ammo ammo : ammunition) {
            for (Enemy enemy : enemies) {
                if (Intersector.overlapConvexPolygons(enemy.getBounds(), ammo.getBounds())) {
                    enemy.damaged(ammo.getDamage(random));
                    if (enemy.isRepelable()) enemy.repel(ammo.getRepelDist());
                    game.assets.playSound(hitSound,0.8f);
                    ammo.delete();
                }
            }
        }
    }

    void restartGame() {
        game.assets.stopMusic();
        init();
        game.assets.playMusic("music", 0.15f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        background.draw(batch);
        wallFloor.draw(batch);
        for (Blood blood : bloodMap) blood.draw(batch);

        for (Enemy enemy : enemies) enemy.draw(batch);
        for (Ally ally : allies) ally.draw(batch);

        weapon.draw(batch);
        for (Ammo ammo : ammunition) ammo.draw(batch);
        pauseButton.draw(batch);
        for (Spell spell : spells) {
            spell.draw(batch);
            if (spell.getId() != Spell.NULLSPELL && spell.isOnCD())
                game.assets.getFont("default").draw(batch, "" + (int) (spell.getCD() ), spell.getX(), spell.getY(), spell.getWidth(), Align.top, true);
        }
        game.assets.getFont("money").draw(game.batch, game.prefs.getInteger("cash", 0) + "$", - width / 2 + 1f, height / 2 - 1f);
    }

    @Override
    public void dispose() {
        game.dispose();
    }
}


