package com.bow.game.model.ammo;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.model.Animation;

import java.util.Random;

public class Arrow extends Ammo {

    private static TextureRegion[][] textures;
    private Animation animation;

    public Arrow(float x, float y, float width, float height, float damage, float critChance, float critDamage, float repelDist, float flyingSPD, Random random) {
        super(textures[0][0], x, y, width, height);
        setCriticalChance(critChance);
        setCriticalDamage(critDamage);
        setDamage(damage);
        setFlyingSpeed(flyingSPD);
        setRepelDist(repelDist);

        animation = new Animation(0.1f, 1, 4, Animation.LOOP);
        animation.setRandomTime(random);
        animation.setFrame(random.nextInt(4));
    }

    @Override
    public void shoot() {
        super.shoot();
    }

    @Override
    public void handle(float dt) {
        super.handle(dt);
        animation.handle(dt);
    }

    public static void setTextures(TextureRegion texture) {
        textures = new TextureRegion[1][4];
        int aWidth = 27;
        int aHeight = 146;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                textures[i][j] = new TextureRegion(texture, j * aWidth, i * aHeight, aWidth, aHeight);
            }
        }
    }

    @Override
    public Arrow copy() {
        return new Arrow(getX(), getY(), getWidth(), getHeight(),
                getDamage(), getCriticalChance(), getCriticalDamage(), getRepelDist(), getFlyingSpeed(), new Random());
    }

    @Override
    public void draw(SpriteBatch batch) {
        setSprite(textures[animation.getState()][animation.getFrame()]);
        super.draw(batch);
    }
}
