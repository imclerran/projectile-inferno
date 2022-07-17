package com.droptableteams.game.components;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class PowerUpComponent extends AbstractComponent {

    public String _powerUpType;
    public boolean _hasPowerUp;

    public PowerUpComponent(int id, String powerUpType){
        _id = id;
        _type = "PowerUpComponent";
        _powerUpType = powerUpType;
        if( powerUpType.equals("")){
            _powerUpType = null;
            _hasPowerUp = false;
        }else {
            _powerUpType = "scripts/powerUp/" + powerUpType + ".json";
            _hasPowerUp = true;
        }
        //powerUp(powerUpType);
    }

    private void powerUp(String powerUp){
        if(powerUp != ""){
            _hasPowerUp = true;
        }
        else{
            _hasPowerUp = false;
        }
    }
}
