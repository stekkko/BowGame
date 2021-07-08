package com.bow.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bow.game.BowGame;
import com.bow.game.model.ui.Button;
import com.bow.game.model.GameObject;
import com.bow.game.screens.PauseMenuScreen;

public class ShopController implements Controller {
    private BowGame game;

    private static final int NULL = 0;
    private static final int BUYING = 1;
    private static final int UPGRADING = 2;

    private int mode;
    private int item;
    private GameObject background;
    private GameObject bowSprite;
    private GameObject turretSprite;
    private GameObject explosionSprite;
    private Button upgradeButton;
    private Button buyButton;
    private Button bowButton;
    private Button turretButton;
    private Button explosionButton;
    private Button backButton;
    private Button goButton;

    private Sound buttonSound;

    private float xp = 0;
    private float yp = 0;

    private float width;
    private float height;

    public ShopController(BowGame game) {
        this.game = game;
        mode = NULL;
        item = 0;
        width = PauseMenuScreen.cameraWidth;
        height = width * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

        background = new GameObject(game.assets.getTexture("background2"),
                -width,  -height / 2, 1.6f * height, height);
        backButton = new Button(game.assets.getTexture("backButton"),
                -9f , -height / 2 + 1f, 2f * 2.9f, 2f);
        goButton = new Button(game.assets.getTexture("goButton"),
                9f - 2f * 2.9f , -height / 2 + 1f, 2f * 2.9f, 2f);
        upgradeButton = new Button(game.assets.getTexture("upgradeButton"),
                -9f, -height / 2 + 4f, 2f * 6.4f, 2f);
        buyButton = new Button(game.assets.getTexture("buyButton"),
                -9f, -height / 2 + 4f, 2f * 6.4f, 2f);

        //shop items
        bowButton = new Button(game.assets.getTexture("panelOff"),
                -9f, height / 2 - 7f, 4f, 4f);
        bowSprite = new GameObject(game.assets.getTexture("bow"),
                -9f + 0.7f, height / 2 - 7f + 1.5f, 3f, 3f * 0.3255f);
        bowSprite.getBounds().setRotation(-45);
        turretButton = new Button(game.assets.getTexture("panelOff"),
                -9f, height / 2 - 12f, 4f, 4f);
        turretSprite = new GameObject(game.assets.getTexture("turret"),
                -9f + 1f, height / 2 - 12f + 0.5f, 2f, 2f * 1.435f);
        explosionButton = new Button(game.assets.getTexture("panelOff"),
                -9f, height / 2 - 17f, 4f, 4f);
        explosionSprite = new GameObject(game.assets.getTexture("explosionSpellButtonOn"),
                -9f + 0.5f, height / 2 - 17f + 0.5f, 3f, 3f);
        //
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("audio/soundButton.ogg"));
    }

    @Override
    public void handle(float dt) {
        handleInput(dt);
        if (item == 0) mode = NULL;
        if (item == 1) {
            if (game.prefs.getBoolean("haveBow", false))
                mode = UPGRADING;
            else mode = BUYING;
        }
        if (item == 2) {
            if (game.prefs.getBoolean("haveTurret", false))
                mode = UPGRADING;
            else mode = BUYING;
        }
        if (item == 3) {
            if (game.prefs.getBoolean("haveExplosion", false))
                mode = UPGRADING;
            else mode = BUYING;
        }
    }

    public void handleInput(float dt) {
        if (Gdx.input.justTouched()) {
            float jtx = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            float jty = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;

            backButton.setToggled(backButton.getBounds().contains(jtx, jty));
            goButton.setToggled(mode == UPGRADING && goButton.getBounds().contains(jtx, jty));

            bowButton.setToggled(bowButton.getBounds().contains(jtx, jty));
            turretButton.setToggled(turretButton.getBounds().contains(jtx, jty));
            explosionButton.setToggled(explosionButton.getBounds().contains(jtx, jty));

            buyButton.setToggled(mode == BUYING && buyButton.getBounds().contains(jtx, jty));
            upgradeButton.setToggled(mode == UPGRADING && upgradeButton.getBounds().contains(jtx, jty));
        }
        if (Gdx.input.isTouched()) {
            xp = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            yp = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;
        }
        else {
            if (goButton.isToggled() && goButton.getBounds().contains(xp, yp)) {
                goButton.setToggled(false);
                game.assets.playSound(buttonSound, 1f);
                game.assets.playMusic("music", 0.15f);
                game.shopScreen.pause();
                game.gameScreen.hide();
                game.setScreen(game.gameScreen);
            }
            if (backButton.isToggled() && backButton.getBounds().contains(xp, yp)) {
                backButton.setToggled(false);
                game.assets.playSound(buttonSound, 1f);
                game.shopScreen.pause();
                game.gameScreen.hide();
                game.setScreen(game.levelSelector);
            }
            if (bowButton.isToggled() && bowButton.getBounds().contains(xp, yp)) {
                bowButton.setToggled(false);
                game.assets.playSound(buttonSound, 1f);
                if (item == 1) {
                    bowButton.setSprite(game.assets.getTexture("panelOff"));
                    item = 0;
                }
                else {
                    item = 1;
                    if (game.prefs.getBoolean("haveBow", false)) {
                        game.prefs.putString("weaponEquip", "bow");
                        game.prefs.putString("ammoEquip", "arrow").flush();
                    }
                    bowButton.setSprite(game.assets.getTexture("panelOn"));
                    explosionButton.setSprite(game.assets.getTexture("panelOff"));
                    turretButton.setSprite(game.assets.getTexture("panelOff"));
                }
            }
            if (turretButton.isToggled() && turretButton.getBounds().contains(xp, yp)) {
                turretButton.setToggled(false);
                game.assets.playSound(buttonSound, 1f);
                if (item == 2) {
                    turretButton.setSprite(game.assets.getTexture("panelOff"));
                    item = 0;
                }
                else {
                    item = 2;
                    if (game.prefs.getBoolean("haveTurret", false)) {
                        game.prefs.putString("weaponEquip", "turret");
                        game.prefs.putString("ammoEquip", "bullet").flush();
                    }
                    turretButton.setSprite(game.assets.getTexture("panelOn"));
                    bowButton.setSprite(game.assets.getTexture("panelOff"));
                    explosionButton.setSprite(game.assets.getTexture("panelOff"));
                }
            }
            if (explosionButton.isToggled() && explosionButton.getBounds().contains(xp, yp)) {
                explosionButton.setToggled(false);
                game.assets.playSound(buttonSound, 1f);
                if (item == 3) {
                    explosionButton.setSprite(game.assets.getTexture("panelOff"));
                    item = 0;
                }
                else {
                    item = 3;
                    if (game.prefs.getBoolean("haveExplosion", false)) {
                        game.prefs.putString("spell3", "spellExplosion").flush();
                    }
                    explosionButton.setSprite(game.assets.getTexture("panelOn"));
                    turretButton.setSprite(game.assets.getTexture("panelOff"));
                    bowButton.setSprite(game.assets.getTexture("panelOff"));
                }
            }

            if (buyButton.isToggled() && buyButton.getBounds().contains(xp, yp)) {
                buyButton.setToggled(false);
                game.assets.playSound(buttonSound, 1f);
                int cash = game.prefs.getInteger("cash", 0);
                if (item == 1) {
                    int cost = game.prefs.getInteger("bowCost", 0);
                    if (cash >= cost) {
                        game.prefs.putBoolean("haveBow", true);
                        game.prefs.putString("weaponEquip", "bow");
                        game.prefs.putString("ammoEquip", "arrow");
                        game.prefs.putInteger("cash", cash - cost);
                        game.prefs.putInteger("bowCost", cost + 50).flush();
                    }
                }
                else if (item == 2) {
                    int cost = game.prefs.getInteger("turretCost", 1000);
                    if (cash >= cost) {
                        game.prefs.putBoolean("haveTurret", true);
                        game.prefs.putString("weaponEquip", "turret");
                        game.prefs.putString("ammoEquip", "bullet");
                        game.prefs.putInteger("cash", cash - cost);
                        game.prefs.putInteger("turretCost", cost + 200).flush();
                    }
                }
                else if (item == 3) {
                    int cost = game.prefs.getInteger("explosionCost", 150);
                    if (cash >= cost) {
                        game.prefs.putBoolean("haveExplosion", true);
                        game.prefs.putString("spell3", "spellExplosion");
                        game.prefs.putInteger("cash", cash - cost);
                        game.prefs.putInteger("explosionCost", cost + 400).flush();
                    }
                }
            }
            if (upgradeButton.isToggled() && upgradeButton.getBounds().contains(xp, yp)) {
                upgradeButton.setToggled(false);
                game.assets.playSound(buttonSound, 1f);
                int cash = game.prefs.getInteger("cash", 0);
                if (item == 1) {
                    int cost = game.prefs.getInteger("bowCost");
                    if (cash >= cost) {
                        game.prefs.putFloat("bowPower", game.prefs.getFloat("bowPower", 25f) + 1f);
                        game.prefs.putFloat("bowRepel", game.prefs.getFloat("bowRepel", 0f) + 0.01f);
                        game.prefs.putFloat("bowSpeed", game.prefs.getFloat("bowSpeed", 1f) + 0.1f);
                        game.prefs.putInteger("cash", cash - cost);
                        game.prefs.putInteger("bowCost", cost + 50).flush();
                    }
                }
                else if (item == 2) {
                    int cost = game.prefs.getInteger("turretCost");
                    if (cash >= cost) {
                        game.prefs.putFloat("turretPower", game.prefs.getFloat("turretPower", 10f) + 5f);
                        game.prefs.putFloat("turretRepel", game.prefs.getFloat("turretRepel", 0f) + 0.02f);
                        game.prefs.putFloat("turretSpeed", game.prefs.getFloat("turretSpeed", 10f) + 0.3f);
                        game.prefs.putInteger("cash", cash - cost);
                        game.prefs.putInteger("turretCost", cost + 400).flush();
                    }
                }
                else if (item == 3) {
                    int cost = game.prefs.getInteger("explosionCost");
                    if (cash >= cost) {
                        game.prefs.putFloat("explosionPower", game.prefs.getFloat("explosionPower", 140f) + 30f);
                        game.prefs.putFloat("explosionCD", game.prefs.getFloat("explosionCD", 20f) - 0.5f);
                        game.prefs.putInteger("cash", cash - cost);
                        game.prefs.putInteger("explosionCost", cost + 500).flush();
                    }
                }
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        background.draw(batch);
        backButton.draw(batch);
        if (mode == UPGRADING) goButton.draw(batch);
        bowButton.draw(batch);
        bowSprite.draw(batch);
        turretButton.draw(batch);
        turretSprite.draw(batch);
        explosionButton.draw(batch);
        explosionSprite.draw(batch);
        if (mode == UPGRADING) upgradeButton.draw(batch);
        if (mode == BUYING) buyButton.draw(batch);
        game.assets.getFont("money").draw(game.batch, game.prefs.getInteger("cash", 0) + "$",
                -width / 2 + 1f, height / 2 - 1f);

        game.assets.getFont("").draw(game.batch, "power " + (int) game.prefs.getFloat("bowPower", 25f),
                bowButton.getX() + bowButton.getWidth() + 1f, bowButton.getY() + 3.6f);
        game.assets.getFont("").draw(game.batch, "repel " + (int) (game.prefs.getFloat("bowRepel", 0f) * 100),
                bowButton.getX() + bowButton.getWidth() + 1f, bowButton.getY() + 2.4f);
        game.assets.getFont("").draw(game.batch, "speed " + (int) (game.prefs.getFloat("bowSpeed", 2f) * 10),
                bowButton.getX() + bowButton.getWidth() + 1f, bowButton.getY() + 1.2f);
        game.assets.getFont("money").draw(game.batch, game.prefs.getInteger("bowCost", 0) + "$",
                4.7f, bowButton.getY() + 2.4f);

        game.assets.getFont("").draw(game.batch, "power " + (int) game.prefs.getFloat("turretPower", 10f),
                turretButton.getX() + turretButton.getWidth() + 1f, turretButton.getY() + 3.6f);
        game.assets.getFont("").draw(game.batch, "repel " + (int) (game.prefs.getFloat("turretRepel", 0f) * 100),
                turretButton.getX() + turretButton.getWidth() + 1f, turretButton.getY() + 2.4f);
        game.assets.getFont("").draw(game.batch, "speed " + (int) (game.prefs.getFloat("turretSpeed", 10f) * 10),
                turretButton.getX() + turretButton.getWidth() + 1f, turretButton.getY() + 1.3f);
        game.assets.getFont("money").draw(game.batch, game.prefs.getInteger("turretCost", 1000) + "$",
                4.7f, turretButton.getY() + 2.4f);

        game.assets.getFont("").draw(game.batch, "power " + (int) game.prefs.getFloat("explosionPower", 80f),
                explosionButton.getX() + explosionButton.getWidth() + 1f, explosionButton.getY() + 3.6f);
        game.assets.getFont("").draw(game.batch, "CD " + (int) (game.prefs.getFloat("explosionCD", 20f) * 10),
                explosionButton.getX() + explosionButton.getWidth() + 1f, explosionButton.getY() + 2.1f);
        game.assets.getFont("money").draw(game.batch, game.prefs.getInteger("explosionCost", 150) + "$",
                4.7f, explosionButton.getY() + 2.4f);
    }

    @Override
    public void dispose() {
        game.dispose();
    }
}
