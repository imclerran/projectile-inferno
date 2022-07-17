package com.droptableteams.game.entities;

import com.droptableteams.game.LibECS.interfaces.AbstractEntity;

public class GameEntity extends AbstractEntity {
    public GameEntity(int id) {
        _id = id;
        _type = "GameEntity";
    }
}
