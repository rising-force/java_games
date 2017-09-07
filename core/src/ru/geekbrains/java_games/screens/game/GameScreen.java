package ru.geekbrains.java_games.screens.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.java_games.Background;
import ru.geekbrains.java_games.pools.BulletPool;
import ru.geekbrains.java_games.screens.stars.TrackingStar;
import ru.geekuniversity.engine.Base2DScreen;
import ru.geekuniversity.engine.Sprite2DTexture;
import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.math.Rnd;

public class GameScreen extends Base2DScreen {

    private static final int STARS_COUNT = 50;
    private static final float STAR_HEIGHT = 0.01f;

    private final BulletPool bulletPool = new BulletPool();

    private Sprite2DTexture textureBackground;
    private TextureAtlas atlas;
    private Background background;
    private final TrackingStar[] stars = new TrackingStar[STARS_COUNT];
    private MainShip mainShip;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        textureBackground = new Sprite2DTexture("textures/bg.png");
        atlas = new TextureAtlas("textures/mainAtlas.tpack");

        background = new Background(new TextureRegion(textureBackground));
        mainShip = new MainShip(atlas, bulletPool);

        TextureRegion starRegion = atlas.findRegion("star");
        for (int i = 0; i < stars.length; i++) {
            float vx = Rnd.nextFloat(-0.005f, 0.005f);
            float vy = Rnd.nextFloat(-0.05f, -0.1f);
            float starHeight = STAR_HEIGHT * Rnd.nextFloat(0.75f, 1f);
            stars[i] = new TrackingStar(starRegion, vx, vy, starHeight, mainShip.getV());
        }
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

    private void update(float deltaTime) {
        for (int i = 0; i < stars.length; i++) stars[i].update(deltaTime);
        bulletPool.updateActiveSprites(deltaTime);
        mainShip.update(deltaTime);
    }

    private void checkCollisions() {

    }

    private void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length; i++) stars[i].draw(batch);
        bulletPool.drawActiveObjects(batch);
        mainShip.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        textureBackground.dispose();
        atlas.dispose();
        bulletPool.dispose();
        super.dispose();
    }
}
