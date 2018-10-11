package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

public class Ammo extends GameObject {

    boolean readyToDelete;
    boolean loadedVisible;
    float damage;
    float flyingSpeed;
    float repelDist;
    float critChance;
    float critDamage;

    public Ammo(TextureRegion texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        readyToDelete = false;
    }

    public void shoot() {
        speedY = flyingSpeed;
    }

    public void delete() {
        readyToDelete = true;
    }

    public boolean isReadyToDelete() {
        return readyToDelete;
    }

    public void setFlyingSpeed(float flyingSpeed) {
        this.flyingSpeed = flyingSpeed;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public void setRepelDist(float repelDist) {
        this.repelDist = repelDist;
    }

    public void setCritChance(float critChance) {
        this.critChance = critChance;
    }

    public void setCritDamage(float critDamage) {
        this.critDamage = critDamage;
    }

    public float getFlyingSpeed() {
        return flyingSpeed;
    }

    public float getCritChance() {
        return critChance;
    }

    public float getCritDamage() {
        return critDamage;
    }

    public float getDamage() {
        return damage;
    }

    public float getRepelDist() {
        return repelDist;
    }

    public boolean isLoadedVisible() {
        return loadedVisible;
    }

    public void setLoadedVisible(boolean loadedVisible) {
        this.loadedVisible = loadedVisible;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (loadedVisible) super.draw(batch);
    }

}
