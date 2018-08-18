package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Wall extends GameObject {

    private boolean broken;

    public Wall(TextureRegion texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        broken = false;
    }

    public void brake(TextureRegion textureRegion) {
        broken = true;
        this.setSprite(textureRegion);
    }

    public void repair(TextureRegion textureRegion) {
        broken = false;
        this.setSprite(textureRegion);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    public boolean isBroken() {
        return broken;
    }

}
