package ru.geekbrains.java_games;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.java_games.pools.BulletPool;
import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.sprites.Sprite;

public class Ship extends Sprite {

    protected final Vector2 v = new Vector2();
    protected Rect worldBounds;

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
//    protected Sound bulletSound;
//    protected int hp;

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    protected float bulletHeight;
    protected final Vector2 bulletV = new Vector2();
    protected int bulletDamage;

    protected float reloadInterval;
    protected float reloadTimer;

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, bulletDamage);
    }
}
