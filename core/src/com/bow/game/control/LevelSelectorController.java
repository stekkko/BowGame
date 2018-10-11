package com.bow.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.bow.game.BowGame;
import com.bow.game.model.Background;
import com.bow.game.model.Button;
import com.bow.game.view.LevelSelector;
import com.bow.game.view.MainMenuScreen;
import com.bow.game.view.PauseMenuScreen;

public class LevelSelectorController {
    private BowGame game;
    private TextureAtlas textureAtlas;

    private Sound buttonSound;

    private Background background;
    private Button playCampaignButton;
    private Button playEndlessButton;


    private float width = LevelSelector.cameraWidth;
    private float height = width * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

    public LevelSelectorController(BowGame game, TextureAtlas textureAtlas) {
        this.game = game;
        this.textureAtlas = textureAtlas;

        background = new Background(textureAtlas.findRegion("menuBack"),
                -width / 2,  -height / 2, 1.6f * height, height);
        playCampaignButton = new Button(textureAtlas.findRegion("goButton"),
                -2f * 2.9f, 0, 4f * 2.9f, 4f);
        playEndlessButton = new Button(textureAtlas.findRegion("goButton"),
                -2f * 2.9f, -5f, 4f * 2.9f, 4f);
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("soundButton.ogg"));
    }

    public void handle() {
        background.handle();
        playCampaignButton.handle();
        playEndlessButton.handle();

        if (Gdx.input.isTouched()) {
            float x = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            float y = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;

            playCampaignButton.setToggled(playCampaignButton.getBounds().contains(x, y));
            playEndlessButton.setToggled(playEndlessButton.getBounds().contains(x, y));
        }
        else {
            if (playCampaignButton.isToggled()) {
                playCampaignButton.setToggled(false);
                if (game.isSoundsAllowed()) buttonSound.play();
                game.levelSelector.pause();
                game.setScreen(game.gameScreen);
                game.gameScreen.resume();
            }
            if (playEndlessButton.isToggled()) {
                playEndlessButton.setToggled(false);
                if (game.isSoundsAllowed()) buttonSound.play();
                game.levelSelector.pause();
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
        textureAtlas.dispose();
    }
}
