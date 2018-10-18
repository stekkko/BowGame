package com.bow.game.control;

import com.badlogic.gdx.Gdx;
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

    private Background background;
    private Button playButton;
    private Button musicButton;
    private Button soundButton;
    private Button exitButton;

    private float width = MainMenuScreen.cameraWidth;
    private float height = width * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

    public MainMenuController(BowGame game, Assets assets) {
        this.game = game;
        this.assets = assets;

        background = new Background(assets.getTexture("menuBack"),
                -width / 2,  -height / 2, 1.6f * height, height);
        playButton = new Button(assets.getTexture("playButton"),
                -2f * 2.9f, 0, 4f * 2.9f, 4f);
        musicButton = new Button(assets.getTexture(game.isMusicAllowed() ? "musicButtonOn" : "musicButtonOff"),
                -2f * 2.9f, -5f, 4f * 1.275f, 4f);
        soundButton = new Button(assets.getTexture(game.isSoundsAllowed() ? "soundButtonOn" : "soundButtonOff"),
                2f * 2.9f - 4f * 1.275f, -5f, 4f * 1.275f, 4f);
        exitButton = new Button(assets.getTexture("exitButton"),
                -2f * 2.9f, -10f, 4f * 2.9f, 4f);
        buttonSound = Gdx.audio.newSound(Gdx.files.internal("soundButton.ogg"));
    }

    public void handle() {
        background.handle();
        playButton.handle();
        musicButton.handle();
        soundButton.handle();

        if (Gdx.input.isTouched()) {
            float x = (float) Gdx.input.getX() / Gdx.graphics.getWidth() * width - width / 2;
            float y = height - (float) Gdx.input.getY() / Gdx.graphics.getHeight() * height - height / 2;

            playButton.setToggled(playButton.getBounds().contains(x, y));
            musicButton.setToggled(musicButton.getBounds().contains(x, y));
            soundButton.setToggled(soundButton.getBounds().contains(x, y));
            exitButton.setToggled(exitButton.getBounds().contains(x, y));
        }
        else {
            if (playButton.isToggled()) {
                playButton.setToggled(false);
                if (game.isSoundsAllowed()) buttonSound.play();
                game.menuScreen.pause();
                game.setScreen(game.levelSelector);
            }
            if (musicButton.isToggled()) {
                if (game.isMusicAllowed()) {
                    game.setMusicAllowed(false);
                    musicButton.setSprite(assets.getTexture("musicButtonOff"));
                }
                else {
                    game.setMusicAllowed(true);
                    musicButton.setSprite(assets.getTexture("musicButtonOn"));
                }
                musicButton.setToggled(false);
                if (game.isSoundsAllowed()) buttonSound.play();
            }
            if (soundButton.isToggled()) {
                if (game.isSoundsAllowed()) {
                    game.setSoundsAllowed(false);
                    soundButton.setSprite(assets.getTexture("soundButtonOff"));
                }
                else {
                    game.setSoundsAllowed(true);
                    soundButton.setSprite(assets.getTexture("soundButtonOn"));
                }
                soundButton.setToggled(false);
                if (game.isSoundsAllowed()) buttonSound.play();
            }
            if (exitButton.isToggled()) {
                exitButton.setToggled(false);
                if (game.isSoundsAllowed()) buttonSound.play();
                System.gc();
                System.exit(0);

            }
        }
    }

    public void sync() {
        musicButton.setSprite(assets.getTexture(game.isMusicAllowed() ? "musicButtonOn" : "musicButtonOff"));
        soundButton.setSprite(assets.getTexture(game.isSoundsAllowed() ? "soundButtonOn" : "soundButtonOff"));
    }

    public void draw(SpriteBatch batch) {
        background.draw(batch);
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
