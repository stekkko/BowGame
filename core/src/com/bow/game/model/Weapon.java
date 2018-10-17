package com.bow.game.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bow.game.view.GameScreen;

import java.util.ArrayList;


public abstract class Weapon extends GameObject {

    private float ammoReloadInterval;
    private float ammoFireInterval;
    private boolean loadedVisible;

    private int maxAmmo;
    private float time;
    Ammo amExample;
    private int ammo;
    private boolean loaded;
    private boolean readyToShoot;

    Weapon(TextureRegion texture, float x, float y, float width, float height, int maxAmmo) {
        super(texture, x, y, width, height);
        ammo = 0;
        time = 0f;
        loaded = false;
        readyToShoot = false;
        setMaxAmmo(maxAmmo);
    }

    public void instReload() {
        ammo = maxAmmo;
        readyToShoot = true;
        loaded = true;
    }

    public void shoot() {
        if (ammo == 0) loaded = false;
        else {
            ammo--;
            readyToShoot = false;
        }
    }

    @Override
    public void handle() {
        super.handle();
        amExample.handle();


        if (!isLoaded()) {
            time += GameScreen.deltaCff;
            if (time > ammoReloadInterval) {
                instReload();
                time = 0;
            }
        }
        else if (!isReadyToShoot()) {
            time += GameScreen.deltaCff;
            if (time > ammoFireInterval) {
                readyToShoot = true;
                time = 0;
            }
        }
    }

    public void setAmmo(Ammo ammo) {
        amExample = ammo;
        instReload();
    }

    public Ammo getAmmo() {
        return amExample;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        if (isLoaded() && isLoadedVisible()) {
            amExample.draw(batch);
        }
    }

    public boolean isLoaded() {
        return loaded;
    }

    public boolean isReadyToShoot() {
        return readyToShoot;
    }

    public boolean isLoadedVisible() {
        return loadedVisible;
    }

    public void setLoadedVisible(boolean loadedVisible) {
        this.loadedVisible = loadedVisible;
    }

    public float getAmmoReloadInterval() {
        return ammoReloadInterval;
    }

    public void setAmmoReloadInterval(float ammoReloadInterval) {
        this.ammoReloadInterval = ammoReloadInterval;
    }

    public float getAmmoFireInterval() {
        return ammoFireInterval;
    }

    public void setAmmoFireInterval(float ammoFireInterval) {
        this.ammoFireInterval = ammoFireInterval;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public void setMaxAmmo(int maxAmmo) {
        this.maxAmmo = maxAmmo;
    }
}
