package com.bow.game.model.mobs;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.model.Mob;

public class Enemy extends Mob {

    private int value;

    public Enemy(TextureRegion texture, float x, float y, float width, float height, float maxHealthPoints, float damage, float repelPower, int value, boolean repelable) {
        super(texture, x, y, width, height, maxHealthPoints, damage, repelPower, repelable);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
