package com.droptableteams.game.events;

import com.droptableteams.game.LibECS.interfaces.AbstractEvent;

import java.util.HashMap;

// TODO: rework - consider changing use of _id field / move collidedWithId into args

public class BulletCollisionEvent extends AbstractEvent {
    public BulletCollisionEvent(int collidedWithId, String bulletType) {
        _id = collidedWithId;
        _type = "BulletCollisionEvent";
        _args = new HashMap<String, Object>();
        _args.put("bulletType", bulletType);
    }
}
