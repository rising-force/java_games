package ru.geekuniversity.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class Base2DScreen implements Screen{

    protected final Game game;

    public Base2DScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        System.out.println("show");
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize: width = " + width + " height = " + height);
    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
    }
}
