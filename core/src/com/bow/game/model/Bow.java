package com.bow.game.model;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bow extends Weapon {

    public Bow(TextureRegion texture, float x, float y, float width, float height, Ammo ammo, float arrowLoadInterval, int maxAmmo) {
        super(texture, x, y, width, height, ammo);
        this.ammoLoadInterval = arrowLoadInterval;
        this.maxAmmo = maxAmmo;
    }
}
