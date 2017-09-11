package ru.geekbrains.java_games.screens.game_screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.java_games.common.Background;
import ru.geekbrains.java_games.common.explosions.Explosion;
import ru.geekbrains.java_games.common.bullets.BulletPool;
import ru.geekbrains.java_games.common.explosions.ExplosionPool;
import ru.geekbrains.java_games.common.stars.TrackingStar;
import ru.geekuniversity.engine.Base2DScreen;
import ru.geekuniversity.engine.Sprite2DTexture;
import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.math.Rnd;

public class GameScreen extends Base2DScreen {

    private static final int STARS_COUNT = 50;
    private static final float STAR_HEIGHT = 0.01f;

    private final BulletPool bulletPool = new BulletPool();
    private ExplosionPool explosionPool;

    private Sprite2DTexture textureBackground;
    private TextureAtlas atlas;
    private Background background;
    private final TrackingStar[] stars = new TrackingStar[STARS_COUNT];
    private MainShip mainShip;

    private Music music;
    private Sound sndLaser;
    private Sound sndBullet;
    private Sound sndExplosion;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        sndBullet = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        sndLaser = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        sndExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        textureBackground = new Sprite2DTexture("textures/bg.png");
        atlas = new TextureAtlas("textures/mainAtlas.tpack");

        explosionPool = new ExplosionPool(atlas, sndExplosion);

        background = new Background(new TextureRegion(textureBackground));
        mainShip = new MainShip(atlas, bulletPool, explosionPool, worldBounds, sndLaser);

        TextureRegion starRegion = atlas.findRegion("star");
        for (int i = 0; i < stars.length; i++) {
            float vx = Rnd.nextFloat(-0.005f, 0.005f);
            float vy = Rnd.nextFloat(-0.05f, -0.1f);
            float starHeight = STAR_HEIGHT * Rnd.nextFloat(0.75f, 1f);
            stars[i] = new TrackingStar(starRegion, vx, vy, starHeight, mainShip.getV());
        }

        music.setLooping(true);
        music.play();
    }

    @Override
    protected void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < stars.length; i++) stars[i].resize(worldBounds);
        mainShip.resize(worldBounds);
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch, pointer);
//        Explosion explosion = explosionPool.obtain();
//        explosion.set(0.1f, touch);
    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

//    private float randomBoomInterval = 3f;
//    private float randomBoomTimer;
//    private final Vector2 randomBoomPos = new Vector2();

    private void update(float deltaTime) {
//        randomBoomTimer += deltaTime;
//        if(randomBoomTimer >= randomBoomInterval) {
//            randomBoomTimer = 0f;
//            Explosion explosion = explosionPool.obtain();
//            randomBoomPos.x = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
//            randomBoomPos.y = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
//            explosion.set(0.1f, randomBoomPos);
//        }
        for (int i = 0; i < stars.length; i++) stars[i].update(deltaTime);
        bulletPool.updateActiveSprites(deltaTime);
        explosionPool.updateActiveSprites(deltaTime);
        mainShip.update(deltaTime);
    }

    private void checkCollisions() {

    }

    private void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length; i++) stars[i].draw(batch);
        bulletPool.drawActiveObjects(batch);
        explosionPool.drawActiveObjects(batch);
        mainShip.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        music.dispose();
        sndBullet.dispose();
        sndLaser.dispose();
        sndExplosion.dispose();

        bulletPool.dispose();
        explosionPool.dispose();
        textureBackground.dispose();
        atlas.dispose();
        super.dispose();
    }
}
