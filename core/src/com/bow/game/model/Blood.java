package com.bow.game.model;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Blood extends GameObject {

    private static TextureRegion textureRegion;

    public Blood(float x, float y, float width, float height) {
        super(textureRegion, x, y, width, height);
    }

    public static void setTextureRegion(TextureRegion texture) {
        textureRegion = texture;
    }
}
