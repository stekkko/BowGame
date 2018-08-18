package com.bow.game.model;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;


public abstract class GameObject {
    Sprite sprite;
    Polygon bounds;

    float speedX;
    float speedY;

    public GameObject(TextureRegion texture, float x, float y, float width, float height) {
        sprite = new Sprite(texture);
        sprite.setSize(width, height);
        sprite.setOrigin(width / 2f, height / 2f);

        bounds = new Polygon(new float[]{0f, 0f, width, 0f, width, height, 0f, height});
        bounds.setPosition(x, y);
        bounds.setOrigin(width / 2f, height / 2f);

        speedX = 0f;
        speedY = 0f;
    }

    public void draw(SpriteBatch batch) {
        sprite.setPosition(bounds.getX(), bounds.getY());
        sprite.setRotation(bounds.getRotation());
        sprite.draw(batch);
    }

    public float getX() {
        return bounds.getX();
    }

    public float getY() {
        return bounds.getY();
    }

    public float getSpeedX() {
        return speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public float getWidth() { return sprite.getWidth(); }

    public float getHeight() { return sprite.getHeight(); }

    public void setPosition(float x, float y) {
        bounds.setPosition(x, y);
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public Polygon getBounds() {
        return this.bounds;
    }
}
