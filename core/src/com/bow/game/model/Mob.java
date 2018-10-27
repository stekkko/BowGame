package com.bow.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

public abstract class Mob extends DynamicGameObject {

    protected HealthBar healthBar;
    private float damage;
    private float repelPower;
    private boolean repelable;

    public Mob(TextureRegion texture, float x, float y, float width, float height, float maxHealthPoints, float damage, float repelPower, boolean repelable) {
        super(texture, x, y, width, height);
        this.healthBar = new HealthBar(x, y + height, width, width * 0.07843f, maxHealthPoints);
        this.damage = damage;
        this.repelPower = repelPower;
        setRepelable(repelable);
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
        setPosition(random.nextFloat() * (delta) + min, height / 2 + (random.nextFloat() * 5f));

        if (healthBar != null) this.healthBar.setPosition(this.getX(), this.getY() - this.healthBar.getHeight());
        setSpeed(speedX, speedY);
    }

    /**
     * Target spawn at any point on batch
     * @param x coordinate
     * @param y coordinate
     */
    public void targetSpawn(float x, float y, float speedX, float speedY) {
        this.setPosition(x, y);
        this.setSpeed(speedX, speedY);
        if (healthBar != null) healthBar.setSpeed(speedX, speedY);
    }

    /**
     * Enemy has been damaged
     * @param value dealt damaged
     */
    public void damaged(float value) {
        if (healthBar != null) healthBar.damage(value);
    }

    /**
     * Enemy has been moved backwards
     * @param dist distance
     */
    public void repel(float dist) {
        if (isRepelable()) setPosition(getX(), getY() + dist);
    }


    @Override
    public void handle(float dt) {
        super.handle(dt);
        if (healthBar != null) {
            healthBar.handle(dt);
            healthBar.setSpeedX(getSpeedX());
            healthBar.setSpeedY(getSpeedY());
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        if (healthBar != null) healthBar.draw(batch);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        if (healthBar != null) healthBar.setPosition(x, y - healthBar.getHeight());
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    public float getRepelPower() {
        return repelPower;
    }

    public float getHealthPoints() {
        return healthBar == null ? 0 : healthBar.getHealthPoints();
    }

    public float getPercentHealthPoints() {
        return healthBar == null ? 0 : healthBar.getPercentHealthPoints();
    }

    public void setRepelable(boolean repelable) {
        this.repelable = repelable;
    }

    public boolean isRepelable() {
        return repelable;
    }
}
