package com.bow.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.bow.game.utils.Assets;
import com.bow.game.view.GameScreen;
import com.bow.game.view.MainMenuScreen;

public class BowGame extends Game {
	private Screen gameScreen;
	private Screen menuScreen;
	private Assets assets;

	@Override
	public void create () {
		assets = new Assets();
		gameScreen = new GameScreen();
		menuScreen = new MainMenuScreen();
		((GameScreen) gameScreen).setTextureAtlas(assets.getManager().get("atlasBow.atlas", TextureAtlas.class));
		((GameScreen) gameScreen).setHPTextureAtlas(assets.getManager().get("atlasHP.atlas", TextureAtlas.class));
		this.setScreen(menuScreen);
	}

	@Override
	public void render () {
		super.render();
		if (getScreen().equals(menuScreen) && Gdx.input.justTouched()) {
			this.setScreen(gameScreen);
		}
	}
	
	@Override
	public void dispose () {
		super.dispose();
		gameScreen.dispose();
		menuScreen.dispose();
		assets.dispose();
	}
}
