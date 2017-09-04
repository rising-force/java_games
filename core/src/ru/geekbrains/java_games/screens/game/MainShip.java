package ru.geekbrains.java_games.screens.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.sprites.Sprite;

class MainShip extends Sprite {

    private static final float SHIP_HEIGHT = 0.15f;
    private static final float  BOTTOM_MARGIN = 0.05f;

    private final Vector2 v0 = new Vector2(0.5f, 0f);
    private final Vector2 v = new Vector2();

    MainShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        setHeightProportion(SHIP_HEIGHT);
    }

    void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                moveRight();
                break;
            case Input.Keys.UP:
                frame = 1;
                break;
        }
    }

    void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                stop();
                break;
            case Input.Keys.UP:
                frame = 0;
                break;
        }
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    @Override
    public void update(float deltaTime) {
        pos.mulAdd(v, deltaTime);

    }
}
