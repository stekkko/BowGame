package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Wall extends GameObject {

    private HealthBar healthBar;
    private boolean broken;

    public Wall(TextureRegion texture, float x, float y, float width, float height, float maxHealthPoints, float hpWidth) {
        super(texture, x, y, width, height);

        this.healthBar = new HealthBar(x, y - hpWidth * 0.04f, hpWidth, hpWidth * 0.03f, maxHealthPoints);
        healthBar.show();
        broken = false;
    }

    public void damaged(float value) {
        this.healthBar.damage(value);
    }

    public void brake(TextureRegion textureRegion) {
        broken = true;
        this.setSprite(textureRegion);
    }

    public void repair(TextureRegion textureRegion) {
        healthBar.setHealthPoints(healthBar.getMaxHealthPoints());
        broken = false;
        this.setSprite(textureRegion);
    }

    @Override
    public void handle() {
        super.handle();
        healthBar.handle();
    }

    public float getPercentHealthPoints() {
        return healthBar.getPercentHealthPoints();
    }

    public float getHealthPoints() {
        return healthBar.getHealthPoints();
    }

    public boolean isBroken() {
        return broken;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        healthBar.draw(batch);
    }

}
