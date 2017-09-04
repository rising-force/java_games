package ru.geekbrains.java_games.screens.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.java_games.screens.game.GameScreen;
import ru.geekbrains.java_games.screens.stars.Star;
import ru.geekuniversity.engine.Base2DScreen;
import ru.geekuniversity.engine.Sprite2DTexture;
import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.math.Rnd;
import ru.geekuniversity.engine.ui.ActionListener;

public class MenuScreen extends Base2DScreen implements ActionListener {

    private static final int STARS_COUNT = 250;
    private static final float STAR_HEIGHT = 0.01f;

    private static final float BUTTONS_HEIGHT = 0.15f;
    private static final float BUTTONS_PRESS_SCALE = 0.9f;

    private Sprite2DTexture textureBackground;
    private TextureAtlas atlas;
    private ru.geekbrains.java_games.Background background;
    private final Star[] stars = new Star[STARS_COUNT];
    private ButtonExit buttonExit;
    private ButtonNewGame buttonNewGame;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        textureBackground = new Sprite2DTexture("textures/bg.png");
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        background = new ru.geekbrains.java_games.Background(new TextureRegion(textureBackground));
        TextureRegion regionStar = atlas.findRegion("star");
        for (int i = 0; i < stars.length; i++) {
            float vx = Rnd.nextFloat(-0.005f, 0.005f);
            float vy = Rnd.nextFloat(-0.05f, -0.1f);
            float starHeight = STAR_HEIGHT * Rnd.nextFloat(0.75f, 1f);
            stars[i] = new Star(regionStar, vx, vy, starHeight);
        }
        buttonNewGame = new ButtonNewGame(atlas, this, BUTTONS_PRESS_SCALE);
        buttonNewGame.setHeightProportion(BUTTONS_HEIGHT);
        buttonExit = new ButtonExit(atlas, this, BUTTONS_PRESS_SCALE);
        buttonExit.setHeightProportion(BUTTONS_HEIGHT);
    }

    @Override
    protected void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < stars.length; i++) stars[i].resize(worldBounds);
        buttonExit.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        buttonExit.touchDown(touch, pointer);
        buttonNewGame.touchDown(touch, pointer);
    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        buttonExit.touchUp(touch, pointer);
        buttonNewGame.touchUp(touch, pointer);
    }

    @Override
    public void actionPerformed(Object src) {
        if(src == buttonExit) {
            Gdx.app.exit();
        } else if(src == buttonNewGame) {
            game.setScreen(new GameScreen(game));
        } else {
            throw new RuntimeException("Unknown src = " + src);
        }
    }

    @Override
    public void render (float delta) {
        update(delta);
        draw();
    }

    private void update(float deltaTime) {
        for (int i = 0; i < stars.length; i++) stars[i].update(deltaTime);
    }

    private void draw() {
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < stars.length; i++) stars[i].draw(batch);
        buttonExit.draw(batch);
        buttonNewGame.draw(batch);
        batch.end();
    }

    @Override
    public void dispose () {
        textureBackground.dispose();
        atlas.dispose();
        super.dispose();
    }
}
