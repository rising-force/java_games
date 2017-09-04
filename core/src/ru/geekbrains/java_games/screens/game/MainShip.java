package ru.geekbrains.java_games.screens.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekuniversity.engine.sprites.Sprite;

class MainShip extends Sprite {

    private static final float SHIP_HEIGHT = 0.15f;

    MainShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        setHeightProportion(SHIP_HEIGHT);
    }
}
