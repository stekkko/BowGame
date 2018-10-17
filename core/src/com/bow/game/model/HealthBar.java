package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HealthBar extends GameObject {

    private static TextureRegion[] textureRegions;
    private boolean shown;

    private float healthPoints;
    private float maxHealthPoints;
    private int percentHealthPoints;

    @Override
    public void handle() {
        super.handle();
        if (healthPoints < maxHealthPoints) show();
        if (percentHealthPoints != (int) (100f * healthPoints / maxHealthPoints)) {
            setPercentHealthPoints((int) (100f * healthPoints / maxHealthPoints));
            setSprite(textureRegions[percentHealthPoints]);
        }
    }

    HealthBar(float x, float y, float width, float height, float maxHealthPoints) {
        super(textureRegions[100], x, y, width, height);
        hide();
        this.maxHealthPoints = maxHealthPoints;
        setHealthPoints(maxHealthPoints);
        setPercentHealthPoints(100);
    }

    public void damage(float damage) {
        this.healthPoints -= damage;
        if (healthPoints < 0) {
            healthPoints = 0;
        }
    }

    public static void setTextureRegions(TextureAtlas textureAtlas) {
        textureRegions = new TextureRegion[101];
        for (int i = 0; i < 101; i++) {
            textureRegions[i] = textureAtlas.findRegion(String.valueOf(i));
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
}
