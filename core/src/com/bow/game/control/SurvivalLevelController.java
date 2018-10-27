package com.bow.game.control;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bow.game.BowGame;

public class SurvivalLevelController extends LevelController {
    public SurvivalLevelController(BowGame game) {
        super(game);
    }

    @Override
    public void handle(float dt) {
        super.handle(dt);
        if (wall.getPercentHealthPoints() <= 0) {
            restartGame();
            game.setScreen(game.menuScreen);
        }
        else if (wall.getPercentHealthPoints() <= 50)
            wall.brake(game.assets.getTexture("wall2"));
    }

    @Override
    public void restartGame() {
        super.restartGame();
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    protected void initParams() {
        spawnInterval = game.prefs.getFloat("spawnIntervalSurvival", 10f);
        time = spawnInterval;
        bossFIGHT = false;
        xp = 0;
        yp = 0;
        jtx = 0;
        jty = 0;
    }

    @Override
    protected void initObjects() {
        super.initObjects();
    }
}