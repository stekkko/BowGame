package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.model.mobs.Ally;

public class Explosion extends Ally {

    private static TextureRegion[][] textures;
    private Animation animation;
    private boolean readyToDelete;

    public Explosion(float x, float y, float width, float height, float damage) {
        super(textures[0][0], x, y, width, height, 1f, damage, 0f, false);
        animation = new Animation(0.04f, 1, 12, Animation.NORMAL);
        this.healthBar = null;
        setReadyToDelete(false);
    }

    @Override
    public void handle() {
        super.handle();
        animation.handle();
        if (animation.getFrame() == 12) setReadyToDelete(true);
    }

    public static void setTextureRegions(TextureRegion textureRegions) {
        textures = new TextureRegion[1][12];
        int eWidth = 256;
        int eHeight = 128;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 12; j++) {
                textures[i][j] = new TextureRegion(textureRegions, j * eWidth, i * eHeight, eWidth, eHeight);
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        setSprite(textures[animation.getState()][animation.getFrame()]);
        super.draw(batch);
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
