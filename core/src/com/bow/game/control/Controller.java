package com.bow.game.control;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Controller {

    void handle(float dt);
    void draw(SpriteBatch batch);
    void dispose();
}
