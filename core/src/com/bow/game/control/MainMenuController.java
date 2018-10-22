package com.bow.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.bow.game.BowGame;
import com.bow.game.model.Background;
import com.bow.game.model.Button;
import com.bow.game.utils.Assets;
import com.bow.game.view.MainMenuScreen;

public class MainMenuController {
    private BowGame game;
    private Assets assets;

    private Sound buttonSound;

    private Background background1;
    private Background background2;
    private Background logo;
    private Button playButton;
    private Button musicButton;
    private Button soundButton;
    private Button exitButton;

    public Music theme;

    private float width = MainMenuScreen.cameraWidth;
    private float height = width * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

    public MainMenuController(BowGame game, Assets assets) {
        this.game = game;
        this.assets = assets;

        logo = new Background(assets.getTexture("logo"),
                -3f * 3.4f, 5f, 6f * 3.4f, 6f);
        background1 = new Background(assets.getTexture("menuBack"),
                -width / 2,  -height / 2, 1.6f * height, height);
        background2 = new Background(assets.getTexture("menuBack"),
                -width / 2 + 1.59f * height,  -height / 2, 1.6f * height, height);
        background1.setSpeedX(-1f);
        background2.setSpeedX(-1f);
        playButton = new Button(assets.getTexture("playButton"),
                -1.5f * 2.9f, 0f, 3f * 2.9f, 3f);
        musicButton = new Button(assets.getTexture(game.prefs.getBoolean("musicAllowed", true) ? "musicButtonOn" : "musicButtonOff"),
                -1.5f * 2.9f, -4f, 3f * 1.275f, 3f);
        soundButton = new Button(assets.getTexture(game.prefs.getBoolean("soundAllowed", true) ? "soundButtonOn" : "soundButtonOff"),
                1.5f * 2.9f - 3f * 1.275f, -4f, 3f * 1.275f, 3f);
        exitButton = new Button(assets.getTexture("exitButton"),
                -1.5f * 2.9f, -8f, 3f * 2.9f, 3f);
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("soundButton.ogg"));

        theme = Gdx.audio.newMusic(Gdx.files.internal("menuTheme.wav"));
        assets.playMusic(theme, 0.5f);
    }

    public void handle() {
        background1.handle();
        background2.handle();
        if (background1.getX() + background1.getWidth() < -width / 2 - 3f) background1.setPosition(background2.getX() + background2.getWidth() - 0.1f, background1.getY());
        if (background1.getX() > width / 2 + 3f) background1.setPosition(background2.getX() - background2.getWidth() + 0.1f, background1.getY());
        if (background2.getX() + background2.getWidth() < -width / 2 - 3f) background2.setPosition(background1.getX() + background1.getWidth() - 0.1f, background2.getY());
        if (background2.getX() > width / 2 + 3f) background2.setPosition(background1.getX() - background1.getWidth() + 0.1f, background2.getY());

        playButton.handle();
        musicButton.handle();
        soundButton.handle();

        if (Gdx.input.justTouched()) {
            float x = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            float y = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;

            playButton.setToggled(playButton.getBounds().contains(x, y));
            musicButton.setToggled(musicButton.getBounds().contains(x, y));
            soundButton.setToggled(soundButton.getBounds().contains(x, y));
            exitButton.setToggled(exitButton.getBounds().contains(x, y));
            if (!playButton.isToggled() && !musicButton.isToggled() && !soundButton.isToggled() && !exitButton.isToggled()) {
                background1.setSpeedX(-background1.getSpeedX());
                background2.setSpeedX(-background2.getSpeedX());
            }
        }

        if (!Gdx.input.isTouched()) {
            if (playButton.isToggled()) {
                playButton.setToggled(false);
                assets.playSound(buttonSound, 1f);
                game.menuScreen.pause();
                game.setScreen(game.levelSelector);
            }
            if (musicButton.isToggled()) {
                if (game.prefs.getBoolean("musicAllowed", true)) {
                    game.prefs.putBoolean("musicAllowed", false).flush();
                    musicButton.setSprite(assets.getTexture("musicButtonOff"));
                    theme.stop();
                }
                else {
                    game.prefs.putBoolean("musicAllowed", true).flush();
                    musicButton.setSprite(assets.getTexture("musicButtonOn"));
                    theme.play();
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
            if (exitButton.isToggled()) {
                exitButton.setToggled(false);
                assets.playSound(buttonSound, 1f);
                game.prefs.flush();
                System.gc();
                System.exit(0);

            }
        }
    }

    public void sync() {
        musicButton.setSprite(assets.getTexture(game.prefs.getBoolean("musicAllowed", true) ? "musicButtonOn" : "musicButtonOff"));
        soundButton.setSprite(assets.getTexture(game.prefs.getBoolean("soundAllowed", true) ? "soundButtonOn" : "soundButtonOff"));
    }

    public void draw(SpriteBatch batch) {
        background1.draw(batch);
        background2.draw(batch);
        logo.draw(batch);
        playButton.draw(batch);
        musicButton.draw(batch);
        soundButton.draw(batch);
        exitButton.draw(batch);
    }

    public void dispose() {
        buttonSound.dispose();
        game.dispose();
        assets.dispose();
    }
}
