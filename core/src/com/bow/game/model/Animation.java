package com.bow.game.model;


import com.bow.game.view.GameScreen;

public class Animation {

    private final float frameDuration;
    private final int states;
    private final int frames;
    private int time;
    private int frame;
    private int state;


    public Animation(float frameDuration, int states, int frames) {
        this.frameDuration = frameDuration;
        this.states = states;
        this.frames = frames;
        time = 0;
        frame = 0;
        state = 0;
    }

    public void handle() {
        time += GameScreen.deltaCff;
        if (time >= frameDuration) {
            frame = (frame + 1) % frames;
        }
    }

    public void update() {
        state = (state + 1) % states;
    }

    public int getFrame() {
        return frame;
    }

    public int getState() {
        return state;
    }
}
