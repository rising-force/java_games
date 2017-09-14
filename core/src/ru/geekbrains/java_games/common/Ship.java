package ru.geekbrains.java_games.common;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.java_games.common.bullets.Bullet;
import ru.geekbrains.java_games.common.bullets.BulletPool;
import ru.geekbrains.java_games.common.explosions.Explosion;
import ru.geekbrains.java_games.common.explosions.ExplosionPool;
import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.sprites.Sprite;

@SuppressWarnings("WeakerAccess")
public class Ship extends Sprite {

    protected final Vector2 v = new Vector2();
    protected final Rect worldBounds;

    protected final BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Sound bulletSound;
    protected final ExplosionPool explosionPool;
    protected int hp;

    protected Ship(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
    }

    public Ship(TextureRegion region, int rows, int cols, int frames, BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        super(region, rows, cols, frames);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
    }

    private static final float DAMAGE_ANIMATION_INTERVAL = 0.1f;
    private float damageAnimationTimer = DAMAGE_ANIMATION_INTERVAL;

    public void damage(int damage) {
//        System.out.println("hp = " + hp + " damage = " + damage);
        frame = 1;
        damageAnimationTimer = 0f;
        hp -= damage;
        if(hp < 0) hp = 0;
        if(hp == 0) destroy();
    }

    @Override
    public void update(float deltaTime) {
        pos.mulAdd(v, deltaTime);
        damageAnimationTimer += deltaTime;
        if(damageAnimationTimer >= DAMAGE_ANIMATION_INTERVAL) frame = 0;
    }

    protected float bulletHeight;
    protected final Vector2 bulletV = new Vector2();
    protected int bulletDamage;

    protected float reloadInterval;
    protected float reloadTimer;

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, bulletDamage);
        if(bulletSound.play() == -1) throw new RuntimeException();
    }

    public void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }
}
