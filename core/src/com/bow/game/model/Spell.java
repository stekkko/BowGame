package com.bow.game.model;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.view.GameScreen;

public class Spell extends Button {

    private float cooldownTime;
    private float time;
    private boolean onCD;

    public Spell(TextureRegion texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        onCD = true;
        cooldownTime = 10f;
    }

    @Override
    public void handle() {
        super.handle();

        if (isOnCD()) {
            setToggled(false);
            setDoEvent(false);
            time += GameScreen.deltaCff;
            if (time >= cooldownTime) {
                setOnCD(false);
                time = 0;
            }
        }
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
