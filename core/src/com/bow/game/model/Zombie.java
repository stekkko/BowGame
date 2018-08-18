package com.bow.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.view.GameScreen;

import java.util.Random;

public class Zombie extends Enemy {

    public Zombie(TextureRegion texture, TextureAtlas HPtextureAtlas, float x, float y, float width, float height, float healthPoints) {
        super(texture, HPtextureAtlas, x, y, width, height, healthPoints);
    }

    public void spawn(float width, Random random) {
        float height = width * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

        float min = -width / 2;
        float maxMmin = width - this.getWidth();
        this.setPosition(random.nextFloat() * maxMmin + min, height / 2);
        this.healthBar.setPosition(this.getX(), this.getY() - this.healthBar.getHeight());
        this.setSpeedY(-3f + random.nextFloat() * 2);

    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }
}
