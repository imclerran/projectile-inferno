package com.droptableteams.game.components.game;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class VictoryStateComponent extends AbstractComponent {

    private boolean _isGameOver;
    private boolean _isVictory;

    public VictoryStateComponent(int id) {
        _id = id;
        _type = "VictoryStateComponent";
        _isGameOver = false;
        _isVictory = false;
    }

    public boolean isGameOver() { return _isGameOver; }
    public boolean isVictory() { return _isVictory; }

    public void gameOverVictory() {
        _isGameOver = true;
        _isVictory = true;
    }
    public void gameOverDefeat() {
        _isGameOver = true;
        _isVictory = false;
    }
}
