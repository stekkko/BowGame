package com.bow.game.utils;


import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.BowGame;

public class Assets {
    private AssetManager manager;
    private BowGame game;

    public Assets(BowGame game) {
        this.game = game;
        manager = new AssetManager();
        manager.load("atlasBow.atlas", TextureAtlas.class);
        manager.load("atlasHP.atlas", TextureAtlas.class);
        manager.finishLoading();
    }

    public void playSound(Sound sound, float volume) {
        if (game.prefs.getBoolean("soundAllowed", true)) sound.play(volume);
    }

    public void playMusic(Music music, float volume) {
        music.setLooping(true);
        music.setVolume(volume);
        if (game.prefs.getBoolean("musicAllowed", true)) music.play();
    }

    public TextureRegion getTexture(String name) {
        return manager.get("atlasBow.atlas", TextureAtlas.class).findRegion(name);
    }

    public TextureAtlas getAtlas(String name) {
        return manager.get(name, TextureAtlas.class);
    }

    public void dispose() {
        manager.dispose();
    }
}
