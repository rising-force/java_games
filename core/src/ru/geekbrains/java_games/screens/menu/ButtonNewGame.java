package ru.geekbrains.java_games.screens.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.ui.ActionListener;
import ru.geekuniversity.engine.ui.ScaledTouchUpButton;

class ButtonNewGame extends ScaledTouchUpButton {

    ButtonNewGame(TextureAtlas atlas, ActionListener listener, float pressScale) {
        super(atlas.findRegion("btPlay"), listener, pressScale);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom());
        setLeft(worldBounds.getLeft());
    }
}
