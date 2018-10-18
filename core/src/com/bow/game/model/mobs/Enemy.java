package com.bow.game.model.mobs;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.model.Mob;

public class Enemy extends Mob {
    public Enemy(TextureRegion texture, float x, float y, float width, float height, float maxHealthPoints, float damage, float repelPower, boolean repelable) {
        super(texture, x, y, width, height, maxHealthPoints, damage, repelPower, repelable);
    }
}
