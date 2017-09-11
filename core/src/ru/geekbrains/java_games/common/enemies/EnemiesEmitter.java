package ru.geekbrains.java_games.common.enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.math.Rnd;
import ru.geekuniversity.engine.utils.Regions;

public class EnemiesEmitter {

    private final Rect worldBounds;
    private final EnemyPool enemyPool;

    private final Sound sndBullet;
    private final TextureRegion bulletRegion;

    private static final float ENEMY_SMALL_HEIGHT = 0.08f;
    private final TextureRegion[] enemySmallRegions;
    private final Vector2 enemySmallV = new Vector2(0f, -0.2f);
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_BULLET_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_SMALL_HP = 1;

//    private final TextureRegion[] enemyMediumRegions;

//    private final TextureRegion[] enemyBigRegions;

    private float generateInterval = 4f;
    private float generateTimer;

    public EnemiesEmitter(EnemyPool enemyPool, Rect worldBounds, TextureAtlas atlas, Sound sndBullet) {
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
        this.sndBullet = sndBullet;
        TextureRegion region0 = atlas.findRegion("enemy0");
        enemySmallRegions = Regions.split(region0, 1, 2, 2);

        bulletRegion = atlas.findRegion("bulletEnemy");
    }

    public void generateEnemies(float deltaTime) {
        generateTimer += deltaTime;
        if(generateTimer >= generateInterval) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            enemy.set(
                    enemySmallRegions,
                    enemySmallV,
                    bulletRegion,
                    ENEMY_SMALL_BULLET_HEIGHT,
                    ENEMY_SMALL_BULLET_VY,
                    ENEMY_SMALL_BULLET_DAMAGE,
                    ENEMY_SMALL_RELOAD_INTERVAL,
                    sndBullet,
                    ENEMY_SMALL_HEIGHT,
                    ENEMY_SMALL_HP
            );
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }
}
