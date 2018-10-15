package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HealthBar extends GameObject {

    private boolean shown;
    private boolean needUpdate;
    private boolean dead;

    private float healthPoints;
    private float maxHealthPoints;
    private int percentHealthPoints;

    @Override
    public void handle() {
        super.handle();
    }

    public HealthBar(TextureRegion texture, float x, float y, float width, float height, float maxHealthPoints) {
        super(texture, x, y, width, height);
        shown = false;
        needUpdate = false;
        this.maxHealthPoints = maxHealthPoints;
        this.healthPoints = maxHealthPoints;
        this.percentHealthPoints = 100;
    }

    public void update(TextureAtlas textureAtlas) {
        if (healthPoints < maxHealthPoints) show();
        if (percentHealthPoints != (int) (100f * healthPoints / maxHealthPoints)) {
            setPercentHealthPoints((int) (100f * healthPoints / maxHealthPoints));
            setNeedUpdate(true);
            setSprite(textureAtlas.findRegion(Integer.toString(percentHealthPoints)));
        }

    }

    public void damage(float damage) {
        this.healthPoints -= damage;
        this.setNeedUpdate(true);
        if (healthPoints < 0) {
            healthPoints = 0;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (isShown())
            super.draw(batch);
    }

    public void setHealthPoints(float healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void setPercentHealthPoints(int percentHealthPoints) {
        this.percentHealthPoints = percentHealthPoints;
    }

    public float getHealthPoints() {
        return healthPoints;
    }

    public float getMaxHealthPoints() {
        return maxHealthPoints;
    }

    public int getPercentHealthPoints() {
        return percentHealthPoints;
    }

    public boolean isShown() {
        return shown;
    }

    public void show() {
        shown = true;
    }

    public void hide() { shown = false; }

    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }
}
