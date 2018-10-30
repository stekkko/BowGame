package com.bow.game.model.mobs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.model.Animation;
import com.bow.game.model.GameObject;

import java.util.Random;

public class Golem extends Enemy {

    private static TextureRegion[][] textures;
    private static TextureRegion[][] texturesLegs;
    private GameObject legs;
    private Animation aniLegs;
    private Animation aniArms;
    private float stopY;
    private boolean moving;
    private boolean readyToShoot;
    private boolean shooted;
    private boolean rightHandThrow;

    public Golem(float x, float y, float width, float height, float maxHealthPoints, float damage, float repelPower) {
        super(textures[0][0], x, y, width, height, maxHealthPoints, damage, repelPower, 50, false);
        legs = new GameObject(texturesLegs[0][0], x, y, width, height);
        rightHandThrow = true;
        setReadyToShoot(false);
        aniArms = new Animation(0.3f, 1, 8, Animation.LOOP);
        aniLegs = new Animation(0.3f, 1, 6, Animation.LOOP);
        setMoving(true);
        stopY = y - 10f;
    }

    @Override
    public void randomSpawn(Random random, float width, float speedX, float speedY) {
        super.randomSpawn(random, width, speedX, speedY);
        stopY = getY() - 10f;
    }

    @Override
    public void targetSpawn(float x, float y, float speedX, float speedY) {
        super.targetSpawn(x, y, speedX, speedY);
        stopY = getY() - 10f;
    }

    public void stop() {
        setMoving(false);
        setSpeed(0f, 0f);
    }

    public GolemStone throwStone() {
        GolemStone stone = new GolemStone(getX() + getWidth() / 2 + (rightHandThrow ? 1f : -2.5f), getY());
        stone.setSpeedY(-10f);
        rightHandThrow = !rightHandThrow;
        setReadyToShoot(false);
        setShooted(true);
        return stone;
    }

    @Override
    public void handle(float dt) {
        super.handle(dt);
        if (isMoving()) aniLegs.handle(dt);
        else aniArms.handle(dt);

        if (!isShooted() && (aniArms.getFrame() == 2 || aniArms.getFrame() == 6)) {
            setReadyToShoot(true);
            setShooted(true);
        }
        else if (aniArms.getFrame() != 2 && aniArms.getFrame() != 6){
            setReadyToShoot(false);
            setShooted(false);
        }
    }

    public static void setTextures(TextureRegion tex, TextureRegion texLegs) {
        textures = new TextureRegion[1][8];
        texturesLegs = new TextureRegion[1][6];
        int gWidth = 120;
        int gHeight = 53;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 8; j++) {
                textures[i][j] = new TextureRegion(tex, j * gWidth, i * gHeight, gWidth, gHeight);
            }
        }
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 6; j++) {
                texturesLegs[i][j] = new TextureRegion(texLegs, j * gWidth, i * gHeight, gWidth, gHeight);
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (isMoving()) {
            legs.setSprite(texturesLegs[aniLegs.getState()][aniLegs.getFrame()]);
            legs.draw(batch);
        }
        setSprite(textures[aniArms.getState()][aniArms.getFrame()]);
        super.draw(batch);
    }

    public float getStopY() {
        return stopY;
    }

    public boolean isShooted() {
        return shooted;
    }

    public void setShooted(boolean shooted) {
        this.shooted = shooted;
    }

    public boolean isReadyToShoot() {
        return readyToShoot;
    }

    public void setReadyToShoot(boolean readyToShoot) {
        this.readyToShoot = readyToShoot;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}
