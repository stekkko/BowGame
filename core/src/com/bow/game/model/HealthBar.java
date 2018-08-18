package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HealthBar extends GameObject {

    private boolean shown;

    @Override
    public void handle() {
        super.handle();
    }

    public HealthBar(TextureRegion texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        shown = false;
    }

    public boolean isShown() {
        return shown;
    }

    public void show() {
        shown = true;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (isShown())
            super.draw(batch);
    }
}
