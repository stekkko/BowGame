package com.bow.game.model.mobs;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.model.Animation;
import com.bow.game.model.Enemy;

public class Zombie extends Enemy {

    private static TextureRegion[][] textures;
    private Animation animation;

    public Zombie(float x, float y, float width, float height, float maxHealthPoints, float damage) {
        super(textures[0][0], x, y, width, height, maxHealthPoints, damage);
        animation = new Animation(0.4f, textures.length, textures[0].length);
    }

    public static void setTextures(TextureRegion[][] texts) {
        textures = texts;
    }

    @Override
    public void damaged(float value) {
        super.damaged(value);
        if (getPercentHealthPoints() <= 66 && animation.getState() < 1) animation.update();
        if (getPercentHealthPoints() <= 33 && animation.getState() < 2) animation.update();
    }

    @Override
    public void handle() {
        super.handle();
        animation.handle();
    }

    @Override
    public void draw(SpriteBatch batch) {
        setSprite(textures[animation.getState()][animation.getFrame()]);
        super.draw(batch);
    }
}
