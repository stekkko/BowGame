package com.bow.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.bow.game.model.Blood;
import com.bow.game.model.Explosion;
import com.bow.game.model.HealthBar;
import com.bow.game.model.mobs.Dog;
import com.bow.game.model.mobs.Zombie;
import com.bow.game.utils.Assets;
import com.bow.game.view.GameScreen;
import com.bow.game.view.LevelSelector;
import com.bow.game.view.MainMenuScreen;
import com.bow.game.view.PauseMenuScreen;

public class BowGame extends Game {
	public Screen gameScreen;
	public Screen menuScreen;
	public Screen pauseScreen;
	public Screen levelSelector;
	private Assets assets;

	//TODO settings
    private boolean musicAllowed;
	private boolean soundsAllowed;
	private int gamemode;

	@Override
	public void create () {
		assets = new Assets(this);
        initializeStatics(assets);
		gameScreen = new GameScreen(this, assets);
		menuScreen = new MainMenuScreen(this, assets);
		pauseScreen = new PauseMenuScreen(this, assets);
		levelSelector = new LevelSelector(this, assets);

		musicAllowed = true;
		soundsAllowed = true;
		gamemode = 1;

		this.setScreen(menuScreen);
	}

	private void initializeStatics(Assets assets) {
        HealthBar.setTextureRegions(assets.getAtlas("atlasHP.atlas"));
		Explosion.setTextureRegions(assets.getTexture("explosion"));
        Zombie.setTextures(assets.getTexture("zombie"));
        Blood.setTextureRegion(assets.getTexture("blood"));
        Dog.setTextureRegion(assets.getTexture("dog"));
    }

	public boolean isMusicAllowed() {
		return musicAllowed;
	}

	public boolean isSoundsAllowed() {
		return soundsAllowed;
	}

	public int getGamemode() {
	    return gamemode;
    }

    public void setGamemode(int gamemode) {
        this.gamemode = gamemode;
    }

    public void setMusicAllowed(boolean musicAllowed) {
		this.musicAllowed = musicAllowed;
	}

	public void setSoundsAllowed(boolean soundsAllowed) {
		this.soundsAllowed = soundsAllowed;
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		super.dispose();
		gameScreen.dispose();
		menuScreen.dispose();
		assets.dispose();
	}
}
