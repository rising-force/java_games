package ru.geekbrains.java_games.common.enemies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.math.Rnd;
import ru.geekuniversity.engine.utils.Regions;

public class EnemiesEmitter {

    private int stage;

    private final Rect worldBounds;
    private final EnemyPool enemyPool;

    private final Sound sndBullet;
    private final TextureRegion bulletRegion;

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private final TextureRegion[] enemySmallRegions;
    private final Vector2 enemySmallV = new Vector2(0f, -0.2f);
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_BULLET_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_SMALL_HP = 1;

    private static final float ENEMY_MEDIUM_HEIGHT = 0.1f;
    private final TextureRegion[] enemyMediumRegions;
    private final Vector2 enemyMediumV = new Vector2(0f, -0.03f);
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_MEDIUM_BULLET_VY = -0.25f;
    private static final int ENEMY_MEDIUM_BULLET_DAMAGE = 5;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 4f;
    private static final int ENEMY_MEDIUM_HP = 5;

    private static final float ENEMY_BIG_HEIGHT = 0.2f;
    private final TextureRegion[] enemyBigRegions;
    private final Vector2 enemyBigV = new Vector2(0f, -0.005f);
    private static final float ENEMY_BIG_BULLET_HEIGHT = 0.04f;
    private static final float ENEMY_BIG_BULLET_VY = -0.3f;
    private static final int ENEMY_BIG_BULLET_DAMAGE = 10;
    private static final float ENEMY_BIG_RELOAD_INTERVAL = 1f;
    private static final int ENEMY_BIG_HP = 20;


    private float generateInterval = 4f;
    private float generateTimer;

    public EnemiesEmitter(EnemyPool enemyPool, Rect worldBounds, TextureAtlas atlas, Sound sndBullet) {
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
        this.sndBullet = sndBullet;
        TextureRegion region0 = atlas.findRegion("enemy0");
        enemySmallRegions = Regions.split(region0, 1, 2, 2);
        TextureRegion region1 = atlas.findRegion("enemy1");
        enemyMediumRegions = Regions.split(region1, 1, 2, 2);
        TextureRegion region2 = atlas.findRegion("enemy2");
        enemyBigRegions = Regions.split(region2, 1, 2, 2);
        bulletRegion = atlas.findRegion("bulletEnemy");
    }

    public void setToNewGame() {
        stage = 1;
    }

    public int getStage() {
        return stage;
    }

    public void generateEnemies(float deltaTime, int frags) {
        stage = frags / 10 + 1;
        generateTimer += deltaTime;
        if(generateTimer >= generateInterval) {
            generateTimer = 0f;
            Enemy enemy = enemyPool.obtain();
            float type = (float) Math.random();
            if(type < 0.7f) {
                enemy.set(
                        enemySmallRegions,
                        enemySmallV,
                        bulletRegion,
                        ENEMY_SMALL_BULLET_HEIGHT,
                        ENEMY_SMALL_BULLET_VY,
                        ENEMY_SMALL_BULLET_DAMAGE * stage,
                        ENEMY_SMALL_RELOAD_INTERVAL,
                        sndBullet,
                        ENEMY_SMALL_HEIGHT,
                        ENEMY_SMALL_HP * stage);
            } else if(type < 0.9f) {
                enemy.set(
                        enemyMediumRegions,
                        enemyMediumV,
                        bulletRegion,
                        ENEMY_MEDIUM_BULLET_HEIGHT,
                        ENEMY_MEDIUM_BULLET_VY,
                        ENEMY_MEDIUM_BULLET_DAMAGE * stage,
                        ENEMY_MEDIUM_RELOAD_INTERVAL,
                        sndBullet,
                        ENEMY_MEDIUM_HEIGHT,
                        ENEMY_MEDIUM_HP * stage);
            } else {
                enemy.set(
                        enemyBigRegions,
                        enemyBigV,
                        bulletRegion,
                        ENEMY_BIG_BULLET_HEIGHT,
                        ENEMY_BIG_BULLET_VY,
                        ENEMY_BIG_BULLET_DAMAGE * stage,
                        ENEMY_BIG_RELOAD_INTERVAL,
                        sndBullet,
                        ENEMY_BIG_HEIGHT,
                        ENEMY_BIG_HP * stage);
            }
            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
//            enemy.pos.x = worldBounds.getLeft() + enemy.getHalfWidth();
            enemy.setBottom(worldBounds.getTop());
        }
    }
}
