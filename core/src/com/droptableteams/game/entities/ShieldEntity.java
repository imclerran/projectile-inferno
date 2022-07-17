package com.droptableteams.game.entities;

import com.droptableteams.game.LibECS.interfaces.AbstractEntity;

public class ShieldEntity extends AbstractEntity {
    public ShieldEntity(int id){
        _id = id;
        _type = "ShieldEntity";
    }
}
