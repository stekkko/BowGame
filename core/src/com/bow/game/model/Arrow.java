package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Arrow extends Ammo {

    public Arrow(TextureRegion texture, float x, float y, float width, float height, float damage, float critChance, float critDamage, float repelDist, float flyingSPD) {
        super(texture, x, y, width, height);
        this.critChance = critChance;
        this.critDamage = critDamage;
        this.damage = damage;
        this.repelDist = repelDist;
        this.flyingSpeed = flyingSPD;
        this.loadedVisible = true;
    }
}
