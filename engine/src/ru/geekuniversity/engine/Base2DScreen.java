package ru.geekuniversity.engine;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.geekuniversity.engine.math.MatrixUtils;
import ru.geekuniversity.engine.math.Rect;

public class Base2DScreen implements Screen, InputProcessor {

    private static final float WORLD_HEIGHT = 1f;

    protected final Game game;

    private final Rect screenBounds = new Rect(); //граница области рисования в px
    private final Rect worldBounds = new Rect();  //желаемые граница проекции мировых координат
    private final Rect glBounds = new Rect(0f, 0f, 1f, 1f); //дефолтные границы проекции мир - gl

    /**
     * Эта матрица используется SpriteBatch.
     * С помощью неё, он наши мировые координаты переводит в GL(-1, 1, -1, 1) для последующей отрисовки.
     * SpriteBather умеет работать только с мартицей 4x4.
     */
    protected final Matrix4 matWorldToGL = new Matrix4();

    /**
     * Эту матрицу мы будем использовать чтобы переводить тачи из экранных координат в мировые.
     * Тут нам удобнее использовать матрицу 3x3, так как класс Vector2 умеет умножатся на неё.
     */
    protected final Matrix3 matScreenToWorld = new Matrix3();

    //С помощью батчера будет рисовать спрайты в наследниках
    protected SpriteBatch batch;

    public Base2DScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        System.out.println("show");
        Gdx.input.setInputProcessor(this);
        if(batch != null) throw new RuntimeException("batch != null, повторная установка screen без dispose");
        batch = new SpriteBatch();
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize: width = " + width + " height = " + height);
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width / (float)height;
        worldBounds.setHeight(WORLD_HEIGHT);
        worldBounds.setWidth(WORLD_HEIGHT * aspect);
        //Расчитываем матрицу перехода Мир-GL
        MatrixUtils.calcTransitionMatrix(matWorldToGL, worldBounds, glBounds);
        //И устанавливаем её батчеру. В общем то она нам больше и не нужна
        batch.setProjectionMatrix(matWorldToGL);
        //Рассчитываем матрицу перехода Экран - Мир
        MatrixUtils.calcTransitionMatrix(matScreenToWorld, screenBounds, worldBounds);
        resize(worldBounds);
    }

    protected void resize(Rect worldBounds) {
    }

    @Override
    public void render(float delta) {
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
        batch.dispose();
        batch = null;
    }

    //Для перехват тачей оверрайдим ЭТИ методы!!!
    protected void touchDown(Vector2 touch, int pointer) {
    }

    protected void touchMove(Vector2 touch, int pointer) {
    }

    protected void touchUp(Vector2 touch, int pointer) {
    }

    //Эти методы НЕ оверрайлим НИКОГДА
    private final Vector2 touch = new Vector2(); //Вектор для принятия/перевода/передачи тачей

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - 1f - screenY).mul(matScreenToWorld);
        touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, screenBounds.getHeight() - 1f - screenY).mul(matScreenToWorld);
        touchUp(touch, pointer);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        touch.set(screenX, screenBounds.getHeight() - 1f - screenY).mul(matScreenToWorld);
        touchMove(touch, pointer);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
