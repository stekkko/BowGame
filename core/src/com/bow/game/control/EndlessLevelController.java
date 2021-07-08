package com.bow.game.control;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bow.game.BowGame;
import com.bow.game.model.mobs.Boss666;
import com.bow.game.model.mobs.Dog;
import com.bow.game.model.mobs.Zombie;

public class EndlessLevelController extends LevelController {

    private boolean bossFIGHT;
    private float bossTime;
    private float timeSurvived;

    public EndlessLevelController(BowGame game) {
        super(game);
    }

    @Override
    public void handle(float dt) {
        super.handle(dt);
        spawnHandle(dt);

        if (wall.getPercentHealthPoints() <= 0) {
            restartGame();
            game.gameScreen.pause();
            game.setScreen(game.levelSelector);
            game.assets.playMusic("theme", 0.4f);
            game.levelSelector.resume();
        }
        else if (wall.getPercentHealthPoints() <= 50)
            wall.brake(game.assets.getTexture("wall2"));

        if (bossFIGHT) {
            boss = new Boss666(game.assets.getTexture("boss2"),
                    - 4f, height / 2, 8f, 8f, 500f + timeSurvived * 2f, (float) Integer.MAX_VALUE);
            boss.targetSpawn(-boss.getWidth() / 2, height / 2, 0f, -1f);
            enemies.add(boss);
            bossFIGHT = false;
        }
    }

    @Override
    void spawnHandle(float dt) {
        super.spawnHandle(dt);
        time += dt;
        timeSurvived += dt;
        bossTime += dt;
        spawnInterval -= 0.007f * dt;
        if (time > spawnInterval) {
            int param = random.nextInt(4);
            int adjustZombies = 1;
            for (int i = 0; i < param + adjustZombies; i++) {
                enemies.add(new Zombie(-width, height / 2, 3f, 3f * 1.12f, 100f + timeSurvived * 0.2f, 30f,-2f, random));
                enemies.get(enemies.size() - 1).randomSpawn(random, width, 0f, -1.5f);
            }
            for (int i = 0; i < 5 - param; i++) {
                enemies.add(new Dog(-width, height / 2, 2f, 2f * 2.8125f, 50f + timeSurvived * 0.1f, 10f,-1f));
                enemies.get(enemies.size() - 1).randomSpawn(random, width, 0f, -4f);
            }
            time = 0;
        }
        if (bossTime > 30f) {
            bossFIGHT = true;
            bossTime = 0f;
        }
    }

    private String formatTime(float time) {
        int t = (int) time;
        return (t / 60 < 10 ? "0" : "") + t / 60 + ":" + (t % 60 < 10 ? "0" : "") + t % 60;
    }

    @Override
    protected void initParams() {
        spawnInterval = game.prefs.getFloat("spawnIntervalEndless", 5f);
        time = spawnInterval;
        bossFIGHT = false;
        bossTime = 0f;
        timeSurvived = 0f;
        xp = 0f;
        yp = 0f;
        jtx = 0f;
        jty = 0f;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        game.assets.getFont("default").draw(batch, formatTime(timeSurvived), - width / 2 + 1f, height / 2 - 2.5f);
    }

    @Override
    protected void initObjects() {
        super.initObjects();
    }
}

