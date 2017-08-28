package ru.geekbrains.java_games.screens.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.geekuniversity.engine.Base2DScreen;

public class MenuScreen extends Base2DScreen {

    private Texture textureCircle;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        textureCircle = new Texture("circle.png");
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
        batch.draw(textureCircle, -0.5f, -0.5f, 1f, 1f);
        batch.end();
    }

    @Override
    public void dispose () {
        textureCircle.dispose();
        super.dispose();
    }
}
