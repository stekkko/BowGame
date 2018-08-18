package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

public class Arrow extends GameObject {

    public boolean isShooted;

    public Arrow(TextureRegion texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        isShooted = false;
    }

    public void shoot() {
        isShooted = true;
        speedY = 24f;
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
