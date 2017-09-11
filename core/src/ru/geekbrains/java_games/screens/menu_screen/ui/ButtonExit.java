package ru.geekbrains.java_games.screens.menu_screen.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekuniversity.engine.math.Rect;
import ru.geekuniversity.engine.ui.ActionListener;
import ru.geekuniversity.engine.ui.ScaledTouchUpButton;

public class ButtonExit extends ScaledTouchUpButton {

    public ButtonExit(TextureAtlas atlas, ActionListener listener, float pressScale) {
        super(atlas.findRegion("btExit"), listener, pressScale);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom());
        setRight(worldBounds.getRight());
    }
}
