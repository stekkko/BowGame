package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Crosshair extends GameObject {

    private boolean drawn;

    public Crosshair(TextureRegion texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        setDrawn(false);
    }

    public boolean isDrawn() {
        return drawn;
    }

    public void setDrawn(boolean drawn) {
        this.drawn = drawn;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (isDrawn()) super.draw(batch);
    }
}
