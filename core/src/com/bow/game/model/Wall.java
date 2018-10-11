package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Wall extends GameObject {

    private float healthPoints;
    private float maxHealthPoints;
    private int percentHealthPoints;
    public HealthBar healthBar;
    private boolean broken;

    public Wall(TextureRegion texture, TextureAtlas HPtextureAtlas, float x, float y, float width, float height, float maxHealthPoints, float hpWidth) {
        super(texture, x, y, width, height);
        this.maxHealthPoints = maxHealthPoints;
        this.healthPoints = maxHealthPoints;
        this.percentHealthPoints = 100;
        this.healthBar = new HealthBar(HPtextureAtlas.findRegion("100"), x, y - hpWidth * 0.04f, hpWidth, hpWidth * 0.03f);
        healthBar.show();
        broken = false;
    }

    public void damage(float value) {
        this.healthPoints -= value;
    }

    public void brake(TextureRegion textureRegion) {
        broken = true;
        this.setSprite(textureRegion);
    }

    public void repair(TextureRegion textureRegion) {
        healthPoints = maxHealthPoints;
        broken = false;
        this.setSprite(textureRegion);
    }

    public void update(TextureAtlas HPtextureAtlas) {
        if (healthBar.isNeedUpdate()) {
            healthBar.update(HPtextureAtlas);
            healthBar.setNeedUpdate(false);
        }
    }

    @Override
    public void handle() {
        super.handle();
        healthBar.handle();
        if (healthPoints < 0) {
            percentHealthPoints = 0;
        }
        else if (percentHealthPoints != (int) (100f * healthPoints / maxHealthPoints)) {
            percentHealthPoints = (int) (100f * healthPoints / maxHealthPoints);

        }
        healthBar.setHealth(percentHealthPoints);
        healthBar.setNeedUpdate(true);
    }

    public float getPerHealthPoints() {
        return percentHealthPoints;
    }

    public float getHealthPoints() {
        return healthPoints;
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
