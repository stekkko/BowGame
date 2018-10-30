package com.bow.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bow.game.BowGame;
import com.bow.game.model.ui.Button;
import com.bow.game.model.GameObject;
import com.bow.game.screens.LevelSelector;


public class LevelSelectorController implements Controller {
    private BowGame game;

    private Sound buttonSound;

    private GameObject background;
    private Button playCampaignButton;
    private Button playEndlessButton;
    private Button backButton;

    private float xp = 0;
    private float yp = 0;

    private float width;
    private float height;

    public LevelSelectorController(BowGame game) {
        this.game = game;
        width = LevelSelector.cameraWidth;
        height = width * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

        background = new GameObject(game.assets.getTexture("background2"),
                -width,  -height / 2, 1.6f * height, height);
        playCampaignButton = new Button(game.assets.getTexture("survivalButton"),
                -4 * 1.45f - 1f, -5f, 4f * 1.45f, 4f);
        playEndlessButton = new Button(game.assets.getTexture("endlessButton"),
                1f, -5f, 4f * 1.45f, 4f);
        backButton = new Button(game.assets.getTexture("backButton"),
                -9f , -height / 2 + 1f, 2f * 2.9f, 2f);
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("audio/soundButton.ogg"));
    }

    @Override
    public void handle(float dt) {
        if (Gdx.input.justTouched()) {
            float x = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            float y = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;

            playCampaignButton.setToggled(playCampaignButton.getBounds().contains(x, y));
            playEndlessButton.setToggled(playEndlessButton.getBounds().contains(x, y));
            backButton.setToggled(backButton.getBounds().contains(x,y));
        }

        if (Gdx.input.isTouched()) {
            xp = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            yp = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;
        }
        else {
            if (playCampaignButton.isToggled() && playCampaignButton.getBounds().contains(xp, yp)) {
                playCampaignButton.setToggled(false);
                game.assets.playSound(buttonSound, 1f);
                game.levelSelector.pause();
                game.setGamemode(game.SURVIVAL);
                game.setScreen(game.shopScreen);
                game.shopScreen.resume();
            }
            if (playEndlessButton.isToggled() && playEndlessButton.getBounds().contains(xp, yp)) {
                playEndlessButton.setToggled(false);
                game.assets.playSound(buttonSound, 1f);
                game.levelSelector.pause();
                game.setGamemode(game.ENDLESS);
                game.setScreen(game.shopScreen);
                game.shopScreen.resume();
            }
            if (backButton.isToggled() && backButton.getBounds().contains(xp, yp)) {
                backButton.setToggled(false);
                game.assets.playSound(buttonSound, 1f);
                game.levelSelector.pause();
                game.setScreen(game.menuScreen);
                game.menuScreen.resume();
            }
        }

    }

    @Override
    public void draw(SpriteBatch batch) {
        background.draw(batch);
        playCampaignButton.draw(batch);
        playEndlessButton.draw(batch);
        backButton.draw(batch);
    }

    @Override
    public void dispose() {
        buttonSound.dispose();
        game.dispose();
    }
}
