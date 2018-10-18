package com.bow.game.model;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.model.mobs.Ally;

public class Wall extends Ally {

    private boolean broken;

    public Wall(TextureRegion texture, float x, float y, float width, float height, float maxHealthPoints, float hpWidth, float repelPower) {
        super(texture, x, y, width, height, maxHealthPoints, 0f, repelPower, false);

        this.healthBar = new HealthBar(x, y - hpWidth * 0.04f, hpWidth, hpWidth * 0.03f, maxHealthPoints);
        healthBar.show();
        broken = false;
    }

    public void brake(TextureRegion textureRegion) {
        broken = true;
        this.setSprite(textureRegion);
    }

    public void repair(TextureRegion textureRegion) {
        healthBar.setHealthPoints(healthBar.getMaxHealthPoints());
        broken = false;
        this.setSprite(textureRegion);
    }

    public boolean isBroken() {
        return broken;
    }
}
