package ru.geekbrains.java_games.common.enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.java_games.common.Ship;
import ru.geekbrains.java_games.common.bullets.BulletPool;
import ru.geekbrains.java_games.common.explosions.ExplosionPool;
import ru.geekbrains.java_games.screens.game_screen.MainShip;
import ru.geekuniversity.engine.math.Rect;

public class Enemy extends Ship {

    private enum State { DESCENT, FIGHT }

    private final Vector2 descentV = new Vector2(0f, -0.15f);
    private final Vector2 v0 = new Vector2();
    private final MainShip mainShip;
    private State state;

    Enemy(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, MainShip mainShip) {
        super(bulletPool, explosionPool, worldBounds);
        this.mainShip = mainShip;
        v.set(v0);
    }

    void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int bulletDamage,
            float reloadInterval,
            Sound soundShoot,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0f, bulletVY);
        this.bulletDamage = bulletDamage;
        this.reloadInterval = reloadInterval;
        this.bulletSound = soundShoot;
        this.hp = hp;
        setHeightProportion(height);
        reloadTimer = reloadInterval;
        v.set(descentV);
        state = State.DESCENT;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        switch (state) {
            case DESCENT:
                if(getTop() <= worldBounds.getTop()) {
                    v.set(v0);
                    state = State.FIGHT;
                }
                break;
            case FIGHT:
                reloadTimer += deltaTime;
                if (reloadTimer >= reloadInterval) {
                    reloadTimer = 0f;
                    shoot();
                }
                if(getBottom() < worldBounds.getBottom()) {
                    mainShip.damage(bulletDamage);
                    boom();
                    destroy();
                }
                break;
            default:
                throw new RuntimeException("Unknown state = " + state);
        }
    }

    public boolean isBulletCollision (Rect bullet) {
        return !(bullet.getRight() < getLeft() || bullet.getLeft() > getRight() || bullet.getBottom() > getTop() || bullet.getTop() < pos.y);
    }
}
