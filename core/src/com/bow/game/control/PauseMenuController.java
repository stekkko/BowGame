package com.bow.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bow.game.BowGame;
import com.bow.game.model.Button;
import com.bow.game.model.GameObject;
import com.bow.game.view.PauseMenuScreen;

public class PauseMenuController {
    private BowGame game;

    private Sound buttonSound;

    private GameObject background;
    private Button resumeButton;
    private Button musicButton;
    private Button soundButton;
    private Button exitMenuButton;

    private float xp = 0;
    private float yp = 0;

    private float width = PauseMenuScreen.cameraWidth;
    private float height = width * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

    public PauseMenuController(BowGame game) {
        this.game = game;

        background = new GameObject(game.assets.getTexture("menuBack"),
                -width / 2,  -height / 2, 1.6f * height, height);
        resumeButton = new Button(game.assets.getTexture("resumeButton"),
                -1.5f * 2.9f, 0, 3f * 2.9f, 3f);
        musicButton = new Button(game.assets.getTexture(game.prefs.getBoolean("musicAllowed", true) ? "musicButtonOn" : "musicButtonOff"),
                -1.5f * 2.9f, -4f, 3f * 1.275f, 3f);
        soundButton = new Button(game.assets.getTexture(game.prefs.getBoolean("soundAllowed", true) ? "soundButtonOn" : "soundButtonOff"),
                1.5f * 2.9f - 3f * 1.275f, -4f, 3f * 1.275f, 3f);
        exitMenuButton = new Button(game.assets.getTexture("mainMenuButton"),
                -1.5f * 2.9f, -8f, 3f * 2.9f, 3f);
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("soundButton.ogg"));
    }

    public void handle() {
        if (Gdx.input.justTouched()) {
            float x = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            float y = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;

            resumeButton.setToggled(resumeButton.getBounds().contains(x, y));
            musicButton.setToggled(musicButton.getBounds().contains(x, y));
            soundButton.setToggled(soundButton.getBounds().contains(x, y));
            exitMenuButton.setToggled(exitMenuButton.getBounds().contains(x, y));
        }

        if (Gdx.input.isTouched()) {
            xp = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            yp = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;
        }
        else {
            if (resumeButton.isToggled() && resumeButton.getBounds().contains(xp, yp)) {
                resumeButton.setToggled(false);
                game.assets.playSound(buttonSound, 1f);
                game.pauseScreen.pause();
                game.setScreen(game.gameScreen);
            }
            else if (musicButton.isToggled() && musicButton.getBounds().contains(xp, yp)) {
                if (game.prefs.getBoolean("musicAllowed", true)) {
                    game.prefs.putBoolean("musicAllowed", false).flush();
                    musicButton.setSprite(game.assets.getTexture("musicButtonOff"));
                }
                else {
                    game.prefs.putBoolean("musicAllowed", true).flush();
                    musicButton.setSprite(game.assets.getTexture("musicButtonOn"));
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
            else if (exitMenuButton.isToggled() && exitMenuButton.getBounds().contains(xp, yp)) {
                exitMenuButton.setToggled(false);
                game.assets.playSound(buttonSound, 1f);
                game.pauseScreen.pause();
                game.setScreen(game.menuScreen);
            }
        }

    }

    public void sync() {
        musicButton.setSprite(game.assets.getTexture(game.prefs.getBoolean("musicAllowed", true) ? "musicButtonOn" : "musicButtonOff"));
        soundButton.setSprite(game.assets.getTexture(game.prefs.getBoolean("soundAllowed", true) ? "soundButtonOn" : "soundButtonOff"));
    }

    public void draw(SpriteBatch batch) {
        background.draw(batch);
        resumeButton.draw(batch);
        musicButton.draw(batch);
        soundButton.draw(batch);
        exitMenuButton.draw(batch);
    }

    public void dispose() {
        buttonSound.dispose();
        game.dispose();
    }
}
