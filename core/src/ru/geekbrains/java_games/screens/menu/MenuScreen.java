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

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown: " + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp: " + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped: " + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown: x = " + screenX + " y = " + screenY + " pointer = " + pointer);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp: x = " + screenX + " y = " + screenY + " pointer = " + pointer);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged: x = " + screenX + " y = " + screenY + " pointer = " + pointer);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        System.out.println("mouseMoved: x = " + screenX + " y = " + screenY );
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        System.out.println("scrolled: " + amount);
        return false;
    }
}
