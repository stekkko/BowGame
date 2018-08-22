package com.bow.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GUI {

    private Stage stage;
    private Label label;
    private Skin skin;
    int score;

    public GUI() {
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        skin = new Skin(Gdx.files.internal("skin.json"));
        score = 0;
        label = new Label("Zombies killed: " + score, skin.get("default", Label.LabelStyle.class));
        label.setAlignment(Align.left);
        label.setPosition(0, Gdx.graphics.getHeight(), Align.topLeft);
        label.setFontScale(0.7f);
        stage.addActor(label);

    }

    public void setScore(int n) {
        score = n;
        label.setText("Zombies killed: " + score);
    }

    public void addScore(int n) {
        score += n;
        label.setText("Zombies killed: " + score);
    }

    public void draw() {
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.act();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
