package com.droptableteams.game.components;

import com.droptableteams.game.LibECS.interfaces.AbstractComponent;

public class HasBeenInboundsComponent extends AbstractComponent {

    private boolean _hasBeenInBounds;

    public HasBeenInboundsComponent(int id, boolean hasBeenInbounds) {
        _id = id;
        _hasBeenInBounds = hasBeenInbounds;
        _type = "HasBeenInboundsComponent";
    }

    public boolean getHasBeenInBounds() {
        return _hasBeenInBounds;
    }

    public void setHasBeenInBounds(boolean hasBeenInBounds) {
        _hasBeenInBounds = hasBeenInBounds;
    }
}
