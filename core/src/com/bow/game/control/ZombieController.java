package com.bow.game.control;

import com.badlogic.gdx.Gdx;
import com.bow.game.model.Zombie;
import com.bow.game.view.GameScreen;


public class ZombieController {
    Zombie zombie;
    float PTM;

    public ZombieController(float PIX_TO_METERS, Zombie zombie) {
        this.zombie = zombie;
        this.PTM = (float) Gdx.graphics.getWidth() / PIX_TO_METERS;
    }

    public void handle() {
        zombie.setPosition(zombie.getX() + zombie.getSpeedX() * GameScreen.deltaCff, zombie.getY() + zombie.getSpeedY() * GameScreen.deltaCff);

        if (zombie.getY() < -Gdx.graphics.getHeight() / 2 / PTM) {
            zombie.respawn(PTM, GameScreen.random);
        }
    }
}
