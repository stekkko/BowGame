package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bullet extends Ammo {
    public Bullet(TextureRegion texture, float x, float y, float width, float height, float damage, float critChance, float critDamage, float repelDist, float flyingSPD) {
        super(texture, x, y, width, height);
        setCriticalChance(critChance);
        setCriticalDamage(critDamage);
        setDamage(damage);
        setFlyingSpeed(flyingSPD);
        setRepelDist(repelDist);
    }

    @Override
    public Bullet copy() {
        return new Bullet(getSprite(), getX(), getY(), getWidth(), getHeight(),
                getDamage(), getCriticalChance(), getCriticalDamage(), getRepelDist(), getFlyingSpeed());
    }
}
