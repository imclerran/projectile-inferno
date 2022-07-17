package com.droptableteams.game.entities;

import com.droptableteams.game.LibECS.interfaces.AbstractEntity;

public class FireControlEntity extends AbstractEntity {
    public FireControlEntity(int id) {
        _id = id;
        _type = "FireControllerEntity";
    }
}
