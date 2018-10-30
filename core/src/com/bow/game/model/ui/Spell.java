package com.bow.game.model.ui;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Spell extends Button {

    public final static int NULLSPELL = 0;
    public final static int KNIGHT = 1;
    public final static int EXPLOSION = 2;

    private Crosshair crosshair;
    private float cooldownTime;
    private float time;
    private boolean onCD;
    private int id;

    public Spell(TextureRegion texture, float x, float y, float width, float height, int id, float cooldownTime) {
        super(texture, x, y, width, height);
        this.id = id;
        this.getSprite().setAlpha(0.7f);
        onCD = true;
        this.cooldownTime = cooldownTime;
    }

    public Spell(TextureRegion texture, float x, float y, float width, float height, int id, float cooldownTime, Crosshair crosshair) {
        this(texture, x, y, width, height, id, cooldownTime);
        this.crosshair = crosshair;
    }

    public void handle(float dt) {
        //special case for no spell
        if (getId() == NULLSPELL) setOnCD(true);
        //other cases
        else if (isOnCD()) {
            setToggled(false);
            if (crosshair != null) crosshair.setDrawn(false);
            time += dt;
            if (time >= cooldownTime) {
                setOnCD(false);
                time = 0;
            }
        }
    }

    public void moveCrosshair(float x, float y) {
        if (crosshair != null) crosshair.setPosition(x - crosshair.getWidth() / 2, y - crosshair.getHeight() / 2);
    }

    public void off() {
        setToggled(false);
        setOnCD(true);
        setDrawnCrosshair(false);
    }

    @Override
    public void draw(SpriteBatch batch) {
        getSprite().setAlpha(0.7f);
        super.draw(batch);
        if (crosshair != null && crosshair.isDrawn()) crosshair.draw(batch);
    }

    public void setDrawnCrosshair(boolean drawnCrosshair) {
        if (crosshair != null) crosshair.setDrawn(drawnCrosshair);
    }

    public float getCD() {
        return cooldownTime - time;
    }

    public boolean isOnCD() {
        return onCD;
    }

    public void setOnCD(boolean onCD) {
        this.onCD = onCD;
    }

    public int getId() {
        return id;
    }
}
