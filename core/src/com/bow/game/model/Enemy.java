package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy extends GameObject {

    float healthPoints;

    public Enemy(TextureRegion texture, float x, float y, float width, float height, float healthPoints) {
        super(texture, x, y, width, height);
        this.healthPoints = healthPoints;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }
}
