package com.bow.game.control;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.bow.game.BowGame;
import com.bow.game.model.ui.TypingText;
import com.bow.game.model.mobs.Boss666;
import com.bow.game.model.mobs.Dog;
import com.bow.game.model.mobs.Golem;
import com.bow.game.model.mobs.Zombie;

public class SurvivalLevelController extends LevelController {
    private int day;
    private int wavesCnt;
    private int maxZombiesCnt;
    private int minZombiesCnt;
    private int maxDogsCnt;
    private int minDogsCnt;
    private int minGolemCnt;

    private TypingText dayText;

    public SurvivalLevelController(BowGame game) {
        super(game);
    }

    @Override
    public void handle(float dt) {
        super.handle(dt);
        dayText.handle(dt);
        golemHandle();

        if (wall.getPercentHealthPoints() <= 0) {
            game.prefs.putString("endText", "YOU LOSE!").flush();
            game.setScreen(game.endGameScreen);
        }
        else if (wall.getPercentHealthPoints() <= 50)
            wall.brake(game.assets.getTexture("wall2"));

        if (wavesCnt > 0) spawnHandle(dt);
        else endHandle(dt);
    }

    @Override
    void spawnHandle(float dt) {
        super.spawnHandle(dt);
        time += dt;
        spawnInterval -= 0.005 * dt;

        if (time > spawnInterval && wavesCnt > 0) {
            int waveType = random.nextInt(4);
            if (waveType == 0) {
                int param = random.nextInt(maxZombiesCnt - minZombiesCnt + 1) + minZombiesCnt;
                for (int i = 0; i < param; i++) {
                    enemies.add(new Zombie(-width, height / 2, 3f, 3f * 1.12f, 94f + 2 * day, 30f, -2f, random));
                    enemies.get(enemies.size() - 1).randomSpawn(random, width, 0f, -2f);
                }
            }
            else if (waveType == 1) {
                int param = random.nextInt(maxDogsCnt - minDogsCnt + 1) + minDogsCnt;
                for (int i = 0; i < 5 - param; i++) {
                    enemies.add(new Dog(-width, height / 2, 2f, 2f * 2.8125f, 46f + 2 * day, 10f, -1f));
                    enemies.get(enemies.size() - 1).randomSpawn(random, width, 0f, -4f);
                }
            }
            else if (waveType == 2) {
                for (int i = 0; i < minZombiesCnt; i++) {
                    enemies.add(new Zombie(-width, height / 2, 3f, 3f * 1.12f, 94f + 2 * day, 30f, -2f, random));
                    enemies.get(enemies.size() - 1).randomSpawn(random, width, 0f, -1.5f);
                }
                for (int i = 0; i < minDogsCnt; i++) {
                    enemies.add(new Dog(-width, height / 2, 2f, 2f * 2.8125f, 46f + 2 * day, 10f, -1f));
                    enemies.get(enemies.size() - 1).randomSpawn(random, width, 0f, -4f);
                }
            }
            else if (waveType == 3) {
                for (int i = 0; i < minGolemCnt; i++) {
                    enemies.add(new Golem(-width, height / 2, 6f, 6f * 0.4416f, 178 + 2 * day, 0f, -3f));
                    enemies.get(enemies.size() - 1).randomSpawn(random, width, 0f, -1f);
                }
            }
            wavesCnt -= 1;
            time = 0;
        }
    }

    private void golemHandle() {
        for (int i = 0, len = enemies.size(); i < len; i++) {
            if (enemies.get(i) instanceof  Golem) {
                Golem golem = (Golem) enemies.get(i);
                if (golem.getY() < golem.getStopY() && golem.isMoving()) golem.stop();
                if (golem.isReadyToShoot()) enemies.add(golem.throwStone());
            }
        }
    }

    private void endHandle(float dt) {
        if (enemies.isEmpty()) {
            if (boss != null) {
                enemies.add(boss);
                enemies.get(0).targetSpawn(-boss.getWidth() / 2, height / 2, 0f, -2f);
                boss = null;
                game.assets.playMusic("boss", 0.5f);
            }
            else time += dt;

        }
        if (time > 2f) {
            game.prefs.putString("endText", "YOU WIN!");
            game.prefs.putInteger("nextDay", day + 1).flush();
            game.assets.stopMusic();
            game.setScreen(game.endGameScreen);
        }

    }

    @Override
    protected void initParams() {
        spawnInterval = game.prefs.getFloat("spawnIntervalSurvival", 5f) - 0.01f * day;
        time = spawnInterval;
        xp = 0;
        yp = 0;
        jtx = 0;
        jty = 0;
        day = game.prefs.getInteger("nextDay", 1);
        dayText = new TypingText("DAY" + day, 0.4f, 2f);
        wavesCnt = (int) (0.8f * (day * 0.04f + 7));
        maxZombiesCnt = 3 + (int) (day * 0.2f);
        minZombiesCnt = 2 + (int) (day * 0.2f);
        maxDogsCnt = 4 + (int) (day * 0.1f);
        minDogsCnt = 2 + (int) (day * 0.1f);
        minGolemCnt = 1 + (int) (day * 0.1f);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        dayText.draw(batch, game.assets.getFont("day"), -width / 2, 3f, width, Align.center, false);
    }

    @Override
    protected void initObjects() {
        super.initObjects();
        if (game.prefs.getInteger("nextDay", 1) % 10 == 0)
            boss = new Boss666(game.assets.getTexture("boss2"), width, height, 10f, 10f, 300f + day * 50f, Float.MAX_VALUE);
    }
}