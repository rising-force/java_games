package ru.geekbrains.java_games.screens.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekuniversity.engine.sprites.Sprite;

public class MainShip extends Sprite {

    public MainShip(TextureAtlas atlas) {
        TextureRegion region = atlas.findRegion("main_ship");
        region.split()
//        super(region);
    }
}
