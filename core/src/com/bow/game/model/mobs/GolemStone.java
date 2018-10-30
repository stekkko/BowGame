package com.bow.game.model.mobs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GolemStone extends Enemy {

    private static TextureRegion texture;
    private boolean readyToDelete;

    public GolemStone(float x, float y) {
        super(texture, x, y, 1f, 1f, 0.1f, 7f, 0.1f, 0, false);
        setReadyToDelete(false);
    }

    @Override
    public void handle(float dt) {
        super.handle(dt);
        getBounds().setRotation(getBounds().getY() * 10);
    }

    @Override
    public void damaged(float value) {
        super.damaged(value);
        readyToDelete = true;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    public static void setTextures(TextureRegion tex) {
        texture = tex;
    }

    @Override
    public float getHealthPoints() {
        return isReadyToDelete() ? 0 : 1;
    }

    @Override
    public float getPercentHealthPoints() {
        return isReadyToDelete() ? 0 : 100;
    }

    public boolean isReadyToDelete() {
        return readyToDelete;
    }

    public void setReadyToDelete(boolean readyToDelete) {
        this.readyToDelete = readyToDelete;
    }
}
