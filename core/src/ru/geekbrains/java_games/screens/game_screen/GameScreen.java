package ru.geekbrains.java_games.screens.game_screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

import ru.geekbrains.java_games.common.Background;
import ru.geekbrains.java_games.common.bullets.Bullet;
import ru.geekbrains.java_games.common.bullets.BulletPool;
import ru.geekbrains.java_games.common.enemies.EnemiesEmitter;
import ru.geekbrains.java_games.common.enemies.Enemy;
import ru.geekbrains.java_games.common.enemies.EnemyPool;
import ru.geekbrains.java_games.common.explosions.ExplosionPool;
import ru.geekbrains.java_games.common.stars.TrackingStar;
import ru.geekbrains.java_games.screens.game_screen.ui.ButtonNewGame;
import ru.geekbrains.java_games.screens.game_screen.ui.MessageGameOver;
import ru.geekuniversity.engine.Base2DScreen;
import ru.geekuniversity.engine.Font;
import ru.geekuniversity.engine.Sprite2DTexture;
import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.math.Rnd;
import ru.geekuniversity.engine.ui.ActionListener;
import ru.geekuniversity.engine.utils.StrBuilder;

public class GameScreen extends Base2DScreen implements ActionListener {

    private static final int STARS_COUNT = 50;
    private static final float STAR_HEIGHT = 0.01f;
    private static final float FONT_SIZE = 0.02f;

    private enum State { PLAYING, GAME_OVER }

    private State state;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;

    private Sprite2DTexture textureBackground;
    private TextureAtlas atlas;
    private Font font;
    private Background background;
    private final TrackingStar[] stars = new TrackingStar[STARS_COUNT];
    private MainShip mainShip;
    private MessageGameOver messageGameOver;
    private ButtonNewGame buttonNewGame;
    private EnemiesEmitter enemiesEmitter;

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

        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, sndExplosion);
        mainShip = new MainShip(atlas, bulletPool, explosionPool, worldBounds, sndLaser);
        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds, mainShip);

        enemiesEmitter = new EnemiesEmitter(enemyPool, worldBounds, atlas, sndBullet);

        background = new Background(new TextureRegion(textureBackground));
        TextureRegion starRegion = atlas.findRegion("star");
        for (int i = 0; i < stars.length; i++) {
            float vx = Rnd.nextFloat(-0.005f, 0.005f);
            float vy = Rnd.nextFloat(-0.05f, -0.1f);
            float starHeight = STAR_HEIGHT * Rnd.nextFloat(0.75f, 1f);
            stars[i] = new TrackingStar(starRegion, vx, vy, starHeight, mainShip.getV());
        }

        messageGameOver = new MessageGameOver(atlas);
        buttonNewGame = new ButtonNewGame(atlas, this);

        font = new Font("fonts/font.fnt", "fonts/font.png");
        font.setWorldSize(FONT_SIZE);

        music.setLooping(true);
        music.play();
        startNewGame();
    }

    private void startNewGame() {
        state = State.PLAYING;
        frags = 0;
        mainShip.setToNewGame();
        enemiesEmitter.setToNewGame();
        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
    }

    @Override
    protected void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < stars.length; i++) stars[i].resize(worldBounds);
        mainShip.resize(worldBounds);
    }

    @Override
    public void actionPerformed(Object src) {
        if(src == buttonNewGame) {
            startNewGame();
        } else {
            throw new RuntimeException("Unknown src = " + src);
        }
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        switch (state) {
            case PLAYING:
                mainShip.touchDown(touch, pointer);
                break;
            case GAME_OVER:
                buttonNewGame.touchDown(touch, pointer);
                break;
            default:
                throw new RuntimeException("Unknown state = " + state);
        }
    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        switch (state) {
            case PLAYING:
                mainShip.touchUp(touch, pointer);
                break;
            case GAME_OVER:
                buttonNewGame.touchUp(touch, pointer);
                break;
            default:
                throw new RuntimeException("Unknown state = " + state);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if(state == State.PLAYING) mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(state == State.PLAYING) mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public void render(float delta) {
        update(delta);
        if(state == State.PLAYING) checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    private void update(float deltaTime) {
        for (int i = 0; i < stars.length; i++) stars[i].update(deltaTime);
        explosionPool.updateActiveSprites(deltaTime);
        if(state == State.PLAYING) {
            mainShip.update(deltaTime);
            enemiesEmitter.generateEnemies(deltaTime, frags);
            bulletPool.updateActiveSprites(deltaTime);
            enemyPool.updateActiveSprites(deltaTime);
            if(mainShip.isDestroyed()) {
                mainShip.boom();
                state = State.GAME_OVER;
            }
        }
    }

    private void checkCollisions() {
        ArrayList<Enemy> enemies = enemyPool.getActiveObjects();
        final int enemyCount = enemies.size();
        ArrayList<Bullet> bullets = bulletPool.getActiveObjects();
        final int bulletCount = bullets.size();

        for (int i = 0; i < enemyCount; i++) {
            Enemy enemy = enemies.get(i);
            if(enemy.isDestroyed()) continue;
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if(enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                enemy.boom();
                enemy.destroy();
                mainShip.boom();
                mainShip.destroy();
                state = State.GAME_OVER;
                return;
            }
        }

        for (int i = 0; i < bulletCount; i++) {
            Bullet bullet = bullets.get(i);
            if(bullet.isDestroyed() || bullet.getOwner() == mainShip) continue;
            if(mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
                if(mainShip.isDestroyed()) {
                    mainShip.boom();
                    state = State.GAME_OVER;
                    return;
                }
            }
        }

        for (int i = 0; i < enemyCount; i++) {
            Enemy enemy = enemies.get(i);
            if(enemy.isDestroyed()) continue;
            for (int j = 0; j < bulletCount; j++) {
                Bullet bullet = bullets.get(j);
                if(bullet.getOwner() != mainShip || bullet.isDestroyed()) continue;
                if(enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                    if(enemy.isDestroyed()) {
                        enemy.boom();
                        frags++;
                        break;
                    }
                }
            }
        }
    }

    private void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length; i++) stars[i].draw(batch);
        bulletPool.drawActiveObjects(batch);
        enemyPool.drawActiveObjects(batch);
        explosionPool.drawActiveObjects(batch);
        mainShip.draw(batch);
        printInfo();
        if(state == State.GAME_OVER) {
            messageGameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
        batch.end();
    }

    private int frags;

    private static final String STR_FRAGS = "Frags: ";
    private StrBuilder sbFrags = new StrBuilder();

    private static final String STR_HP = "HP: ";
    private StrBuilder sbHP = new StrBuilder();

    private static final String STR_STAGE = "Stage: ";
    private StrBuilder sbStage = new StrBuilder();

    private void printInfo() {
        font.draw(batch, sbFrags.clear().append(STR_FRAGS).append(frags), worldBounds.getLeft(), worldBounds.getTop());
        font.draw(batch, sbHP.clear().append(STR_HP).append(mainShip.getHP()), worldBounds.pos.x, worldBounds.getTop(), Align.center);
        font.draw(batch, sbStage.clear().append(STR_STAGE).append(enemiesEmitter.getStage()), worldBounds.getRight(), worldBounds.getTop(), Align.right);
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
        font.dispose();
        super.dispose();
    }
}
