package com.bow.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bow.game.BowGame;
import com.bow.game.model.Background;
import com.bow.game.model.Button;
import com.bow.game.view.LevelSelector;


public class LevelSelectorController {
    private BowGame game;

    private Sound buttonSound;

    private Background background;
    private Button playCampaignButton;
    private Button playEndlessButton;

    private float xp = 0;
    private float yp = 0;

    private float width = LevelSelector.cameraWidth;
    private float height = width * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

    public LevelSelectorController(BowGame game) {
        this.game = game;

        background = new Background(game.assets.getTexture("menuBack"),
                -width / 2,  -height / 2, 1.6f * height, height);
        playCampaignButton = new Button(game.assets.getTexture("survivalButton"),
                -2f * 2.9f, 0, 4f * 2.9f, 4f);
        playEndlessButton = new Button(game.assets.getTexture("endlessButton"),
                -2f * 2.9f, -5f, 4f * 2.9f, 4f);
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("soundButton.ogg"));
    }

    public void handle() {
        background.handle();
        playCampaignButton.handle();
        playEndlessButton.handle();

        if (Gdx.input.justTouched()) {
            float x = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            float y = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;

            playCampaignButton.setToggled(playCampaignButton.getBounds().contains(x, y));
            playEndlessButton.setToggled(playEndlessButton.getBounds().contains(x, y));
        }

        if (Gdx.input.isTouched()) {
            xp = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            yp = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;
        }
        else {
            if (playCampaignButton.isToggled() && playCampaignButton.getBounds().contains(xp, yp)) {
                playCampaignButton.setToggled(false);
                game.assets.playSound(buttonSound, 1f);
                game.assets.stopMusic("theme");
                game.assets.playMusic("music", 0.15f);
                game.levelSelector.pause();
                game.setGamemode(game.SURVIVAL);
                game.setScreen(game.gameScreen);
                game.gameScreen.resume();
            }
            if (playEndlessButton.isToggled() && playEndlessButton.getBounds().contains(xp, yp)) {
                playEndlessButton.setToggled(false);
                game.assets.playSound(buttonSound, 1f);
                game.assets.stopMusic("theme");
                game.assets.playMusic("music", 0.15f);
                game.levelSelector.pause();
                game.setGamemode(game.ENDLESS);
                game.setScreen(game.gameScreen);
                game.gameScreen.resume();
            }
        }

    }

    public void draw(SpriteBatch batch) {
        background.draw(batch);
        playCampaignButton.draw(batch);
        playEndlessButton.draw(batch);
    }

    public void dispose() {
        buttonSound.dispose();
        game.dispose();
    }
}
