package com.bow.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.bow.game.BowGame;
import com.bow.game.model.Background;
import com.bow.game.model.Button;
import com.bow.game.utils.Assets;
import com.bow.game.view.PauseMenuScreen;

public class PauseMenuController {
    private BowGame game;
    private Assets assets;

    private Sound buttonSound;

    private Background background;
    private Button resumeButton;
    private Button musicButton;
    private Button soundButton;
    private Button exitMainMenuButton;

    private float width = PauseMenuScreen.cameraWidth;
    private float height = width * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

    public PauseMenuController(BowGame game, Assets assets) {
        this.game = game;
        this.assets = assets;

        background = new Background(assets.getTexture("menuBack"),
                -width / 2,  -height / 2, 1.6f * height, height);
        resumeButton = new Button(assets.getTexture("resumeButton"),
                -1.5f * 2.9f, 0, 3f * 2.9f, 3f);
        musicButton = new Button(assets.getTexture(game.prefs.getBoolean("musicAllowed", true) ? "musicButtonOn" : "musicButtonOff"),
                -1.5f * 2.9f, -4f, 3f * 1.275f, 3f);
        soundButton = new Button(assets.getTexture(game.prefs.getBoolean("soundAllowed", true) ? "soundButtonOn" : "soundButtonOff"),
                1.5f * 2.9f - 3f * 1.275f, -4f, 3f * 1.275f, 3f);
        exitMainMenuButton = new Button(assets.getTexture("mainMenuButton"),
                -1.5f * 2.9f, -8f, 3f * 2.9f, 3f);
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("soundButton.ogg"));
    }

    public void handle() {
        background.handle();
        resumeButton.handle();
        musicButton.handle();
        soundButton.handle();

        if (Gdx.input.justTouched()) {
            float x = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            float y = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;

            resumeButton.setToggled(resumeButton.getBounds().contains(x, y));
            musicButton.setToggled(musicButton.getBounds().contains(x, y));
            soundButton.setToggled(soundButton.getBounds().contains(x, y));
            exitMainMenuButton.setToggled(exitMainMenuButton.getBounds().contains(x, y));
        }

        if (!Gdx.input.isTouched()) {
            if (resumeButton.isToggled()) {
                resumeButton.setToggled(false);
                assets.playSound(buttonSound, 1f);
                game.pauseScreen.pause();
                game.setScreen(game.gameScreen);
            }
            if (musicButton.isToggled()) {
                if (game.prefs.getBoolean("musicAllowed", true)) {
                    game.prefs.putBoolean("musicAllowed", false).flush();
                    musicButton.setSprite(assets.getTexture("musicButtonOff"));
                }
                else {
                    game.prefs.putBoolean("musicAllowed", true).flush();
                    musicButton.setSprite(assets.getTexture("musicButtonOn"));
                }
                musicButton.setToggled(false);
                assets.playSound(buttonSound, 1f);
            }
            if (soundButton.isToggled()) {
                if (game.prefs.getBoolean("soundAllowed", true)) {
                    game.prefs.putBoolean("soundAllowed", false).flush();
                    soundButton.setSprite(assets.getTexture("soundButtonOff"));
                }
                else {
                    game.prefs.putBoolean("soundAllowed", true).flush();
                    soundButton.setSprite(assets.getTexture("soundButtonOn"));
                }
                soundButton.setToggled(false);
                assets.playSound(buttonSound, 1f);
            }
            if (exitMainMenuButton.isToggled()) {
                exitMainMenuButton.setToggled(false);
                assets.playSound(buttonSound, 1f);
                game.pauseScreen.pause();
                game.setScreen(game.menuScreen);
            }
        }

    }

    public void sync() {
        musicButton.setSprite(assets.getTexture(game.prefs.getBoolean("musicAllowed", true) ? "musicButtonOn" : "musicButtonOff"));
        soundButton.setSprite(assets.getTexture(game.prefs.getBoolean("soundAllowed", true) ? "soundButtonOn" : "soundButtonOff"));
    }

    public void draw(SpriteBatch batch) {
        background.draw(batch);
        resumeButton.draw(batch);
        musicButton.draw(batch);
        soundButton.draw(batch);
        exitMainMenuButton.draw(batch);
    }

    public void dispose() {
        buttonSound.dispose();
        game.dispose();
        assets.dispose();
    }
}
