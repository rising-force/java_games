package ru.geekbrains.java_games.screens.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import ru.geekuniversity.engine.Base2DScreen;

public class GameScreen extends Base2DScreen {

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
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

        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
