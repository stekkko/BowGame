package com.bow.game.model;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bow extends Weapon {

    public Bow(TextureRegion texture, float x, float y, float width, float height, Ammo ammo, float arrowLoadInterval) {
        super(texture, x, y, width, height, 1);
        setAmmo(ammo);
        instReload();
        setAmmoReloadInterval(arrowLoadInterval);
        setMaxAmmo(1);
        setLoadedVisible(true);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        amExample.setPosition(x + this.getWidth() / 2 - amExample.getWidth() / 2, y);
    }
}
