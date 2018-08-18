package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Background extends GameObject {
    public Background(TextureRegion texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }
}
