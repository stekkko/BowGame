package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Button extends GameObject {

    private boolean toggled;
    private boolean doEvent;

    public Button(TextureRegion texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        toggled = false;
        doEvent = false;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public boolean isDoEvent() {
        return doEvent;
    }

    public void setDoEvent(boolean doEvent) {
        this.doEvent = doEvent;
    }

    public boolean isToggled() {
        return toggled;
    }
}
