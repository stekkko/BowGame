package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.model.mobs.Ally;
import com.bow.game.view.GameScreen;

public class Explosion extends Ally {

    private float time;
    private float lifeTime;
    private float damage;
    private boolean readyToDelete;

    public Explosion(TextureRegion texture, float x, float y, float width, float height, float damage, float lifeTime) {
        super(texture, x, y, width, height, 1f, damage, 0f, false);
        this.healthBar = null;
        this.lifeTime = lifeTime;
        readyToDelete = false;
        time = 0;
    }

    @Override
    public void handle() {
        super.handle();
        if (!readyToDelete) {
            time += GameScreen.deltaCff;
            if (time > lifeTime) {
                setReadyToDelete(true);
                time = 0;
            }
        }
    }

    @Override
    public float getHealthPoints() {
        return lifeTime;
    }

    @Override
    public float getPercentHealthPoints() {
        return time;
    }

    public boolean isReadyToDelete() {
        return readyToDelete;
    }

    public void setReadyToDelete(boolean readyToDelete) {
        this.readyToDelete = readyToDelete;
    }
}
