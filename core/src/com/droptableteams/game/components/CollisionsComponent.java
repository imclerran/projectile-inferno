package com.droptableteams.game.components;

import com.badlogic.gdx.utils.Array;
import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class CollisionsComponent extends AbstractComponent {

    private Array<Integer> _collisions;

    public CollisionsComponent(int id) {
        _id = id;
        _type = "CollisionsComponent";
        _collisions = new Array<Integer>();
    }

    public Array<Integer> getCollisions() {
        return _collisions;
    }

    public void addCollision(int collidedWithId) {
        _collisions.add(collidedWithId);
    }

    public void clearCollisions() {
        _collisions.clear();
    }
}
