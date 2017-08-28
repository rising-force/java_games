package ru.geekbrains.java_games.screens.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekuniversity.engine.Base2DScreen;
import ru.geekuniversity.engine.Sprite2DTexture;
import ru.geekuniversity.engine.sprites.Sprite;

public class MenuScreen extends Base2DScreen {

    private Sprite2DTexture textureCircle;
    private TextureRegion textureRegion;
    private Sprite circle;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        textureCircle = new Sprite2DTexture("circle.png");
        circle = new Sprite(new TextureRegion(textureCircle));
        circle.setWidthProportion(0.67f);
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
        circle.draw(batch);
        batch.end();
    }

    @Override
    public void dispose () {
        textureCircle.dispose();
        super.dispose();
    }
}
