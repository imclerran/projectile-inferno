package com.droptableteams.game.entities;

import com.droptableteams.game.LibECS.interfaces.AbstractEntity;

public class VisibleHitboxEntity extends AbstractEntity {
    public VisibleHitboxEntity(int id) {
        _id = id;
        _type = "VisibleHitboxEntity";
    }
}
