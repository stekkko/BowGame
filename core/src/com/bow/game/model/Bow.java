package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.bow.game.view.GameScreen;

public class Bow extends GameObject {

    private float arrowLoadInterval;
    private float time;
    private Arrow arrow;
    private boolean loaded;

    public Bow(TextureRegion texture, Arrow arrow, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        arrowLoadInterval = 100f;
        time = 0;
        this.arrow = arrow;
        loaded = true;
    }

    public void shoot() {
        loaded = false;
    }

    @Override
    public void handle() {
        super.handle();
        arrow.handle();

        if (!isLoaded()) {
            time += GameScreen.deltaCff * 290f;
            if (time > arrowLoadInterval) {
                loaded = true;
                time = 0;
            }
        }
    }

    public Arrow getArrow() {
        return arrow;
    }

    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        if (isLoaded()) {
            arrow.draw(batch);
        }
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        arrow.setPosition(x + this.getWidth() / 2 - arrow.getWidth() / 2, y);
    }
}
