package ru.geekbrains.java_games.pools;

import ru.geekbrains.java_games.Bullet;
import ru.geekuniversity.engine.pool.SpritesPool;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }

    @Override
    protected void debugLog() {
        System.out.println("BulletPool change active/free: " + activeObjects.size() + "/" + freeObjects.size());
    }
}
