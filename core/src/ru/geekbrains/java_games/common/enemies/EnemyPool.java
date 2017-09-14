package ru.geekbrains.java_games.common.enemies;

import ru.geekbrains.java_games.common.bullets.BulletPool;
import ru.geekbrains.java_games.common.explosions.ExplosionPool;
import ru.geekbrains.java_games.screens.game_screen.MainShip;
import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.pool.SpritesPool;

public class EnemyPool extends SpritesPool<Enemy> {

    private final BulletPool bulletPool;
    private final ExplosionPool explosionPool;
    private final Rect worldBounds;
    private final MainShip mainShip;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, MainShip mainShip) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.mainShip = mainShip;
    }

    @Override
    protected Enemy newObject() {
        return new Enemy(bulletPool, explosionPool, worldBounds, mainShip);
    }

    @Override
    protected void debugLog() {
        System.out.println("EnemyPool change active/free: " + activeObjects.size() + "/" + freeObjects.size());
    }
}
