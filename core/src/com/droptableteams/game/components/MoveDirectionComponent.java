package com.droptableteams.game.components;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class MoveDirectionComponent extends AbstractComponent {

    private Float _radians;

    public MoveDirectionComponent(int id, Float radians) {
        _id = id;
        _radians = radians;
        _type = "MoveDirectionComponent";
    }

    public Float getRadians() {
        return _radians;
    }

    /**
     * Set the direction of travel in radians.
     * @param radians the direction of travel. Set to null if not moving.
     */
    public void setRadians(Float radians) {
        _radians = radians;
    }
}
