package com.bow.game.model.mobs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.model.Enemy;

public class Boss extends Enemy {
    //    TODO
    public Boss(TextureRegion texture, TextureAtlas HPtextureAtlas, float x, float y, float width, float height, float maxHealthPoints) {
        super(texture, HPtextureAtlas, x, y, width, height, maxHealthPoints);

    }

    public void spawn() {
        this.healthBar.setPosition(this.getX(), this.getY() - this.healthBar.getHeight());
        this.setSpeedY(-0.5f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    public void damage(float value) {
        super.damage(value);
    }

    public void update(TextureAtlas HPtextureAtlas) {
        if (healthBar.isNeedUpdate()) {
            healthBar.update(HPtextureAtlas);
            healthBar.setNeedUpdate(false);
        }
    }

    @Override
    public void handle() {
        super.handle();
        healthBar.hide();
    }

    @Override
    public float getPerHealthPoints() {
        return super.getPerHealthPoints();
    }

    @Override
    public float getHealthPoints() {
        return super.getHealthPoints();
    }

    @Override
    public float getMaxHealthPoints() {
        return super.getMaxHealthPoints();
    }
}
