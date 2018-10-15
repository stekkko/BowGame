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
    private Skin skin;
    private float score;
    private float cooldown;

    public GUI() {

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        skin = new Skin(Gdx.files.internal("skin.json"));
        time = 0;

        labelScore = new Label("Time: " + time, skin.get("default", Label.LabelStyle.class));
        labelScore.setAlignment(Align.left);
        labelScore.setPosition(0, Gdx.graphics.getHeight(), Align.topLeft);
        labelScore.setFontScale(0.7f);
        stage.addActor(labelScore);


        labelCD = new Label(Float.toString(10f - cooldown), skin.get("default", Label.LabelStyle.class));
        labelCD.setAlignment(Align.left);
        labelCD.setPosition(0, Gdx.graphics.getHeight() / 2, Align.topLeft);
        labelCD.setFontScale(0.5f);
        stage.addActor(labelCD);
    }

    public float getCooldown() {
        return cooldown;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
        labelCD.setText(String.format(Locale.US,"%.1f",cooldown));
    }

    public float getTime() {
        return time;
    }

    public void setTime(int n) {
        time = n;
        labelScore.setText("Time: " + String.format(Locale.US,"%.2f",time));
    }

    public void addFloat(float f) {
        time += f;
        labelScore.setText("Time: " + String.format(Locale.US,"%.2f",time));
    }

    public void addScore(int n) {
        time += n;
        labelScore.setText("Time: " + String.format(Locale.US,"%.2f",time));
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
