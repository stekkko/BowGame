package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy extends GameObject {

    private float healthPoints;
    private float maxHealthPoints;
    private int percentHealthPoints;
    public HealthBar healthBar;
    private TextureAtlas HPtextureAtlas;

    public Enemy(TextureRegion texture, TextureAtlas HPtextureAtlas, float x, float y, float width, float height, float maxHealthPoints) {
        super(texture, x, y, width, height);
        this.maxHealthPoints = maxHealthPoints;
        this.healthPoints = maxHealthPoints;
        this.percentHealthPoints = 100;
        this.HPtextureAtlas = HPtextureAtlas;
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

    public TextureAtlas getHPtextureAtlas() {
        return HPtextureAtlas;
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
            healthBar.setSprite(HPtextureAtlas.findRegion(Integer.toString(percentHealthPoints)));
        }
    }

    public float getHealthPoints() {
        return healthPoints;
    }
}
