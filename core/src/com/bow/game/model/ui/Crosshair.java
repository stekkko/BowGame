package com.bow.game.model.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.model.GameObject;

public class Crosshair extends GameObject {

    private boolean drawn;

    public Crosshair(TextureRegion texture, float width, float height) {
        super(texture,0 ,0,  width, height);
        setDrawn(false);
    }

    public boolean isDrawn() {
        return drawn;
    }

    public void setDrawn(boolean drawn) {
        this.drawn = drawn;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (isDrawn()) super.draw(batch);
    }
}
