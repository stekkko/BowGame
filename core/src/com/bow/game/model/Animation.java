package com.bow.game.model;


import com.bow.game.view.GameScreen;

import java.util.Random;

public class Animation {
    public final static int LOOP = 1;
    public final static int  NORMAL = 2;

    private final float frameDuration;
    private final int states;
    private final int frames;
    private float time;
    private int frame;
    private int state;
    private int mode;


    public Animation(float frameDuration, int states, int frames, int mode) {
        this.frameDuration = frameDuration;
        this.states = states;
        this.frames = frames;
        this.mode = mode;
        time = 0f;
        frame = 0;
        state = 0;
    }

    public void handle() {
        time += GameScreen.deltaCff;
        if (time > frameDuration) {
            if (mode == NORMAL) frame = Math.min(frames, frame + 1);
            else if (mode == LOOP) frame = (frame + 1) % frames;
            time = 0;
        }

    }

    public void update() {
        state = (state + 1) % states;
    }

    public void setRandomTime(Random random) {
        this.time = random.nextFloat() * frameDuration;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public int getFrame() {
        return frame;
    }

    public int getState() {
        return state;
    }
}
