package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class DynamicGameObject extends GameObject {

    private float speedX;
    private float speedY;

    public DynamicGameObject(TextureRegion texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);

        speedX = 0f;
        speedY = 0f;
    }

    public void handle(float dt) {
        this.setPosition(getX() + getSpeedX() * dt, getY() + getSpeedY() * dt);
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public void setSpeed(float speedX, float speedY) {
        setSpeedX(speedX);
        setSpeedY(speedY);
    }
}
