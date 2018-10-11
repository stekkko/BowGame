package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.view.GameScreen;

public class Weapon extends GameObject {

    float ammoLoadInterval;
    int maxAmmo;
    private float time;
    private Ammo ammo;
    private boolean loaded;

    public Weapon(TextureRegion texture, float x, float y, float width, float height, Ammo ammo) {
        super(texture, x, y, width, height);
        this.ammo = ammo;
        time = 0f;
        loaded = false;
    }

    public void shoot() {
        ammo.shoot();
        loaded = false;
    }

    @Override
    public void handle() {
        super.handle();
        ammo.handle();

        if (!isLoaded()) {
            time += GameScreen.deltaCff * 320f;
            if (time > ammoLoadInterval) {
                loaded = true;
                time = 0;
            }
        }
    }

    public void setAmmo(Ammo ammo) {
        this.ammo = ammo;
    }

    public Ammo getAmmo() {
        return ammo;
    }

    public boolean isLoaded() {
        return loaded;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        if (isLoaded()) {
            ammo.draw(batch);
        }
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        ammo.setPosition(x + this.getWidth() / 2 - ammo.getWidth() / 2, y);
    }
}
