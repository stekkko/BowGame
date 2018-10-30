package com.bow.game.utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.BowGame;

public class Assets {
    private AssetManager manager;
    private Music theme;
    private Music gMusic;
    private Music bossSong;
    private Music musicPlaying;
    private BowGame game;
    private BitmapFont fontMoney;
    private BitmapFont fontDay;
    private BitmapFont fontDefault;

    public Assets(BowGame game) {
        this.game = game;
        manager = new AssetManager();
        manager.load("atlasBow.atlas", TextureAtlas.class);
        manager.load("atlasHP.atlas", TextureAtlas.class);
        manager.finishLoading();

        theme = Gdx.audio.newMusic(Gdx.files.internal("audio/menuTheme.wav"));
        gMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/bensound-instinct.mp3"));
        bossSong = Gdx.audio.newMusic(Gdx.files.internal("audio/bossSong.wav"));
        musicPlaying = null;

        fontMoney = new BitmapFont(Gdx.files.internal("font123.fnt"), Gdx.files.internal("font123.png"), false);
        fontMoney.getData().setScale(0.05f);
        fontMoney.setColor(Color.LIME);

        fontDay = new BitmapFont(Gdx.files.internal("font123.fnt"), Gdx.files.internal("font123.png"), false);
        fontDay.getData().setScale(0.1f);
        fontDay.setColor(Color.WHITE);

        fontDefault = new BitmapFont(Gdx.files.internal("font123.fnt"), Gdx.files.internal("font123.png"), false);
        fontDefault.getData().setScale(0.05f);
        fontDefault.setColor(Color.WHITE);
    }

    public BitmapFont getFont(String key) {
        switch (key) {
            case ("money"): return fontMoney;
            case ("day"): return fontDay;
            default: return fontDefault;
        }
    }

    public void playSound(Sound sound, float volume) {
        if (game.prefs.getBoolean("soundAllowed", true)) sound.play(volume);
    }

    public void stopMusic() {
        if (musicPlaying != null) musicPlaying.stop();
        musicPlaying = null;
    }

    public void pauseMusic() {
        if (musicPlaying != null) musicPlaying.pause();
    }

    public void playMusic(String key, float volume) {
        Music music = musicPlaying;
        if (key.equals("theme")) music = theme;
        if (key.equals("music")) music = gMusic;
        if (key.equals("boss")) music = bossSong;
        if (music != null && music == musicPlaying) {
            if (game.prefs.getBoolean("musicAllowed", true)) musicPlaying.play();
        }
        else if (music != null) {
            music.setLooping(true);
            music.setVolume(volume);
            if (musicPlaying != null) musicPlaying.stop();
            musicPlaying = music;
            if (game.prefs.getBoolean("musicAllowed", true)) musicPlaying.play();
        }
    }

    public TextureRegion getTexture(String name) {
        return manager.get("atlasBow.atlas", TextureAtlas.class).findRegion(name);
    }

    public TextureAtlas getAtlas(String name) {
        return manager.get(name, TextureAtlas.class);
    }

    public void dispose() {
        manager.dispose();
        theme.dispose();
        gMusic.dispose();
    }
}
