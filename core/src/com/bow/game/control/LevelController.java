package com.bow.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.bow.game.model.Arrow;
import com.bow.game.model.Background;
import com.bow.game.model.Blood;
import com.bow.game.model.Bow;
import com.bow.game.model.Wall;
import com.bow.game.model.Zombie;
import com.bow.game.utils.GUI;
import com.bow.game.view.GameScreen;

import java.util.ArrayList;
import java.util.Random;

public class LevelController {

    public static Random random;

    private Background background;
    private ArrayList<Zombie> zombies;
    private ArrayList<Zombie> zombiesInStash;
    private Bow bow;
    private ArrayList<Arrow> arrows;
    private Wall wall;
    private ArrayList<Blood> bloodMap;

    private Blood bloodExample;
    private Wall brokenWallExample;
    private Wall fullWallExample;
    private GUI gui;

    private float spawnInterval;
    private float spawnRate;
    private float time;
    private float width = GameScreen.cameraWidth;
    private float height = width * (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();

    public LevelController(TextureAtlas textureAtlas, TextureAtlas HPtextueAtlas, GUI gui, int zombieCount, float zombieHp) {

        random = new Random();
        this.gui = gui;

        spawnInterval = 100f;
        spawnRate = 25f;
        time = 99f;

        this.zombies = new ArrayList<Zombie>();
        this.zombiesInStash = new ArrayList<Zombie>();
        this.bloodMap = new ArrayList<Blood>();
        this.arrows = new ArrayList<Arrow>();
        Arrow arrowInBow = new Arrow(textureAtlas.findRegion("arrow"),
                2.25f, -height / 2, 0.5f, 0.5f * 5.6153f);
        bow = new Bow(textureAtlas.findRegion("bow"), arrowInBow,
                0f, -height / 2, 5f, 5f * 0.3255f);
        for (int i = 0; i < zombieCount; i++)
            zombiesInStash.add(new Zombie(textureAtlas.findRegion("zombie"), HPtextueAtlas,
                -width, height / 2, 3f, 3f * 1.12f, zombieHp));
        wall = new Wall(textureAtlas.findRegion("wall1"),
                -width / 2, 4f -height / 2, 2f * 28.96f, 2f);


        background = new Background(textureAtlas.findRegion("grass"),
                -width / 2, -height / 2, height * 1f, height);

        bloodExample = new Blood(textureAtlas.findRegion("blood"), 0f, 0f, 2f, 2f);
        brokenWallExample = new Wall(textureAtlas.findRegion("wall2"), 0, 0, 2f * 28.96f, 2f);
        fullWallExample = new Wall(textureAtlas.findRegion("wall1"), 0, 0, 2f * 28.96f, 2f);

//        for (Zombie zombie : zombies) zombie.spawn(width, random);
    }


    public void handle() {
        for (Arrow arrow : arrows) arrow.handle();
        for (Arrow arrow : arrows) {
            if (arrow.isReadyToDelete() || arrow.getY() > height / 2) {
                arrows.remove(arrow);
                break;
            }
        }
        for (Zombie zombie : zombies) { zombie.handle(); }
        for (Zombie zombie : zombies) {
            if (Intersector.overlapConvexPolygons(zombie.getBounds(), wall.getBounds())) {
                zombies.remove(zombie);
                if (wall.isBroken()) {
                    gui.setScore(0);
                    wall.repair(fullWallExample.getSprite());
                }
                else {
                    wall.brake(brokenWallExample.getSprite());
                }
                break;
            }
            if (zombie.getHealthPoints() <= 0) {
                zombies.remove(zombie);
                gui.addScore(1);
                break;
            }
        }
        time += GameScreen.deltaCff * spawnRate;
        if (time > spawnInterval) {
            zombies.add(zombiesInStash.get(0));
            zombiesInStash.remove(0);
            zombies.get(zombies.size() - 1).spawn(width, random);
            zombies.add(zombiesInStash.get(0));
            zombiesInStash.remove(0);
            zombies.get(zombies.size() - 1).spawn(width, random);
            time = 0;
        }

        bow.handle();
        wall.handle();

        if (Gdx.input.isTouched()) {
            bow.setPosition(-width / 2 -bow.getWidth() / 2 + width * Gdx.input.getX() / Gdx.graphics.getWidth(), bow.getY());

            if (bow.isLoaded()) {
                arrows.add(new Arrow(bow.getArrow().getSprite(),
                        bow.getArrow().getX(), bow.getArrow().getY(), bow.getArrow().getWidth(), bow.getArrow().getHeight()));
                arrows.get(arrows.size() - 1).shoot();
                bow.shoot();
            }
        }
        for (Arrow arrow : arrows) {
            for (Zombie zombie : zombies) {
                if (Intersector.overlapConvexPolygons(zombie.getBounds(), arrow.getBounds())) {
                    bloodMap.add(new Blood(bloodExample.getSprite(), zombie.getX(), zombie.getY(), zombie.getWidth(), zombie.getWidth()));
                    zombie.damage(arrow.getDamage());
                    arrow.delete();
                }
            }
        }
        if (bloodMap.size() > 200) {
            bloodMap.remove(bloodMap.size() - 1);
        }

//        for (Blood blood : bloodMap) blood.handle();
    }

    public void draw(SpriteBatch batch) {
        background.draw(batch);
        for (Blood blood : bloodMap) blood.draw(batch);
        for (Zombie zombie : zombies) zombie.draw(batch);
        wall.draw(batch);
        bow.draw(batch);
        for (Arrow arrow : arrows) arrow.draw(batch);
    }

    public void dispose() {
        gui.dispose();
    }
}
