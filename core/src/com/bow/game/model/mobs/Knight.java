package com.bow.game.model.mobs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.model.Enemy;

public class Knight extends Enemy {
    public Knight(TextureRegion texture, float x, float y, float width, float height, float maxHealthPoints, float damage) {
        super(texture, x, y, width, height, maxHealthPoints, damage);
    }
}
