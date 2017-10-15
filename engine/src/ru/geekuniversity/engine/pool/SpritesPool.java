package ru.geekuniversity.engine.pool;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import ru.geekuniversity.engine.sprites.Sprite;

public abstract class SpritesPool<T extends Sprite> {

    protected final ArrayList<T> activeSprites = new ArrayList<T>();
    protected final ArrayList<T> freeSprites = new ArrayList<T>();

    protected abstract T newObject();

    public T obtain() {
        T sprite;
        if (freeSprites.isEmpty())
            sprite = newObject();
        else
            sprite =  freeSprites.remove(freeSprites.size() - 1);
        activeSprites.add(sprite);
        debugLog();
        return sprite;
    }

    public void free(T sprite) {
        if (!activeSprites.remove(sprite)) throw new RuntimeException("Deleting nonexistent sprite = " + sprite);
        freeSprites.add(sprite);
        sprite.flushDestroy();
        debugLog();
    }

    public void freeAllActiveSprites() {
        final int cnt = activeSprites.size();
        for (int i = 0; i < cnt; i++) {
            T sprite = activeSprites.get(i);
            freeSprites.add(sprite);
            sprite.flushDestroy();
        }
        activeSprites.clear();
        debugLog();
    }

    public void updateActiveSprites(float deltaTime) {
        final int cnt = activeSprites.size();
        for (int i = 0; i < cnt; i++) {
            Sprite sprite = activeSprites.get(i);
            if(sprite.isDestroyed()) throw new RuntimeException("Update destroyed sprite");
            sprite.update(deltaTime);
        }
    }

    public ArrayList<T> getActiveSprites() {
        return activeSprites;
    }

    public void freeAllDestroyedActiveSprites() {
        for (int i = 0; i < activeSprites.size(); i++) {
            T sprite = activeSprites.get(i);
            if(sprite.isDestroyed()) {
                free(sprite);
                i--;
            }
        }
    }

    public void drawActiveSprites(SpriteBatch batch) {
        final int cnt = activeSprites.size();
        for (int i = 0; i < cnt; i++) {
            Sprite sprite = activeSprites.get(i);
            if(sprite.isDestroyed()) throw new RuntimeException("Draw destroyed sprite");
            sprite.draw(batch);
        }
    }

    public void foregroundSprite(T sprite) {
        if (!activeSprites.remove(sprite)) throw new RuntimeException("!activeSprites.remove(sprite)");
        activeSprites.add(sprite);
    }

    protected void debugLog() {
    }

    public void dispose() {
        freeSprites.clear();
        activeSprites.clear();
    }
}
