package com.bow.game.control;

import com.badlogic.gdx.Gdx;
import com.bow.game.model.Arrow;
import com.bow.game.view.GameScreen;

public class ArrowController {
    Arrow arrow;
    float PTM;

    public ArrowController(float PIX_TO_METERS, Arrow arrow) {
        this.arrow = arrow;
        this.PTM = (float) Gdx.graphics.getWidth() / PIX_TO_METERS;
    }

    public void handle() {
        arrow.setPosition(arrow.getX() + arrow.getSpeedX() * GameScreen.deltaCff, arrow.getY() + arrow.getSpeedY() * GameScreen.deltaCff);
    }
}
