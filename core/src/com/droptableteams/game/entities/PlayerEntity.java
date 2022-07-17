package com.droptableteams.game.entities;

import com.droptableteams.game.LibECS.interfaces.AbstractEntity;

public class PlayerEntity extends AbstractEntity {
    public PlayerEntity(int id) {
        _id = id;
        _type = "PlayerEntity";
    }
}
