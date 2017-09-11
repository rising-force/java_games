package ru.geekbrains.java_games.common.bullets;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.sprites.Sprite;

public class Bullet extends Sprite {

    private Rect worldBounds;
    private final Vector2 v = new Vector2();
    private int damage;
    private Object owner;

    Bullet() {
        regions = new TextureRegion[1];
    }

    public void set(
            Object owner,
            TextureRegion region,
            Vector2 pos0,
            Vector2 v0,
            float height,
            Rect worldBounds,
            int damage
            ) {
        this.owner = owner;
        regions[0] = region;
        pos.set(pos0);
        v.set(v0);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;
    }

    @Override
    public void update(float deltaTime) {
        pos.mulAdd(v, deltaTime);
        if(isOutside(worldBounds)) destroy();
    }

    public int getDamage() {
        return damage;
    }

    public Object getOwner() {
        return owner;
    }
}
