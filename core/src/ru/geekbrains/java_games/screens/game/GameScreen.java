package ru.geekbrains.java_games.screens.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.java_games.Background;
import ru.geekuniversity.engine.Base2DScreen;
import ru.geekuniversity.engine.Sprite2DTexture;
import ru.geekuniversity.engine.math.Rect;

public class GameScreen extends Base2DScreen {

    private Sprite2DTexture textureBackground;
    private TextureAtlas atlas;
    private Background background;
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
        mainShip = new MainShip(atlas);
    }

    @Override
    protected void resize(Rect worldBounds) {
        background.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw();
    }

    private void update(float deltaTime) {

    }

    private void checkCollisions() {

    }

    private void deleteAllDestroyed() {

    }

    private void draw() {
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        mainShip.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        textureBackground.dispose();
        atlas.dispose();
        super.dispose();
    }
}
