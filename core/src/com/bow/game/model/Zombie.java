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
        this.setSpeedY(-2f + random.nextFloat() * 0.5f);
    }

    public void repel(float dist) {
        this.setPosition(this.getX(), this.getY() + dist);
    }

    @Override
    public void handle() {
        super.handle();
    }

    public void update(TextureAtlas HPtextureAtlas) {
        if (healthBar.isNeedUpdate()) {
            healthBar.update(HPtextureAtlas);
            healthBar.setNeedUpdate(false);
        }
    }

    @Override
    public void damage(float value) {
        super.damage(value);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        healthBar.setPosition(x, y - this.healthBar.getHeight());
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }
}
