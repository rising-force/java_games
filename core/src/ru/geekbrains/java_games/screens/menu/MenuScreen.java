package ru.geekbrains.java_games.screens.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekuniversity.engine.Base2DScreen;
import ru.geekuniversity.engine.Sprite2DTexture;
import ru.geekuniversity.engine.math.Rect;

public class MenuScreen extends Base2DScreen {

    private Sprite2DTexture textureBackground;
    private Background background;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        textureBackground = new Sprite2DTexture("textures/bg.png");
        background = new Background(new TextureRegion(textureBackground));
    }

    @Override
    protected void resize(Rect worldBounds) {
        background.resize(worldBounds);
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        System.out.println(touch);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        batch.end();
    }

    @Override
    public void dispose () {
        textureBackground.dispose();
        super.dispose();
    }
}
