package com.droptableteams.game.entities;

import com.droptableteams.game.LibECS.interfaces.AbstractEntity;

public class StaticSpriteEntity extends AbstractEntity {
    public StaticSpriteEntity(int id){
        _id = id;
        _type = "StaticSpriteEntity";
    }
}
