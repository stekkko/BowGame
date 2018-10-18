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
        if (game.isSoundsAllowed()) sound.play(volume);
    }

    public void playMusic(Music music) {
        music.setLooping(true);
        music.setVolume(0.15f);
        if (game.isMusicAllowed()) music.play();
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
