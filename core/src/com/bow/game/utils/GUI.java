package com.bow.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Locale;

public class GUI {


    private Stage stage;
    private Label labelScore;
    private Label labelCD;
    private Label labelCD1;
    private Skin skin;
    private float score;
    private float cooldown;
    private float cooldown1;

    public GUI() {

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 25));
        skin = new Skin(Gdx.files.internal("skin.json"));
        score = 0;

        labelScore = new Label("Time: " + score, skin.get("default", Label.LabelStyle.class));
        labelScore.setAlignment(Align.left);
        labelScore.setPosition(0, Gdx.graphics.getHeight(), Align.topLeft);
        labelScore.setFontScale(0.7f);
        stage.addActor(labelScore);

        cooldown = 10f;
        labelCD = new Label(String.valueOf((int)(10f - cooldown)), skin.get("default", Label.LabelStyle.class));
        labelCD.setAlignment(Align.left);
        labelCD.setPosition(50, Gdx.graphics.getHeight() / 2, Align.topLeft);
        labelCD.setFontScale(0.5f);
        stage.addActor(labelCD);

        cooldown1 = 0.5f;
        labelCD1 = new Label(String.valueOf((int)(0.5f - cooldown)), skin.get("default", Label.LabelStyle.class));
        labelCD1.setAlignment(Align.left);
        labelCD1.setPosition(50, Gdx.graphics.getHeight() / 2 + 200, Align.topLeft);
        labelCD1.setFontScale(0.5f);
        stage.addActor(labelCD1);
    }

    public float getCooldown() {
        return cooldown;
    }

    public float getCooldown1() {
        return cooldown1;
    }

    public void hideCooldown() {
        labelCD.setText("");
    }
    public void hideCooldown1() {
        labelCD1.setText("");
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
        labelCD.setText(String.format(Locale.US,"%.1f", cooldown));
    }
    public void setCooldown1(float cooldown) {
        this.cooldown1 = cooldown;
        labelCD1.setText(String.format(Locale.US,"%.1f", cooldown));
    }

    public float getScore() {
        return score;
    }

    public void setScore(int n) {
        score = n;
        labelScore.setText("Time: " + String.format(Locale.US,"%.2f", score));
    }

    public void addFloat(float f) {
        score += f;
        labelScore.setText("Time: " + String.format(Locale.US,"%.2f", score));
    }

    public void addScore(int n) {
        score += n;
        labelScore.setText("Time: " + String.format(Locale.US,"%.2f", score));
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
