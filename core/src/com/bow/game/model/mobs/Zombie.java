package com.bow.game.model.mobs;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.model.Animation;

import java.util.Random;

public class Zombie extends Enemy {

    private static TextureRegion[][] textures;
    private Animation animation;

    public Zombie(float x, float y, float width, float height, float maxHealthPoints, float damage, float repelPower) {
        super(textures[0][0], x, y, width, height, maxHealthPoints, damage, repelPower, true);
        animation = new Animation(0.2f, 3, 9, Animation.LOOP);
    }

    @Override
    public void randomSpawn(Random random, float width, float speedX, float speedY) {
        super.randomSpawn(random, width, speedX, speedY);
        animation.setRandomTime(random);
        animation.setFrame(random.nextInt(9));
    }

    @Override
    public void targetSpawn(float x, float y, float speedX, float speedY) {
        super.targetSpawn(x, y, speedX, speedY);
    }

    public static void setTextures(TextureRegion texture) {
        textures = new TextureRegion[3][9];
        int zWidth = 125;
        int zHeight = 140;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                textures[i][j] = new TextureRegion(texture, j * zWidth, i * zHeight, zWidth, zHeight);
            }
        }
    }

    @Override
    public void damaged(float value) {
        super.damaged(value);
    }

    @Override
    public void handle() {
        super.handle();
        animation.handle();
        if (getPercentHealthPoints() <= 66 && animation.getState() == 0) animation.update();
        if (getPercentHealthPoints() <= 33 && animation.getState() == 1) animation.update();
    }

    @Override
    public void draw(SpriteBatch batch) {
        setSprite(textures[animation.getState()][animation.getFrame()]);
        super.draw(batch);
    }
}
