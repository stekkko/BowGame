package com.bow.game.model;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.model.mobs.Enemy;

import java.util.Random;

public class Blood extends GameObject {

    private static TextureRegion textureRegion;

    public Blood(float x, float y, float width, float height) {
        super(textureRegion, x, y, width, height);
    }

    public Blood(Enemy enemy, Random random) {
        this(enemy.getX() - enemy.getWidth() / 2 - 0.5f + random.nextFloat(),
                enemy.getY(), enemy.getWidth() * 2, enemy.getWidth() * 2);
        getBounds().setRotation(random.nextFloat() * 360f);
        getSprite().setAlpha(0.7f);
    }

    public static void setTextureRegion(TextureRegion texture) {
        textureRegion = texture;
    }
}
