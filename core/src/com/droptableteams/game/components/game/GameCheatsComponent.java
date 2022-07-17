package com.droptableteams.game.components.game;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class GameCheatsComponent extends AbstractComponent {

    private float _speedMultiplier;
    private float _slowMultiplier;

    public GameCheatsComponent(int id, float slowMultiplier) {
        _id = id;
        _speedMultiplier = 1f;
        _slowMultiplier = slowMultiplier;
        _type = "GameCheatsComponent";
    }

    public void toggleSpeedMultiplier(){
        if(_speedMultiplier == _slowMultiplier){
            _speedMultiplier = 1f;
        }else {
            _speedMultiplier = _slowMultiplier;
        }
    }

    public void setSpeedMultiplier(float multiplier) { _speedMultiplier = multiplier; }
    public float getSpeedMultiplier() { return _speedMultiplier; }
}
