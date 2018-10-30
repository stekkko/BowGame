package com.bow.game.model.weapons;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.model.ammo.Ammo;

public class Bow extends Weapon {
    public Bow(TextureRegion texture, float x, float y, float width, float height, Ammo ammo, float arrowLoadInterval) {
        super(texture, x, y, width, height, 1);
        setAmmo(ammo);
        setAmmoReloadInterval(arrowLoadInterval);
        setAmmoFireInterval(arrowLoadInterval);
        setLoadedVisible(true);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        amExample.setPosition(x + this.getWidth() / 2 - amExample.getWidth() / 2, y);
    }
}
