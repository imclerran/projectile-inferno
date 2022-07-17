package com.droptableteams.game.components;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class VelocityComponent extends AbstractComponent {

    private float _baseSpeed;
    private float _modifiedSpeed;
    private float _dx;
    private float _dy;

    public VelocityComponent(int id, float baseSpeed) {
        _id = id;
        _baseSpeed = baseSpeed;
        _modifiedSpeed = baseSpeed;
        _type = "VelocityComponent";
    }

    public float getBaseSpeed() {
        return _baseSpeed;
    }

    public void setBaseSpeed(float baseSpeed) {
        _baseSpeed = baseSpeed;
    }

    @Deprecated
    public float getDx() {
        return _dx;
    }

    @Deprecated
    public void setDx(float dx) {
        _dx = dx;
    }

    @Deprecated
    public float getDy() {
        return _dy;
    }

    @Deprecated
    public void setDy(float dy) {
        _dy = dy;
    }

    public float getModifiedSpeed() {
        return _modifiedSpeed;
    }

    public void setModifiedSpeed(float modifiedSpeed) {
        _modifiedSpeed = modifiedSpeed;
    }
}
