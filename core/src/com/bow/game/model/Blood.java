package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Blood extends GameObject {

    public Blood(TextureRegion texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }
}
