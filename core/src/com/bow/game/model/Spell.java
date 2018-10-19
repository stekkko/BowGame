package com.bow.game.model;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.view.GameScreen;

public class Spell extends Button {

    private Crosshair crosshair;
    private float cooldownTime;
    private float time;
    private boolean onCD;

    public Spell(TextureRegion texture, float x, float y, float width, float height, Crosshair crosshair) {
        super(texture, x, y, width, height);
        this.crosshair = crosshair;
        onCD = true;
        cooldownTime = 10f;
        this.getSprite().setAlpha(0.7f);
    }

    @Override
    public void handle() {
        super.handle();
        crosshair.handle();

        if (isOnCD()) {
            setToggled(false);
            setDoEvent(false);
            crosshair.setDrawn(false);
            time += GameScreen.deltaCff;
            if (time >= cooldownTime) {
                setOnCD(false);
                time = 0;
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        getSprite().setAlpha(0.7f);
        super.draw(batch);
        if (crosshair.isDrawn()) crosshair.draw(batch);
    }

    public Crosshair getCrosshair() {
        return crosshair;
    }

    public void setCrosshair(Crosshair crosshair) {
        this.crosshair = crosshair;
    }

    public float getTime() {
        return time;
    }

    public float getCooldownTime() {
        return cooldownTime;
    }

    public void setCooldownTime(float cooldownTime) {
        this.cooldownTime = cooldownTime;
    }

    public boolean isOnCD() {
        return onCD;
    }

    public void setOnCD(boolean onCD) {
        this.onCD = onCD;
    }
}
