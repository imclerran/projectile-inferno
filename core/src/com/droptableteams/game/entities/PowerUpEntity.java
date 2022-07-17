package com.droptableteams.game.entities;

import com.droptableteams.game.LibECS.interfaces.AbstractEntity;

public class PowerUpEntity extends AbstractEntity {
    public PowerUpEntity(int id){
        _id = id;
        _type = "PowerUpEntity";
    }
}
