package com.bow.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.bow.game.BowGame;
import com.bow.game.model.Background;
import com.bow.game.model.Button;
import com.bow.game.view.PauseMenuScreen;

public class PauseMenuController {
    private BowGame game;
    private TextureAtlas textureAtlas;

    private Sound buttonSound;

    private Background background;
    private Button resumeButton;
    private Button musicButton;
    private Button soundButton;
    private Button exitMainMenuButton;

    private float width = PauseMenuScreen.cameraWidth;
    private float height = width * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

    public PauseMenuController(BowGame game, TextureAtlas textureAtlas) {
        this.game = game;
        this.textureAtlas = textureAtlas;

        background = new Background(textureAtlas.findRegion("menuBack"),
                -width / 2,  -height / 2, 1.6f * height, height);
        resumeButton = new Button(textureAtlas.findRegion("resumeButton"),
                -2f * 2.9f, 0, 4f * 2.9f, 4f);
        musicButton = new Button(textureAtlas.findRegion(game.isMusicAllowed() ? "musicButtonOn" : "musicButtonOff"),
                -2f * 2.9f, -5f, 4f * 1.275f, 4f);
        soundButton = new Button(textureAtlas.findRegion(game.isSoundsAllowed() ? "soundButtonOn" : "soundButtonOff"),
                2f * 2.9f - 4f * 1.275f, -5f, 4f * 1.275f, 4f);
        exitMainMenuButton = new Button(textureAtlas.findRegion("mainMenuButton"),
                -2f * 2.9f, -10f, 4f * 2.9f, 4f);
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("soundButton.ogg"));
    }

    public void handle() {
        background.handle();
        resumeButton.handle();
        musicButton.handle();
        soundButton.handle();

        if (Gdx.input.isTouched()) {
            float x = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            float y = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;

            resumeButton.setToggled(resumeButton.getBounds().contains(x, y));
            musicButton.setToggled(musicButton.getBounds().contains(x, y));
            soundButton.setToggled(soundButton.getBounds().contains(x, y));
            exitMainMenuButton.setToggled(exitMainMenuButton.getBounds().contains(x, y));
        }
        else {
            if (resumeButton.isToggled()) {
                resumeButton.setToggled(false);
                if (game.isSoundsAllowed()) buttonSound.play();
                game.pauseScreen.pause();
                game.setScreen(game.gameScreen);
            }
            if (musicButton.isToggled()) {
                if (game.isMusicAllowed()) {
                    game.setMusicAllowed(false);
                    musicButton.setSprite(textureAtlas.findRegion("musicButtonOff"));
                }
                else {
                    game.setMusicAllowed(true);
                    musicButton.setSprite(textureAtlas.findRegion("musicButtonOn"));
                }
                musicButton.setToggled(false);
                if (game.isSoundsAllowed()) buttonSound.play();
            }
            if (soundButton.isToggled()) {
                if (game.isSoundsAllowed()) {
                    game.setSoundsAllowed(false);
                    soundButton.setSprite(textureAtlas.findRegion("soundButtonOff"));
                }
                else {
                    game.setSoundsAllowed(true);
                    soundButton.setSprite(textureAtlas.findRegion("soundButtonOn"));
                }
                soundButton.setToggled(false);
                if (game.isSoundsAllowed()) buttonSound.play();
            }
            if (exitMainMenuButton.isToggled()) {
                exitMainMenuButton.setToggled(false);
                if (game.isSoundsAllowed()) buttonSound.play();
                game.pauseScreen.pause();
                game.setScreen(game.menuScreen);
            }
        }

    }

    public void sync() {
        musicButton.setSprite(textureAtlas.findRegion(game.isMusicAllowed() ? "musicButtonOn" : "musicButtonOff"));
        soundButton.setSprite(textureAtlas.findRegion(game.isSoundsAllowed() ? "soundButtonOn" : "soundButtonOff"));
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
        textureAtlas.dispose();
    }
}
