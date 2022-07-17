package com.droptableteams.game.entities;

import com.droptableteams.game.LibECS.interfaces.AbstractEntity;

public class BulletEntity extends AbstractEntity {
    public BulletEntity(int id) {
        _id = id;
        _type = "BulletEntity";
    }
}
