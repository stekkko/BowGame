package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

public abstract class Ammo extends DynamicGameObject {

    private boolean readyToDelete;
    private float damage;
    private float flyingSpeed;
    private float repelDist;
    private float criticalChance;
    private float criticalDamage;

    Ammo(TextureRegion texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        readyToDelete = false;
    }

    protected abstract Ammo copy();

    public static Ammo copy(Ammo ammo) {
        return ammo.copy();
    }

    public void shoot() {
        setSpeedY(flyingSpeed);
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

    public void setCriticalChance(float criticalChance) {
        this.criticalChance = criticalChance;
    }

    public void setCriticalDamage(float criticalDamage) {
        this.criticalDamage = criticalDamage;
    }

    public float getFlyingSpeed() {
        return flyingSpeed;
    }

    public float getCriticalChance() {
        return criticalChance;
    }

    public float getCriticalDamage() {
        return criticalDamage;
    }

    public float getDamage() {
        return damage;
    }

    public float getDamage(Random random) {
        return random.nextFloat() < getCriticalChance() ? getCriticalDamage() : getDamage();
    }

    public float getRepelDist() {
        return repelDist;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }
}
