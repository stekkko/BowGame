package com.bow.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

public class Zombie extends Enemy {

    public Zombie(TextureRegion texture, float x, float y, float width, float height, float healthPoints) {
        super(texture, x, y, width, height, healthPoints);
    }

    public void respawn(float PTM, Random random) {
        float min = -Gdx.graphics.getWidth() / 2 / PTM;
        float maxMmin = Gdx.graphics.getWidth() / PTM - this.getWidth();
        this.setPosition(random.nextFloat() * maxMmin + min, Gdx.graphics.getHeight() / 2 / PTM);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }
}
