package ru.geekbrains.java_games;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekuniversity.engine.sprites.Sprite;

public class Explosion extends Sprite {

    private float animateInterval = 0.017f;
    private float animateTimer;

    public Explosion(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public void set(float height, Vector2 pos) {
        frame = 0;
        this.pos.set(pos);
        setHeightProportion(height);
    }

    @Override
    public void update(float deltaTime) {
        animateTimer += deltaTime;
        if(animateTimer >= animateInterval) {
            animateTimer = 0f;
            if(++frame == regions.length) destroy();
        }
    }
}
