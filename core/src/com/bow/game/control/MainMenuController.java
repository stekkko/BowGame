package com.bow.game.control;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bow.game.BowGame;
import com.bow.game.model.ui.Button;
import com.bow.game.model.DynamicGameObject;
import com.bow.game.model.GameObject;
import com.bow.game.screens.MainMenuScreen;

public class MainMenuController implements Controller {
    private BowGame game;

    private Sound buttonSound;

    private DynamicGameObject background1;
    private DynamicGameObject background2;
    private GameObject logo;
    private Button playButton;
    private Button musicButton;
    private Button soundButton;
    private Button exitButton;

    private float xp = 0;
    private float yp = 0;

    private float width;
    private float height;

    public MainMenuController(BowGame game) {
        this.game = game;
        width = MainMenuScreen.cameraWidth;
        height = width * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

        logo = new GameObject(game.assets.getTexture("logo"),
                -3f * 3.4f, 5f, 6f * 3.4f, 6f);
        background1 = new DynamicGameObject(game.assets.getTexture("background2"),
                -width / 2 - 3f,  -height / 2, 1.6f * height, height);
        background2 = new DynamicGameObject(game.assets.getTexture("background2"),
                -width / 2 + 1.59f * height -3f,  -height / 2, 1.6f * height, height);
        background1.setSpeedX(-1f);
        background2.setSpeedX(-1f);
        playButton = new Button(game.assets.getTexture("playButton"),
                -1.5f * 2.9f, 0f, 3f * 2.9f, 3f);
        musicButton = new Button(game.assets.getTexture(game.prefs.getBoolean("musicAllowed", true) ? "musicButtonOn" : "musicButtonOff"),
                -1.5f * 2.9f, -4f, 3f * 1.275f, 3f);
        soundButton = new Button(game.assets.getTexture(game.prefs.getBoolean("soundAllowed", true) ? "soundButtonOn" : "soundButtonOff"),
                1.5f * 2.9f - 3f * 1.275f, -4f, 3f * 1.275f, 3f);
        exitButton = new Button(game.assets.getTexture("exitButton"),
                -1.5f * 2.9f, -8f, 3f * 2.9f, 3f);
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("audio/soundButton.ogg"));

        game.assets.playMusic("theme", 0.5f);
    }

    @Override
    public void handle(float dt) {
        backgroundHandle(dt);

        if (Gdx.input.justTouched()) {
            float jtx = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            float jty = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;

            playButton.setToggled(playButton.getBounds().contains(jtx, jty));
            musicButton.setToggled(musicButton.getBounds().contains(jtx, jty));
            soundButton.setToggled(soundButton.getBounds().contains(jtx, jty));
            exitButton.setToggled(exitButton.getBounds().contains(jtx, jty));
            if (!playButton.isToggled() && !musicButton.isToggled() && !soundButton.isToggled() && !exitButton.isToggled()) {
                background1.setSpeedX(-background1.getSpeedX());
                background2.setSpeedX(-background2.getSpeedX());
            }
        }

        if (Gdx.input.isTouched()) {
            xp = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            yp = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;
        }
        else {
            if (playButton.isToggled() && playButton.getBounds().contains(xp, yp)) {
                playButton.setToggled(false);
                game.assets.playSound(buttonSound, 1f);
                game.menuScreen.pause();
                game.setScreen(game.levelSelector);
            }
            else if (musicButton.isToggled() && musicButton.getBounds().contains(xp, yp)) {
                if (game.prefs.getBoolean("musicAllowed", true)) {
                    game.prefs.putBoolean("musicAllowed", false).flush();
                    musicButton.setSprite(game.assets.getTexture("musicButtonOff"));
                    game.assets.pauseMusic();
                }
                else {
                    game.prefs.putBoolean("musicAllowed", true).flush();
                    musicButton.setSprite(game.assets.getTexture("musicButtonOn"));
                    game.assets.playMusic("theme", 0.5f);
                }
                musicButton.setToggled(false);
                game.assets.playSound(buttonSound, 1f);
            }
            else if (soundButton.isToggled() && soundButton.getBounds().contains(xp, yp)) {
                if (game.prefs.getBoolean("soundAllowed", true)) {
                    game.prefs.putBoolean("soundAllowed", false).flush();
                    soundButton.setSprite(game.assets.getTexture("soundButtonOff"));
                }
                else {
                    game.prefs.putBoolean("soundAllowed", true).flush();
                    soundButton.setSprite(game.assets.getTexture("soundButtonOn"));
                }
                soundButton.setToggled(false);
                game.assets.playSound(buttonSound, 1f);
            }
            else if (exitButton.isToggled() && exitButton.getBounds().contains(xp, yp)) {
                exitButton.setToggled(false);
                game.assets.playSound(buttonSound, 1f);
                game.prefs.flush();
                System.gc();
                System.exit(0);
            }

        }
    }

    private void backgroundHandle(float dt) {
        background1.handle(dt);
        background2.handle(dt);
        if (background1.getX() + background1.getWidth() < -width / 2 - 3f) background1.setPosition(background2.getX() + background2.getWidth() - 0.1f, background1.getY());
        if (background1.getX() > width / 2 + 3f) background1.setPosition(background2.getX() - background2.getWidth() + 0.1f, background1.getY());
        if (background2.getX() + background2.getWidth() < -width / 2 - 3f) background2.setPosition(background1.getX() + background1.getWidth() - 0.1f, background2.getY());
        if (background2.getX() > width / 2 + 3f) background2.setPosition(background1.getX() - background1.getWidth() + 0.1f, background2.getY());
    }

    public void sync() {
        musicButton.setSprite(game.assets.getTexture(game.prefs.getBoolean("musicAllowed", true) ? "musicButtonOn" : "musicButtonOff"));
        soundButton.setSprite(game.assets.getTexture(game.prefs.getBoolean("soundAllowed", true) ? "soundButtonOn" : "soundButtonOff"));
    }

    @Override
    public void draw(SpriteBatch batch) {
        background1.draw(batch);
        background2.draw(batch);
        logo.draw(batch);
        playButton.draw(batch);
        musicButton.draw(batch);
        soundButton.draw(batch);
        exitButton.draw(batch);
    }

    @Override
    public void dispose() {
        buttonSound.dispose();
        game.dispose();
    }
}
