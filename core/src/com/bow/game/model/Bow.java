package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

public class Bow extends GameObject {

    public Bow(TextureRegion texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    public float getX() {
        return super.getX();
    }

    @Override
    public float getY() {
        return super.getY();
    }

    @Override
    public Polygon getBounds() {
        return super.getBounds();
    }
}
