package ru.geekbrains.java_games.screens.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.geekuniversity.engine.Base2DScreen;

public class MenuScreen extends Base2DScreen {

    private SpriteBatch batch;
    private Texture textureCircle;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        batch.getProjectionMatrix().idt().translate(0.1f, 0.2f, 0f).scale(0.1f, 0.1f, 1f);
        textureCircle = new Texture("circle.png");
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0.7f, 0.7f, 0.7f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(textureCircle, -1f, -1f, 2f, 2f);
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
        textureCircle.dispose();
        super.dispose();
    }
}
