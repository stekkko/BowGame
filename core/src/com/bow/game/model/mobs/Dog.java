package com.bow.game.model.mobs;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Dog extends Enemy {

    private static TextureRegion texture;
    public static void setTextureRegion(TextureRegion textureRegion) {
        texture = textureRegion;
    }

    public Dog(float x, float y, float width, float height, float maxHealthPoints, float damage, float repelPower) {
        super(texture, x, y, width, height, maxHealthPoints, damage, repelPower, true);
    }


}
