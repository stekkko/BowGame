package com.bow.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.bow.game.BowGame;
import com.bow.game.model.GameObject;
import com.bow.game.model.ui.TypingText;
import com.bow.game.screens.EndGameScreen;

public class EndLevelController implements Controller {
    BowGame game;
    GameObject background;

    private TypingText resultText;

    private float xp = 0;
    private float yp = 0;

    private float width;
    private float height;

    public EndLevelController(BowGame game) {
        this.game = game;
        width = EndGameScreen.cameraWidth;
        height = width * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
        background = new GameObject(game.assets.getTexture("background2"),
                -width,  -height / 2, 1.6f * height, height);
        resultText = new TypingText(game.prefs.getString("endText"), 0.15f);
    }

    @Override
    public void handle(float dt) {
        resultText.handle(dt);
        if (Gdx.input.justTouched()) {
            game.assets.playMusic("theme", 0.15f);
            game.levelSelector.pause();
            game.setScreen(game.shopScreen);
            game.shopScreen.resume();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        background.draw(batch);
        resultText.draw(batch, game.assets.getFont("day"), -width / 2, 3f, width, Align.center, true);
    }

    @Override
    public void dispose() {

    }
}
