package ru.geekbrains.java_games.pools;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.java_games.Explosion;
import ru.geekuniversity.engine.pool.SpritesPool;

public class ExplosionPool extends SpritesPool<Explosion> {

    private final TextureRegion explosionRegion;

    public ExplosionPool(TextureAtlas atlas) {
        String regionName = "explosion";
        explosionRegion = atlas.findRegion(regionName);
        if(explosionRegion == null) throw new RuntimeException("Region " + regionName + " not found.");
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(explosionRegion, 9, 9, 74);
    }

    @Override
    protected void debugLog() {
        System.out.println("ExplosionPool change active/free: " + activeObjects.size() + "/" + freeObjects.size());
    }
}
