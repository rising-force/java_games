package ru.geekuniversity.engine.utils;

import com.badlogic.gdx.utils.StringBuilder;

public class StrBuilder extends StringBuilder {

    public StringBuilder clear() {
        setLength(0);
        return this;
    }
}
