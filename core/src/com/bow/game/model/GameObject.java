package com.bow.game.model;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

public class GameObject {
    private Sprite sprite;
    private Polygon bounds;

    public GameObject(TextureRegion texture, float x, float y, float width, float height) {
        sprite = new Sprite(texture);
        sprite.setSize(width, height);
        sprite.setOrigin(width / 2f, height / 2f);

        bounds = new Polygon(new float[]{0f, 0f, width, 0f, width, height, 0f, height});
        bounds.setPosition(x, y);
        bounds.setOrigin(width / 2f, height / 2f);
    }

    public void draw(SpriteBatch batch) {
        sprite.setPosition(bounds.getX(), bounds.getY());
        sprite.setRotation(bounds.getRotation());
        sprite.draw(batch);
    }

    public void setSprite(TextureRegion textureRegion) {
        float width = this.getWidth();
        float height = this.getHeight();
        sprite = new Sprite(textureRegion);
        sprite.setSize(width, height);
        sprite.setOrigin(width / 2f, height / 2f);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getX() {
        return bounds.getX();
    }

    public float getY() {
        return bounds.getY();
    }

    public float getWidth() { return sprite.getWidth(); }

    public float getHeight() { return sprite.getHeight(); }

    public void setPosition(float x, float y) {
        bounds.setPosition(x, y);
    }

    public Polygon getBounds() {
        return this.bounds;
    }
}
