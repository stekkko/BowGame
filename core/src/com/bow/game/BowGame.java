package com.bow.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.bow.game.utils.Assets;
import com.bow.game.view.GameScreen;
import com.bow.game.view.MainMenuScreen;

public class BowGame extends Game {
	public Screen gameScreen;
	public Screen menuScreen;
	private Assets assets;
	private boolean musicAllowed;
	private boolean soundsAllowed;

	@Override
	public void create () {
		assets = new Assets();
		gameScreen = new GameScreen(this,
				assets.getManager().get("atlasBow.atlas", TextureAtlas.class),
				assets.getManager().get("atlasHP.atlas", TextureAtlas.class));
		menuScreen = new MainMenuScreen(this,
				assets.getManager().get("atlasBow.atlas", TextureAtlas.class));
		musicAllowed = true;
		soundsAllowed = true;
		this.setScreen(menuScreen);
	}

	public boolean isMusicAllowed() {
		return musicAllowed;
	}

	public boolean isSoundsAllowed() {
		return soundsAllowed;
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
