package com.bow.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

public abstract class Enemy extends GameObject {

    private HealthBar healthBar;
    private float damage;

    public Enemy(TextureRegion texture, float x, float y, float width, float height, float maxHealthPoints, float damage) {
        super(texture, x, y, width, height);
        this.healthBar = new HealthBar(x, y + height, width, width * 0.07843f, maxHealthPoints);
        this.damage = damage;
    }

    /**
     * Randomly spawn enemy in range of screen width
     * @param random random function
     * @param width of the screen
     * @param speedX horizontal speed
     * @param speedY vertical speed
     */
    public void randomSpawn(Random random, float width, float speedX, float speedY) {
        float height = width * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

        float min = -width / 2;
        float max = width / 2 - getWidth();
        float delta = max - min;
        this.setPosition(random.nextFloat() * (delta) + min, height / 2 + (random.nextFloat() * 5f));

        this.healthBar.setPosition(this.getX(), this.getY() - this.healthBar.getHeight());
        setSpeed(speedX, speedY);
        healthBar.setSpeed(speedX, speedY);
    }

    /**
     * Target spawn at any point on batch
     * @param x coordinate
     * @param y coordinate
     */
    public void targetSpawn(float x, float y, float speedX, float speedY) {
        this.setPosition(x, y);
        this.setSpeed(speedX, speedY);
        this.healthBar.setSpeed(speedX, speedY);
    }

    /**
     * Enemy has been damaged
     * @param value dealt damaged
     */
    public void damaged(float value) {
        healthBar.damage(value);
    }

    /**
     * Enemy has been moved backwards
     * @param dist distance
     */
    public void repel(float dist) {
        this.setPosition(this.getX(), this.getY() + dist);
    }


    @Override
    public void handle() {
        super.handle();
        healthBar.handle();

        healthBar.setSpeedX(this.speedX);
        healthBar.setSpeedY(this.speedY);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        healthBar.draw(batch);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        healthBar.setPosition(x, y - this.healthBar.getHeight());
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    public float getHealthPoints() {
        return healthBar.getHealthPoints();
    }

    public float getPercentHealthPoints() {
        return healthBar.getPercentHealthPoints();
    }

    public float getMaxHealthPoints() {
        return healthBar.getMaxHealthPoints();
    }
}
