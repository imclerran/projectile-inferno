package com.droptableteams.game.components.game;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;
import com.droptableteams.game.util.Utils;

public class GameTimeComponent extends AbstractComponent {
    private long _startTime;

    public GameTimeComponent(int id) {
        _id = id;
        _type = "GameTimeComponent";
        _startTime = Utils.nanosToMillis(System.nanoTime());
    }

    public long getTimeInMillis() {
        long now = Utils.nanosToMillis(System.nanoTime());
        return now - _startTime;
    }
}
