package com.bow.game.model.mobs;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Knight extends Ally {
    public Knight(TextureRegion texture, float x, float y, float width, float height, float maxHealthPoints, float damage, float repelPower) {
        super(texture, x, y, width, height, maxHealthPoints, damage, repelPower, true);
    }
}
