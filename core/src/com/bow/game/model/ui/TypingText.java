package com.bow.game.model.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class TypingText {
    private float time;
    private float timeForOneSymbol;
    private float lifeTime;
    private String text;
    private String writtenText;

    public TypingText(String text, float timeForOneSymbol, float lifeTime) {
        this.text = text;
        this.writtenText = "";
        this.timeForOneSymbol = timeForOneSymbol;
        this.lifeTime = lifeTime;
        this.time = 0f;
    }

    public TypingText(String text, float timeForOneSymbol) {
        this(text, timeForOneSymbol, Float.MAX_VALUE);
    }

    public void handle(float dt) {
        time += dt;
        if (time > timeForOneSymbol && writtenText.length() != text.length()) {
            writtenText += text.charAt(writtenText.length());
            time = 0f;
        }
        if (writtenText.length() == text.length()) lifeTime -= dt;
    }

    public void draw(SpriteBatch batch, BitmapFont font, float x, float y) {
        if (lifeTime > 0) font.draw(batch, writtenText, x, y);
    }

    public void draw(SpriteBatch batch, BitmapFont font, float x, float y, float width, int align, boolean wrap) {
        if (lifeTime > 0) font.draw(batch, writtenText, x, y, width, align, wrap);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getTimeForOneSymbol() {
        return timeForOneSymbol;
    }
}
