package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.bow.game.view.GameScreen;

public class Arrow extends GameObject {

    private boolean shooted;
    private boolean readyToDelete;
    private float damage;

    public Arrow(TextureRegion texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        shooted = false;
        readyToDelete = false;
        damage = 21f;
    }

    public void shoot() {
        shooted = true;
        speedY = 24f;
    }

    public void delete() {
        readyToDelete = true;
    }

    public boolean isReadyToDelete() {
        return readyToDelete;
    }

    public float getDamage() {
        return damage;
    }

    public boolean isShooted() {
        return shooted;
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
