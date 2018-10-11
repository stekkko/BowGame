package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HealthBar extends GameObject {

    private boolean shown;
    private int health;
    private boolean needUpdate;

    @Override
    public void handle() {
        super.handle();
    }

    public HealthBar(TextureRegion texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        shown = false;
        needUpdate = false;
        health = 100;
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

    public void update(TextureAtlas textureAtlas) {
        this.setSprite(textureAtlas.findRegion(Integer.toString(health)));
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (isShown())
            super.draw(batch);
    }
}
