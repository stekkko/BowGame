package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.view.GameScreen;

public class Explosion extends GameObject {

    private float lifeTime;
    private float time;
    private float damage;
    private boolean readyToDelete;

    public Explosion(TextureRegion texture, float x, float y, float width, float height, float damage) {
        super(texture, x, y, width, height);
        readyToDelete = false;
        time = 0;
        lifeTime = 200f;
        setDamage(damage);
    }

    @Override
    public void handle() {
        super.handle();

        if (!readyToDelete) {
            time += GameScreen.deltaCff * 320f;
            if (time > lifeTime) {
                readyToDelete = true;
                time = 0;
            }
        }
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(float lifeTime) {
        this.lifeTime = lifeTime;
    }

    public boolean isReadyToDelete() {
        return readyToDelete;
    }

    public void setReadyToDelete(boolean readyToDelete) {
        this.readyToDelete = readyToDelete;
    }
}
