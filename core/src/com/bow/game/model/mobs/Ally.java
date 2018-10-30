package com.bow.game.model.mobs;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Ally extends Mob {
    public Ally(TextureRegion texture, float x, float y, float width, float height, float maxHealthPoints, float damage, float repelPower, boolean repelable) {
        super(texture, x, y, width, height, maxHealthPoints, damage, repelPower, repelable);
    }
}
