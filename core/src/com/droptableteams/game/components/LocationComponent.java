package com.droptableteams.game.components;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class LocationComponent extends AbstractComponent {

    private float _x;
    private float _y;

    public LocationComponent(int id, float x, float y) {
        _id = id;
        _x = x;
        _y = y;
        _type = "LocationComponent";
    }

    public float getX() {
        return _x;
    }
    public void setX(float x) { _x = x; }

    public float getY() {
        return _y;
    }
    public void setY(float y) { _y = y; }
}
