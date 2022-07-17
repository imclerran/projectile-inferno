package com.droptableteams.game.entities;

import com.droptableteams.game.LibECS.interfaces.AbstractEntity;

public class LifeDisplayEntity extends AbstractEntity {
    public LifeDisplayEntity(int id){
        _id = id;
        _type = "LifeDisplayEntity";
    }
}
