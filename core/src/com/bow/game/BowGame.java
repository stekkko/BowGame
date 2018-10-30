package com.bow.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bow.game.model.ammo.Arrow;
import com.bow.game.model.Blood;
import com.bow.game.model.mobs.Dog;
import com.bow.game.model.mobs.Explosion;
import com.bow.game.model.HealthBar;
import com.bow.game.model.mobs.Golem;
import com.bow.game.model.mobs.GolemStone;
import com.bow.game.model.mobs.Zombie;
import com.bow.game.utils.Assets;
import com.bow.game.screens.EndGameScreen;
import com.bow.game.screens.GameScreen;
import com.bow.game.screens.LevelSelector;
import com.bow.game.screens.MainMenuScreen;
import com.bow.game.screens.PauseMenuScreen;
import com.bow.game.screens.ShopScreen;

public class BowGame extends Game {
	public Preferences prefs;
	public Screen gameScreen;
	public Screen menuScreen;
	public Screen pauseScreen;
	public Screen levelSelector;
	public Screen endGameScreen;
	public Screen shopScreen;
	public SpriteBatch batch;
	public Assets assets;

	//TODO settings
	public final int SURVIVAL = 1;
	public final int ENDLESS = 2;
	private int gamemode;

	@Override
	public void create () {
	    prefs = Gdx.app.getPreferences("bow-settings");
		assets = new Assets(this);
		batch = new SpriteBatch();
        initializeStatics();
		gameScreen = new GameScreen(this);
		menuScreen = new MainMenuScreen(this);
		pauseScreen = new PauseMenuScreen(this);
		levelSelector = new LevelSelector(this);
		endGameScreen = new EndGameScreen(this);
		shopScreen = new ShopScreen(this);
		gamemode = SURVIVAL;
		this.setScreen(menuScreen);
	}

	private void initializeStatics() {
		Arrow.setTextures(assets.getTexture("arrow"));
        HealthBar.setTextureRegions(assets.getAtlas("atlasHP.atlas"));
		Explosion.setTextureRegions(assets.getTexture("explosion"));
        Zombie.setTextures(assets.getTexture("zombie"));
        Blood.setTextureRegion(assets.getTexture("blood"));
        Dog.setTextureRegion(assets.getTexture("dog"));
		Golem.setTextures(assets.getTexture("golem"), assets.getTexture("golemLegs"));
		GolemStone.setTextures(assets.getTexture("golemStone"));
    }

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		super.dispose();
		prefs.flush();
		batch.dispose();
		gameScreen.dispose();
		menuScreen.dispose();
		pauseScreen.dispose();
		levelSelector.dispose();
		endGameScreen.dispose();
		shopScreen.dispose();
		assets.dispose();
	}

	public int getGamemode() {
		return gamemode;
	}

	public void setGamemode(int gamemode) {
		this.gamemode = gamemode;
	}
}
