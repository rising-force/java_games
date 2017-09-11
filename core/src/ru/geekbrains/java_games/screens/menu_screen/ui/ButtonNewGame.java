package ru.geekbrains.java_games.screens.menu_screen.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.ui.ActionListener;
import ru.geekuniversity.engine.ui.ScaledTouchUpButton;

public class ButtonNewGame extends ScaledTouchUpButton {

    public ButtonNewGame(TextureAtlas atlas, ActionListener listener, float pressScale) {
        super(atlas.findRegion("btPlay"), listener, pressScale);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom());
        setLeft(worldBounds.getLeft());
    }
}
