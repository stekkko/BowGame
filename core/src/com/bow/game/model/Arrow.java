package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

public class Arrow extends GameObject {

    private boolean readyToDelete;
    private float damage;
    private float repelDist;
    private float critChance;
    private float critDamage;

    public Arrow(TextureRegion texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        readyToDelete = false;
        critChance = 0.1f;
        critDamage = 100f;
        damage = 34f;
        repelDist = 0.3f;
    }

    public void shoot() {
        speedY = 25f;
    }

    public void delete() {
        readyToDelete = true;
    }

    public boolean isReadyToDelete() {
        return readyToDelete;
    }

    public float getCritChance() {
        return critChance;
    }

    public float getCritDamage() {
        return critDamage;
    }

    public float getDamage() {
        return damage;
    }

    public float getRepelDist() {
        return repelDist;
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
