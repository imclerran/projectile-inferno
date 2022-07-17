package com.droptableteams.game.entities;

import com.droptableteams.game.LibECS.interfaces.AbstractEntity;

public class EnemyEntity extends AbstractEntity {
    public EnemyEntity(int id){
        _id = id;
        _type = "EnemyEntity";
    }
}
