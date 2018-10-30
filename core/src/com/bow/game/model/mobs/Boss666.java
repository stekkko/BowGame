package com.bow.game.model.mobs;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Boss666 extends Enemy {
    public Boss666(TextureRegion texture, float x, float y, float width, float height, float maxHealthPoints, float damage) {
        super(texture, x, y, width, height, maxHealthPoints, damage, 0f, 200, false);
    }
}
