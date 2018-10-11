package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.model.GameObject;
import com.bow.game.model.HealthBar;

public class Enemy extends GameObject {

//    TODO
    private float healthPoints;
    private float maxHealthPoints;
    private int percentHealthPoints;
    public HealthBar healthBar;

    public Enemy(TextureRegion texture, TextureAtlas HPtextureAtlas, float x, float y, float width, float height, float maxHealthPoints) {
        super(texture, x, y, width, height);
        this.maxHealthPoints = maxHealthPoints;
        this.healthPoints = maxHealthPoints;
        this.percentHealthPoints = 100;
        this.healthBar = new HealthBar(HPtextureAtlas.findRegion("100"), x, y + height, width, width * 0.07843f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        healthBar.draw(batch);
    }

    public void damage(float value) {
        this.healthPoints -= value;
        if (healthPoints < 0) healthPoints = 0;
    }

    @Override
    public void handle() {
        super.handle();
        healthBar.handle();

        healthBar.setSpeedX(this.speedX);
        healthBar.setSpeedY(this.speedY);

        if (healthPoints < maxHealthPoints) healthBar.show();
        if (percentHealthPoints != (int) (100f * healthPoints / maxHealthPoints)) {
            percentHealthPoints = (int) (100f * healthPoints / maxHealthPoints);
            healthBar.setHealth(percentHealthPoints);
            healthBar.setNeedUpdate(true);
        }
    }

    public float getPerHealthPoints() {
        return percentHealthPoints;
    }

    public float getHealthPoints() {
        return healthPoints;
    }

    public float getMaxHealthPoints() {
        return healthPoints;
    }
}
