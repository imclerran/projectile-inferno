package com.droptableteams.game.entities;

import com.droptableteams.game.LibECS.interfaces.AbstractEntity;


    public class BossEntity extends AbstractEntity {
        public BossEntity(int id){
            _id = id;
            _type = "BossEntity";
        }
    }


