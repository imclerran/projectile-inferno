package com.droptableteams.game.components;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class RelativePositionComponent extends AbstractComponent {

    private float _angle;
    private float _radius;
    private int _relativeEntityId;

    public RelativePositionComponent(int id, float angle, float radius, int relativeEntityId) {
        _id = id;
        _angle = angle;
        _radius = radius;
        _relativeEntityId = relativeEntityId;
        _type = "RelativePositionComponent";
    }

    public float getAngle() {
        return _angle;
    }

    public void setAngle(float angle) {
        _angle = angle;
    }

    public float getRadius() {
        return _radius;
    }

    public void setRadius(float radius) {
        _radius = radius;
    }

    public int getRelativeEntityId() {
        return _relativeEntityId;
    }

    public void setRelativeEntityId(int relativeEntityId) {
        _relativeEntityId = relativeEntityId;
    }
}
