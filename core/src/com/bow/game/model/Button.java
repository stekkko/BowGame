package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Button extends GameObject {

    private boolean toggled;

    public Button(TextureRegion texture, float x, float y, float width, float height) {
        super(texture, x, y, width, height);
        toggled = false;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public boolean isToggled() {
        return toggled;
    }

}
