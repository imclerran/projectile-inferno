package com.droptableteams.game.components;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class SpiralComponent extends AbstractComponent {

    private float _deltaRadius;
    private float _deltaTheta;

    public SpiralComponent(int id, float deltaRadius, float deltaTheta) {
        _id = id;
        _type = "SpiralComponent";
        _deltaRadius = deltaRadius;
        _deltaTheta = deltaTheta;
    }

    public float getDeltaRadius() {
        return _deltaRadius;
    }

    public void setDeltaRadius(float deltaRadius) {
        _deltaRadius = deltaRadius;
    }

    public float getDeltaTheta() {
        return _deltaTheta;
    }

    public void setDeltaTheta(float deltaTheta) {
        _deltaTheta = deltaTheta;
    }
}
