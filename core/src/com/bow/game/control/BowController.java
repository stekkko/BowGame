package com.bow.game.control;

import com.badlogic.gdx.Gdx;
import com.bow.game.model.Arrow;
import com.bow.game.model.Bow;
import com.bow.game.view.GameScreen;

public class BowController {
    Bow bow;
    Arrow arrow;
    float PTM;

    public BowController(float PIX_TO_METERS, Bow bow, Arrow arrow) {
        this.bow = bow;
        this.arrow = arrow;
        this.PTM = (float) Gdx.graphics.getWidth() / PIX_TO_METERS;
    }

    public void handle() {
        bow.setPosition(bow.getX() + bow.getSpeedX() * GameScreen.deltaCff, bow.getY() + bow.getSpeedY() * GameScreen.deltaCff);
        if (!arrow.isShooted) {
            arrow.setPosition(arrow.getX() + bow.getSpeedX() * GameScreen.deltaCff, arrow.getY() + bow.getSpeedY() * GameScreen.deltaCff);
        }

        if (arrow.getY() > Gdx.graphics.getHeight() / 2 / PTM) {
            arrow.setPosition(bow.getX() + bow.getWidth() / 2 - arrow.getWidth() / 2, bow.getY());
            arrow.setSpeedX(0f);
            arrow.setSpeedY(0f);
            arrow.isShooted = false;
        }

        if (Gdx.input.justTouched()) {
            arrow.shoot();
        }

        if (bow.getX() < -Gdx.graphics.getWidth() / 2 / PTM) {
            bow.setSpeedX(Math.abs(bow.getSpeedX()));
        }
        if (bow.getX() + bow.getWidth() > Gdx.graphics.getWidth() / 2 / PTM) {
            bow.setSpeedX(-Math.abs(bow.getSpeedX()));
        }
    }
}
