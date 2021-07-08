package com.bow.game.model.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.model.GameObject;

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
