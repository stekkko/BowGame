package com.bow.game.model;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Turret extends Weapon {
    public Turret(TextureRegion texture, float x, float y, float width, float height, Ammo ammo, int maxAmmo, float bulletLoadInterval, float bulletFireInterval) {
        super(texture, x, y, width, height, maxAmmo);
        setAmmo(ammo);
        setAmmoReloadInterval(bulletLoadInterval);
        setAmmoFireInterval(bulletFireInterval);
        setLoadedVisible(false);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        amExample.setPosition(x + this.getWidth() / 2 - amExample.getWidth() / 2, y + this.getHeight());
    }
}
