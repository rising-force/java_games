package ru.geekbrains.java_games;

import com.badlogic.gdx.Game;

import ru.geekbrains.java_games.screens.menu.MenuScreen;

public class StarGame extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}
